package com.pccc.library.dialog.validator;

/**
 * created by liliangjun
 * 2020/10/20
 */
public class EmailValidator implements IValidator {
    @Override
    public boolean validate(String value) throws ValidationError {
        if (value.getBytes().length < 51 && value
                .matches("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$"))
            return true;
        else
            throw new ValidationError("无法识别输入的邮箱");
    }
}
