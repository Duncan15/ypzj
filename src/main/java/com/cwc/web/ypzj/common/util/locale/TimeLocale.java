package com.cwc.web.ypzj.common.util.locale;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class TimeLocale {
    public static Locale defaultLocale=Locale.CHINA;
    public static SimpleDateFormat defaultFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",defaultLocale);
}
