package uk.co.mruoc.json.mask.phone;

import uk.co.mruoc.json.mask.MaskFunction;
import uk.co.mruoc.string.mask.IgnoreLastNCharsMasker;

public class PhoneNumberMaskFunction extends MaskFunction {

    public PhoneNumberMaskFunction() {
        super(new IgnoreLastNCharsMasker(3));
    }

}
