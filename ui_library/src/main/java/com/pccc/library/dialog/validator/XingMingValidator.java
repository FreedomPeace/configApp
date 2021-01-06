package com.pccc.library.dialog.validator;

import android.text.TextUtils;

/**
 * created by liliangjun
 * 2020/11/2
 */
class XingMingValidator implements IValidator {
    private int maxlength;

    public XingMingValidator(int maxLength) {
        this.maxlength = maxLength;
    }

    @Override
    public boolean validate(String value) throws ValidationError {
        JudgeMethods judgeMethods = new JudgeMethods();
        if (!TextUtils.isEmpty(value) && judgeMethods.allowMaxLenthOfString(value, maxlength)
                && judgeMethods.isEnglishAndDigitAndChineseAndOther4(value)) {

            if (!judgeMethods.isAllDigit(value)) {
                if (judgeMethods.isDouble(value)) {

                    return true;
                } else {
                    throw new ValidationError("符号[]{}()必须成对出现");
                }
            } else {
                throw new ValidationError("输入内容不能全部为数字");
            }
        } else {
            throw new ValidationError("不能超过" + maxlength + "字符，且只能输入数字、字母、汉字和中英文状态符号-[]{}()");
        }
    }

}
