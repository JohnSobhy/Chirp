package com.john_halaka.chat.presentation.create_chat

import androidx.compose.foundation.text.input.clearText
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.error_participant_not_found
import com.john_halaka.chat.domain.chat.ChatParticipantService
import com.john_halaka.chat.domain.chat.ChatService
import com.john_halaka.chat.presentation.mappers.toChatParticipantUi
import com.john_halaka.core.domain.util.DataError
import com.john_halaka.core.domain.util.onFailure
import com.john_halaka.core.domain.util.onSuccess
import com.john_halaka.core.presentation.util.UiText
import com.john_halaka.core.presentation.util.toUiText
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds


@OptIn(FlowPreview::class)
class CreateChatViewModel(
    private val chatParticipantService: ChatParticipantService,
    private val chatService: ChatService
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val eventChannel = Channel<CreateChatEvent>()
    val events = eventChannel.receiveAsFlow()


    private val _state = MutableStateFlow(CreateChatState())
    private val searchFlow = snapshotFlow { _state.value.queryTextState.text.toString() }
        .debounce(1.seconds)
        .onEach { query ->
            performSearch(query)
        }
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                searchFlow.launchIn(viewModelScope)
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = CreateChatState()
        )

    fun onAction(action: CreateChatAction) {
        when (action) {
            CreateChatAction.OnAddClick -> addParticipant()
            CreateChatAction.OnCreateChatClick -> createChat()
            CreateChatAction.OnDismissDialog -> Unit
        }
    }

    private fun createChat() {
        val userIds = state.value.selectedChatParticipants.map { it.id }
        if (userIds.isEmpty()) return
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isCreatingChat = true,
                    canAddParticipant = false
                )
            }
            chatService
                .createChat(
                    otherUserIds = userIds
                )
                .onSuccess { chat ->
                    _state.update {
                        it.copy(
                            isCreatingChat = false,
                            createChatError = null
                        )
                    }
                    eventChannel.send(CreateChatEvent.OnChatCreated(chat))
                }
                .onFailure { error ->
                    _state.update {
                        it.copy(
                            isCreatingChat = false,
                            canAddParticipant = it.currentSearchResult != null && !it.isSearching,
                            createChatError = error.toUiText()
                        )
                    }
                }
        }
    }

    private fun addParticipant() {
        state.value.currentSearchResult?.let { participant ->
            val isAlreadyPartOfChat =
                state.value.selectedChatParticipants.any { it.id == participant.id }
            if (!isAlreadyPartOfChat) {
                _state.update {
                    it.copy(
                        selectedChatParticipants = it.selectedChatParticipants + participant,
                        canAddParticipant = false,
                        currentSearchResult = null,
                    )
                }
                _state.value.queryTextState.clearText()
            }
        }
    }

    private fun performSearch(query: String) {
        if (query.isBlank()) {
            _state.update {
                it.copy(
                    currentSearchResult = null,
                    canAddParticipant = false,
                    searchError = null
                )
            }
            return
        }
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isSearching = true,
                    canAddParticipant = false
                )
            }
            chatParticipantService
                .searchParticipant(query)
                .onSuccess { participant ->
                    _state.update {
                        it.copy(
                            currentSearchResult = participant.toChatParticipantUi(),
                            isSearching = false,
                            canAddParticipant = true,
                            searchError = null
                        )
                    }
                }
                .onFailure { error ->
                    println("error: $error")
                    val errorMsg = when (error) {
                        DataError.Remote.NOT_FOUND -> UiText.Resource(Res.string.error_participant_not_found)
                        else -> error.toUiText()
                    }
                    _state.update {
                        it.copy(
                            currentSearchResult = null,
                            isSearching = false,
                            canAddParticipant = false,
                            searchError = errorMsg
                        )
                    }
                }
        }
    }
}