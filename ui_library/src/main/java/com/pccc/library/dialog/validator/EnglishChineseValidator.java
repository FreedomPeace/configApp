package com.pccc.library.dialog.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * created by liliangjun
 * 2020/10/20
 */
class EnglishChineseValidator implements IValidator {
    @Override
    public boolean validate(String value) throws ValidationError {
        int i = 0;
        int k = 0;
        int length = value.length();
        Pattern pattern = Pattern.compile("[\\u4e00-\\u9fa5]");
        Matcher m = pattern.matcher(value);
        while (m.find()) {
            i++;
        }
        for (int idx = 0; idx < length; idx++) {
            char c = value.charAt(idx);
            int tmp = (int) c;
            if ((tmp >= 'a' && tmp <= 'z') || (tmp >= 'A' && tmp <= 'Z')) {
                k++;
            }
        }
        if (length == k + i) {
            return true;
        }
        throw new ValidationError("只可输入中英文");
    }
}
