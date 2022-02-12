package com.garbagesto.json;

/**
 * Json value interface.
 * @param <T> java value type
 */
public interface JsonValue<T>{

    /**
     * return json format string.
     * @return json format string
     */
    public String toJsonString();

    /**
     * return java value.
     * @return java value
     */
    public T getValue();

    /**
     * set java type value
     * @param value
     */
    public void setValue(T value);

}
