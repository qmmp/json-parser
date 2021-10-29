package com.garbagesto.json;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class JsonStringTest{

    @Test
    public void toJsonString(){
        JsonString normal = new JsonString("test");
        assertEquals("\"test\"", normal.toJsonString());
        JsonString special = new JsonString("\"test\"\rcarige return\nline feed");
        assertEquals("\"\\\"test\\\"\\rcarige return\\nline feed\"", special.toJsonString());
    }

    @Test
    public void getValue(){
        String testValue = "\"test\"\rcarige return\nline feed";
        JsonString test = new JsonString(testValue);
        assertEquals(testValue, test.getValue());
    }
    
}
