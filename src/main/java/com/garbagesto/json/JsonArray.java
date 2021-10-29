package com.garbagesto.json;

import java.util.ArrayList;

public class JsonArray extends ArrayList<JsonValue<?>> implements JsonValue<JsonValue<?>[]>{

    public JsonArray push(JsonValue<?> val){
        this.add(val);
        return this;
    }

    public String toJsonString(){
        StringBuilder builder = new StringBuilder("[");
        for( int i=0; i<size(); i++){
            if( i>0 ){
                builder.append(",");
            }
            builder.append(get(i).toJsonString());
        }
        builder.append("]");
        return builder.toString();
    }

    public JsonValue<?>[] getValue(){
        return this.toArray(new JsonValue<?>[this.size()]); 
    }

    public String toString(){
        return toJsonString();
    }

    public int hashCode(){
        return toJsonString().hashCode();
    }

    @SuppressWarnings("unchecked")
    public <T extends JsonValue<?>> T getObject(int index){
        return (T)get(index);
    }
}
