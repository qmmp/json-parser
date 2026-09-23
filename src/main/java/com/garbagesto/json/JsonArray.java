package com.garbagesto.json;

import com.garbagesto.json.util.JsonMapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class JsonArray implements JsonValue<List<Object>> {

    private final ArrayList<JsonValue<?>> _value = new ArrayList<>();

    @Override
    public String toJsonString() {
        StringBuilder work = new StringBuilder();
        work.append("[");
        boolean hasValue = false;
        for( JsonValue<?> v: _value){
            if( hasValue ){
                work.append(",");
            }
            work.append(v.toJsonString());
            hasValue = true;
        }
        work.append("]");
        return work.toString();
    }

    @Override
    public List<Object> getValue() {
        List<Object> ret = new ArrayList<>();
        for( JsonValue<?> v: _value){
            ret.add(v.getValue());
        }
        return ret;
    }

    @Override
    public JsonArray setValue(List<Object> value) {
        setValue(value.toArray());
        return this;
    }

    public void setValue(Object[] value) {
        List<JsonValue<?>> work = new ArrayList<>();
        for( Object o: value){
            JsonValue<?> v = JsonMapper.mapping(o);
            work.add(v);
        }
        _value.clear();
        _value.addAll(work);
    }

    @Override
    public boolean equals(Object o) {
        if( o instanceof  JsonArray ){
            return toJsonString().equals(((JsonArray) o).toJsonString());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return toJsonString().hashCode();
    }

    public JsonArray push(Object v){
        _value.add(JsonMapper.mapping(v));
        return this;
    }

    public void clear(){
        _value.clear();
    }

    public int size(){
        return _value.size();
    }

    public String getJsonString(int i){
        JsonValue<?> val = _value.get(i);
        if( val != null ){
            return _value.get(i).toJsonString();
        }
        return null;
    }

    public BigDecimal getJsonNumber(int i){
        JsonValue<?> val = _value.get(i);
        if( val instanceof JsonNumber ){
            return ((JsonNumber)val).getValue();
        }
        return null;
    }

    public JsonValue<?> get(int i){
        return _value.get(i);
    }

    public <T extends JsonValue<?>> T get(int i, Class<T> c){
        JsonValue<?> val = _value.get(i);
        if( c.isInstance(val) ){
            return c.cast(val);
        }
        return null;
    }

    public Object getValue(int i){
        JsonValue<?> val = _value.get(i);
        if( val == null ){
            return null;
        }
        return val.getValue();
    }

    public String getStringValue(int i){
        JsonValue<?> val = _value.get(i);
        if( val == null ){
            return null;
        }
        if( val instanceof JsonString ){
            return ((JsonString)val).getValue();
        }
        return val.toJsonString();
    }

    public BigDecimal getNumberValue(int i){
        JsonValue<?> val = _value.get(i);
        if( val instanceof JsonNumber ){
            return ((JsonNumber)val).getValue();
        }
        return null;
    }

    @Override
    public String toString() {
        return "JsonArray" + toJsonString();
    }
}
