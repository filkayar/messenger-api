package com.example.messenger.api.components

import com.example.messenger.api.helpers.objects.ConversationListVO
import com.example.messenger.api.helpers.objects.ConversationVO
import com.example.messenger.api.helpers.objects.MessageVO
import com.example.messenger.api.models.Conversation
import com.example.messenger.api.services.ConversationServiceImpl
import org.springframework.stereotype.Component

@Component
class ConversationAssembler(val conversationService: ConversationServiceImpl, val messageAssembler: MessageAssembler) {
    fun toConversationVO(conversation: Conversation, userId: Long): ConversationVO {
        val conversationMessages: ArrayList<MessageVO> = ArrayList()
        conversation.messages?.mapTo(conversationMessages) {
            messageAssembler.toMessageVO(it)
        }
        return ConversationVO(conversation.id, conversationService.nameSecondParty(conversation, userId), conversationMessages)
    }

    fun toConversationListVO(conversations: ArrayList<Conversation>, userId: Long): ConversationListVO {
        val conversationVOList = conversations.map {
             toConversationVO(it, userId)
        }
        return ConversationListVO(conversationVOList)
    }
}