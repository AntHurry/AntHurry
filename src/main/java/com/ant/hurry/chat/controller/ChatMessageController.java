package com.ant.hurry.chat.controller;

import com.ant.hurry.base.rq.Rq;
import com.ant.hurry.base.rsData.RsData;
import com.ant.hurry.boundedContext.member.entity.Member;
import com.ant.hurry.chat.dto.ChatMessageDto;
import com.ant.hurry.chat.entity.ChatFileMessage;
import com.ant.hurry.chat.entity.ChatMessage;
import com.ant.hurry.chat.entity.ChatRoom;
import com.ant.hurry.chat.service.ChatMessageService;
import com.ant.hurry.chat.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatMessageController {

    private final ChatMessageService chatMessageService;
    private final ChatRoomService chatRoomService;
    private final SimpMessagingTemplate messagingTemplate;
    private final Rq rq;

    @MessageMapping("/chat/message")
    public void sendMessage(@Payload ChatMessageDto dto) {
        chatMessageService.send(dto);
        messagingTemplate.convertAndSend("/sub/chat/room/%s".formatted(dto.getRoomId()), dto);
    }

    public void sendExitMessage(String roomId) {
        ChatMessageDto dto = new ChatMessageDto();
        dto.setWriter("admin");
        dto.setRoomId(roomId);
        dto.setMessage("[알림]\n" + rq.getMember().getNickname() + " 님이 채팅방을 나가셨습니다.");

        ChatMessage noticeMessage = ChatMessage.builder()
                .id(UUID.randomUUID().toString())
                .roomId(dto.getRoomId())
                .writer(dto.getWriter())
                .message(dto.getMessage())
                .createdAt(LocalDateTime.now())
                .build();
        chatMessageService.save(noticeMessage);

        messagingTemplate.convertAndSend("/sub/chat/room/%s".formatted(roomId), dto);
    }

    @PostMapping("/file")
    public void sendFile(@RequestParam String roomId, @Payload MultipartFile file) throws IOException {
        ChatRoom chatRoom = chatRoomService.findById(roomId).getData();
        RsData<ChatFileMessage> rs = chatMessageService.sendFile(file, rq.getMember(), chatRoom);
        ChatFileMessage message = rs.getData();

        sendFileMessage(roomId, message);
    }

    @MessageMapping("/chat/file")
    public void sendFileMessage(String roomId, ChatFileMessage message) {
        messagingTemplate.convertAndSend("/sub/chat/room/%s".formatted(roomId), message);
    }

    @GetMapping("/download/{messageId}")
    public ResponseEntity<StreamingResponseBody> downloadFile(@PathVariable("messageId") String messageId) {
        RsData<ChatFileMessage> findRs = chatMessageService.findFileMessageById(messageId);

        if (findRs.getData() == null) {
            return ResponseEntity.notFound().build();
        }

        ChatFileMessage message = findRs.getData();
        return chatMessageService.downloadFile(message);
    }

}
