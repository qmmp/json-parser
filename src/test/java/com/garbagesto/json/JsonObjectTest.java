package com.garbagesto.json;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class JsonObjectTest {

    @Test
    public void toJsonString(){
        JsonObject test = new JsonObject();
        assertEquals("{}", test.toJsonString());
        test.push(new JsonString("test string"), new JsonString("test value"))
            .push(new JsonString("test number"), new JsonNumber(10))
            .push(new JsonString("test array"),new JsonArray().push(new JsonString("array")))
            .push(new JsonString("test object"),new JsonObject());
        assertEquals("{\"test string\":\"test value\",\"test number\":10,\"test array\":[\"array\"],\"test object\":{}}", test.toJsonString());
    }

    @Test
    public void getValue(){
        JsonObject test = new JsonObject();
        assertEquals(0, test.size());
        test.push(new JsonString("test string"), new JsonString("test value"))
            .push(new JsonString("test number"), new JsonNumber(10))
            .push(new JsonString("test array"),new JsonArray().push(new JsonString("array")))
            .push(new JsonString("test object"),new JsonObject());
        Map<String, Object> val = test.getValue();
        assertEquals(4, val.size());
        assertEquals("test value", val.get("test string"));
        assertEquals(Long.valueOf(10), val.get("test number"));
        JsonValue<?>[] array = (JsonValue<?>[])val.get("test array");
        assertEquals(1, array.length);
        assertEquals(new JsonString("array"), array[0]);
        Map<?, ?> obj = (Map<?,?>)val.get("test object");
        assertEquals(0, obj.size());
    }
}
