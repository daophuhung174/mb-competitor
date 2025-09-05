package vn.com.mb.ai.competitor.common.web.configurations;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.convention.NameTransformers;
import org.modelmapper.convention.NamingConventions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author hungdp
 */
@Configuration
public class ModelMapperConfiguration {

    @Bean("mapperForBuilder")
    public ModelMapper mapperWithBuilder() {
        var mapper = new ModelMapper();
        mapper
                .getConfiguration()
                .setDestinationNameTransformer(NameTransformers.builder())
                .setDestinationNamingConvention(NamingConventions.builder())
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setAmbiguityIgnored(true);
        return mapper;
    }

    @Bean("mapper")
    public ModelMapper mapperWithoutBuilder() {
        var mapper = new ModelMapper();
        mapper
                .getConfiguration()
                .setFieldMatchingEnabled(true)
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setAmbiguityIgnored(true);
        return mapper;
    }
}
