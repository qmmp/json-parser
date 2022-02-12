package com.garbagesto.json.util;

import com.garbagesto.json.*;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class MappingTest {

    @Test
    public void nullMappingTest(){
        JsonValue<?> v = JsonMapper.mapping(null);
        assertTrue(v instanceof JsonNull);
    }

    @Test
    public void stringMappingTest(){
        {
            JsonValue<?> v = JsonMapper.mapping("abcd");
            assertTrue(v instanceof JsonString);
            assertEquals("abcd", v.getValue());
        }
        {
            Object actual = new Object();
            JsonValue<?> v = JsonMapper.mapping(actual);
            assertTrue(v instanceof JsonString);
            assertEquals(actual.toString(), v.getValue());
        }
    }

    @Test
    public void numberMappingTest(){
        {
            JsonValue<?> v = JsonMapper.mapping(new BigDecimal(100));
            assertTrue(v instanceof JsonNumber);
            assertEquals(new BigDecimal(100),v.getValue());
        }
        {
            JsonValue<?> v = JsonMapper.mapping(new Integer(100));
            assertTrue(v instanceof JsonNumber);
            assertEquals(new Integer(100),v.getValue());
        }
        {
            JsonValue<?> v = JsonMapper.mapping(new Long(100));
            assertTrue(v instanceof JsonNumber);
            assertEquals(new Long(100),v.getValue());
        }
        {
            JsonValue<?> v = JsonMapper.mapping(new BigInteger("100000"));
            assertTrue(v instanceof JsonNumber);
            assertEquals(new BigInteger("100000"),v.getValue());
        }
        {
            JsonValue<?> v = JsonMapper.mapping(new Float(100.1));
            assertTrue(v instanceof JsonNumber);
            assertEquals(new Float(100.1),v.getValue());
        }
        {
            JsonValue<?> v = JsonMapper.mapping(new Double(100.1));
            assertTrue(v instanceof JsonNumber);
            assertEquals(new Double(100.1),v.getValue());
        }
    }

    @Test
    public void booleanMappingTest(){
        JsonValue<?> v = JsonMapper.mapping(true);
        assertTrue(v instanceof JsonBoolean);
        assertTrue(((JsonBoolean) v).getValue());

        v = JsonMapper.mapping(Boolean.TRUE);
        assertTrue(v instanceof JsonBoolean);
        assertTrue(((JsonBoolean) v).getValue());

        v = JsonMapper.mapping(false);
        assertTrue(v instanceof JsonBoolean);
        assertFalse(((JsonBoolean) v).getValue());

        v = JsonMapper.mapping(Boolean.FALSE);
        assertTrue(v instanceof JsonBoolean);
        assertFalse(((JsonBoolean) v).getValue());
    }

    @Test
    public void arrayMappingTest(){
        ArrayList<Object> sourceList = new ArrayList<>();
        sourceList.add("abc");
        sourceList.add(new JsonString("ABC"));
        JsonValue<?> v = JsonMapper.mapping(sourceList);
        assertTrue(v instanceof JsonArray);
        assertEquals(2, ((JsonArray) v).size());
        assertEquals("[\"abc\",\"ABC\"]", v.toJsonString());

        Object[] sourceArray = {"def","DEF",null,100};
        v = JsonMapper.mapping(sourceArray);
        assertTrue(v instanceof JsonArray);
        assertEquals(4, ((JsonArray) v).size());
        assertEquals("[\"def\",\"DEF\",null,100]", v.toJsonString());
    }

    @Test
    public void objectMappingTest(){
        Object key = new Object();
        Map<Object,Object> source = new LinkedHashMap<>();
        source.put("abc","ABC");
        source.put(new JsonString("def"), "DEF");
        source.put(key, 100);
        source.put("null", JsonNull.NULL);
        JsonValue<?> v = JsonMapper.mapping(source);
        assertTrue(v instanceof JsonObject);
        assertEquals("{\"abc\":\"ABC\",\"def\":\"DEF\",\""+key.toString()+"\":100,\"null\":null}",v.toJsonString());
        assertEquals(4, ((JsonObject) v).size());
        assertEquals(((JsonObject) v).get("abc"),new JsonString("ABC"));
        assertEquals(((JsonObject) v).getValue("abc"),"ABC");
        assertEquals(((JsonObject) v).get("def"),new JsonString("DEF"));
        assertEquals(((JsonObject) v).getValue("def"),"DEF");
        assertEquals(((JsonObject) v).get(key.toString()),new JsonNumber(100));
        assertEquals(((JsonObject) v).getValue(key.toString()),new Integer(100));
        assertTrue(((JsonObject) v).get("null") instanceof JsonNull);
        assertNull(((JsonObject) v).getValue("null"));
    }
}
