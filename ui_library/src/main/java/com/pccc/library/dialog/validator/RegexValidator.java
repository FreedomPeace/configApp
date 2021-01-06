package com.pccc.library.dialog.validator;

/**
 * created by liliangjun
 * 2020/10/20
 */
public class RegexValidator implements IValidator {

    private String regex;
    private String type;

    RegexValidator(String regex, String type) {
        this.regex = regex;
        this.type = type;
    }

    @Override
    public boolean validate(String value) throws ValidationError {
        if (value.matches(regex)) {
            return true;
        }
        if (type != null) {
            throw new ValidationError("输入的" + type + "错误");
        }
        throw new ValidationError("输入错误");
    }
}
