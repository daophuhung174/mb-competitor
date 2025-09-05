package vn.com.mb.ai.competitor.common.web.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.util.HtmlUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author datdt2.os
 * Module dung de kiem soat du lieu dau vao:
 * - Strim du lieu string
 * - Xoa cac tag vi pham XSS
 */
@Slf4j
public class InputControlModule extends SimpleModule {
    private final XssCleaner xssCleaner = XssCleaner.builder()
            .safeTags(List.of("a", "abbr", "acronym", "address", "area", "article", "aside", "audio", "b", "bdi",
                    "big", "blockquote", "br", "caption", "cite", "code", "col", "colgroup", "datalist", "dd",
                    "del", "details", "div", "dl", "dt", "em", "fieldset", "figcaption", "figure", "footer", "h1",
                    "h2", "h3", "h4", "h5", "h6", "hr", "i", "img", "li", "ins", "ol", "p", "pre", "q", "ul",
                    "small", "span"))
            .safeAttributes(List.of("style", "title"))
            .safeTagAttributes(Map.of("a", List.of("href"), "img", List.of("src")))
            .enforcedAttributes(Map.of("a", Map.of("rel", "nofollow")))
            .safeProtocols(Map.of("a", Map.of("href", List.of("#", "http", "https", "ftp", "mailto")), "blockquote",
                    Map.of("cite", List.of("http", "https")), "cite", Map.of("cite", List.of("http", "https")), "q",
                    Map.of("cite", List.of("http", "https"))))
            .build();

    public InputControlModule() {
        addDeserializer(String.class, new JsonDeserializer<String>() {
            @Override
            public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                final String value = jsonParser.getValueAsString();
                if ("password".equals(jsonParser.getCurrentName())) {
                    return value;
                } else {
                    if (StringUtils.isBlank(value)) {
                        return null;
                    }
                    return HtmlUtils.htmlUnescape(xssCleaner.clean(value.trim()));
                }
            }
        });
    }

}
