package com.pccc.library.dialog.validator;

/**
 * created by liliangjun
 * 2020/10/19
 */
public class LongValidator implements IValidator {
    int maxLength;

    LongValidator(int maxLength) {
        this.maxLength = maxLength;
    }


    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public boolean validate(String value) throws ValidationError {
        //不验证长度
        if (maxLength < 0) {
            return true;
        }
        if( value.length() <= maxLength){
            return true;
        }else {
            throw new ValidationError("输入长度超过"+maxLength);
        }
    }
}
