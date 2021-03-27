package uk.co.mruoc.json.mask.email;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import uk.co.mruoc.json.mask.JsonMasker;
import uk.co.mruoc.json.mask.JsonPathConfig;

import java.util.Collection;

public class EmailAddressJsonMasker extends JsonMasker {

    public EmailAddressJsonMasker(ObjectMapper mapper, Collection<JsonPath> jsonPaths) {
        super(mapper, jsonPaths, new EmailAddressMaskFunction(), JsonPathConfig.build());
    }

}
