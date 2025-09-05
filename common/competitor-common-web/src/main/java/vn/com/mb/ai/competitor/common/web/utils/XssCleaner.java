package vn.com.mb.ai.competitor.common.web.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Safelist;
import org.springframework.web.util.HtmlUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class XssCleaner {
    private List<String> safeTags;
    private List<String> safeAttributes; // Attributes allowed for all tags
    private Map<String, List<String>> safeTagAttributes;
    private Map<String, Map<String, String>> enforcedAttributes;
    private Map<String, Map<String, List<String>>> safeProtocols;

    public String clean(String content) {
        if (content == null) {
            return null;
        }

        Safelist safelist = Safelist.none();

        safelist.addTags(toArray(safeTags));
        safelist.addAttributes(":all", toArray(safeAttributes));
        safeTagAttributes.forEach((key, value) -> safelist.addAttributes(key, toArray(value)));
        enforcedAttributes.forEach((key, value) -> value.forEach((key1, value1) -> safelist.addEnforcedAttribute(key, key1, value1)));
        safeProtocols.forEach((key, value) -> value.forEach((key1, value1) -> safelist.addProtocols(key, key1, toArray(value1))));

        return Jsoup.clean(HtmlUtils.htmlUnescape(content), "", safelist, new Document.OutputSettings().prettyPrint(false).charset(StandardCharsets.UTF_8));
    }

    private String[] toArray(List<String> list) {
        return list == null ? new String[0] : list.toArray(String[]::new);
    }

    private String json() {
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }

    public static XssCleaner fromJson(String json) {
        return new Gson().fromJson(json, XssCleaner.class);
    }
}