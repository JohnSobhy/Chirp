package com.john_halaka.chat.presentation.chat_list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.john_halaka.designsystem.theme.ChirpTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ChatListRoot(
    viewModel: ChatListViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ChatListScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun ChatListScreen(
    state: ChatListState,
    onAction: (ChatListAction) -> Unit,
) {

}

@Preview
@Composable
private fun Preview() {
    ChirpTheme {
        ChatListScreen(
            state = ChatListState(),
            onAction = {}
        )
    }
}