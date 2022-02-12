package com.garbagesto.json;

import java.math.BigDecimal;
import java.util.Objects;

public class JsonNumber implements JsonValue<Number>{

    private Number _value = new Integer(0);

    public JsonNumber(){

    }

    public JsonNumber(Number value){
        _value = value;
    }

    public JsonNumber(String value){
        _value = new BigDecimal(value);
    }

    @Override
    public String toJsonString() {
        return _value.toString();
    }

    @Override
    public Number getValue() {
        return _value;
    }

    @Override
    public void setValue(Number value) {
        _value = value;
    }

    public void setValue(int v){
        _value = new Integer(v);
    }

    public void setValue(long v){
        _value = new Long(v);
    }

    public void setValue(float v){
        _value = new Float(v);
    }

    public void setValue(Double v){
        _value = new Double(v);
    }

    public void setValue(String v){
        _value = new BigDecimal(v);
    }

    @Override
    public boolean equals(Object o) {
        if( o instanceof Number ){
            return _value.equals(o.toString());
        }else if( o instanceof JsonNumber ){
            return _value.equals(((JsonNumber) o)._value);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return _value.hashCode();
    }

    @Override
    public String toString() {
        return "JsonNumber["+_value.toString()+"]";
    }
}
