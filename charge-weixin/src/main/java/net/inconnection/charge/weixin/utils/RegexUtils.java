package net.inconnection.charge.weixin.utils;

import com.jfinal.kit.StrKit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {
    public static final String USER_NAME = "^[a-zA-Z\\u4E00-\\u9FA5][a-zA-Z0-9_\\u4E00-\\u9FA5]{1,11}$";
    public static final String USER_PASSWORD = "^.{6,32}$";
    public static final String EMAIL = "^\\w+([-+.]*\\w+)*@([\\da-z](-[\\da-z])?)+(\\.{1,2}[a-z]+)+$";
    public static final String PHONE = "^1[34578]\\d{9}$";
    public static final String EMAIL_OR_PHONE = "^\\w+([-+.]*\\w+)*@([\\da-z](-[\\da-z])?)+(\\.{1,2}[a-z]+)+$|^1[34578]\\d{9}$";
    public static final String URL = "^(https?:\\/\\/)?([\\da-z\\.-]+)\\.([a-z\\.]{2,6})(:[\\d]+)?([\\/\\w\\.-]*)*\\/?$";
    public static final String ID_CARD = "^\\d{15}$|^\\d{17}([0-9]|X)$";

    public RegexUtils() {
    }

    public static boolean match(String regex, String beTestString) {
        Pattern pattern = Pattern.compile(regex, 2);
        Matcher matcher = pattern.matcher(beTestString);
        return matcher.matches();
    }

    public static boolean find(String regex, String beTestString) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(beTestString);
        return matcher.find();
    }

    public static String findResult(String regex, String beFoundString) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(beFoundString);
        return matcher.find() ? matcher.group() : null;
    }

    public static String encodePhone(String phone) {
        if (StrKit.isBlank(phone)) {
            return "";
        } else if (match("^1[34578]\\d{9}$", phone)) {
            String begin = phone.substring(0, 3);
            String end = phone.substring(7, phone.length());
            return begin + "****" + end;
        } else {
            return phone;
        }
    }
}

