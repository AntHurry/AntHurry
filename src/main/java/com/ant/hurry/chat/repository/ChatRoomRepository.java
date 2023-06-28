package com.ant.hurry.chat.repository;

import com.ant.hurry.boundedContext.member.entity.Member;
import com.ant.hurry.chat.entity.ChatRoom;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRoomRepository extends MongoRepository<ChatRoom, String>, CustomChatRoomRepository {
    List<ChatRoom> findByMembersContaining(Member member);
}
