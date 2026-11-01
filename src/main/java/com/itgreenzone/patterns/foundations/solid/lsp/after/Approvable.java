package com.itgreenzone.patterns.foundations.solid.lsp.after;

/** Only claims that can REALLY be approved implement this. */
public interface Approvable extends ClaimView {

    void approve();
}
