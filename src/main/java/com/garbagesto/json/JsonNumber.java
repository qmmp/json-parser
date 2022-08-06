package com.garbagesto.json;

import java.math.BigDecimal;

public class JsonNumber extends Number implements JsonValue<Number>{

    private Number _value = Integer.valueOf(0);

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

    /**
     * int、long、floatでの値設定で再起呼び出しが発生してしまうので実態をラッピング。
     * @param value
     * @return
     */
    private JsonNumber setValueNumber(Number value) {
        _value = value;
        return this;
    }

    @Override
    public JsonNumber setValue(Number value) {
        return setValueNumber(value);
    }

    public JsonNumber setValue(int v){
        return setValueNumber(Integer.valueOf(v));
    }

    public JsonNumber setValue(long v){
        return setValueNumber(Long.valueOf(v));
    }

    public JsonNumber setValue(float v){
        return setValueNumber(Float.valueOf(v));
    }

    public JsonNumber setValue(Double v){
        return setValueNumber(Double.valueOf(v));
    }

    public JsonNumber setValue(String v){
        return setValueNumber(new BigDecimal(v));
    }

    @Override
    public boolean equals(Object o) {
        if( o instanceof JsonNumber ){
            return _value.equals(((JsonNumber) o)._value);
        }else if( o instanceof Number ){
            return _value.equals((Number)o);
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

    @Override
    public int intValue() {
        return _value.intValue();
    }

    @Override
    public long longValue() {
        return _value.longValue();
    }

    @Override
    public float floatValue() {
        return _value.floatValue();
    }

    @Override
    public double doubleValue() {
        return _value.doubleValue();
    }
}
