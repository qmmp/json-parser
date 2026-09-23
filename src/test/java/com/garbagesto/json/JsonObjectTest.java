package com.garbagesto.json;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

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
        map.put("num1",1);
        map.put("num2","2");
        obj.push("map",map);
        assertEquals(6,obj.size());
        assertEquals(
                "{\"abc\":\"ABC\",\"def\":\"DEF\",\"null\":null,\"null2\":null,\"array\":[\"a\",\"b\",\"c\"],\"map\":{\"num1\":1,\"num2\":\"2\"}}",
                obj.toJsonString());

        assertTrue(obj.get("abc") instanceof JsonString);
        assertNull(obj.get("abc",JsonNumber.class));
        assertEquals(obj.get("abc"), obj.get("abc",JsonString.class));
        assertEquals("ABC", obj.getJsonString("abc"));
        assertNull(obj.get("abc",JsonArray.class));
        assertNull(obj.get("abc",JsonObject.class));
        assertTrue(obj.get("def") instanceof JsonString);
        assertTrue(obj.get("null") instanceof JsonNull);
        assertTrue(obj.get("null2") instanceof JsonNull);
        assertNull(obj.get("undefined"));
        assertNull(obj.getJsonString("undefined"));
        assertNull(obj.getJsonNumber("undefined"));
        assertTrue(obj.get("array") instanceof JsonArray);
        assertEquals("[\"a\",\"b\",\"c\"]",obj.getJsonString("array"));
        assertTrue(obj.get("map") instanceof JsonObject);
        assertEquals("{\"num1\":1,\"num2\":\"2\"}",obj.getJsonString("map"));
        JsonObject jsonObject = (JsonObject)obj.get("map");
        assertEquals(new BigDecimal(1), jsonObject.getJsonNumber("num1"));
        assertEquals(null, jsonObject.getJsonNumber("num2"));
    }

    @Test
    public void mapMethodTest() throws Exception{
        JsonObject obj = new JsonObject();
        obj.push("test1", 10);        
        obj.push("test2", 20);        
        obj.push("test3", 30);
        
        Set<JsonString> keys = obj.keySet();
        assertEquals(3, keys.size());
        assertTrue(keys.contains(new JsonString("test1")));
        assertTrue(keys.contains(new JsonString("test2")));
        assertTrue(keys.contains(new JsonString("test3")));

        Collection<JsonValue<?>> values = obj.values();
        assertEquals(3, values.size());
        assertTrue(values.contains(new JsonNumber("10")));
        assertTrue(values.contains(new JsonNumber(20)));
        assertTrue(values.contains(new JsonNumber(30L)));

        Set<Entry<JsonString,JsonValue<?>>> entrySet = obj.entrySet();
        assertEquals(3, entrySet.size());
        for( Entry<JsonString,JsonValue<?>> entry: entrySet){
            if( entry.getKey().equals(new JsonString("test1")) ){
                assertEquals(new JsonNumber(10), entry.getValue());
            }else if( entry.getKey().equals(new JsonString("test2")) ){
                assertEquals(new JsonNumber(20), entry.getValue());
            }else if( entry.getKey().equals(new JsonString("test3")) ){
                assertEquals(new JsonNumber(30), entry.getValue());
            }else{
                fail();
            }
        }

    }
}
