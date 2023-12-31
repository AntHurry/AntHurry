package com.ant.hurry.boundedContext.tradeStatus.event;

import com.ant.hurry.boundedContext.board.service.BoardService;
import com.ant.hurry.chat.event.EventAfterDeletedChatRoom;
import com.ant.hurry.chat.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional
public class TradeStatusEventListener {

    private final ChatRoomService chatRoomService;
    private final BoardService boardService;

    @EventListener
    public void listenEventDeledtedTradeStatus(EventAfterDeletedTradeStatus event){
        chatRoomService.whenAfterDeletedTradeStatus(event.getTradeStatusList());
    }

    @EventListener
    public void listenEventAfterUpdateStatus(EventAfterUpdateStatus event) {
        boardService.whenAfterUpdateStatus(event.getTradeStatus());
    }
}
