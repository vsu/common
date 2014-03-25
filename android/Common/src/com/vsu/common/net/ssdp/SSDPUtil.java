package com.vsu.common.net.ssdp;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Victor Su
 */
public class SSDPUtil {
    public static String getMethod(String data) {
        String method = "";

        int pos = data.indexOf(SSDPConstants.NEWLINE);
        if (pos != -1) {
            method =  data.substring(0, pos);
        }

        return method;
    }

    public static Map<String, String> parseHeaders(String data) {
        Map<String, String> headers = new HashMap<String, String>();

        String[] lines = data.split(SSDPConstants.NEWLINE);

        for (String str : lines) {
            int pos = str.indexOf(":");
            if (pos != -1) {
                String key = str.substring(0, pos).trim().toUpperCase(Locale.US);
                String value = str.substring(pos + 1).trim();
                headers.put(key, value);
            }
        }

        return headers;
    }
}
