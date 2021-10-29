package com.garbagesto.json;

import java.math.BigDecimal;

public class JsonNumber implements JsonValue<Number> {

    private final Number _value;

    public JsonNumber(long val){
        _value = val;
    }

    public JsonNumber(double val){
        _value = val;
    }

    public JsonNumber(BigDecimal val){
        _value = val;
    }

    public JsonNumber(String val){
        _value = new BigDecimal(val);
    }

    public String toJsonString(){
        return _value.toString();
    }

    public Number getValue(){
        return _value;
    }

    public String toString(){
        return toJsonString();
    }

    public int hashCode(){
        return toJsonString().hashCode();
    }

    public boolean equals(Object o){
        if( o instanceof JsonNumber && o != null ){
            Number targetValue = new BigDecimal(((JsonNumber)o).getValue().toString());
            BigDecimal thisV = new BigDecimal(_value.toString());
            return targetValue.equals(thisV);
        }
        return false;
    }
    
}
