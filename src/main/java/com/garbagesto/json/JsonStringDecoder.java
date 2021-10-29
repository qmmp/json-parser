package com.garbagesto.json;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonStringDecoder {
    private static final String JSON_STRING_PATTERN = "(?<!\\\\)\"([^\\\"]|\\\\\")*\"";
    private static final String JSON_NUMBER_PATTERN = "[-+]?[0-9]+(\\.[0-9]*)?(e[+-][0-9]+)?";
    private static final String JSON_BOOL_PATTERN = "(true|false)";
    private static final String JSON_NULL_PATTERN = "null";
    private static final String JSON_VALUE_PATTERN = "^("+JSON_NULL_PATTERN+"|"+JSON_STRING_PATTERN+"|"+JSON_NUMBER_PATTERN+"|"+JSON_BOOL_PATTERN+")";

    public static JsonValue<?> decode(String str) throws JsonException{
        TrimStringBuffer buffer = new TrimStringBuffer(str);
        if( str.startsWith("[") ){
            JsonArray ret = parseArrayString(buffer);
            if( buffer.length() != 0 ){
                throw new JsonException(JsonException.ERROR.UNEXPECTED_CHAR_ARRAY,str);
            }
            return ret;
        }
        if( str.startsWith("{") ){
            JsonObject ret = parseObjectString(buffer);
            if( buffer.length() != 0 ){
                throw new JsonException(JsonException.ERROR.UNEXPECTED_CHAR_OBJECT,str);
            }
            return ret;
        }
        JsonValue<?> ret = parseJsonLiteral(buffer);
        if( ret == null ){
            throw new JsonException(JsonException.ERROR.ILLEGAL_JSON_LITERAL,str);
        }
        return ret;
    }

    public static JsonValue<?> decodeJavaString(String str) throws JsonException{
        if( str == null ){
            return JsonNull.NULL;
        }
        if( str.trim().startsWith("[") || str.trim().startsWith("{") ){
            TrimStringBuffer buffer = new TrimStringBuffer(str);
            JsonValue<?> obj = parseJsonLiteral(buffer);
            if( buffer.length() != 0 ){
                if( obj instanceof JsonArray ){
                    throw new JsonException(JsonException.ERROR.UNEXPECTED_CHAR_ARRAY,str);
                }else if( obj instanceof JsonObject ){
                    throw new JsonException(JsonException.ERROR.UNEXPECTED_CHAR_OBJECT,str);
                }else{
                    throw new JsonException(JsonException.ERROR.UNKNOWN_ERROR,str);
                }
            }
            return obj;
        }
        return new JsonString(str);
    }

    private static JsonObject parseObjectString(TrimStringBuffer str) throws JsonException{
        if( str.startsWith("{")){
            JsonObject object = new JsonObject();
            str.substring(1, str.length());
            boolean needSep = false;
            while( str.length() != 0 ){
                if( str.startsWith("}") ){
                    str.substring(1, str.length());
                    return object;
                }else if( str.startsWith(",") ){
                    str.substring(1,str.length());
                    needSep = false;
                    continue;
                }else if( needSep ){
                    throw new JsonException(JsonException.ERROR.UNEXPECTED_CHAR_OBJECT_VALUE,str.toString());
                }
                needSep = true;
                JsonValue<?> keyVal = parseJsonLiteral(str);
                if( keyVal == null ){
                    throw new JsonException(JsonException.ERROR.NO_JSON_OBJECT_KEY,str.toString());
                }
                JsonString key = null;
                if( keyVal instanceof JsonString){
                    key = (JsonString)keyVal;
                }else{
                    throw new JsonException(JsonException.ERROR.UNMATCH_TYPE_JSON_KEY,keyVal.toJsonString());
                }
                if( str.startsWith(":") ){
                    str.substring(1, str.length());
                    if( str.startsWith("[") ){
                        object.push(key,parseArrayString(str));
                    }else if( str.startsWith("{") ){
                        object.push(key,parseObjectString(str));
                    }else{
                        JsonValue<?> value = parseJsonLiteral(str);
                        if( value != null ){
                            object.push(key, value);
                        }else{
                            throw new JsonException(JsonException.ERROR.NO_JSON_OBJECT_VALUE,key.toJsonString());
                        }
                    }
                }else{
                    throw new JsonException(JsonException.ERROR.NO_JSON_OBJECT_KEY_VALUE_SEPARATOR,key.toJsonString());
                }
            }
        }
        throw new JsonException(JsonException.ERROR.UNMATCH_OBJECT_BLANKET);
    }

    private static JsonArray parseArrayString(TrimStringBuffer str) throws JsonException{
        if( str.startsWith("[")){
            JsonArray array = new JsonArray();
            str.substring(1, str.length());
            boolean needSep = false;
            while( str.length() != 0 ){
                if( str.startsWith("]") ){
                    str.substring(1, str.length());
                    return array;
                }else if( str.startsWith(",") ){
                    str.substring(1,str.length());
                    needSep = false;
                    continue;
                }else if( needSep ){
                    throw new JsonException(JsonException.ERROR.UNEXPECTED_CHAR_ARRAY_VALUE,str.toString());
                }
                needSep = true;
                if( str.startsWith("[") ){
                    array.push(parseArrayString(str));
                }else if( str.startsWith("{") ){
                    array.push(parseObjectString(str));
                }else{
                    JsonValue<?> value = parseJsonLiteral(str);
                    if( value != null ){
                        array.push(value);
                    }else{
                        throw new JsonException(JsonException.ERROR.ILLEGAL_JSON_LITERAL,str.toString());
                    }
                }
            }
        }
        throw new JsonException(JsonException.ERROR.UNMATCH_ARRAY_BLANKET);
    }

    private static JsonValue<?> parseJsonLiteral(TrimStringBuffer str) throws JsonException{
        if( str.startsWith("[") ){
            return parseArrayString(str);
        }
        if( str.startsWith("{") ){
            return parseObjectString(str);
        }
        Pattern pattern = Pattern.compile(JSON_VALUE_PATTERN);
        Matcher m = pattern.matcher(str.toString());
        if( m.find() ){
            String strVal = m.group(0);
            str.substring(strVal.length(),str.length());
            if( strVal.startsWith("\"") ){
                return paseJsonStringLiteral(strVal);
            }else if( strVal.startsWith("t") || strVal.startsWith("f") ){
                return JsonBoolean.valueOf(strVal);
            }else if( strVal.equals("null") ){
                return JsonNull.NULL;
            }else{
                return new JsonNumber(strVal);
            }
        }
        return null;
}

    private static JsonString paseJsonStringLiteral(String strVal) throws JsonException{
        String trimVal = strVal.trim();
        if( trimVal.startsWith("\"") && trimVal.endsWith("\"") && trimVal.length() > 1 ){
            StringBuffer buf = new StringBuffer(trimVal.substring(1, trimVal.length()-1));
            boolean escape = false;
            StringBuilder builder = new StringBuilder();
           while( buf.length() != 0 ){
                char c = buf.charAt(0);
                buf.delete(0, 1);
                if( escape ){
                    escape = false;
                    if( c == '\\' ){
                        builder.append("\\");
                    }else if( c == 'r' ){
                        builder.append("\r");
                    }else if( c == 'n' ){
                        builder.append("\n");
                    }else if( c == 't' ){
                        builder.append("\t");
                    }else if( c == 'b' ){
                        builder.append("\b");
                    }else if( c == 'f' ){
                        builder.append("\f");
                    }else if( c == 'u' ){
                        if( buf.length() < 4 ){
                            throw new JsonException(JsonException.ERROR.ILLEGAL_UNICODE_VALUE,"\\u"+buf.toString());
                        }
                        String codePoint = buf.substring(0, 4);
                        try{
                            char[] code = Character.toChars(Integer.parseInt(codePoint,16));
                            builder.append(code);
                            buf.delete(0, 4);
                        }catch(NumberFormatException e){
                            throw new JsonException(JsonException.ERROR.ILLEGAL_UNICODE_VALUE,"\\u"+codePoint);
                        }
                    }else if( c == '\"' ){
                        builder.append("\"");
                    }else{
                        throw new JsonException(JsonException.ERROR.ILLEGAL_STRING_LITERAL,strVal);
                    }
                }else{
                    if( c == '\\' ){
                        escape = true;
                    }else if( c == '\"' ){
                        throw new JsonException(JsonException.ERROR.ILLEGAL_STRING_LITERAL,strVal);
                    }else{
                        builder.append(c);
                    }
                }
            }
            return new JsonString(builder.toString());
        }
        throw new JsonException(JsonException.ERROR.ILLEGAL_STRING_LITERAL,strVal);
    }

    private static class TrimStringBuffer {

        private String _value = null;

        private TrimStringBuffer(String str){
            if( str == null ){
                _value = "";
            }else{
                _value = str.trim();
            }
        }
        private boolean startsWith(String s){
            return _value.startsWith(s);
        }
        private int length(){
            return _value.length();
        }
        private void substring(int s,int e){
            _value = _value.substring(s,e).trim();
        }
        public String toString(){
            return _value;
        }
    }

    
}