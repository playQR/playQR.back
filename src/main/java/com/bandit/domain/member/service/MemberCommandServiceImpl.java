package com.bandit.domain.member.service;

import com.bandit.domain.member.dto.MemberRequest.MemberModifyDto;
import com.bandit.domain.member.dto.MemberRequest.MemberRegisterDto;
import com.bandit.domain.member.entity.Member;
import com.bandit.domain.member.entity.Role;
import com.bandit.domain.member.repository.MemberRepository;
import com.bandit.global.service.AwsS3Service;
import com.bandit.global.util.ImageUtil;
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

    @Override
    public void removeMember(Member member) {
        //TODO image remove in s3
        memberRepository.deleteWithRelations(member);
    }
}
