package com.garbagesto.json;

import org.junit.Test;
import static org.junit.Assert.*;

public class JsonBooleanTest {

    @Test
    public void setValueTest(){
        JsonBoolean b = new JsonBoolean();
        assertFalse(b.getValue());
        assertEquals("false",b.toJsonString());

        b.setValue(true);
        assertTrue(b.getValue());
        assertEquals("true",b.toJsonString());

        b.setValue(false);
        assertFalse(b.getValue());
        assertEquals("false",b.toJsonString());

        b.setValue(Boolean.TRUE);
        assertTrue(b.getValue());
        assertEquals("true",b.toJsonString());

        b.setValue(Boolean.FALSE);
        assertFalse(b.getValue());
        assertEquals("false",b.toJsonString());
    }
}
