package com.jachin.des.util;

import java.nio.ByteBuffer;
import java.util.Calendar;

/**
 * @author Jachin
 * @since 2019/3/12 13:51
 */
class JsonParse {
    static void toJson(Object obj, StringBuilder buf) {
        if (obj != null) {
            byte type = parseType(obj);
            switch(type) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                    buf.append(obj.toString());
                    return;
                case 6: // String
                    buf.append("\"");
                    encodeJson((String)obj, buf);
                    buf.append("\"");
                    return;
                case 7: // Calender
                    buf.append(((Calendar) obj).getTimeInMillis());
                    return;
                case 8:
                case 11:
                case 12:
                default:
                    buf.append("\"");
                    encodeJson(obj.toString(), buf);
                    buf.append("\"");
                    return;
                case 9:
                    ((JacParam)obj).dealBuf(buf);
            }
        }
    }

    private static byte parseType(Object obj) {
        if (obj instanceof Integer) {
            return 1;
        } else if (obj instanceof Long) {
            return 2;
        } else if (obj instanceof Float) {
            return 3;
        } else if (obj instanceof Double) {
            return 4;
        } else if (obj instanceof Boolean) {
            return 5;
        } else if (obj instanceof String) {
            return 6;
        } else if (obj instanceof Calendar) {
            return 7;
        } else if (obj instanceof JacParam) {
            return 9;
        } else if (obj instanceof Byte) {
            return 11;
        } else if (obj instanceof Short) {
            return 12;
        } else if (obj instanceof ByteBuffer) {
            return 8;
        }
        return  0;
    }

    private static void encodeJson(String html, StringBuilder buf) {
        try {
            if (html == null) {
                return;
            }

            int len = html.length();
            boolean inBracket = false;

            for(int i = 0; i < len; ++i) {
                char c;
                label86: {
                    c = html.charAt(i);
                    switch(c) {
                        case '\b':
                            buf.append("\\b");
                            break label86;
                        case '\t':
                            buf.append("\\t");
                            break label86;
                        case '\n':
                            buf.append("\\n");
                            break label86;
                        case '\f':
                            buf.append("\\f");
                            break label86;
                        case '\r':
                            buf.append("\\r");
                            break label86;
                        case '!':
                            if (inBracket) {
                                buf.append("\\u0021");
                            } else {
                                buf.append("!");
                            }
                            break label86;
                        case '"':
                            buf.append("\\\"");
                            break label86;
                        case '/':
                            if (inBracket) {
                                buf.append("\\/");
                            } else {
                                buf.append("/");
                            }
                            break label86;
                        case '\\':
                            buf.append("\\\\");
                            break label86;
                    }

                    if ((c < 0 || c > 31) && (c < 127 || c > 159) && (c < 8192 || c > 8447)) {
                        buf.append(c);
                    } else {
                        String ss = Integer.toHexString(c);
                        buf.append("\\u");

                        for(int k = 0; k < 4 - ss.length(); ++k) {
                            buf.append('0');
                        }

                        buf.append(ss.toUpperCase());
                    }
                }

                if (c == '<') {
                    inBracket = true;
                } else {
                    inBracket = false;
                }
            }
        } catch (Exception var8) {
            buf.setLength(0);
        }

    }
}
