package com.garbagesto.json.util;

import com.garbagesto.json.*;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;


public class ParserTest {

    @Test
    public void illegalStringTest(){
        try{
            JsonParser.parse("   ,   ");
            fail("expect Exception");
        }catch(JsonParseException e){
            assertEquals(JsonParseException.MESSAGE.UNEXPECTED_CHARACTER.format(",",3),e.getMessage());
        }
        try{
            JsonParser.parse("   x   ");
            fail("expect Exception");
        }catch(JsonParseException e){
            assertEquals(JsonParseException.MESSAGE.UNEXPECTED_CHARACTER.format("x",3),e.getMessage());
        }
        try{
            JsonParser.parse("   ]},   ");
            fail("expect Exception");
        }catch(JsonParseException e){
            assertEquals(JsonParseException.MESSAGE.UNEXPECTED_CHARACTER.format("]",3),e.getMessage());
        }
        try{
            JsonParser.parse("   },   ");
            fail("expect Exception");
        }catch(JsonParseException e){
            assertEquals(JsonParseException.MESSAGE.UNEXPECTED_CHARACTER.format("}",3),e.getMessage());
        }
        try{
            JsonParser.parse("\"123\"\"456\"");
            fail("expect Exception");
        }catch(JsonParseException e){
            assertEquals("illegal next value at 5",e.getMessage());
        }
        try{
            JsonParser.parse("\"123\"   \"456\"");
            fail("expect Exception");
        }catch(JsonParseException e){
            assertEquals("illegal next value at 5",e.getMessage());
        }
    }

    @Test
    public void nullParseTest(){
        try{
            JsonValue<?> v = JsonParser.parse("    null    ");
            assertTrue(v instanceof JsonNull);
        }catch(JsonParseException e) {
            fail(e.getMessage());
        }
        try{
            JsonParser.parse("       nUll    ");
            fail("expect Exception");
        }catch(JsonParseException e) {
            assertEquals(JsonParseException.MESSAGE.ILLEGAL_NULL_LITERAL.format("nUll",7),e.getMessage());
        }
        try{
            JsonParser.parse("       Null    ");
            fail("expect Exception");
        }catch(JsonParseException e) {
            assertEquals(JsonParseException.MESSAGE.UNEXPECTED_CHARACTER.format("N",7),e.getMessage());
        }
    }

    @Test
    public void booleanParseTest(){
        try{
            JsonValue<?> v = JsonParser.parse("    true    ");
            assertTrue(v instanceof JsonBoolean);
            assertTrue(((JsonBoolean) v).getValue());
        }catch(JsonParseException e) {
            fail(e.getMessage());
        }
        try{
            JsonValue<?> v = JsonParser.parse("    false    ");
            assertTrue(v instanceof JsonBoolean);
            assertFalse(((JsonBoolean) v).getValue());
        }catch(JsonParseException e) {
            fail(e.getMessage());
        }
        try{
            JsonParser.parse("       tRue    ");
            fail("expect Exception");
        }catch(JsonParseException e) {
            assertEquals(JsonParseException.MESSAGE.ILLEGAL_BOOLEAN_LITERAL.format("tRue",7),e.getMessage());
        }
        try{
            JsonParser.parse("       fAlse    ");
            fail("expect Exception");
        }catch(JsonParseException e) {
            assertEquals(JsonParseException.MESSAGE.ILLEGAL_BOOLEAN_LITERAL.format("fAlse",7),e.getMessage());
        }
    }

    @Test
    public void stringParseTest(){

        try{
            JsonValue<?> v = JsonParser.parse("\"\"");
            assertTrue(v instanceof JsonString);
            assertEquals("", v.getValue());
            assertEquals("\"\"", v.toJsonString());

            v = JsonParser.parse("\"abc\"");
            assertTrue(v instanceof JsonString);
            assertEquals("abc", v.getValue());
            assertEquals("\"abc\"", v.toJsonString());

            String value = "a\"b\\c/d\be\ff\ng\rh\ti";
            String jsonV = "\"a\\\"b\\\\c\\/d\\be\\ff\\ng\\rh\\ti\"";
            v = JsonParser.parse(jsonV);
            assertTrue(v instanceof JsonString);
            assertEquals(value, v.getValue());
            assertEquals(jsonV, v.toJsonString());

            v = JsonParser.parse("     \r\n\t    "+jsonV+ "    \r\n\t   ");
            assertTrue(v instanceof JsonString);
            assertEquals(value, v.getValue());
            assertEquals(jsonV, v.toJsonString());

        }catch(JsonParseException e){
            fail(e.getMessage());
        }
        try{
            JsonParser.parse("\"abc");
            fail("expect Exception");
        }catch(JsonParseException e){
            assertEquals(JsonParseException.MESSAGE.ILLEGAL_STRING_LITERAL.format("\"abc",3),e.getMessage());
            assertEquals(4,"\"abc".length());
        }
    }

