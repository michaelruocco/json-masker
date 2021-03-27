package uk.co.mruoc.json.mask.email;

import uk.co.mruoc.json.mask.MaskFunction;
import uk.co.mruoc.string.mask.IgnoreFirstNAndLastNCharsMasker;

public class EmailAddressMaskFunction extends MaskFunction {

    public EmailAddressMaskFunction() {
        super(new IgnoreFirstNAndLastNCharsMasker(4, 6));
    }

}
