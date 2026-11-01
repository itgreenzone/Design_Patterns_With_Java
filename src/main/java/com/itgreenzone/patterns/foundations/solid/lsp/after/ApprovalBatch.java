package com.itgreenzone.patterns.foundations.solid.lsp.after;

import java.util.List;

/** The compiler now stops anyone passing an ArchivedClaim here. */
public class ApprovalBatch {

    public void approveAll(List<? extends Approvable> claims) {
        claims.forEach(Approvable::approve);
    }
}
