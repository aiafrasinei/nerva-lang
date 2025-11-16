package org.nerva;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ParserUtils {
    private static String DEFAULT_INDENT = "    ";

    public static List<String> splitSpecial(String text) {
        List<String> ret = new ArrayList<>();

        if(text.contains("\"")) {
            for(String s : List.of(text.split("\""))) {
                if(s.startsWith(" ")) {
                    Collections.addAll(ret, s.substring(1).split(" "));
                } else if(s.endsWith(" ")) {
                    Collections.addAll(ret, s.split(" "));
                } else {
                    ret.add(s);
                }
            }
            if(text.endsWith("\"\"")) {
                ret.add("");
            }
        } else {
            Collections.addAll(ret, text.split(" "));
        }

        return ret;
    }

    public static String indentString(int level) {
        StringBuilder ret = new StringBuilder();
        for(int i=0; i<level; i++) {
            ret.append(DEFAULT_INDENT);
        }

        return ret.toString();
    }

    public static boolean isStandardTypeAnnotation(char c) {
        boolean ret = false;
        if(c == 's' || c == 'i' || c == 'b' || c == 'r' || c == 't' || c == 'f') {
            ret = true;
        }
        return ret;
    }

    public static char getAnnotationChar(String token) {
        char retch = '0';

        int underscoreIndex = token.indexOf('_');
        if ((underscoreIndex+2) == token.length()) {
            retch = token.charAt(underscoreIndex + 1);
        }

        return retch;
    }

    public static String getAnnotationString(String token) {
        int underscoreIndex = token.indexOf('_');
        return token.substring(underscoreIndex+1);
    }

    public static String getVarName(String token) {
        int underscoreIndex = token.indexOf('_');
        return token.substring(0, underscoreIndex);
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
