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

    public static boolean isTypeAnnotation(char c) {
        boolean ret = false;
        if(c == 's' || c == 'i' || c == 'r' || c == 't' || c == 'f') {
            ret = true;
        }
        return ret;
    }

    static char getAnnotationChar(String token) {
        int underscoreIndex = token.indexOf('_');
        return token.charAt(underscoreIndex + 1);
    }

    static String getVarName(String token) {
        int underscoreIndex = token.indexOf('_');
        return token.substring(0, underscoreIndex);
    }
}
