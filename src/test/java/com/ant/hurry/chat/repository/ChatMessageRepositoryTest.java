package com.ant.hurry.chat.repository;

import com.ant.hurry.boundedContext.member.entity.Member;
import com.ant.hurry.boundedContext.tradeStatus.entity.TradeStatus;
import com.ant.hurry.chat.entity.ChatMessage;
import com.ant.hurry.chat.entity.ChatRoom;
import com.ant.hurry.chat.service.ChatRoomService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ChatMessageRepositoryTest {

    @Autowired
    private ChatRoomService chatRoomService;
    @Autowired
    ChatMessageRepository chatMessageRepository;
    @Autowired
    ChatRoomRepository chatRoomRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    @AfterAll
    void refresh() {
        chatRoomRepository.deleteAll();
        chatMessageRepository.deleteAll();
    }

    @Test
    @DisplayName("repository를 통해 채팅 메시지를 생성하고 저장합니다.")
    void chatMessage_saveChatRoomByRepository() {
        Member member1 = Member.builder().build();
        Member member2 = Member.builder().build();
        ChatRoom chatRoom = chatRoomService
                .create(TradeStatus.builder().requester(member1).helper(member2).build()).getData();
        ChatMessage chatMessage = ChatMessage.builder().roomId(chatRoom).build();
        chatMessageRepository.save(chatMessage);

        assertThat(chatMessageRepository.findAll()).hasSize(1);
    }

    @Test
    @DisplayName("MongoTemplate을 통해 채팅 메시지를 생성하고 저장합니다.")
    void chatMessage_saveByMongoTemplate() {
        Member member1 = Member.builder().build();
        Member member2 = Member.builder().build();
        ChatRoom chatRoom = chatRoomService
                .create(TradeStatus.builder().requester(member1).helper(member2).build()).getData();
        ChatMessage chatMessage = ChatMessage.builder().roomId(chatRoom).build();
        mongoTemplate.save(chatMessage);

        assertThat(mongoTemplate.findAll(ChatMessage.class)).hasSize(1);
    }

    @Test
    @DisplayName("채팅 메시지를 저장한 후 조회하고, 삭제합니다.")
    void chatMessage_save_find_delete() {
        Member member1 = Member.builder().build();
        Member member2 = Member.builder().build();
        ChatRoom chatRoom = chatRoomService
                .create(TradeStatus.builder().requester(member1).helper(member2).build()).getData();
        ChatMessage chatMessage = ChatMessage.builder().roomId(chatRoom).build();
        ChatMessage savedChatMessage = chatMessageRepository.save(chatMessage);

        assertThat(chatMessageRepository.findById(chatMessage.getId())).isNotNull();

        chatMessageRepository.delete(savedChatMessage);
        assertThat(chatMessageRepository.findAll()).isEmpty();
    }

}
