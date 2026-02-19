package com.john_halaka.chat.presentation.di

import com.john_halaka.chat.presentation.chat_list.ChatListViewModel
import com.john_halaka.chat.presentation.chat_list_detail.ChatListDetailViewModel
import com.john_halaka.chat.presentation.create_chat.CreateChatViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val chatPresentationModule = module {
    viewModelOf(::ChatListViewModel)
    viewModelOf(::ChatListDetailViewModel)
    viewModelOf(::CreateChatViewModel)
}