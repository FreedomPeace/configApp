package com.pccc.library.dialog.validator;

import android.text.TextUtils;

/**
 * created by liliangjun
 * 2020/11/2
 */
class AddressValidator implements IValidator {
    private int maxLength;

    public AddressValidator(int maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public boolean validate(String value) throws ValidationError {
        JudgeMethods methods = new JudgeMethods();
        if (methods.allowMaxLenthOfString(value, maxLength)
                && methods.isEnglishAndDigitAndChineseAndOther5(value)
                && !TextUtils.isEmpty(value)) {
            if (methods.isDouble(value)) {
                return true;
            } else {
                throw new ValidationError("符号()[]{}必须成对出现");
            }
        } else {
            throw new ValidationError("不能超过" + maxLength + "字符，且只能输入数字、字母、汉字、中文符号顿号、和中英文状态符号-:[]{}()");
        }
    }
}
