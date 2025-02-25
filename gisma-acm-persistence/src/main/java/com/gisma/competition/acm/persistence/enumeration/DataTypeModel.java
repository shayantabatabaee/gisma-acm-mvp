package com.gisma.competition.acm.persistence.enumeration;

public enum DataTypeModel {
    BYTE,
    SHORT,
    INT,
    LONG,
    FLOAT,
    DOUBLE,
    BOOLEAN,
    CHAR,
    STRING;

    public Class<?> getDataTypeClass(boolean isArray) {
        return switch (this) {
            case BYTE -> isArray ? Byte[].class : Byte.class;
            case SHORT -> isArray ? Short[].class : Short.class;
            case INT -> isArray ? Integer[].class : Integer.class;
            case LONG -> isArray ? Long[].class : Long.class;
            case FLOAT -> isArray ? Float[].class : Float.class;
            case DOUBLE -> isArray ? Double[].class : Double.class;
            case BOOLEAN -> isArray ? Boolean[].class : Boolean.class;
            case CHAR -> isArray ? Character[].class : Character.class;
            case STRING -> isArray ? String[].class : String.class;
        };
    }
}
