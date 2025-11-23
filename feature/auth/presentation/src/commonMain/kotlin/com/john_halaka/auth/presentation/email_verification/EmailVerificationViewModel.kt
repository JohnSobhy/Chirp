package com.john_halaka.auth.presentation.email_verification

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.john_halaka.core.domain.auth.AuthService
import com.john_halaka.core.domain.util.onFailure
import com.john_halaka.core.domain.util.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EmailVerificationViewModel(
    private val authService: AuthService,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var hasLoadedInitialData = false
    private val token = savedStateHandle.get<String>("token")

    private val _state = MutableStateFlow(EmailVerificationState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                verifyEmail()        //this viewModel is only initialized when verifying email from redirected link so we verify on init
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = EmailVerificationState()
        )

    //NO_OP Actions are purely Navigation, so handled in events
    fun onAction(action: EmailVerificationAction) = Unit


    private fun verifyEmail() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isVerifying = true
                )
            }
            authService
                .verifyEmail(token ?: "Invalid Token")
                .onSuccess {
                    _state.update {
                        it.copy(
                            isVerifying = false,
                            isVerified = true
                        )
                    }
                }
                .onFailure { _ ->
                    _state.update {
                        it.copy(
                            isVerifying = false,
                            isVerified = false
                        )
                    }
                }
        }
    }
}