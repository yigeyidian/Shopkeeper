package com.admin.shopkeeper.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/9/8.
 */

public class Tools {

    public static String formatNowDate(String format) {
        return new SimpleDateFormat(format).format(new Date());
    }
}
