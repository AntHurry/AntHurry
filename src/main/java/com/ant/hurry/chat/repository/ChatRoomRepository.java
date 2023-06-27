package com.ant.hurry.chat.repository;

import com.ant.hurry.chat.entity.ChatRoom;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends MongoRepository<ChatRoom, String>, CustomChatRoomRepository {
}
