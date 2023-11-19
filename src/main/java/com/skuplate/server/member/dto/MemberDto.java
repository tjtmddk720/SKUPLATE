package com.skuplate.server.member.dto;

import com.skuplate.server.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MemberDto {
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class GetResponseDto {
        private String name;
        private String email;
        private Member.accountProvider provider;
    }
}
