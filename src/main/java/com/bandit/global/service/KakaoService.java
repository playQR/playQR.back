package com.bandit.global.service;


import com.bandit.global.dto.KakaoFriendDto;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class KakaoService extends HttpCallService {
    private static final String GET_FRIENDS_LIST_URL = "https://kapi.kakao.com/v1/api/talk/friends";
    public List<KakaoFriendDto> getFriendsList(String accessToken) {
        HttpHeaders header = new HttpHeaders();
        header.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>(header);

        ResponseEntity<String> response = httpRequest(GET_FRIENDS_LIST_URL, HttpMethod.GET, entity);
        List<KakaoFriendDto> list = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(Integer.parseInt(response.getBody()));
        JSONArray elements = (JSONArray) jsonObject.get("elements");

        for (Object element : elements) {

            KakaoFriendDto dto = new KakaoFriendDto();
            JSONObject obj = (JSONObject) element;
            log.info("obj = {}", obj.get("profile_nickname").toString());

            dto.setId(obj.get("id").toString());
            dto.setUuid(obj.get("uuid").toString());
            dto.setFavorite((boolean) obj.get("favorite"));
            dto.setProfile_nickname(obj.get("profile_nickname").toString());
            dto.setProfile_thumbnail_image(obj.get("profile_thumbnail_image").toString());

            list.add(dto);
        }

        return list;
    }
}
