package uk.co.mruoc.json.mask;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.spi.json.JacksonJsonNodeJsonProvider;

public interface DefaultJsonPathConfig {

    static Configuration build() {
        return builder().build();
    }

    static Configuration.ConfigurationBuilder builder() {
        return Configuration.builder()
                .jsonProvider(new JacksonJsonNodeJsonProvider())
                .options(Option.AS_PATH_LIST);
    }

}
