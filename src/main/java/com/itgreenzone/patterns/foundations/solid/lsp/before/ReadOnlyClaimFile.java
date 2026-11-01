package com.itgreenzone.patterns.foundations.solid.lsp.before;

/**
 * BEFORE (breaks LSP): an archived claim "is a" ClaimFile by inheritance,
 * but it cannot do what a ClaimFile promises. Callers explode at runtime.
 */
public class ReadOnlyClaimFile extends ClaimFile {

    public ReadOnlyClaimFile(String id) {
        super(id);
    }

    @Override
    public void approve() {
        throw new UnsupportedOperationException("Archived claims are read-only");
    }
}
