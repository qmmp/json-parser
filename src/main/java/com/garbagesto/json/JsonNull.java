package com.garbagesto.json;

/**
 * JsonNull value.
 */
public class JsonNull implements JsonValue<Object>{

    /**
     * JsonNull
     */
    public static final JsonNull NULL = new JsonNull();

    private JsonNull(){

    }

    @Override
    public String toJsonString() {
        return "null";
    }

    public Object getValue(){
        return null;
    }

    @Override
    public void setValue(Object value) {
        //Nothing
    }

    @Override
    public boolean equals(Object obj) {
        if( obj == null || obj instanceof JsonNull ){
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return "null".hashCode();
    }

    @Override
    public String toString() {
        return "JsonNull";
    }
}
