package com.booboomx.hotvideo.utils;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.widget.TextView;

import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.typeface.IIcon;

import java.util.Random;

/**
 * Created by booboomx on 17/3/14.
 */

public class StringUtils {

    /**
     * 去掉特殊字符
     *
     * @param s
     * @return
     */
    public static String removeOtherCode(String s) {
        if (TextUtils.isEmpty(s))
            return "";
        s = s.replaceAll("\\<.*?>|\\n", "");
        return s;
    }

    /**
     * 判断非空
     *
     * @param str
     * @return
     */
    public static String isEmpty(String str) {
        String result = TextUtils.isEmpty(str) ? "" : str;
        return result;
    }

    /**
     * 根据Url获取catalogId
     *
     * @param url
     * @return
     */
    public static String getCatalogId(String url) {
        String catalogId = "";
        if (!TextUtils.isEmpty(url) && url.contains("="))
            catalogId = url.substring(url.lastIndexOf("=") + 1);
        return catalogId;
    }

    public static int getRandomNumber(int min, int max) {
        return new Random().nextInt(max) % (max - min + 1) + min;
    }

    public static String getErrorMsg(String msg) {
        if (msg.contains("*")) {
            msg = msg.substring(msg.indexOf("*") + 1);
            return msg;
        } else
            return "";
    }

    public static void setIconDrawable(Context mContext, TextView view, IIcon icon, int size, int padding) {
        view.setCompoundDrawablesWithIntrinsicBounds(new IconicsDrawable(mContext)
                        .icon(icon)
                        .color(Color.WHITE)
                        .sizeDp(size),
                null, null, null);
        view.setCompoundDrawablePadding(ScreenUtil.dip2px(mContext, padding));
    }
}
