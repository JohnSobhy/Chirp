package com.john_halaka.auth.presentation.reset_password

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import chirp.feature.auth.presentation.generated.resources.Res
import chirp.feature.auth.presentation.generated.resources.error_reset_password_token_invalid
import chirp.feature.auth.presentation.generated.resources.error_same_password
import com.john_halaka.core.domain.auth.AuthService
import com.john_halaka.core.domain.util.DataError
import com.john_halaka.core.domain.util.onFailure
import com.john_halaka.core.domain.util.onSuccess
import com.john_halaka.core.domain.validation.PasswordValidator
import com.john_halaka.core.presentation.util.UiText
import com.john_halaka.core.presentation.util.toUiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class ResetPasswordViewModel(
    private val authService: AuthService,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val token = savedStateHandle.get<String>("token")
        ?: throw IllegalStateException("No password reset token")

    private val isPasswordValidFlow = snapshotFlow { state.value.passwordTextState.text.toString() }
        .map { password -> PasswordValidator.validate(password).isValidPassword }
        .distinctUntilChanged()
    private val eventChannel = Channel<ResetPasswordEvent>()
    val events = eventChannel.receiveAsFlow()

    private val _state = MutableStateFlow(ResetPasswordState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                observeValidationState()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = ResetPasswordState()
        )

    fun onAction(action: ResetPasswordAction) {
        when (action) {
            ResetPasswordAction.OnSubmitClick -> resetPassword()
            ResetPasswordAction.OnTogglePasswordVisibilityClick -> {
                _state.update { it.copy(
                    isPasswordVisible = !it.isPasswordVisible
                ) }
            }

        }
    }

    private fun observeValidationState() {
        isPasswordValidFlow.onEach { isPasswordValid ->
            _state.update { it.copy(
                canSubmit = isPasswordValid
            ) }
        }.launchIn(viewModelScope)
    }

    private fun resetPassword() {
        if(state.value.isLoading || !state.value.canSubmit) {
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(
                isLoading = true,
                isResetSuccessful = false
            ) }

            val newPassword = state.value.passwordTextState.text.toString()
            authService
                .resetPassword(
                    newPassword = newPassword,
                    token = token
                )
                .onSuccess {
                    _state.update { it.copy(
                        isLoading = false,
                        isResetSuccessful = true,
                        errorText = null
                    ) }
                }
                .onFailure { error ->
                    val errorText = when(error) {
                        DataError.Remote.UNAUTHORIZED -> UiText.Resource(Res.string.error_reset_password_token_invalid)
                        DataError.Remote.CONFLICT -> UiText.Resource(Res.string.error_same_password)
                        else -> error.toUiText()
                    }
                    _state.update { it.copy(
                        errorText = errorText,
                        isLoading = false,
                    ) }
                }
        }
    }

}