package com.itgreenzone.patterns.foundations.solid.isp.before;

import com.itgreenzone.patterns.foundations.solid.Claim;

import java.util.List;
import java.util.Optional;

/** A read-only reporting store is FORCED to implement write/admin methods it can't support. */
public class ReportingRepository implements ClaimRepository {

    private final List<Claim> snapshot;

    public ReportingRepository(List<Claim> snapshot) {
        this.snapshot = List.copyOf(snapshot);
    }

    public Optional<Claim> findById(String id) {
        return snapshot.stream().filter(c -> c.id().equals(id)).findFirst();
    }

    public List<Claim> findAll() {
        return snapshot;
    }

    public void save(Claim claim) {
        throw new UnsupportedOperationException("read-only");
    }

    public void delete(String id) {
        throw new UnsupportedOperationException("read-only");
    }

    public int purgeOlderThanYears(int years) {
        throw new UnsupportedOperationException("read-only");
    }

    public String exportCsv() {
        throw new UnsupportedOperationException("not supported");
    }
}
