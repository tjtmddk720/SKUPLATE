package com.skuplate.server.member.service;

import com.skuplate.server.exception.BusinessLogicException;
import com.skuplate.server.exception.ExceptionCode;
import com.skuplate.server.member.entity.Member;
import com.skuplate.server.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Member createMember(Member member) {
        if (!existsEmail(member.getEmail())) {

            Member savedMember = memberRepository.save(member);
            return savedMember;
        }

        return null;
    }

    public Member getMember(Long memberId){

        return findVerifiedMember(memberId);
    }
    private boolean existsEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        return member.isPresent();
    }
    public Member findVerifiedMember(long memberId) {
        Optional<Member> optionalMember =
                memberRepository.findById(memberId);
        return optionalMember.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

    }
}
