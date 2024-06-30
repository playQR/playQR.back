package com.bandit.security.oauth.service;

import com.bandit.domain.member.entity.Member;
import com.bandit.domain.member.entity.Role;
import com.bandit.domain.member.repository.MemberRepository;
import com.bandit.global.util.RandomNameUtil;
import com.bandit.global.util.RandomNameUtil.NameType;
import com.bandit.presentation.payload.code.ErrorStatus;
import com.bandit.presentation.payload.exception.security.OAuth2Exception;
import com.bandit.security.oauth.dto.CustomUserDetails;
import com.bandit.security.oauth.dto.OAuth2UserInfo;
import com.bandit.security.oauth.factory.OAuth2UserInfoFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Optional;

import static com.bandit.global.util.RandomNameUtil.NameType.*;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final MemberRepository memberRepository;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService oAuth2UserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);

        return processOAuth2User(oAuth2User, userRequest.getAccessToken());
    }
    protected OAuth2User processOAuth2User(OAuth2User oAuth2User, OAuth2AccessToken oAuth2AccessToken) {
        //OAuth2 로그인 플랫폼 구분 과정 생략, 카카오에서 필요한 정보 가져오기(이메일)
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(oAuth2User.getAttributes());

        if (!StringUtils.hasText(oAuth2UserInfo.getEmail())) {
            throw new OAuth2Exception(ErrorStatus.AUTH_OAUTH2_EMAIL_NOT_FOUND_FROM_PROVIDER);
        }
        Optional<Member> byEmail = memberRepository.findByKakaoEmail(oAuth2UserInfo.getEmail());
        Member member = byEmail.orElseGet(() -> registerMember(oAuth2UserInfo));

        return CustomUserDetails.create(member, oAuth2AccessToken);
    }

    private Member registerMember(OAuth2UserInfo oAuth2UserInfo) {
        Member register = Member.builder()
                .kakaoEmail(oAuth2UserInfo.getEmail())
                .username(generateRandomName(USERNAME))
                .nickname(generateRandomName(NICKNAME))
                .name(generateRandomName(NAME))
                .role(Role.USER)           //회원가입시에만 guest로 두고 이후 사용에는 user로 돌린다
                .build();

        return memberRepository.save(register);
    }

    private String generateRandomName(NameType nameType) {
        String randomName;
        switch (nameType) {
            case NAME -> {
                do {
                    randomName = RandomNameUtil.generateAuto(NAME);

                } while (memberRepository.existsByName(randomName));
            }
            case NICKNAME -> {
                do {
                    randomName = RandomNameUtil.generateAuto(NameType.NICKNAME);
                } while (memberRepository.existsByNickname(randomName));
            }
            case USERNAME -> {
                do {
                    randomName = RandomNameUtil.generateAuto(NameType.USERNAME);
                }
                while (memberRepository.existsByUsername(randomName));
            }
            default -> {
                throw new OAuth2Exception(ErrorStatus.MEMBER_NAME_TYPE_IS_INVALID);
            }
        }
        return randomName;
    }

}
