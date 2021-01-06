package com.pccc.library.dialog.validator;

/**
 * created by liliangjun
 * 2020/11/2
 */
class ShenFenZhengValidator implements IValidator {


    @Override
    public boolean validate(String value) throws ValidationError {
        JudgeMethods methods = new JudgeMethods();
        if (methods.allowMaxLenthOfString(value, 18)) {
            if (value.matches("\\d{17}[0-9X]{1}")) {
                return true;
            } else {
                throw new ValidationError("请输入正确身份证号");
            }
        } else {
            throw new ValidationError("不能超过" + 18 + "个字符");
        }
    }
}
