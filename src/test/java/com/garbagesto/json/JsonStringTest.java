package com.garbagesto.json;

import org.junit.Test;
import static org.junit.Assert.*;

public class JsonStringTest {

    @Test
    public void testToJsonString(){
        JsonString str = new JsonString();
        assertEquals("\"\"", str.toJsonString());
        assertEquals("",str.getValue());
        {
            String value = "   test value  ";
            str.setValue(value);
            assertEquals("\""+value+"\"",str.toJsonString());
            assertEquals(value, str.getValue());
        }
        {
            String value = "a\"b\\c/d\be\ff\ng\rh\ti";
            String jsonV = "a\\\"b\\\\c\\/d\\be\\ff\\ng\\rh\\ti";
            str.setValue(value);
            assertEquals("\""+jsonV+"\"",str.toJsonString());
            assertEquals(value, str.getValue());
        }
    }

}
