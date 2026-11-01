package com.itgreenzone.patterns.foundations.solid.lsp.before;

import java.util.List;

/** Written against ClaimFile – and broken by a subclass it has never heard of. */
public class ApprovalBatch {

    public void approveAll(List<ClaimFile> files) {
        files.forEach(ClaimFile::approve);
    }
}
