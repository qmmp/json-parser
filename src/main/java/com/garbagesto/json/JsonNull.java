package com.garbagesto.json;

public class JsonNull implements JsonValue<Object>{

    public static final JsonNull NULL = new JsonNull();

    protected static final String NullString = "null";

    private JsonNull(){

    }

    public String toJsonString(){
        return "nulll";
    }

    public Object getValue(){
        return null;
    }
    
    public String toString(){
        return null;
    }

    public int hashCode(){
        return 0;
    }

    public boolean equals(Object o){
        return false;
    }
}