    @Test
    public void numberParseTest(){
        try{
            JsonValue<?> v = JsonParser.parse("   123  ");
            assertTrue(v instanceof JsonNumber);
            assertEquals("123",v.toJsonString());
            assertEquals(new BigDecimal(123),v.getValue());

            v = JsonParser.parse("-123");
            assertTrue(v instanceof JsonNumber);
            assertEquals("-123",v.toJsonString());
            assertEquals(new BigDecimal(-123),v.getValue());

            v = JsonParser.parse("-123.10");
            assertTrue(v instanceof JsonNumber);
            assertEquals("-123.10",v.toJsonString());
            assertEquals(new BigDecimal("-123.10"),v.getValue());

            v = JsonParser.parse("123.10");
            assertTrue(v instanceof JsonNumber);
            assertEquals("123.10",v.toJsonString());
            assertEquals(new BigDecimal("123.10"),v.getValue());

            v = JsonParser.parse("123e+2");
            assertTrue(v instanceof JsonNumber);
            assertEquals("1.23E+4",v.toJsonString());
            assertEquals(new BigDecimal("1.23e+4"),v.getValue());

            v = JsonParser.parse("123e-2");
            assertTrue(v instanceof JsonNumber);
            assertEquals("1.23",v.toJsonString());
            assertEquals(new BigDecimal("1.23"),v.getValue());

            v = JsonParser.parse("123e-10");
            assertTrue(v instanceof JsonNumber);
            assertEquals("1.23E-8",v.toJsonString());
            assertEquals(new BigDecimal("1.23e-8"),v.getValue());
        }catch(JsonParseException e){
            fail(e.getMessage());
        }
        try{
            JsonParser.parse("123.9.1");
            fail("expect Exception");
        }catch(JsonParseException e){
            assertEquals(JsonParseException.MESSAGE.ILLEGAL_NUMBER_FORMAT.format("123.9.",5), e.getMessage());
        }
        try{
            JsonParser.parse("123.9e1");
            fail("expect Exception");
        }catch(JsonParseException e){
            assertEquals(JsonParseException.MESSAGE.ILLEGAL_NUMBER_FORMAT.format("123.9e1",6), e.getMessage());
        }
        try{
            JsonParser.parse("123.9e++1");
            fail("expect Exception");
        }catch(JsonParseException e){
            assertEquals(JsonParseException.MESSAGE.ILLEGAL_NUMBER_FORMAT.format("123.9e+",0), e.getMessage());
        }
        try{
            JsonParser.parse("123.9e+-1");
            fail("expect Exception");
        }catch(JsonParseException e){
            assertEquals(JsonParseException.MESSAGE.ILLEGAL_NUMBER_FORMAT.format("123.9e+",0), e.getMessage());
        }
        try{
            JsonParser.parse("-+123.9e+-1");
            fail("expect Exception");
        }catch(JsonParseException e){
            assertEquals(JsonParseException.MESSAGE.ILLEGAL_NUMBER_FORMAT.format("-",0), e.getMessage());
        }
        try{
            JsonParser.parse("123.9e+1.4");
            fail("expect Exception");
        }catch(JsonParseException e){
            assertEquals(JsonParseException.MESSAGE.ILLEGAL_NUMBER_FORMAT.format("123.9e+1.",8), e.getMessage());
        }
        try{
            JsonValue<?> v = JsonParser.parse("100a");
            fail("expect Exception;"+v.getValue());
        }catch(JsonParseException e){
            assertEquals(JsonParseException.MESSAGE.UNEXPECTED_CHARACTER.format("a",3), e.getMessage());
        }
    }

