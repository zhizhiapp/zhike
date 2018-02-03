package com.example.administrator.utils;

public class PasswordFormat {


    public static boolean pwdMatch(String str) {
        boolean isDigit = false;
        boolean isLetter = false;
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {//是否数字
                isDigit = true;
            }
            if (Character.isLetter(str.charAt(i))) {//是否字母
                isLetter = true;
            }
        }
        String regex = "^[a-zA-Z0-9]+$";
        boolean isRight = isDigit && isLetter && str.matches(regex);
        return isRight;
    }
}
