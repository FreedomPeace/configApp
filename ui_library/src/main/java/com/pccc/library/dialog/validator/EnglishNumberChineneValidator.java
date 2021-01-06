package com.pccc.library.dialog.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * created by liliangjun
 * 2020/10/20
 */
class EnglishNumberChineneValidator implements IValidator {
    @Override
    public boolean validate(String value) throws ValidationError {
        int j = 0;
        int k = 0;
        int q = 0;

        for (int idx = 0; idx < value.length(); idx++) {
            if (Character.isDigit(value.charAt(idx))) {
                j++;
            }
        }
        for (int idx = 0; idx < value.length(); idx++) {
            char c = value.charAt(idx);
            if ('a' <= c && c <= 'z' || 'A' <= c && c <= 'Z') {
                k++;
            }
        }
        Pattern pattern = Pattern.compile("[\\u4e00-\\u9fa5]");
        Matcher m = pattern.matcher(value);
        while (m.find()) {
            q++;
        }
        if (k + j + q == value.length()) {
            return true;
        }

        throw new ValidationError("输入只能是英文、数字和中文");
    }
}
