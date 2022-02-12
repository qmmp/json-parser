package com.garbagesto.json;

public class JsonBoolean implements JsonValue<Boolean>{

    private boolean _value = false;

    public JsonBoolean(){
    }

    public JsonBoolean(boolean value){
        _value = value;
    }

    @Override
    public String toJsonString() {
        if( _value ){
            return "true";
        }else{
            return "false";
        }
    }

    @Override
    public Boolean getValue() {
        return _value;
    }

    @Override
    public void setValue(Boolean value) {
        _value = value;
    }

    @Override
    public boolean equals(Object o) {
        if( o instanceof Boolean ){
            return _value == ((Boolean) o);
        }else if( o instanceof JsonBoolean ){
            return _value == ((JsonBoolean) o)._value;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (_value ? -1 : 0);
    }

    @Override
    public String toString() {
        return "JsonBoolean["+toJsonString()+"]";
    }
}
