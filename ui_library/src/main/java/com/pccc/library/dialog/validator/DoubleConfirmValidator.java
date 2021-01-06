package com.pccc.library.dialog.validator;

/**
 * created by liliangjun
 * 2020/10/19
 */
public class DoubleConfirmValidator implements IValidator {

    String value2;

    DoubleConfirmValidator(String value2) {
        this.value2 = value2;
    }

    @Override
    public boolean validate(String value1) throws ValidationError {
        if (value1.equals(value2)) {
            return true;
        }
        throw new ValidationError("两次输入不一致");
    }
}
