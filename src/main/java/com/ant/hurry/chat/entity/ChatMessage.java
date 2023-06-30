package com.ant.hurry.chat.entity;

import com.ant.hurry.boundedContext.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Document(collection = "chat_message")
public class ChatMessage extends BaseMessage {

    private String content;

    private Member sender;

    private LocalDateTime deletedAt;

    private LocalDateTime readAt;

    public void markAsRead() {
        readAt = LocalDateTime.now();
    }

    public boolean isNotRead() {
        return readAt == null;
    }
}
