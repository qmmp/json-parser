package com.garbagesto.json;

import com.garbagesto.json.util.JsonMapper;

import java.util.LinkedHashMap;
import java.util.Map;

public class JsonObject implements JsonValue<Map<String,Object>> {

    private final Map<JsonString,JsonValue<?>> _value = new LinkedHashMap<>();

    @Override
    public String toJsonString() {
        StringBuilder work = new StringBuilder();
        work.append("{");
        boolean hasData = false;
        for(Map.Entry<JsonString,JsonValue<?>> e: _value.entrySet()){
            if( hasData ){
                work.append(",");
            }
            work.append(e.getKey().toJsonString());
            work.append(":");
            work.append(e.getValue().toJsonString());
            hasData = true;
        }
        work.append("}");
        return work.toString();
    }

    @Override
    public Map<String, Object> getValue() {
        Map<String, Object> ret = new LinkedHashMap<>();
        for(Map.Entry<JsonString,JsonValue<?>> e: _value.entrySet()){
            ret.put(e.getKey().getValue(),e.getValue().getValue());
        }
        return ret;
    }

    @Override
    public void setValue(Map<String, Object> value) {
        Map<JsonString, JsonValue<?>> work = new LinkedHashMap<>();
        for(Map.Entry<String,Object> e: value.entrySet()){
            work.put(new JsonString(e.getKey()), JsonMapper.mapping(e.getValue()));
        }
        _value.clear();
        _value.putAll(work);
    }

    @Override
    public boolean equals(Object o) {
        if( o instanceof  JsonObject ){
            return toJsonString().equals(((JsonObject) o).toJsonString());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return toJsonString().hashCode();
    }

    public JsonObject push(String key, Object v){
        return push(new JsonString(key),v);
    }

    public JsonObject push(JsonString key, Object v){
        _value.put(key,JsonMapper.mapping(v));
        return this;
    }

    public void clear(){
        _value.clear();
    }

    public int size(){
        return _value.size();
    }

    public String getJsonString(String key) {
        return getJsonString(new JsonString(key));
    }
    public String getJsonString(JsonString key) {
        return _value.get(key).toJsonString();
    }

    public JsonValue<?> get(String key) {
        return get(new JsonString(key));
    }
    public JsonValue<?> get(JsonString key) {
        return _value.get(key);
    }

    public Object getValue(String key) {
        return getValue(new JsonString(key));
    }
    public Object getValue(JsonString key) {
        return _value.get(key).getValue();
    }

    @Override
    public String toString() {
        return "JsonObject" + toJsonString() ;
    }
}

