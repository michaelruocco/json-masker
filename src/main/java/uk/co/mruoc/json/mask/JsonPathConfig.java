package uk.co.mruoc.json.mask;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.spi.json.JacksonJsonNodeJsonProvider;
import com.jayway.jsonpath.spi.json.JsonProvider;

public class JsonPathConfig {

    static final JsonProvider DEFAULT_PROVIDER = new JacksonJsonNodeJsonProvider();

    private JsonPathConfig() {
        // utility class
    }

    static Configuration build() {
        return builder().build();
    }

    static Configuration withNoOptions() {
        return Configuration.builder()
                .jsonProvider(DEFAULT_PROVIDER)
                .options()
                .build();
    }

    static Configuration.ConfigurationBuilder builder() {
        return Configuration.builder()
                .jsonProvider(DEFAULT_PROVIDER)
                .options(Option.AS_PATH_LIST, Option.SUPPRESS_EXCEPTIONS);
    }

}
