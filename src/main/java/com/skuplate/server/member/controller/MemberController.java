package com.skuplate.server.member.controller;

import com.skuplate.server.member.entity.Member;
import com.skuplate.server.member.mapper.MemberMapper;
import com.skuplate.server.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/members")
@Validated
@RequiredArgsConstructor
public class MemberController {
    public final static String MEMBER_DEFAULT_URL = "/members";
    public final MemberService memberService;
    public final MemberMapper memberMapper;

    @GetMapping("/{member-id}")
    public ResponseEntity getMember(@Positive @PathVariable("member-id") Long memberId){

        Member member = memberService.getMember(memberId);

        return new ResponseEntity<>(memberMapper.memberToGetResponseDto(member), HttpStatus.OK);
    }
}
