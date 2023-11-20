package com.skuplate.server.member.mapper;

import com.skuplate.server.member.dto.MemberDto;
import com.skuplate.server.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MemberMapper {
    MemberDto.GetResponseDto memberToGetResponseDto(Member member);
}
