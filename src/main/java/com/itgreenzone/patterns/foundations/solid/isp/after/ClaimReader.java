package com.itgreenzone.patterns.foundations.solid.isp.after;

import com.itgreenzone.patterns.foundations.solid.Claim;

import java.util.List;
import java.util.Optional;

/** Small role interface: reading. */
public interface ClaimReader {

    Optional<Claim> findById(String id);

    List<Claim> findAll();
}
