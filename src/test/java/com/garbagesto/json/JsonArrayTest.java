package com.garbagesto.json;

import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class JsonArrayTest {

    @Test
    public void pushTest(){

        JsonArray array = new JsonArray();
        assertEquals("[]", array.toJsonString());
        assertEquals(0, array.size());

        JsonArray retV = array.push("abc");
        assertSame( array , retV );
        assertEquals("[\"abc\"]", array.toJsonString());
        assertEquals(1, array.size());

        array.push(100);
        assertEquals("[\"abc\",100]", array.toJsonString());
        assertEquals(2, array.size());

        array.push(false);
        assertEquals("[\"abc\",100,false]", array.toJsonString());
        assertEquals(3, array.size());

        array.push(null);
        assertEquals("[\"abc\",100,false,null]", array.toJsonString());
        assertEquals(4, array.size());

        array.push(new ArrayList<String>());
        assertEquals("[\"abc\",100,false,null,[]]", array.toJsonString());
        assertEquals(5, array.size());

        List<Object> childList = new ArrayList<>();
        childList.add("ABC");
        array.push(childList);
        assertEquals("[\"abc\",100,false,null,[],[\"ABC\"]]", array.toJsonString());
        assertEquals(6, array.size());

        Map<String,String> childMap = new LinkedHashMap<>();
        childMap.put("def","DEF");
        childMap.put("ghi","GHI");
        array.push(childMap);
        assertEquals("[\"abc\",100,false,null,[],[\"ABC\"],{\"def\":\"DEF\",\"ghi\":\"GHI\"}]", array.toJsonString());
        assertEquals(7, array.size());

        assertTrue(array.get(0) instanceof JsonString);
        assertEquals("\"abc\"", array.get(0).toJsonString());
        assertTrue(array.getValue(0) instanceof String);
        assertEquals("abc", array.getValue(0));
        assertEquals("\"abc\"", array.getJsonString(0));

        assertTrue(array.get(1) instanceof JsonNumber);
        assertEquals("100", array.get(1).toJsonString());
        assertTrue(array.getValue(1) instanceof Number);
        assertEquals(100, array.getValue(1));
        assertEquals("100", array.getJsonString(1));

        assertTrue(array.get(2) instanceof JsonBoolean);
        assertEquals("false", array.get(2).toJsonString());
        assertTrue(array.getValue(2) instanceof Boolean);
        assertEquals(Boolean.FALSE, array.getValue(2));
        assertEquals("false", array.getJsonString(2));

        assertTrue(array.get(3) instanceof JsonNull);
        assertEquals("null", array.get(3).toJsonString());
        assertNull(array.getValue(3));
        assertEquals("null", array.getJsonString(3));

        assertTrue(array.get(4) instanceof JsonArray);
        assertEquals("[]", array.get(4).toJsonString());
        assertTrue(array.getValue(4) instanceof List);
        assertEquals(0, ((List<?>) array.getValue(4)).size());
        assertEquals("[]", array.getJsonString(4));

        assertTrue(array.get(5) instanceof JsonArray);
        assertEquals("[\"ABC\"]", array.get(5).toJsonString());
        assertTrue(array.getValue(5) instanceof List);
        assertEquals(1, ((List<?>) array.getValue(5)).size());
        assertEquals("[\"ABC\"]", array.getJsonString(5));

        assertTrue(array.get(6) instanceof JsonObject);
        assertEquals("{\"def\":\"DEF\",\"ghi\":\"GHI\"}", array.get(6).toJsonString());
        assertTrue(array.getValue(6) instanceof Map);
        assertEquals(2, ((Map<?,?>) array.getValue(6)).size());
        assertEquals("{\"def\":\"DEF\",\"ghi\":\"GHI\"}", array.getJsonString(6));

        array.clear();
        assertEquals("[]", array.toJsonString());
        assertEquals(0, array.size());

    }
}
