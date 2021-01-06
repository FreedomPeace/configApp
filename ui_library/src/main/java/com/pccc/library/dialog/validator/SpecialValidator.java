package com.pccc.library.dialog.validator;

import android.text.TextUtils;

/**
 * created by liliangjun
 * 2020/10/20
 */
class SpecialValidator implements IValidator {

    private int maxLength;

    public SpecialValidator(int maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public boolean validate(String value) throws ValidationError {
        JudgeMethods methods = new JudgeMethods();
        if (methods.allowMaxLenthOfString(value, maxLength) && !TextUtils.isEmpty(value)) {
            if (methods.isEnglishAndDigitAndChineseAndOther(value)
                    && methods.isNotContainedSpecialCharact(value)) {
                return true;
            } else {
                throw new ValidationError("请输入正确不含特殊字符的注册名");
            }
        } else {
            throw new ValidationError("请输入不大于" + maxLength + "个字符");
        }
    }
}
