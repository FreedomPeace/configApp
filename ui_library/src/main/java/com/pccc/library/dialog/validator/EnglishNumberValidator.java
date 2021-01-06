package com.pccc.library.dialog.validator;

/**
 * created by liliangjun
 * 2020/10/20
 */
class EnglishNumberValidator implements IValidator {
    @Override
    public boolean validate(String value) throws ValidationError {
        int j = 0;
        int k = 0;
        for (int idx = 0; idx < value.length(); idx++) {
            if (Character.isDigit(value.charAt(idx))) {
                j++;
            }
        }
        for (int idx = 0; idx < value.length(); idx++) {
            char c = value.charAt(idx);
            if ('a' <= c && c <= 'z' || 'A' <= c && c <= 'Z') {
                k++;
            }
        }
        if (k + j == value.length()) {
            return true;
        }

        throw new ValidationError("输入只能是英文及数字");
    }
}
