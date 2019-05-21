package fastjson.parser.deserializer;

import java.lang.reflect.Type;
import java.math.BigDecimal;

import fastjson.parser.DefaultJSONParser;
import fastjson.parser.JSONLexer;
import fastjson.parser.JSONToken;
import fastjson.util.TypeUtils;

public class BigDecimalDeserializer implements ObjectDeserializer {

    public final static BigDecimalDeserializer instance = new BigDecimalDeserializer();

    @SuppressWarnings("unchecked")
    public <T> T deserialze(DefaultJSONParser parser, Type clazz, Object fieldName) {
        return (T) deserialze(parser);
    }

    @SuppressWarnings("unchecked")
    public static <T> T deserialze(DefaultJSONParser parser) {
        final JSONLexer lexer = parser.getLexer();
        if (lexer.token() == JSONToken.LITERAL_INT) {
            long val = lexer.longValue();
            lexer.nextToken(JSONToken.COMMA);
            return (T) new BigDecimal(val);
        }

        if (lexer.token() == JSONToken.LITERAL_FLOAT) {
            BigDecimal val = lexer.decimalValue();
            lexer.nextToken(JSONToken.COMMA);
            return (T) val;
        }

        Object value = parser.parse();

        if (value == null) {
            return null;
        }

        return (T) TypeUtils.castToBigDecimal(value);
    }

    public int getFastMatchToken() {
        return JSONToken.LITERAL_INT;
    }
}
