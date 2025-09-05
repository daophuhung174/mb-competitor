package vn.com.mb.ai.competitor.common.web.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Set;

/**
 * @author hungdp
 */
@Slf4j
public final class JsonUtil {
    private static final String remove = "<<Not recorded to log>>";
    private static final Set<String> ignoreFields = Set.of("password", "accessToken", "refreshToken", "token", "authorization");
    private static final ObjectMapper toJsonMapper = new ObjectMapper();
    private static final ObjectMapper toObjectMapper = new ObjectMapper();

    static {
        toJsonMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        toJsonMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        SimpleModule module = new SimpleModule();
        module.addSerializer(String.class, new RemoveLogSerializer());
        toJsonMapper.registerModule(module);
        toJsonMapper.registerModule(new JavaTimeModule());

        toObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private JsonUtil() {
        throw new UnsupportedOperationException("This is a utility class");
    }

    public static String toJson(Object object) {
        String jsonString;
        try {
            jsonString = toJsonMapper.writeValueAsString(object);
        } catch (JsonProcessingException ex) {
            log.error("Error convert Object to Json string", ex);
            jsonString = "Can not build Json string from Object";
        }
        return jsonString;
    }

    public static <T> T toObject(Object object, TypeReference<T> typeReference) {
        try {
            return toObjectMapper.convertValue(object, typeReference);
        } catch (Exception ex) {
            log.error("Error toObject", ex);
            return null;
        }
    }

    public static class RemoveLogSerializer extends StdSerializer<String> {
        public RemoveLogSerializer() {
            this(null);
        }

        public RemoveLogSerializer(Class<String> t) {
            super(t);
        }

        @Override
        public void serialize(String value, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException, JsonProcessingException {
            var context = jsonGenerator.getOutputContext();
            var name = context.getCurrentName();
            if (name != null && ignoreFields.contains(name)) {
                jsonGenerator.writeString(remove);
            } else {
                jsonGenerator.writeString(value);
            }
        }
    }
}
