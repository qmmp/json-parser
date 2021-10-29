package com.garbagesto.json;

public class JsonBoolean implements JsonValue<Boolean>{

    public static final JsonBoolean TRUE = new JsonBoolean(true);
    public static final JsonBoolean FALSE = new JsonBoolean(false);
    protected static final String TrueString = "true";
    protected static final String FalseString = "false";

    public static JsonBoolean valueOf(String str) throws JsonException{
        if( str == null ){
            throw new NullPointerException("JsonBoolean is not null");
        }
        if(!str.equals(TrueString) && !str.equals(FalseString) ){
            throw new JsonException(JsonException.ERROR.ILLEGAL_BOOLEAN_LITERAL,str);
        }
        if( Boolean.valueOf(str) ){
            return JsonBoolean.TRUE;
        }else{
            return JsonBoolean.FALSE;
        }
    }

    private final boolean _bool;

    private JsonBoolean(boolean bool){
        _bool = bool;
    }

    public String toJsonString(){
        return _bool? "true": "false";
    }

    public Boolean getValue(){
        return Boolean.valueOf(_bool);
    }

    public String toString(){
        return toJsonString();
    }

    public int hashCode(){
        return toJsonString().hashCode();
    }

    public boolean equals(Object o){
        if( o instanceof JsonBoolean && o != null ){
            return _bool == ((JsonBoolean)o)._bool;
        }
        return false;
    }
}
