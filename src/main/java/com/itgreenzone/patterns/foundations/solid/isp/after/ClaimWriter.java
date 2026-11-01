package com.itgreenzone.patterns.foundations.solid.isp.after;

import com.itgreenzone.patterns.foundations.solid.Claim;

/** Small role interface: writing. */
public interface ClaimWriter {

    void save(Claim claim);

    void delete(String id);
}
