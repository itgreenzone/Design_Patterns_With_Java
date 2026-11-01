package com.itgreenzone.patterns.foundations.solid.lsp.after;

public class OpenClaim implements Approvable {

    private final String id;
    private String status = "OPEN";

    public OpenClaim(String id) {
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
