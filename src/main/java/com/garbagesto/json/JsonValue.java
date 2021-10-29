package com.garbagesto.json;

public interface JsonValue<T> {
    
    public String toJsonString();

    public T getValue();

}
