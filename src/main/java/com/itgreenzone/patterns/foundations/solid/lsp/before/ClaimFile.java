package com.itgreenzone.patterns.foundations.solid.lsp.before;

/** A claim file that can be approved. */
public class ClaimFile {

    private final String id;
    private String status = "OPEN";

    public ClaimFile(String id) {
        this.id = id;
    }

    public void approve() {
        status = "APPROVED";
    }

    public String id() {
        return id;
    }

    public String status() {
        return status;
    }
}
