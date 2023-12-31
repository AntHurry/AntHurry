package com.ant.hurry.boundedContext.notification.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
@AutoConfigureMockMvc
class NotificationControllerTest {

    @Autowired
    MockMvc mockMvc;


    @Test
    @DisplayName("알림 목록 페이지 불러오기")
    @WithUserDetails("user1")
    void showNotificationList() throws Exception {

        //when
        ResultActions resultActions = mockMvc.perform(
                get("/notification/list")
        ).andDo(print());


        //then
        resultActions
                .andExpect(handler().handlerType(NotificationController.class))
                .andExpect(handler().methodName("list"))
                .andExpect(status().isOk())
                .andExpect(view().name("notification/list"));

    }

    @Test
    @DisplayName("알림 삭제 하기")
    @WithUserDetails("user1")
    void notification_delete() throws Exception {

        //given
        Long id = 1L;

        //when
        ResultActions resultActions = mockMvc.perform(
                delete("/notification/delete/{id}", id)
                        .with(csrf())
        ).andDo(print());


        //then
        resultActions
                .andExpect(handler().handlerType(NotificationController.class))
                .andExpect(handler().methodName("delete"))
                .andExpect(status().is3xxRedirection());

    }

}