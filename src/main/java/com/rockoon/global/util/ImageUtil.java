package com.rockoon.global.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ImageUtil {
    private static String SERVER_URI;

    @Value("${app.server.uri}")
    public void setServerUri(String SERVER_URI) {
        this.SERVER_URI = SERVER_URI + "/";
    }

    public static String appendUri(String s3URI) {
        StringBuffer stringBuffer = new StringBuffer(SERVER_URI);
        return stringBuffer.append(s3URI).toString();
    }

    public static String removePrefix(String url) {
        String subPrefixUrl = null;
        if (url != null) {
            if (url.startsWith(SERVER_URI)) {
                subPrefixUrl = url.substring(SERVER_URI.length());
            }
        }
        return subPrefixUrl;
    }

    public static boolean validateRemoveImgInS3(String previous, String current) {
        if (previous == null) {
            return false;
        }
        return !previous.equals(current);
    }
}
