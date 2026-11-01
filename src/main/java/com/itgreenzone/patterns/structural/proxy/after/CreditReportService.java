package com.itgreenzone.patterns.structural.proxy.after;

import com.itgreenzone.patterns.structural.proxy.CreditReport;
import com.itgreenzone.patterns.structural.proxy.User;

/** SUBJECT: the interface the real service AND every proxy implement. */
public interface CreditReportService {

    CreditReport reportFor(String customerId, User requester);
}
