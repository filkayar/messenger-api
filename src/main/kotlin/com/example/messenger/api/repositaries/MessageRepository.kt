package com.example.messenger.api.repositaries

import com.example.messenger.api.models.Message
import org.springframework.data.repository.CrudRepository

interface MessageRepository : CrudRepository<Message, Long> {
    fun findByConversationId(conversationId: Long): List<Message>
}