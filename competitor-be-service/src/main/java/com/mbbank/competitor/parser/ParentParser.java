package com.mbbank.competitor.parser;

import java.util.*;

public class ParentParser implements Parser {
    private final List<Parser> parsers;

    public ParentParser() {
        this.parsers = new ArrayList<>();
        this.parsers.addAll(Arrays.asList(DefaultParser.values()));
    }

    public void register(Parser parser) {
        this.parsers.add(parser);
    }

    @Override
    public boolean canParse(Class clazz) {
        return findParser(clazz).isPresent();
    }

    @Override
    public <D> D parse(Class<D> outClazz, String value) {
        try {
            return Objects.requireNonNull(findParser(outClazz).orElse(null))
                    .parse(outClazz, value);
        } catch (Exception e) {
            return null;
        }
    }

    private Optional<Parser> findParser(Class clazz) {
        return this.parsers.stream().filter(parser -> parser.canParse(clazz)).findAny();
    }
}
