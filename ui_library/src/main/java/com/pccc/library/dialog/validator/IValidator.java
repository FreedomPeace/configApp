package com.pccc.library.dialog.validator;

/**
 * 实现{@link IValidator#validate(String)}验证数据正确性
 * 数据正确的{@code return true}
 * 错误的{@code throw new ValidationError("Why") }
 * <p>
 * created by liliangjun
 * 2020/10/19
 */
public interface IValidator {
    /**
     * 数据正确的{@code return true}
     * 错误的{@code throw new ValidationError("Why") }
     *
     * @return 数据是否正确
     * @throws ValidationError 验证错误原因
     */
    boolean validate(String value) throws ValidationError;

    class ValidationError extends Exception {
        public String message;

        @Override
        public String getMessage() {
            return message;
        }

        /**
         * @param message 数据无法验证通过的原因
         */
        public ValidationError(String message) {
            this.message = message;
        }
    }
}
