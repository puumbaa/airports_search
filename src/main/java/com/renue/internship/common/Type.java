package com.renue.internship.common;

public enum Type {
    STRING, INT, DOUBLE;

    public Type get(String str){
        if (str.matches("-?[0-9]{0,10}")){
            return INT;
        }else if (str.matches("-?[0-9]+.[0-9]{1,17}+")){
            return DOUBLE;
        }else {
            return STRING;
        }
    }
}
