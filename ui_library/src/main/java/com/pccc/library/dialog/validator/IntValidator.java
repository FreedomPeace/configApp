package com.pccc.library.dialog.validator;

/**
 * created by liliangjun
 * 2020/10/19
 */
public class IntValidator implements IValidator {

    int maxLength;

    IntValidator(int maxLength) {
        this.maxLength = maxLength;
    }


    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public boolean validate(String value) throws ValidationError {
        int i;
        try {
            i = Integer.valueOf(value);
        } catch (NumberFormatException e) {
            throw new ValidationError("只能输入数字");
        }
        if (i < 1) {
            throw new ValidationError("输入数字不能小于1");
        }
        if (maxLength <= 0) {
            return true;
        }
        if (value.length() <= maxLength) {
            return true;
        }
        throw new ValidationError("输入数字大于" + maxLength + "位");
    }
}
