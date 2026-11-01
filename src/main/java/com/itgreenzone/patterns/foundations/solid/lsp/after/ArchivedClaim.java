package com.itgreenzone.patterns.foundations.solid.lsp.after;

/** AFTER (LSP): never pretends it can be approved, so it can't break callers. */
public record ArchivedClaim(String id) implements ClaimView {

    public String status() {
        return "ARCHIVED";
    }
}
