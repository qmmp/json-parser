package com.garbagesto.json;

public class JsonException extends Exception{

    public enum ERROR{
        UNKNOWN_ERROR("unknown error"),
        UNMATCH_ARRAY_BLANKET("unmatch array blanket"),
        UNMATCH_OBJECT_BLANKET("unmatch object blanket"),
        ILLEGAL_BOOLEAN_LITERAL("illegal boolean literal"),
        ILLEGAL_JSON_LITERAL("illegal json literal"),
        UNMATCH_TYPE_JSON_KEY("json object key is not strng"),
        NO_JSON_OBJECT_KEY("no json object key"),
        NO_JSON_OBJECT_VALUE("no json object value"),
        NO_JSON_OBJECT_KEY_VALUE_SEPARATOR("no json object key value separator"),
        UNEXPECTED_CHAR_ARRAY("unexpected character on end of array"),
        UNEXPECTED_CHAR_OBJECT("unexpected character on end of object"),
        UNEXPECTED_CHAR_OBJECT_VALUE("unexpected char after json object entry"),
        UNEXPECTED_CHAR_ARRAY_VALUE("unexpected char after json array entry"),
        ILLEGAL_UNICODE_VALUE("illegal unicode value"),
        ILLEGAL_STRING_LITERAL("illegal string literal"),
        ;
        private final String msg;
        private ERROR(String msg){
            this.msg = msg;
        }
        public String getMessage() {
            return this.msg;
        }
    }

    private final ERROR _error;
    private final String _errVal;

    public JsonException(ERROR msg){
        super(msg.getMessage());
        _error = msg;
        _errVal = null;
    }

    public JsonException(ERROR msg, String errVal){
        super(msg.getMessage() + ":[" + errVal + "]");
        _error = msg;
        _errVal = errVal;
    }
    /*
    public JsonException(String msg){
        super(msg);
        _error  = ERROR.UNKNOWN_ERROR;
    }
    public JsonException(String msg, Throwable cause){
        super(msg,cause);
        _error  = ERROR.UNKNOWN_ERROR;
    }
    */

    public ERROR getError(){
        return _error;
    }

    public String getErrorValue(){
        return _errVal;
    }
}
