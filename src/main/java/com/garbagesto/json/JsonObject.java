package com.garbagesto.json;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class JsonObject extends LinkedHashMap<JsonString,JsonValue<?>> implements JsonValue<Map<String,Object>> {
    
    public String toJsonString(){
        StringBuilder builder = new StringBuilder("{");
        for( Entry<JsonString,JsonValue<?>> entry: this.entrySet() ){
            if( builder.length() > 1 ){
                builder.append(",");
            }
            builder.append(entry.getKey().toJsonString());
            builder.append(":");
            builder.append(entry.getValue().toJsonString());
        }
        builder.append("}");
        return builder.toString();
    }

    public Map<String, Object> getValue(){
        Map<String,Object> ret = new LinkedHashMap<>();
        for( Entry<JsonString,JsonValue<?>> entry: this.entrySet() ){
            ret.put(entry.getKey().getValue(),entry.getValue().getValue());
        }
        return ret;
    }

    public JsonObject push(JsonString key, JsonValue<?> value){
        this.put(key,value);
        return this;
    }

    @SuppressWarnings("unchecked")
    public <T extends JsonValue<?>> T getObject(JsonString key){
        return (T)get(key);
    }

    @SuppressWarnings("unchecked")
    public <T extends JsonValue<?>> T getObject(String key){
        return (T)get(new JsonString(key));
    }

    public String toString(){
        return toJsonString();
    }

    public int hashCode(){
        return toJsonString().hashCode();
    }}
