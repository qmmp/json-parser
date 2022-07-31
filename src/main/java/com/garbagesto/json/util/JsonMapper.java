package com.garbagesto.json.util;

import com.garbagesto.json.*;

import java.util.List;
import java.util.Map;

public class JsonMapper {

    /**
     * mapping java type object to json value.
     * @param o java type value
     * @return JsonValue object
     */
    public static JsonValue<?> mapping(Object o){
        if( o == null ) {
            return JsonNull.NULL;
        }else if( o instanceof JsonValue<?>){
            return (JsonValue<?>)o;
        }else if( o instanceof Number ) {
            JsonNumber n = new JsonNumber();
            n.setValue((Number) o);
            return n;
        }else if( o instanceof Boolean) {
            return new JsonBoolean(((Boolean) o));
        }else if( o instanceof List ) {
            JsonArray array = new JsonArray();
            @SuppressWarnings("unchecked") List<Object> l = (List<Object>)o;
            return array.setValue(l);
        }else if( o instanceof Object[] ) {
            JsonArray array = new JsonArray();
            array.setValue((Object[]) o);
            return array;
        }else if( o instanceof Map){
            JsonObject obj = new JsonObject();
            @SuppressWarnings("unchecked") Map<Object,Object> m = (Map<Object,Object>)o;
            for(Map.Entry<Object,Object> e: m.entrySet()){
                JsonString key;
                if( e.getKey() instanceof String ){
                    key = new JsonString((String)e.getKey());
                }else if( e.getKey() instanceof JsonString){
                    key = (JsonString)e.getKey();
                }else{
                    key = new JsonString(e.getKey().toString());
                }
                obj.push(key,e.getValue());
            }
            return obj;
        }else if( o instanceof String ) {
            return new JsonString((String) o);
        }else{
            return new JsonString(o.toString());
        }
    }

}
