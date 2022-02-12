package com.garbagesto.json.util;

import com.garbagesto.json.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class JsonParser {

    public static JsonValue<?> parse(String str) throws JsonParseException {
        try(StringReader reader = new StringReader(str)){
            return parse(reader);
        }
    }
    public static JsonValue<?> parse(Reader reader) throws JsonParseException {
        InnerReader r = new InnerReader(reader);
        JsonValue<?> ret = innerParse(r,false,false);
        int count = r.count;
        if( innerParse(r,false,false) != null ){
            throw new JsonParseException(JsonParseException.MESSAGE.ILLEGAL_MULTI_VALUE,count);
        }
        return ret;
    }

    private static JsonValue<?> innerParse(InnerReader r, boolean array, boolean object) throws JsonParseException {
        String str;
        while ( (str = r.read1Str()) != null ) {
            if( str.trim().isEmpty() ){
                continue;
            }
            char c = str.charAt(0);
            if (c == '\"') {
                r.reset();
                return readJsonString(r);
            }else if( c == 't' || c == 'f'){
                r.reset();
                return readJsonBoolean(r);
            }else if( c == 'n' ) {
                r.reset();
                return readJsonNull(r);
            }else if( c == '-' || (c >= '0' && c <= '9' )) {
                r.reset();
                return readJsonNumber(r);
            }else if( c == '[' ){
                r.reset();
                return readJsonArray(r);
            }else if( c == ']' ){
                if( array ){
                    //array end bracket;
                    r.reset();
                    return null;
                }
                throw new JsonParseException(JsonParseException.MESSAGE.UNEXPECTED_CHARACTER,str,r.count-1);
            }else if( c == '{' ){
                r.reset();
                return readJsonObject(r);
            }else if( c == '}' ){
                if( object ){
                    r.reset();
                    return null;
                }
                throw new JsonParseException(JsonParseException.MESSAGE.UNEXPECTED_CHARACTER,str, (r.count-1));
            }else{
                throw new JsonParseException(JsonParseException.MESSAGE.UNEXPECTED_CHARACTER,str, (r.count-1));
            }
        }
        return null;
    }

    private static JsonObject readJsonObject(InnerReader r) throws JsonParseException{
        int count= r.count;
        String str = r.read1Str();
        if( str == null || !str.equals("{") ){
            throw new JsonParseException(JsonParseException.MESSAGE.UNEXPECTED_CHARACTER,str,count);
        }
        JsonObject objectValue = new JsonObject();
        JsonValue<?> key;
        while( (key = innerParse(r,false,true)) != null ){
            if( !(key instanceof JsonString) ){
                throw new JsonParseException(JsonParseException.MESSAGE.ILLEGAL_KEY_TYPE,r.count);
            }
            r.skipSeparator(":");
            int sepCount = r.count;
            JsonValue<?> value = innerParse(r,false,false);
            if( value == null ){
                throw new JsonParseException(JsonParseException.MESSAGE.VALUE_NOT_FOUND,sepCount);
            }
            objectValue.push((JsonString)key,value);
            if( r.skipSeparator(",]}").equals("}") ){
                r.reset();
                break;
            }
        }
        String endBracket = r.read1Str();
        if( endBracket == null || !endBracket.equals("}") ){
            throw new JsonParseException(JsonParseException.MESSAGE.OBJECT_END_BRACKET_NOT_FOUND,count,r.count-1);
        }
        return objectValue;
    }

    private static JsonArray readJsonArray(InnerReader r) throws JsonParseException{
        int count = r.count;
        String str = r.read1Str();
        if( str == null || !str.equals("[") ){
            throw new JsonParseException(JsonParseException.MESSAGE.UNEXPECTED_CHARACTER,str,count);
        }
        JsonArray arrayValue = new JsonArray();
        JsonValue<?> value;
        while( (value = innerParse(r,true,false)) != null ){
            arrayValue.push(value);
            if( r.skipSeparator(",]}").equals("]") ){
                r.reset();
                break;
            }
        }
        String endBracket = r.read1Str();
        if( endBracket == null || !endBracket.equals("]")  ){
            throw new JsonParseException(JsonParseException.MESSAGE.ARRAY_END_BRACKET_NOT_FOUND, count,(r.count-1));
        }
        return arrayValue;
    }

    private static JsonNull readJsonNull(InnerReader r) throws JsonParseException{
        int count = r.count;
        String str = r.readNStr("null".length());
        if( str == null || !str.equals("null") ){
            throw new JsonParseException(JsonParseException.MESSAGE.ILLEGAL_NULL_LITERAL,str,count);
        }
        return JsonNull.NULL;
    }


    private static JsonBoolean readJsonBoolean(InnerReader r) throws JsonParseException {
        int count = r.count;
        String str = r.read1Str();
        if( str != null && str.equals("t") ){
            r.reset();
            String literal = r.readNStr("true".length());
            if( literal == null || !literal.equals("true") ){
                throw new JsonParseException(JsonParseException.MESSAGE.ILLEGAL_BOOLEAN_LITERAL,literal,count);
            }
            return new JsonBoolean(true);
        }
        if( str != null && str.equals("f") ){
            r.reset();
            String literal = r.readNStr("false".length());
            if( literal == null || !literal.equals("false") ){
                throw new JsonParseException(JsonParseException.MESSAGE.ILLEGAL_BOOLEAN_LITERAL,literal,count);
            }
            return new JsonBoolean(false);
        }
        throw new JsonParseException(JsonParseException.MESSAGE.UNEXPECTED_CHARACTER,str,count);
    }

    private static JsonValue<?> readJsonNumber(InnerReader r) throws JsonParseException{
        int count = r.count;
        String str = r.read1Str();
        if( str == null || !str.matches("^[-0-9]$") ){
            throw new JsonParseException(JsonParseException.MESSAGE.UNEXPECTED_CHARACTER,str,count);
        }
        StringBuilder builder = new StringBuilder();
        builder.append(str);
        boolean hasPeriod = false;
        boolean currentExp = false;
        while( (str = r.read1Str()) != null ){
            if( str.matches("^[.eE0-9]$")){
                builder.append(str);
                if( str.equals(".") ){
                    if( hasPeriod ){
                        throw new JsonParseException(JsonParseException.MESSAGE.ILLEGAL_NUMBER_FORMAT,builder.toString(),(r.count-1));
                    }
                    if( currentExp ){
                        throw new JsonParseException(JsonParseException.MESSAGE.ILLEGAL_NUMBER_FORMAT,builder.toString(),(r.count-1));
                    }
                    hasPeriod = true;
                }else if( str.equalsIgnoreCase("e") ) {
                    if (currentExp) {
                        throw new JsonParseException(JsonParseException.MESSAGE.ILLEGAL_NUMBER_FORMAT, builder.toString(), (r.count - 1));
                    }
                    currentExp = true;
                    hasPeriod = false;
                    String sign = r.read1Str();
                    builder.append(sign);
                    if (sign == null || !sign.matches("^[-+]$")) {
                        throw new JsonParseException(JsonParseException.MESSAGE.ILLEGAL_NUMBER_FORMAT, builder.toString(), (r.count - 1));
                    }
                }
            }else{
                r.reset();
                break;
            }
        }
        try {
            return new JsonNumber(builder.toString());
        }catch(NumberFormatException e){
            throw new JsonParseException(JsonParseException.MESSAGE.ILLEGAL_NUMBER_FORMAT,e,builder.toString(),count);
        }
    }

    private static JsonString readJsonString(InnerReader r) throws JsonParseException{
        StringBuilder builder = new StringBuilder();
        StringBuilder builderOrig = new StringBuilder();
        boolean escape = false;
        String str = r.read1Str();
        if( str == null || !str.equals("\"") ){
            r.reset();
            throw new JsonParseException(JsonParseException.MESSAGE.ILLEGAL_STRING_LITERAL,str,r.count);
        }
        builderOrig.append(str);
        while( (str = r.read1Str()) != null ){
            builderOrig.append(str);
            char c = str.charAt(0);
            if( escape ){
                if( c == '\\' || c == '\"' || c == '/' ){
                    builder.append(c);
                }else if( c == 'b'){
                    builder.append("\b");
                }else if( c == 'f' ){
                    builder.append("\f");
                }else if( c == 'n' ){
                    builder.append("\n");
                }else if( c == 'r' ){
                    builder.append("\r");
                }else if( c == 't' ){
                    builder.append("\t");
                }else if( c == 'u' ){
                    String codePointStr = r.readNStr(4);
                    if( codePointStr == null ){
                        throw new JsonParseException(JsonParseException.MESSAGE.ILLEGAL_CODEPOINT_TOO_SHORT,builderOrig.toString(),r.count);
                    }
                    try {
                        int[] codePoint = new int[1];
                        codePoint[0] = Integer.parseInt(codePointStr, 16);
                        builder.append(new String(codePoint,0,1));
                    }catch(NumberFormatException e){
                        throw new JsonParseException(JsonParseException.MESSAGE.ILLEGAL_CODEPOINT,codePointStr,r.count);
                    }
                }else{
                    throw new JsonParseException(JsonParseException.MESSAGE.ILLEGAL_STRING_LITERAL,builderOrig.toString(),r.count);
                }
                escape = false;
            }else if( c == '\\') {
                escape = true;
            }else if( c == '\"' ){
                return new JsonString(builder.toString());
            }else{
                builder.append(c);
            }
        }
        throw new JsonParseException(JsonParseException.MESSAGE.ILLEGAL_STRING_LITERAL,builderOrig.toString(),r.count-1);
    }

    private static class InnerReader{

        private int count = 0;
        private int preCount = 0;
        private final BufferedReader reader;

        private InnerReader(Reader r){
            if( r instanceof BufferedReader){
                reader = (BufferedReader) r;
            }else{
                reader = new BufferedReader(r);
            }
        }

        private String read1Str() throws JsonParseException{
            char[] c = new char[1];
            try{
                reader.mark(10);
                preCount = count;
                if( (reader.read(c,0,1)) <= 0 ){
                    return null;
                }
                count++;
                return new String(c,0,1);
            }catch(IOException e){
                throw new JsonParseException(JsonParseException.MESSAGE.UNEXPECTED_ERROR,e,"IOException");
            }
        }

        private String readNStr(int len) throws JsonParseException{
            preCount = count;
            char[] c = new char[len];
            try{
                reader.mark(10);
                int readLen = reader.read(c,0,len);
                count = count + readLen;
                if( readLen != len ){
                    return null;
                }
                return new String(c,0,readLen);
            }catch(IOException e){
                throw new JsonParseException(JsonParseException.MESSAGE.UNEXPECTED_ERROR,e,"IOException");
            }
        }

        private String skipSeparator(String sep) throws JsonParseException{
            String str;
            while( (str = read1Str()) != null ){
                if( sep.contains(str) ){
                    return str;
                }
                if( !str.trim().isEmpty() ){
                    throw new JsonParseException(JsonParseException.MESSAGE.UNEXPECTED_CHARACTER,str, count-1);
                }
            }
            return "";
        }

        private void reset() throws JsonParseException{
            try {
                reader.reset();
                count = preCount;
            }catch(IOException e){
                throw new JsonParseException(JsonParseException.MESSAGE.UNEXPECTED_ERROR,e,"IOException");
            }
        }

    }
}
