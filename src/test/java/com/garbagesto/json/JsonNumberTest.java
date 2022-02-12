package com.garbagesto.json;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.junit.Assert.*;

public class JsonNumberTest {

    @Test
    public void testSetValue(){
        JsonNumber n = new JsonNumber();
        assertEquals(new Integer(0), n.getValue());
        assertEquals("0",n.toJsonString());

        n.setValue(100);
        assertEquals(new Integer(100),n.getValue());
        assertEquals("100", n.toJsonString());

        n.setValue(100L);
        assertEquals(new Long(100),n.getValue());
        assertEquals("100", n.toJsonString());

        n.setValue(new BigInteger("1000"));
        assertEquals(new BigInteger("1000"),n.getValue());
        assertEquals("1000", n.toJsonString());

        n.setValue(new BigDecimal("1000.1"));
        assertEquals(new BigDecimal("1000.1"),n.getValue());
        assertEquals("1000.1", n.toJsonString());

        n.setValue(100.1f);
        assertEquals(new Float(100.1),n.getValue());
        assertEquals("100.1", n.toJsonString());

        n.setValue(100.1d);
        assertEquals(new Double(100.1),n.getValue());
        assertEquals("100.1", n.toJsonString());

        n.setValue("100");
        assertEquals(new BigDecimal("100"),n.getValue());
        assertEquals("100", n.toJsonString());

        n.setValue("101e+10");
        assertEquals(new BigDecimal("1.01e+12"),n.getValue());
        assertEquals("1.01E+12", n.toJsonString());

        n.setValue("-10");
        assertEquals(new BigDecimal("-10"),n.getValue());
        assertEquals("-10", n.toJsonString());

        n.setValue("101e-10");
        assertEquals(new BigDecimal("1.01e-8"),n.getValue());
        assertEquals("1.01E-8",n.toJsonString());
    }

}
