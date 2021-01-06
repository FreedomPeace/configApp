package com.pccc.library.dialog.validator;



import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * created by liliangjun
 * 2020/10/19
 */
public class ValidatorUtil {
    /**
     * 验证输入字符串长度
     */
    public static final int CASE_LENGTH = 1000;
    /**
     * 确认输入的长整数
     */
    public static final int CASE_LONG = 1001;
    /*
     * 确认输入的整数，最小值为1
     */
    public static final int CASE_INT = 1002;
    /**
     * 二次确认输入
     */
    public static final int CASE_DOUBLE_CONFIRM = 1003;

    /**
     * 验证浮点数
     */
    public static final int CASE_FLOAT = 1005;
    /**
     * 验证手机号
     */
    public static final int CASE_PHONE = 1006;
    /**
     * 验证自定义正则
     */
    public static final int CASE_REGEX = 1007;
    /**
     * 验证邮箱
     */
    public static final int CASE_EMAIL = 1008;
    /**
     * 验证中英文
     */
    public static final int CASE_ENGLISH_CHINESE = 1009;
    /**
     * 验证英文及数字
     */
    public static final int CASE_ENGLISH_NUMBER = 1010;
    /**
     * 验证大写英文数字和括号(“()”、“[]”、“{}”、“-”)证件号、组织机构代码
     */
    public static final int CASE_UPPER_NUMBER_BRACKET = 1011;
    /**
     * 验证英文数字及中文
     */
    public static final int CASE_ENGLISH_NUMBER_CHINESE = 1012;
    /**
     * 验证特殊符号
     */
    public static final int CASE_SPECIAL = 1013;
    /**
     * 验证时间，未实现
     */
    public static final int CASE_DATE = 1014;
    /**
     * 字串s是否为数字、大写字母、汉字、和中英文状态符号-[]{}()，不能仅为数字   商户姓名、法人姓名、授权人姓名
     */
    public static final int CASE_XING_MING = 1015;
    /**
     * 身份证号、临时身份证号
     */
    public static final int CASE_SHENG_FEN_ZHENG = 1016;
    /**
     * 字串s是否为数字、字母、汉字、中文符号顿号‘、’和中英文状态符号-:[]{}() 商户注册地址、 营业办公地址、门店地址
     */
    public static final int CASE_ADDRESS = 1017;
    /**
     * 字串s是否为数字、字母、汉字、中文符号‘’顿号‘、’和中英文状态符号-:[]{}() 商户注册名、商户简称、门店名称、门店简称
     */
    public static final int CASE_MING_CHENG = 1018;
    /**
     * 字串s是否为数字、字母、汉字、中文符号顿号、‘’；和中英文状态符号。，-:[]{}() 公司信息校验：经营范围  门店信息页面：门店介绍
     */
    public static final int CASE_INFO = 1019;
    /**
     * 营业时间
     */
    public static final int CASE_TIME = 1020;

    /**
     * 自定义
     */
//    private static final int CASE_CUSTOM = 3000;


    @IntDef(value = {
            CASE_LONG,
            CASE_INT,
            CASE_DOUBLE_CONFIRM,
            CASE_LENGTH,
            CASE_FLOAT,
            CASE_PHONE,
            CASE_REGEX,
            CASE_EMAIL,
            CASE_ENGLISH_CHINESE,
            CASE_ENGLISH_NUMBER,
            CASE_UPPER_NUMBER_BRACKET,
            CASE_ENGLISH_NUMBER_CHINESE,
            CASE_SPECIAL,
            CASE_DATE,
            CASE_XING_MING,
            CASE_SHENG_FEN_ZHENG,
            CASE_ADDRESS,
            CASE_MING_CHENG,
            CASE_INFO,
            CASE_TIME,
//            CASE_CUSTOM,//自定义
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface ValidateCase {
    }


    /**
     * 根据{@link ValidateCase}生成{@link IValidator}实例
     *
     * @param validateCase {@link ValidateCase}
     * @param value2       二次确认时的字符串，无需二次确认时设为空
     * @return {@link IValidator}实例
     */
    public static IValidator validator(@ValidateCase int validateCase, @Nullable String value2) {
        return validator(validateCase, value2, -1);
    }

    /**
     * 根据{@link ValidateCase}生成{@link IValidator}实例
     *
     * @param validateCase {@link ValidateCase}
     * @param maxLength    需要验证字符串的长度
     * @return {@link IValidator}实例
     */
    public static IValidator validator(@ValidateCase int validateCase, int maxLength) {
        return validator(validateCase, null, maxLength);
    }

    /**
     * 根据{@link ValidateCase}生成{@link IValidator}实例
     *
     * @param validateCase {@link ValidateCase}
     * @return {@link IValidator}实例
     */
    public static IValidator validator(@ValidateCase int validateCase) {
        return validator(validateCase, null, -1);
    }

    /**
     * 使用正则表达式验证
     *
     * @param regex 正则表达式
     * @param type  数据验证错误时告知异常类型{@code throw new ValidationError("输入的" + type + "错误")}
     *              可以设为空
     * @return {@link IValidator}实例
     */
    public static IValidator validator(@NonNull String regex, @Nullable String type) {
        return new RegexValidator(regex, type);
    }


    /**
     * 根据{@link ValidateCase}生成{@link IValidator}实例
     *
     * @param validateCase {@link ValidateCase}
     * @param value2       二次确认时的字符串，无需二次确认时设为空
     * @param maxLength    需要验证字符串的长度
     * @return {@link IValidator}实例
     */
    private static IValidator validator(@ValidateCase int validateCase, @Nullable String value2, int maxLength) {
        switch (validateCase) {
            case CASE_FLOAT:
                return new FloatValidator(maxLength);
            case CASE_DOUBLE_CONFIRM:
                return new DoubleConfirmValidator(value2);
            case CASE_LONG:
                return new LongValidator(maxLength);
            case CASE_INT:
                return new IntValidator(maxLength);
            case CASE_PHONE:
                return new PhoneValidator();
            case CASE_EMAIL:
                return new EmailValidator();
            case CASE_ENGLISH_CHINESE:
                return new EnglishChineseValidator();
            case CASE_ENGLISH_NUMBER:
                return new EnglishNumberValidator();
            case CASE_UPPER_NUMBER_BRACKET:
                return new UpperNumberBracketValidator();
            case CASE_ENGLISH_NUMBER_CHINESE:
                return new EnglishNumberChineneValidator();
            case CASE_SPECIAL:
                return new SpecialValidator(maxLength);
            case CASE_XING_MING:
                return new XingMingValidator(maxLength);
            case CASE_SHENG_FEN_ZHENG:
                return new ShenFenZhengValidator();
            case CASE_ADDRESS:
                return new AddressValidator(maxLength);
            case CASE_MING_CHENG:
                return new MingChengValidator(maxLength);
            case CASE_INFO:
                return new InfoValidator(maxLength);
            case CASE_TIME:
                return new TimeValidator(maxLength);
            case CASE_LENGTH:
            default:
                return new LengthValidator(maxLength);
        }
    }


}
