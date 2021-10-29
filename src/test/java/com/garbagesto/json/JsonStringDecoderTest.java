package com.garbagesto.json;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class JsonStringDecoderTest {
    
    @Test
    public void javeEncode() throws JsonException{

        JsonValue<?> test = null;
        JsonArray array = null;
        
        test = JsonStringDecoder.decodeJavaString("  abc  ");
        assertTrue(test instanceof JsonString );
        assertEquals("  abc  ", test.getValue());
        test = JsonStringDecoder.decodeJavaString("[]");
        assertTrue(test instanceof JsonArray);
        array = (JsonArray)test;
        assertEquals(0, array.size());

        test = JsonStringDecoder.decodeJavaString("[\"A\\rA\",\"A\\nA\",\"A\\fA\",\"A\\tA\",\"A\\bA\",\"A\\u304bA\"]");
        assertTrue(test instanceof JsonArray);
        array = (JsonArray)test;
        assertEquals(6, array.size());
        assertEquals("A\rA", array.get(0).getValue());
        assertEquals("A\nA", array.get(1).getValue());
        assertEquals("A\fA", array.get(2).getValue());
        assertEquals("A\tA", array.get(3).getValue());
        assertEquals("A\bA", array.get(4).getValue());
        assertEquals("A\u304bA", array.get(5).getValue());

        test = JsonStringDecoder.decodeJavaString("[\"aaa\",\"bbb\",false,null,10,+1.0e+1]");
        assertTrue(test instanceof JsonArray);
        array = (JsonArray)test;
        assertEquals(6, array.size());
        assertEquals("aaa", array.get(0).getValue());
        assertEquals("bbb", array.get(1).getValue());
        assertEquals(JsonBoolean.FALSE, array.get(2));
        assertTrue(array.get(3) instanceof JsonNull );
        assertEquals(new JsonNumber(10), array.get(4));
        assertEquals(new JsonNumber("1.0e+1"), array.get(5));


        test = JsonStringDecoder.decodeJavaString("    [    \"aaa\"   ,    \"bbb\" ,[\"ccc\"],{\"ddd\":\"eee\"}   ]    ");
        assertTrue(test instanceof JsonArray);
        array = (JsonArray)test;
        assertEquals(4, array.size());
        assertEquals("aaa", array.get(0).getValue());
        assertEquals("bbb", array.get(1).getValue());
        assertTrue( array.get(2) instanceof JsonArray );
        JsonArray subArray = (JsonArray)array.get(2);
        assertEquals(1, subArray.size());
        assertEquals("ccc", subArray.get(0).getValue());
        assertTrue( array.get(3) instanceof JsonObject );
        JsonObject subObject = (JsonObject)array.get(3);
        assertEquals(1, subObject.size());
        assertEquals("eee", subObject.get(new JsonString("ddd")).getValue());

        test = JsonStringDecoder.decodeJavaString("  {   \"key1\" : \"string1\" , \"key2\":\"string2\",\"keyArray\":[\"test\"],\"keyObject\":{\"keysub\":\"subtest\"} } ");
    
        try{
            JsonStringDecoder.decode("[");
            fail();
        }catch(JsonException e){
            assertEquals(e.getError(), JsonException.ERROR.UNMATCH_ARRAY_BLANKET);
        }
        try{
            JsonStringDecoder.decode("{");
            fail();
        }catch(JsonException e){
            assertEquals(e.getError(), JsonException.ERROR.UNMATCH_OBJECT_BLANKET);
        }
        try{
            JsonStringDecoder.decode("[]aaa");
            fail();
        }catch(JsonException e){
            assertEquals(e.getError(), JsonException.ERROR.UNEXPECTED_CHAR_ARRAY);
            assertEquals(e.getErrorValue(), "[]aaa");
        }
        try{
            JsonStringDecoder.decode("{}aaa");
            fail();
        }catch(JsonException e){
            assertEquals(e.getError(), JsonException.ERROR.UNEXPECTED_CHAR_OBJECT);
            assertEquals(e.getErrorValue(), "{}aaa");
        }
        try{
            JsonStringDecoder.decode("{\"aaa\":\"bbb\"10}");
            fail();
        }catch(JsonException e){
            assertEquals(e.getError(), JsonException.ERROR.UNEXPECTED_CHAR_OBJECT_VALUE);
            assertEquals(e.getErrorValue(), "10}");
        }
        try{
            JsonStringDecoder.decode("[\"aaa\"10]");
            fail();
        }catch(JsonException e){
            assertEquals(e.getError(), JsonException.ERROR.UNEXPECTED_CHAR_ARRAY_VALUE);
            assertEquals(e.getErrorValue(), "10]");
        }
        try{
            JsonStringDecoder.decode("AAA");
            fail();
        }catch(JsonException e){
            assertEquals(e.getError(), JsonException.ERROR.ILLEGAL_JSON_LITERAL);
            assertEquals(e.getErrorValue(), "AAA");
        }
        try{
            JsonStringDecoder.decode("{20:10}");
            fail();
        }catch(JsonException e){
            assertEquals(e.getError(), JsonException.ERROR.UNMATCH_TYPE_JSON_KEY);
            assertEquals(e.getErrorValue(), "20");
        }
        try{
            JsonStringDecoder.decode("{AAA:10}");
            fail();
        }catch(JsonException e){
            assertEquals(e.getError(), JsonException.ERROR.NO_JSON_OBJECT_KEY);
            assertEquals(e.getErrorValue(), "AAA:10}");
        }
        try{
            JsonStringDecoder.decode("{\"AAA\":}");
            fail();
        }catch(JsonException e){
            assertEquals(e.getError(), JsonException.ERROR.NO_JSON_OBJECT_VALUE);
            assertEquals(e.getErrorValue(), "\"AAA\"");
        }
        try{
            JsonStringDecoder.decode("{\"A\\rA\"}");
            fail();
        }catch(JsonException e){
            assertEquals(e.getError(), JsonException.ERROR.NO_JSON_OBJECT_KEY_VALUE_SEPARATOR);
            assertEquals(e.getErrorValue(), "\"A\\rA\"");
        }
        try{
            JsonStringDecoder.decode("\"\\u123\"");
            fail();
        }catch(JsonException e){
            assertEquals(e.getError(), JsonException.ERROR.ILLEGAL_UNICODE_VALUE);
            assertEquals(e.getErrorValue(), "\\u123");
        }
        try{
            JsonStringDecoder.decode("\"\\uXXXXAAA\"");
            fail();
        }catch(JsonException e){
            assertEquals(e.getError(), JsonException.ERROR.ILLEGAL_UNICODE_VALUE);
            assertEquals(e.getErrorValue(), "\\uXXXX");
        }
        try{
            JsonStringDecoder.decode("[\"AAA\\zAAA\"]");
            fail();
        }catch(JsonException e){
            assertEquals(e.getError(), JsonException.ERROR.ILLEGAL_STRING_LITERAL);
            assertEquals(e.getErrorValue(), "\"AAA\\zAAA\"");
        }
    }
    
}
