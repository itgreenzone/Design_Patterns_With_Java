package com.itgreenzone.patterns.foundations.solid.isp.before;

import com.itgreenzone.patterns.foundations.solid.Claim;

import java.util.List;
import java.util.Optional;

/** BEFORE (breaks ISP): one fat interface for readers, writers and admins. */
public interface ClaimRepository {

    Optional<Claim> findById(String id);

    List<Claim> findAll();

    void save(Claim claim);

    void delete(String id);

    int purgeOlderThanYears(int years);

    String exportCsv();
}