    @Test
    public void arrayParseTest() {
        try {
            JsonValue<?> v = JsonParser.parse("    []    ");
            assertTrue(v instanceof JsonArray);
            assertEquals(0, ((JsonArray) v).size());

            v = JsonParser.parse("    [true]    ");
            assertTrue(v instanceof JsonArray);
            assertEquals(1, ((JsonArray) v).size());
            assertTrue(((JsonArray) v).get(0) instanceof JsonBoolean);
            assertEquals(Boolean.TRUE, ((JsonArray) v).getValue(0));

            v = JsonParser.parse("    [true,null]    ");
            assertTrue(v instanceof JsonArray);
            assertEquals(2, ((JsonArray) v).size());
            assertTrue(((JsonArray) v).get(0) instanceof JsonBoolean);
            assertEquals(Boolean.TRUE, ((JsonArray) v).getValue(0));
            assertTrue(((JsonArray) v).get(1) instanceof JsonNull);

            v = JsonParser.parse("    [true,null,]    ");
            assertTrue(v instanceof JsonArray);
            assertEquals(2, ((JsonArray) v).size());
            assertTrue(((JsonArray) v).get(0) instanceof JsonBoolean);
            assertEquals(Boolean.TRUE, ((JsonArray) v).getValue(0));
            assertTrue(((JsonArray) v).get(1) instanceof JsonNull);

            v = JsonParser.parse("    [true,null,[\"abc\"],]    ");
            assertTrue(v instanceof JsonArray);
            assertEquals(3, ((JsonArray) v).size());
            assertTrue(((JsonArray) v).get(0) instanceof JsonBoolean);
            assertEquals(Boolean.TRUE, ((JsonArray) v).getValue(0));
            assertTrue(((JsonArray) v).get(1) instanceof JsonNull);
            assertTrue(((JsonArray) v).get(2) instanceof JsonArray);
            assertEquals("abc", ((JsonArray) ((JsonArray) v).get(2)).getValue(0));

            v = JsonParser.parse("    [null,{\"abc\":\"ABC\"}]    ");
            assertTrue(v instanceof JsonArray);
            assertEquals(2, ((JsonArray) v).size());
            assertTrue(((JsonArray) v).get(0) instanceof JsonNull);
            assertTrue(((JsonArray) v).get(1) instanceof JsonObject);
            assertEquals("ABC", ((JsonObject) ((JsonArray) v).get(1)).getValue("abc"));
        }catch(JsonParseException e) {
            fail(e.getMessage());
        }
        try{
            JsonValue<?> v = JsonParser.parse("[123,[456]");
            fail("expect Exception;"+v.getValue());
        }catch(JsonParseException e){
            assertEquals("array end bracket not found at 0 to 9", e.getMessage());
        }
        try{
            JsonValue<?> v = JsonParser.parse("{\"abc\":[123,[456]}");
            fail("expect Exception;"+v.getValue());
        }catch(JsonParseException e){
            assertEquals("array end bracket not found at 7 to 17", e.getMessage());
        }
    }
    @Test
    public void objectParseTest() {
        try{
            JsonValue<?> v = JsonParser.parse("    {}   ");
            assertTrue(v instanceof JsonObject);
            assertEquals(0, ((JsonObject) v).size());

            v = JsonParser.parse("    {\"abc\":\"ABC\"}    ");
            assertTrue(v instanceof JsonObject);
            assertEquals(1, ((JsonObject) v).size());
            assertTrue(((JsonObject) v).get("abc") instanceof JsonString);
            assertEquals("ABC", ((JsonObject) v).getValue("abc"));

            v = JsonParser.parse("    {\"abc\":\"ABC\",\"null\":null}    ");
            assertTrue(v instanceof JsonObject);
            assertEquals(2, ((JsonObject) v).size());
            assertTrue(((JsonObject) v).get("abc") instanceof JsonString);
            assertEquals("ABC", ((JsonObject) v).getValue("abc"));
            assertTrue(((JsonObject) v).get("null") instanceof JsonNull);

            v = JsonParser.parse("    {\"abc\":\"ABC\",\"null\":null,\"array\":[\"1\",\"2\",],}    ");
            assertTrue(v instanceof JsonObject);
            assertEquals(3, ((JsonObject) v).size());
            assertTrue(((JsonObject) v).get("abc") instanceof JsonString);
            assertEquals("ABC", ((JsonObject) v).getValue("abc"));
            assertTrue(((JsonObject) v).get("null") instanceof JsonNull);
            assertTrue(((JsonObject) v).get("array") instanceof JsonArray);
            assertEquals(2, ((JsonArray) ((JsonObject) v).get("array")).size());
            assertEquals("1", ((JsonArray) ((JsonObject) v).get("array")).getValue(0));
            assertEquals("2", ((JsonArray) ((JsonObject) v).get("array")).getValue(1));

            v = JsonParser.parse("    {\"abc\":\"ABC\",\"null\":null,\"object\":{\"1\":1,\"2\":2,},}    ");
            assertTrue(v instanceof JsonObject);
            assertEquals(3, ((JsonObject) v).size());
            assertTrue(((JsonObject) v).get("abc") instanceof JsonString);
            assertEquals("ABC", ((JsonObject) v).getValue("abc"));
            assertTrue(((JsonObject) v).get("null") instanceof JsonNull);
            assertTrue(((JsonObject) v).get("object") instanceof JsonObject);
            assertEquals(2, ((JsonObject) ((JsonObject) v).get("object")).size());
            assertEquals(new BigDecimal(1), ((JsonObject) ((JsonObject) v).get("object")).getValue("1"));
            assertEquals(new BigDecimal(2), ((JsonObject) ((JsonObject) v).get("object")).getValue("2"));
        }catch(JsonParseException e) {
            fail(e.getMessage());
        }
    }
}

