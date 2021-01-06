package com.pccc.library.dialog.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * created by liliangjun
 * 2020/11/2
 */
class TimeValidator implements IValidator {
    private int maxLen;

    public TimeValidator(int maxLen) {
        this.maxLen = maxLen;
    }

    @Override
    public boolean validate(String value) throws ValidationError {
        JudgeMethods methods = new JudgeMethods();
        if (methods.allowMaxLenthOfString(value, maxLen) && !methods.isExistUp(value) && isExticEnglishOrChinese(methods, value)) {
            return true;
        } else {
            throw new ValidationError("营业时间输入错误");
        }
    }

    public boolean isExticEnglishOrChinese(JudgeMethods methods, String str) {
        char[] charArray = str.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            if (isChinese(charArray[i]) || isEnglish(charArray[i])) {
                return false;
            }
        }
        return true;
    }

    private boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        return ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS;
    }

    private static boolean isEnglish(char c) {
        Pattern pattern = Pattern.compile("^[A-Za-z]+$");
        Matcher m = pattern.matcher(String.valueOf(c));
        return m.matches();
    }
}
