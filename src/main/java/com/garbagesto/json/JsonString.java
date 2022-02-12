package com.garbagesto.json;

import java.io.IOException;
import java.io.StringReader;
import java.util.Objects;

public class JsonString implements JsonValue<String>{

    private String _value;

    public JsonString(){
        _value = "";
    }
    public JsonString(String value){
        _value = value;
    }

    @Override
    public String toJsonString() {
        StringBuilder work = new StringBuilder();
        work.append("\"");
        for( char c: _value.toCharArray()){
            if( c == '\"' || c == '\\' || c == '/' ){
                work.append("\\");
            }
            if( c == '\b' ){
                work.append("\\b");
            }else if( c == '\f'){
                work.append("\\f");
            }else if( c == '\n' ){
                work.append("\\n");
            }else if( c == '\r' ){
                work.append("\\r");
            }else if( c == '\t' ){
                work.append("\\t");
            }else {
                work.append(c);
            }
        }
        work.append("\"");
        return work.toString();
    }

    @Override
    public String getValue() {
        return _value;
    }

    @Override
    public void setValue(String value) {
        _value = value==null?"":value;
    }

    @Override
    public boolean equals(Object o) {
        if( o instanceof JsonString ){
            return _value.equals(((JsonString) o)._value);
        }else if( o instanceof String ){
            return equals( _value.equals((String)o));
        }
        return false;
    }

    @Override
    public int hashCode() {
        return _value.hashCode();
    }

    @Override
    public String toString() {
        return "JsonString["+_value+"]";
    }
}
