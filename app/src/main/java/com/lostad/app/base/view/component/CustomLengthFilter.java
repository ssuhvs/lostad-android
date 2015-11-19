package com.lostad.app.base.view.component;

import android.text.InputFilter.LengthFilter;
import android.text.Spanned;

public class CustomLengthFilter extends LengthFilter {
    private int max;// 字符串能输入的最大长度
    private onFullListener listener;
 
    /**
     * @param max
     *            字符串能输入的最大长度，半角字符算1，全角字符算2
     */
    public CustomLengthFilter(final int max) {
        super(max);
        this.max = max;
    }
 
    @Override
    public CharSequence filter(CharSequence source, int start, int end,
            Spanned dest, int dstart, int dend) {
        int mlength = getLength(dest.subSequence(dstart, dend).toString());// 修改字符串的长度
        int dlength = getLength(dest.toString());// 已有字符串的长度
        int slength = getLength(source.subSequence(start, end).toString());// 要增加的字符串的长度
        int keep = this.max - (dlength - mlength);// 还差多少字符到最大长度
        if (keep <= 0) {
            // 已经到达最大长度
            if (null != listener) {
                listener.isFull();
            }
            return "";
        } else if (keep >= slength) {
            // 还没到达最大长度
            return null;
        } else {
            // 超出最大长度
            int tmp = 0;
            int index;
            for (index = start; index <= end; index++) {
                if (isFullwidthCharacter(source.charAt(index))) {
                    tmp += 2;
                } else {
                    tmp += 1;
                }
                if (tmp > keep) {
                    break;
                }
            }
            if (null != listener) {
                listener.isFull();
            }
            return source.subSequence(start, index);
        }
    }
 
    public void setOnFullListener(onFullListener listener) {
        this.listener = listener;
    }
 
    public interface onFullListener {
 
        /**
         * 这个方法会在输入字符串超出极限时被调用
         */
        void isFull();
    }
 
    /**
     * 判断字符串是否为空或空串
     * 
     * @param str
     *            待判断的字符串
     * @return true：字符串为空或空串
     */
    public static boolean isNull(final String str) {
        return null == str || "".equals(str);
    }
 
    /**
     * 获取字符串长度（半角算1、全角算2）
     * 
     * @param str
     *            字符串
     * @return 字符串长度
     */
    public static int getLength(final String str) {
        if (isNull(str)) {
            return 0;
        }
        int len = str.length();
        for (int i = 0; i < str.length(); i++) {
            if (isFullwidthCharacter(str.charAt(i))) {
                len = len + 1;
            }
        }
        return len;
    }
 
    /**
     * 获取字符串的全角字符数
     * 
     * @param str
     *            待计算的字符串
     * @return 字符串的全角字符数
     */
    public static int getFwCharNum(final String str) {
        if (isNull(str)) {
            return 0;
        }
        int num = 0;
        for (int i = 0; i < str.length(); i++) {
            if (isFullwidthCharacter(str.charAt(i))) {
                num++;
            }
        }
        return num;
    }
 
    /**
     * 判断字符是否为全角字符
     * 
     * @param ch
     *            待判断的字符
     * @return true：全角； false：半角
     */
    public static boolean isFullwidthCharacter(final char ch) {
        if (ch >= 32 && ch <= 127) {
            // 基本拉丁字母（即键盘上可见的，空格、数字、字母、符号）
            return false;
        } else return !(ch >= 65377 && ch <= 65439);
    }
 
}