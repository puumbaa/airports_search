package com.renue.internship.common;

import java.util.Collection;
import java.util.Collections;

public enum Type {
    STRING, INT, DOUBLE;

    public static Type get(Collection<String> words){
        boolean isInt = false;
        boolean isDouble = false;
        for (String word : words) {
            if (word.matches("-?[0-9]{0,10}")){
                isInt = true;
            }else if (word.matches("-?[0-9]+.[0-9]{1,17}+")){
                isDouble = true;
            }
        }
        if (isDouble) return DOUBLE;
        if (isInt) return INT;
        return STRING;
    }
}
