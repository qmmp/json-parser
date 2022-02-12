package com.garbagesto.json;

import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class JsonObjectTest {

    @Test
    public void pushTest(){
        JsonObject obj = new JsonObject();
        assertEquals(0,obj.size());
        assertEquals("{}", obj.toJsonString());

        JsonObject retObj = obj.push("abc","ABC");
        assertTrue(obj == retObj);
        assertEquals(1,obj.size());
        assertEquals("{\"abc\":\"ABC\"}", obj.toJsonString());

        obj.push(new JsonString("def"),"DEF");
        assertEquals(2,obj.size());
        assertEquals("{\"abc\":\"ABC\",\"def\":\"DEF\"}", obj.toJsonString());

        obj.push(new JsonString("null"),null);
        assertEquals(3,obj.size());
        assertEquals("{\"abc\":\"ABC\",\"def\":\"DEF\",\"null\":null}", obj.toJsonString());

        obj.push("null2",JsonNull.NULL);
        assertEquals(4,obj.size());
        assertEquals("{\"abc\":\"ABC\",\"def\":\"DEF\",\"null\":null,\"null2\":null}", obj.toJsonString());

        String[] array = {"a","b","c"};
        obj.push("array",array);
        assertEquals(5,obj.size());
        assertEquals(
                "{\"abc\":\"ABC\",\"def\":\"DEF\",\"null\":null,\"null2\":null,\"array\":[\"a\",\"b\",\"c\"]}",
                obj.toJsonString());

        Map<Object,Object> map = new LinkedHashMap<>();
        map.put("1","2");
        obj.push("map",map);
        assertEquals(6,obj.size());
        assertEquals(
                "{\"abc\":\"ABC\",\"def\":\"DEF\",\"null\":null,\"null2\":null,\"array\":[\"a\",\"b\",\"c\"],\"map\":{\"1\":\"2\"}}",
                obj.toJsonString());

        assertTrue(obj.get("abc") instanceof JsonString);
        assertTrue(obj.get("def") instanceof JsonString);
        assertTrue(obj.get("null") instanceof JsonNull);
        assertTrue(obj.get("null2") instanceof JsonNull);
        assertTrue(obj.get("array") instanceof JsonArray);
        assertEquals("[\"a\",\"b\",\"c\"]",obj.getJsonString("array"));
        assertTrue(obj.get("map") instanceof JsonObject);
        assertEquals("{\"1\":\"2\"}",obj.getJsonString("map"));
    }
}
