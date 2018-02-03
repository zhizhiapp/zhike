package com.example.administrator.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.Collator;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class DescendingEncryption {
    //降序
    public static String Descending(HashMap<String, Object> map, String[] arr) {
    /*设置语言环境*/
        Comparator<Object> com = Collator.getInstance(java.util.Locale.CHINA);
        Arrays.sort(arr, com);
        String hhh = "";
        for (String item : arr) {
            hhh += map.get(item);
        }
        return md5(hhh);
    }

    //md5加密
    public static String md5(String content) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(content.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("NoSuchAlgorithmException", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UnsupportedEncodingException", e);
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) {
                hex.append("0");
            }
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

    //获取时间戳
    public int getTime() {
        long time = System.currentTimeMillis() / 1000;//获取系统时间的10位的时间戳
        return (int) time;
    }

    //获取时间戳
    public static int gettime() {
        long time = System.currentTimeMillis() / 1000;//获取系统时间的10位的时间戳
        return (int) time;
    }


    private static final String API_KEY = "3bdb25d93535b66fd13c16379d26f46fgzzzwh";
    private static final String TIME_STAMP = "timeStamp";
    private static final String KEY_API = "apiKey";
    private static final String API_SIGN = "apiSign";

    public static void init(Map<String, Object> parameterMap) {
        HashMap<String, Object> map = new HashMap<>();
        int size;
        if (parameterMap != null && !parameterMap.isEmpty()) {
            size = parameterMap.size() + 2;
            String[] strings = new String[size];
            map.put(KEY_API, API_KEY);
            map.put(TIME_STAMP, (int) (System.currentTimeMillis() / 1000));
            strings[0] = TIME_STAMP;
            strings[1] = KEY_API;
            int index = 1;
            for (String key : parameterMap.keySet()) {
                index++;
                Object value = parameterMap.get(key);
                map.put(key, value);
                strings[index] = key;
            }
            String md5 = Descending(map, strings);
            parameterMap.put(TIME_STAMP, (int) (System.currentTimeMillis() / 1000));
            parameterMap.put(API_SIGN, md5);
        }
    }


    public static Map<String, Object> getDefault() {
        Map<String, Object> map = new HashMap<>();
        map.put(KEY_API, API_KEY);
        map.put(TIME_STAMP, (int) (System.currentTimeMillis() / 1000));
        String[] strings = new String[]{TIME_STAMP, KEY_API};
        String md5 = new DescendingEncryption().Descending((HashMap<String, Object>) map, strings);//鍔犲瘑
        Map<String, Object> defaultMap = new HashMap<>();
        defaultMap.put(TIME_STAMP, (int) (System.currentTimeMillis() / 1000));
        defaultMap.put(API_SIGN, md5);
        return defaultMap;
    }




}
