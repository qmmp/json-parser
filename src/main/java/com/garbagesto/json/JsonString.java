package com.garbagesto.json;

public class JsonString implements JsonValue<String>{

    private final String _value;

    public JsonString(String value){
        _value = value;
    }

    public String toJsonString(){
        String retVal = _value.replaceAll("\"", "\\\\\"").replaceAll("\n", "\\\\n").replaceAll("\r", "\\\\r");
        return "\""+retVal + "\"";
    }
    
    public String getValue(){
        return _value;
    }

    public String toString(){
        return toJsonString();
    }

    public int hashCode(){
        return toJsonString().hashCode();
    }

    public boolean equals(Object o){
        if( o instanceof JsonString && o != null ){
            return _value.equals(((JsonString)o)._value);
        }
        return false;
    }
}
