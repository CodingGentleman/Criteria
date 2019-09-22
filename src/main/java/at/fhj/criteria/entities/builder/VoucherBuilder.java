package at.fhj.criteria.entities.builder;

import at.fhj.criteria.entities.Voucher;
import at.fhj.criteria.entities.immutable.VoucherView;

public final class VoucherBuilder extends BaseBuilder<Voucher, VoucherView> {
    private String code;
    private double value;

    private VoucherBuilder() {
    }

    public static VoucherBuilder aVoucher() {
        return new VoucherBuilder();
    }

    public VoucherBuilder withCode(String code) {
        this.code = code;
        return this;
    }

    public VoucherBuilder withValue(double value) {
        this.value = value;
        return this;
    }

    @Override
    protected Voucher buildInstance() {
        Voucher voucher = new Voucher(this);
        voucher.setCode(code);
        voucher.setValue(value);
        return voucher;
    }
}
