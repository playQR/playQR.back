package com.bandit.global.util;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import java.util.List;

public class KakaoMessageTemplateUtil {
    public static JSONObject inviteMessageTemplate(String buttonUrl) {
        JSONObject linkObj = new JSONObject();
        linkObj.put("web_url", "https://www.naver.com");
        linkObj.put("mobile_web_url", "https://www.naver.com");

        JSONObject contentObj = new JSONObject();
        contentObj.put("title", "test");
        contentObj.put("description", "테스트 중입니다");
        contentObj.put("image_url", "https://velog.velcdn.com/images/hann1233/post/bab13e2c-d511-4379-9ac7-e2a2561462ad/image.png");
        contentObj.put("image_width", 640);
        contentObj.put("image_height", 640);
        contentObj.put("link", linkObj);

        JSONObject buttonObj = new JSONObject();
        buttonObj.put("title", "링크로 이동");

        JSONObject buttonLinkObj = new JSONObject();
        buttonLinkObj.put("web_url", buttonUrl);
        buttonLinkObj.put("mobile_web_url", buttonUrl);
        buttonObj.put("link", buttonLinkObj);

        JSONArray buttonsArray = new JSONArray();
        buttonsArray.add(buttonObj);

        JSONObject templateObj = new JSONObject();
        templateObj.put("object_type", "feed");
        templateObj.put("buttons", buttonsArray);
        templateObj.put("content", contentObj);

        return templateObj;
    }

    public static JSONArray createReceiverUuidsArray(List<String> receiverUuidList) {
        JSONArray uuidArray = new JSONArray();
        uuidArray.addAll(receiverUuidList);
        return uuidArray;
    }
}
