package com.garbagesto.json;

import com.garbagesto.json.util.JsonMapper;

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
    public void setValue(List<Object> value) {
        setValue(value.toArray());
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
        return _value.get(i).toJsonString();
    }

    public JsonValue<?> get(int i){
        return _value.get(i);
    }

    public Object getValue(int i){
        return _value.get(i).getValue();
    }

    @Override
    public String toString() {
        return "JsonArray" + toJsonString();
    }
}
