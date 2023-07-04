package com.ant.hurry.boundedContext.review.controller;

import com.ant.hurry.boundedContext.member.service.MemberService;
import com.ant.hurry.boundedContext.review.dto.ReviewRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
@AutoConfigureMockMvc
class ReviewControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    MemberService memberService;

    @Test
    @DisplayName("후기 작성페이지 접근 시 거래 상태가 COMPLETE가 아닌 상황")
    @WithMockUser(username = "user1")
    void review_tradeStatus_not_complete() throws Exception {

        //when
        ResultActions resultActions = mockMvc.perform(get("/review/create/1"))
                .andDo(print());


        //then
        resultActions
                .andExpect(handler().handlerType(ReviewController.class))
                .andExpect(handler().methodName("showCreate"))
                .andExpect(model().attributeDoesNotExist("opponentNickname"))
                .andExpect(view().name("common/js"))
                .andExpect(status().is4xxClientError());
    }


    @Test
    @DisplayName("후기 작성페이지 접근 시 내가 Requester인 상황")
    @WithMockUser("user3")
    void review_member_requester() throws Exception {

        //when
        ResultActions resultActions = mockMvc.perform(get("/review/create/4"))
                .andDo(print());

        //then
        resultActions
                .andExpect(handler().handlerType(ReviewController.class))
                .andExpect(handler().methodName("showCreate"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("reviewRequest"))
                .andExpect(model().attributeExists("tradeStatusId"))
                .andExpect(view().name("review/create"));

    }


    @Test
    @DisplayName("후기 작성페이지 접근 시 내가 Helper인 상황")
    @WithMockUser(username = "user4")
    void review_member_helper() throws Exception {

        //when
        ResultActions resultActions = mockMvc.perform(get("/review/create/4"))
                .andDo(print());

        //then
        resultActions
                .andExpect(handler().handlerType(ReviewController.class))
                .andExpect(handler().methodName("showCreate"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("reviewRequest"))
                .andExpect(model().attributeExists("tradeStatusId"))
                .andExpect(view().name("review/create"));

    }


    @Test
    @DisplayName("후기 또는 별점을 작성 안하고 제출 시 bindingResult에 담긴 메시지로 historyBack 한다.")
    @WithMockUser("user3")
    void review_content_not_exists() throws Exception {

        //when
        String content = "";
        String rating = "";

        ResultActions resultActions = mockMvc.perform(post("/review/create/4")
                        .param("content", content)
                        .param("rating", rating)
                        .with(csrf()))
                        .andDo(print());

        //then
        resultActions
                .andExpect(handler().handlerType(ReviewController.class))
                .andExpect(handler().methodName("create"))
                .andExpect(status().is4xxClientError())
                .andExpect(view().name("common/js"));
    }

    @Test
    @DisplayName("정상적인 후기 작성")
    @WithMockUser("user3")
    void review_success_write() throws Exception {

        //given
        ReviewRequest reviewRequest = new ReviewRequest("후기테스트입니다.", 5.0, "User4");
        Long tradeStatusId = 4L;

        //when
        ResultActions resultActions = mockMvc.perform(post("/review/create/{tradeStatusId}", tradeStatusId)
                        .with(csrf())
                        .param("content", reviewRequest.getContent())
                        .param("rating", String.valueOf(reviewRequest.getRating())))
                .andDo(print());

        //then
        resultActions
                .andExpect(handler().handlerType(ReviewController.class))
                .andExpect(handler().methodName("create"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("유효한 멤버로 후기 목록 페이지 접근")
    @WithMockUser("user1")
    void review_list_member_exists() throws Exception {

        //when
        ResultActions resultActions = mockMvc.perform(get("/review/list/1"))
                .andDo(print());

        //then
        resultActions
                .andExpect(handler().handlerType(ReviewController.class))
                .andExpect(handler().methodName("list"))
                .andExpect(model().attributeExists("reviewData"))
                .andExpect(view().name("review/list"))
                .andExpect(status().is2xxSuccessful());

    }

}