package com.garbagesto.json.util;

public class JsonParseException extends Exception{

    public enum MESSAGE {
        UNEXPECTED_CHARACTER("unexpected character [%s] at %d"),
        ILLEGAL_MULTI_VALUE("illegal next value at %d"),
        ILLEGAL_KEY_TYPE("illegal key type at %d"),
        ILLEGAL_NULL_LITERAL("illegal null literal [%s] at %d"),
        ILLEGAL_BOOLEAN_LITERAL("illegal boolean literal [%s] at %d"),
        ILLEGAL_STRING_LITERAL("illegal string literal [%s] at %d"),
        ILLEGAL_CODEPOINT_TOO_SHORT("illegal codepoint too short [%s] at %d"),
        ILLEGAL_CODEPOINT("illegal codepoint [%s] at %d"),
        ILLEGAL_NUMBER_FORMAT("illegal number format [%s] at %d"),
        VALUE_NOT_FOUND("value is not found at %d"),
        ARRAY_END_BRACKET_NOT_FOUND("array end bracket not found at %d to %d"),
        OBJECT_END_BRACKET_NOT_FOUND("object end bracket not found at %d to %d"),
        UNEXPECTED_ERROR("unexpected error: %s"),
        ;

        private final String _msg;
        MESSAGE(String msg){
            _msg = msg;
        }
        public String format(Object... f){
            return String.format(_msg,f);
        }
    }

    public JsonParseException(MESSAGE msg, Object... f){
        super(msg.format(f));
    }

    public JsonParseException(MESSAGE msg, Throwable cause,Object... f){
        super(msg.format(f),cause);
    }
}
