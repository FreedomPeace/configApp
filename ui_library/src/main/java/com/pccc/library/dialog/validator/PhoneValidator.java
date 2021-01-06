package com.pccc.library.dialog.validator;

/**
 * created by liliangjun
 * 2020/10/20
 */
class PhoneValidator implements IValidator {

    PhoneValidator() {
    }

    @Override
    public boolean validate(String phone) throws ValidationError {
        String re1 = "^(1[358]\\d{9})$";
        String re2 = "^(14[578]\\d{8})$";
        String re3 = "^(170[0-35-9]\\d{7})$";
        String re4 = "^(10649\\d+)$";
        String re5 = "^(17[135-9]\\d{8})$";
        String re6 = "^(166\\d{8})$";
        String re7 = "^(19[89]\\d{8})$";
        //170,171,173,175-178,180-189，10649
        if (phone.matches(re1) || phone.matches(re2) || phone.matches(re3) || phone.matches(re4)
                || phone.matches(re5) || phone.matches(re6) || phone.matches(re7)) {
            return true;
        }
        throw new ValidationError("无法识别手机号");
    }
}
