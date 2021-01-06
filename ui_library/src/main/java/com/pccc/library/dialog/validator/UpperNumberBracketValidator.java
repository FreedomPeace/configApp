package com.pccc.library.dialog.validator;

import java.util.Stack;

/**
 * created by liliangjun
 * 2020/10/20
 */
class UpperNumberBracketValidator implements IValidator {
    @Override
    public boolean validate(String value) throws ValidationError {
        int j = 0;
        int k = 0;
        int i = 0;
        for (int idx = 0; idx < value.length(); idx++) {
            if (Character.isDigit(value.charAt(idx))) {
                j++;
            }
        }
        for (int idx = 0; idx < value.length(); idx++) {
            char c = value.charAt(idx);
            if ('A' <= c && c <= 'Z') {
                k++;
            }
        }
        for (int ic = 0; ic < value.length(); ic++) {
            char c = value.charAt(ic);
            if (c == '(' || c == ')' || c == '[' || c == ']' || c == '{' || c == '}' || c == '-') {
                i++;
            }
        }

        if (i + k + j == value.length()) {
            if (i > 0) {
                if (isDouble(value)) {
                    return true;
                }
                throw new ValidationError("括号需要成对出现");
            }
            return true;
        }
        throw new ValidationError("输入只能是大写英文、数字、大括号{}、中括号[]、小括号()和横杠-");
    }

    /**
     * 判断[]{}()是否成对出现
     * false 表示不成对出现
     */
    private boolean isDouble(String str) {
        Stack<Character> sc = new Stack<Character>();
        try {
            char[] c = str.toCharArray();
            for (int i = 0; i < c.length; i++) {
                if (c[i] == '(' || c[i] == '[' || c[i] == '{' || c[i] == '（' || c[i] == '【') {
                    sc.push(c[i]);
                } else if (c[i] == ')') {
                    if (sc.peek() == '(') {
                        sc.pop();
                    }
                } else if (c[i] == ']') {
                    if (sc.peek() == '[') {
                        sc.pop();
                    }
                } else if (c[i] == '}') {
                    if (sc.peek() == '{') {
                        sc.pop();
                    }
                } else if (c[i] == '）') {
                    if (sc.peek() == '（') {
                        sc.pop();
                    }
                } else if (c[i] == '】') {
                    if (sc.peek() == '【') {
                        sc.pop();
                    }
                }
            }
        } catch (Exception e) {
            return false;
        }
        if (sc.empty()) {
            return true;
        } else {
            return false;
        }
    }
}
