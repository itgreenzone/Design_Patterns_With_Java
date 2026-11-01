package com.itgreenzone.patterns.foundations.solid.isp.after;

import com.itgreenzone.patterns.foundations.solid.Claim;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/** A full store implements several small interfaces; a read-only one would implement just ClaimReader. */
public class InMemoryClaimStore implements ClaimReader, ClaimWriter {

    private final Map<String, Claim> claims = new LinkedHashMap<>();

    public Optional<Claim> findById(String id) {
        return Optional.ofNullable(claims.get(id));
    }

    public List<Claim> findAll() {
        return new ArrayList<>(claims.values());
    }

    public void save(Claim claim) {
        claims.put(claim.id(), claim);
    }

    public void delete(String id) {
        claims.remove(id);
    }
}
