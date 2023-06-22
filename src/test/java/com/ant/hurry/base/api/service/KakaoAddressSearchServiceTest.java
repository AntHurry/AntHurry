package com.ant.hurry.base.api.service;

import com.ant.hurry.base.api.dto.KakaoApiResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@ActiveProfiles("test")
public class KakaoAddressSearchServiceTest {

    @Autowired
    private KakaoAddressSearchService kakaoAddressSearchService;

    @Test
    @DisplayName("주소를 넣어서 카카오 API 호출 성공")
    public void successKakaoApi() throws Exception {
        Mono<KakaoApiResponseDTO> result = kakaoAddressSearchService.requestAddressSearch("서울 관악구 신림동 1662-3");
        assertNotNull(result.block().getDocumentDTOList().get(0).getX());
        assertNotNull(result.block().getDocumentDTOList().get(0).getY());

    }

    @Test
    @DisplayName("주소를 안넣을경우 카카오 API 호출 실패")
    public void failKakaoApi() throws Exception {
        Mono<KakaoApiResponseDTO> result = kakaoAddressSearchService.requestAddressSearch("");
        assertNull(result);

    }

}