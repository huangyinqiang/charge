package net.inconnection.charge.weixin.utils;

import com.jfinal.kit.StrKit;

public class WeiXinUtils {
    public WeiXinUtils() {
    }

    public static String filterWeixinEmoji(String source) {
        if (containsEmoji(source)) {
            source = filterEmoji(source);
        }

        return source;
    }

    public static boolean containsEmoji(String source) {
        if (StrKit.isBlank(source)) {
            return false;
        } else {
            int len = source.length();

            for(int i = 0; i < len; ++i) {
                char codePoint = source.charAt(i);
                if (isEmojiCharacter(codePoint)) {
                    return true;
                }
            }

            return false;
        }
    }

    private static boolean isEmojiCharacter(char codePoint) {
        return codePoint == 0 || codePoint == '\t' || codePoint == '\n' || codePoint == '\r' || codePoint >= ' ' && codePoint <= '\ud7ff' || codePoint >= '\ue000' && codePoint <= '�' || codePoint >= 65536 && codePoint <= 1114111;
    }

    public static String filterEmoji(String source) {
        if (!containsEmoji(source)) {
            return source;
        } else {
            StringBuilder buf = null;
            int len = source.length();

            for(int i = 0; i < len; ++i) {
                char codePoint = source.charAt(i);
                if (isEmojiCharacter(codePoint)) {
                    if (buf == null) {
                        buf = new StringBuilder(source.length());
                    }

                    buf.append(codePoint);
                }
            }

            if (buf == null) {
                return source;
            } else if (buf.length() == len) {
                buf = null;
                return source;
            } else {
                return buf.toString();
            }
        }
    }

    public static String emoji(int hexEmoji) {
        return String.valueOf(Character.toChars(hexEmoji));
    }
}

