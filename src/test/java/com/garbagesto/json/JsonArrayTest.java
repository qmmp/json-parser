package com.garbagesto.json;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class JsonArrayTest {

    @Test
    public void toJsonString(){
        JsonArray test = new JsonArray();
        assertEquals("[]", test.toJsonString());
        test.add(new JsonString("string"));
        assertEquals("[\"string\"]", test.toJsonString());
        test.push(new JsonNumber(10))
            .push(new JsonNumber(100))
            .push(new JsonString("test"))
            .push(new JsonArray().push(new JsonString("array")))
            .push(new JsonObject());
        assertEquals("[\"string\",10,100,\"test\",[\"array\"],{}]", test.toJsonString());
    }

    @Test
    public void getValue(){
        JsonArray test = new JsonArray();
        assertArrayEquals(new JsonValue[0], test.getValue());
        test.push(new JsonNumber(10))
            .push(new JsonNumber(100))
            .push(new JsonString("test"))
            .push(new JsonArray().push(new JsonString("array")))
            .push(new JsonObject());
        
        JsonValue<?>[] value = test.getValue();
        assertEquals(5, value.length);
        assertEquals(new JsonNumber(10), value[0]);
        assertEquals(new JsonNumber(100), value[1]);
        assertEquals(new JsonString("test"), value[2]);
        assertEquals("[\"array\"]", value[3].toJsonString());
        assertEquals("{}", value[4].toJsonString());
    }
}
