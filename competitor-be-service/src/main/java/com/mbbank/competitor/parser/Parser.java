package com.mbbank.competitor.parser;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public interface Parser {

    List<Pattern> patterns = Arrays.asList(Pattern.compile(
                    "^([1-2][0-9]{3})[-.\\/](0[1-9]|1[0-2])[-.\\/](0[1-9]|[12][0-9]|3[01])[T ]" +
                            "(0[0-9]|1[0-9]|2[0-3])[-.\\:]([0-5][0-9])[-.\\:]([0-5][0-9]).?([0-9]*)$"),
            Pattern.compile("^([0-9]*$)")
    );

    boolean canParse(Class clazz);

    <D> D parse(Class<D> clazz, String value);
}
