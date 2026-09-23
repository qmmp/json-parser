package com.garbagesto.json;

import com.garbagesto.json.util.JsonMapper;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

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
    public JsonObject setValue(Map<String, Object> value) {
        Map<JsonString, JsonValue<?>> work = new LinkedHashMap<>();
        for(Map.Entry<String,Object> e: value.entrySet()){
            work.put(new JsonString(e.getKey()), JsonMapper.mapping(e.getValue()));
        }
        _value.clear();
        _value.putAll(work);
        return this;
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

    /**
     * 指定の項目を取り除く。
     * @param key
     * @return
     */
    public JsonObject remove(JsonString key){
        _value.remove(key);
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
        JsonValue<?> val = _value.get(key);
        if( val instanceof JsonString ){
            return ((JsonString)val).getValue();
        }
        if( val == null ){
            return null;
        }
        return val.toJsonString();
    }

    public BigDecimal getJsonNumber(String key){
        return getJsonNumner(new JsonString(key));
    }

    public BigDecimal getJsonNumner(JsonString key){
        JsonValue<?> val = _value.get(key);
        if( val instanceof JsonNumber ){
            return ((JsonNumber)val).getValue();
        }
        return null;
    }

    public JsonValue<?> get(String key) {
        return get(new JsonString(key));
    }
    public JsonValue<?> get(JsonString key) {
        return _value.get(key);
    }
    public <T extends JsonValue<?>> T get(String key, Class<T> c) {
        return get(new JsonString(key),c);
    }
    public <T extends JsonValue<?>> T get(JsonString key, Class<T> c) {
        JsonValue<?> val = _value.get(key);
        if( c.isInstance(val) ){
            return c.cast(val);
        }
        return null;
    }

    public Object getValue(String key) {
        return getValue(new JsonString(key));
    }
    public Object getValue(JsonString key) {
        JsonValue<?> val = _value.get(key);
        if( val == null ){
            return null;
        }
        return _value.get(key).getValue();
    }

    public String getStringValue(String key){
        return getStringValue(new JsonString(key));
    }

    public String getStringValue(JsonString key){
        JsonValue<?> val = get(key);
        if( val == null ){
            return null;
        }
        if( val instanceof JsonString ){
            return ((JsonString)val).getValue();
        }
        return val.toJsonString();
    }

    public BigDecimal getNimberValue(String key){
        return getNumberValue(new JsonString(key));
    }

    public BigDecimal getNumberValue(JsonString key){
        JsonValue<?> val = get(key);
        if( val instanceof JsonNumber ){
            return ((JsonNumber)val).getValue();
        }
        return null;
    }


    @Override
    public String toString() {
        return "JsonObject" + toJsonString() ;
    }

    public Set<JsonString> keySet(){
        return _value.keySet();
    }

    public Collection<JsonValue<?>> values(){
        return _value.values();
    }

    public Set<Entry<JsonString, JsonValue<?>>> entrySet(){
        return _value.entrySet();
    }
}

