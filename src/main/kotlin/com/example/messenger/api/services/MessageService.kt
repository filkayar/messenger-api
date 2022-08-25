package com.example.messenger.api.services

import com.example.messenger.api.models.Message
import com.example.messenger.api.models.User

interface MessageService {
    fun sendMessage(sender: User, recipientId: Long, messageText: String): Message
}