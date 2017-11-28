package com.admin.shopkeeper.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/9/8.
 */

public class Tools {

    public static String formatNowDate(String format) {
        return new SimpleDateFormat(format).format(new Date());
    }

    public static String formatNowDate(String format,Date date) {
        return new SimpleDateFormat(format).format(date);
    }

    public static String formatLastMonthDate(String format) {
        long l = System.currentTimeMillis() - 60 * 60 * 24 * 30 * 1000L;
        return new SimpleDateFormat(format).format(new Date(l));
    }

    public static Date toDate(String str) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
            return sdf.parse(str);
        } catch (Exception e) {
        }
        return null;
    }

    public static boolean comparaDate(Date startDate, Date endDate) {
        long l = startDate.getTime();
        long l1 = endDate.getTime();
        if (l1 < l) {
            return true;
        }
        return false;
    }

    public static boolean checkDate(Date startDate, Date endDate) {
        long l = startDate.getTime() + 60 * 60 * 24 * 30 * 1000L;
        long l1 = endDate.getTime();
        if (l1 > l) {
            return true;
        }
        return false;
    }

    public static int getVersionCode(Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo;
        int versionCode = 0;
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionCode = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    public static void hideSoftKeyboard(Context context, EditText editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);

    }
}
