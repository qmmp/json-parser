package com.garbagesto.json;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class JsonNumberTest {

    @Test
    public void testSetValue(){
        JsonNumber n = new JsonNumber();
        assertEquals(BigDecimal.ZERO, n.getValue());
        assertEquals("0",n.toJsonString());

        assertEquals(new BigDecimal(100),n.setValue(100).getValue());
        assertEquals(new BigDecimal(100),n.getValue());
        assertEquals("100", n.toJsonString());

        assertEquals(new BigDecimal(100),n.setValue(100L).getValue());
        assertEquals(new BigDecimal(100),n.getValue());
        assertEquals("100", n.toJsonString());

        assertEquals(new BigDecimal("1000.1"),n.setValue(new BigDecimal("1000.1")).getValue());
        assertEquals(new BigDecimal("1000.1"),n.getValue());
        assertEquals("1000.1", n.toJsonString());

        n.setValue(100.1f);
        assertEquals(new BigDecimal("100.1"),n.getValue());
        assertEquals("100.1", n.toJsonString());

        n.setValue(100.1d);
        assertEquals(new BigDecimal("100.1"),n.getValue());
        assertEquals("100.1", n.toJsonString());

        n.setValue("100");
        assertEquals(new BigDecimal("100"),n.getValue());
        assertEquals("100", n.toJsonString());

        n.setValue("101e+10");
        assertEquals(new BigDecimal("1.01e+12"),n.getValue());
        assertEquals("1010000000000", n.toJsonString());

        n.setValue("-10");
        assertEquals(new BigDecimal("-10"),n.getValue());
        assertEquals("-10", n.toJsonString());

        n.setValue("101e-10");
        assertEquals(new BigDecimal("1.01e-8"),n.getValue());
        assertEquals("0.0000000101",n.toJsonString());
    }

}
