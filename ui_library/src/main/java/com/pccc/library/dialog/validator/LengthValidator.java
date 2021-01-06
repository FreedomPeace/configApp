package com.pccc.library.dialog.validator;

/**
 * created by liliangjun
 * 2020/10/19
 */
public class LengthValidator implements IValidator {
    int allowLength;

    LengthValidator(int allowLength) {
        this.allowLength = allowLength;
    }

    @Override
    public boolean validate(String value) throws ValidationError {
        if (allowLength <= 0) {
            return true;
        }
        if (value.length() <= allowLength) {
            return true;
        }
        throw new ValidationError("输入不可超过" + allowLength + "个字符");
    }
}
