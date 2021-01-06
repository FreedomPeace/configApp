package com.pccc.library.dialog.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * created by liliangjun
 * 2020/10/19
 */
public class FloatValidator implements IValidator {
    private int maxLen = 3;

    FloatValidator(int maxLen) {
        this.maxLen = maxLen;
    }


    public void setMaxLen(int maxLen) {
        this.maxLen = maxLen;
    }

    @Override
    public boolean validate(String value) throws ValidationError {
        if (maxLen > 0) {
            if (allowMaxLengthOfString(value, maxLen)) {
                if (isDigitAndDot(value)) {
                    if (!(value.startsWith(".") || value.endsWith(".") || value.split("\\.", -1).length > 2)) {
                        if (!value.startsWith("0")) {
                            return true;
                        } else {
                            if ("0".equals(value) || (value.length() > 2 && value.substring(1, 2).equals("."))) {
                                return true;
                            } else {
                                throw new ValidationError("输入格式有误，以0开头的浮点数第二位是小数点且长度大于2");
//                                promptDialog("提示", "输入格式有误，以0开头的浮点数第二位是小数点且长度大于2", null);
                            }
                        }
                    } else {
                        throw new ValidationError("输入格式有误，不能以.开头或者结尾且只能包含一个小数点");
//                        promptDialog("提示", "输入格式有误，不能以.开头或者结尾且只能包含一个小数点", null);
                    }
                } else {
                    throw new ValidationError("必须输入纯数字或小数点");
//                    promptDialog("提示", "必须输入纯数字或小数点", null);
                }
            } else {
                throw new ValidationError("不能超过" + maxLen + "字符");
//                promptDialog("提示", "不能超过" + maxLen + "字符", null);
            }
        } else {
            //无长度限制
            return true;
        }
    }

    private boolean allowMaxLengthOfString(String s, int maxLen) {
        int num = 0;
        for (int i = 0; i < s.length(); i++) {
            String tmp = s.substring(i, i + 1);
            if (isChinese(tmp)) {
                num += 2;
            } else {
                if (tmp.getBytes().length == 3) {
                    num += 2;
                } else if (tmp.getBytes().length == 1) {
                    num += 1;
                }
            }
        }
        return num <= maxLen;
    }

    private boolean isDigitAndDot(String str) {

        int len = 0;
        for (int idx = 0; idx < str.length(); idx++) {
            if (Character.isDigit(str.charAt(idx)) || str.charAt(idx) == '.') {
                len++;
            }
        }
        return len == str.length();
    }

    private boolean isChinese(String name) {
        int idx = 0;
        name = name.trim();
        int len = name.length();
        Pattern pattern = Pattern.compile("[\\u4e00-\\u9fa5]");
        Matcher m = pattern.matcher(name);
        while (m.find()) {
            idx++;
        }
        return len == idx;
    }
}
