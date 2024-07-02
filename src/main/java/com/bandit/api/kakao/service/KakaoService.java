package com.bandit.api.kakao.service;


import com.bandit.api.kakao.dto.KakaoFriendDto;
import com.bandit.api.kakao.dto.KakaoMessageDto;
import com.bandit.api.kakao.dto.KakaoMessageRequest;
import com.bandit.global.service.HttpCallService;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class KakaoService extends HttpCallService {
    private static final String GET_FRIENDS_LIST_URL = "https://kapi.kakao.com/v1/api/talk/friends";
    private static final String POST_MESSAGE_TO_FRIEND_URL = "https://kapi.kakao.com/v1/api/talk/friends/message/default/send";
    public List<KakaoFriendDto> getFriendsList(String accessToken) {
        HttpHeaders header = new HttpHeaders();
        header.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>(header);

        ResponseEntity<String> response = httpRequest(GET_FRIENDS_LIST_URL, HttpMethod.GET, entity);
        List<KakaoFriendDto> list = new ArrayList<>();

        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(response.getBody());
            JSONArray elements = (JSONArray) jsonObject.get("elements");

            for (Object element : elements) {
                KakaoFriendDto kakaoFriendDto = builderKakaoFriendDto(element);
                list.add(kakaoFriendDto);
            }

        } catch (ParseException e) {
            log.error("JSON parsing error", e);
        }

        return list;
    }
    private KakaoFriendDto builderKakaoFriendDto(Object element) {
        JSONObject obj = (JSONObject) element;
        return KakaoFriendDto.builder()
                .id(obj.get("id").toString())
                .uuid(obj.get("uuid").toString())
                .favorite((boolean) obj.get("favorite"))
                .profile_nickname(obj.get("profile_nickname").toString())
                .profile_thumbnail_image(obj.get("profile_thumbnail_image").toString())
                .build();
    }
    public String sendMessage(String accessToken,
                              KakaoMessageRequest request) {
        KakaoMessageDto msgDto = request.getMessageDto();
        JSONObject linkObj = new JSONObject();
        JSONObject templateObj = new JSONObject();

        mapMessageToTemplate(linkObj, templateObj, msgDto);

        HttpHeaders header = new HttpHeaders();
        header.set("Content-Type", APP_TYPE_URL_ENCODED);
        header.set("Authorization", "Bearer " + accessToken);

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("receiver_uuids", request.getReceiverUuidList().toString());
        parameters.add("template_object", templateObj.toString());

        HttpEntity<?> messageRequestEntity = httpClientEntity(header, parameters);

        ResponseEntity<String> response = httpRequest(POST_MESSAGE_TO_FRIEND_URL, HttpMethod.POST, messageRequestEntity);
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) parser.parse(response.getBody());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return jsonObject.get("result_code").toString();
    }

    private void mapMessageToTemplate(JSONObject linkObj, JSONObject templateObj, KakaoMessageDto msgDto) {
        linkObj.put("web_url", msgDto.getWebUrl());
        linkObj.put("mobile_web_url", msgDto.getMobileUrl());

        templateObj.put("object_type", msgDto.getObjType());
        templateObj.put("text", msgDto.getText());
        templateObj.put("link", linkObj);
        templateObj.put("button_title", msgDto.getBtnTitle());
    }
}
