package com.rockoon.domain.member.service;

import com.rockoon.domain.member.dto.MemberRequest.MemberModifyDto;
import com.rockoon.domain.member.dto.MemberRequest.MemberRegisterDto;
import com.rockoon.domain.member.entity.Member;
import com.rockoon.domain.member.entity.Role;
import com.rockoon.domain.member.repository.MemberRepository;
import com.rockoon.global.service.AwsS3Service;
import com.rockoon.global.util.ImageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberCommandServiceImpl implements MemberCommandService {
    private final MemberRepository memberRepository;
    private final AwsS3Service awsS3Service;
    @Override
    public Long registerMember(MemberRegisterDto memberRequest) {
        Member member = Member.of(memberRequest);
        member.setUsername(String.valueOf(UUID.randomUUID()));      //TODO USERNAME UTIL
        member.convertRole(Role.USER);
        return memberRepository.save(member).getId();
    }

    @Override
    public Long modifyMemberInfo(Member member, MemberModifyDto memberRequest) {
        if (ImageUtil.validateRemoveImgInS3(member.getProfileImg(), memberRequest.getProfileImg())) {
            awsS3Service.deleteFile(member.getProfileImg());
        }
        member.modifyInfo(memberRequest);
        return member.getId();
    }
}
