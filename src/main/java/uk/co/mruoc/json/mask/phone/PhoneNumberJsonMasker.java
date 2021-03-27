package uk.co.mruoc.json.mask.phone;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import uk.co.mruoc.json.mask.JsonMasker;
import uk.co.mruoc.json.mask.JsonPathConfig;

import java.util.Collection;

public class PhoneNumberJsonMasker extends JsonMasker {

    public PhoneNumberJsonMasker(ObjectMapper mapper, Collection<JsonPath> jsonPaths) {
        super(mapper, jsonPaths, new PhoneNumberMaskFunction(), JsonPathConfig.build());
    }

}
