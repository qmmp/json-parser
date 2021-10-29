package com.garbagesto.json;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class JsonNumberTest {
    
    @Test
    public void getJsonString(){
        assertEquals("10", new JsonNumber(10).toJsonString());
        assertEquals("10", new JsonNumber(10L).toJsonString());
        assertEquals("10.0", new JsonNumber(10.0).toJsonString());
        assertEquals("10.0", new JsonNumber(10.0d).toJsonString());
        assertEquals("10.0", new JsonNumber(10.00).toJsonString());
        assertEquals("10.0", new JsonNumber(10.00d).toJsonString());
        assertEquals("100.0", new JsonNumber(1e+2).toJsonString());
        assertEquals("10", new JsonNumber(new BigDecimal(10)).toJsonString());
        assertEquals("10.00", new JsonNumber(new BigDecimal("10.00")).toJsonString());
        assertEquals("10.00", new JsonNumber("10.00").toJsonString());
        assertEquals("10", new JsonNumber("10").toJsonString());
        assertEquals("1E+1", new JsonNumber("1e+1").toJsonString());
    }

    @Test
    public void getValue(){
        assertEquals(Long.valueOf(10), new JsonNumber(10).getValue());
        assertEquals(Long.valueOf(100L), new JsonNumber(100).getValue());
        assertEquals(Double.valueOf(10.0), new JsonNumber(10.0).getValue());
        assertEquals(Double.valueOf(100.0), new JsonNumber(100.0d).getValue());
        assertEquals(Double.valueOf(100.0), new JsonNumber(1e+2).getValue());
        assertEquals(new BigDecimal(10.00), new JsonNumber(new BigDecimal(10.00)).getValue());
    }
}

