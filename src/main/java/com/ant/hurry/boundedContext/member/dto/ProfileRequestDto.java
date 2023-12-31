package com.ant.hurry.boundedContext.member.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.Getter;

@Data
public class ProfileRequestDto {

    @Pattern(regexp = "^[a-zA-Z0-9가-힣-_]{4,12}$", message = "닉네임은 특수문자를 제외한 4~12자리여야 합니다.")
    private String nickname;

    private String imagePath;
}
