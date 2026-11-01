package com.itgreenzone.patterns.foundations.intro.exercise;

import java.util.List;

/** A short real-world design problem to classify. */
public record DesignProblem(int id, String description) {

    public static final List<DesignProblem> ALL = List.of(
        new DesignProblem(1, "The whole app must share exactly one connection pool to the core-banking host."),
        new DesignProblem(2, "Customers must be able to undo a funds transfer within 5 minutes."),
        new DesignProblem(3, "Our new app must call a mainframe API whose method names and units differ from ours."),
        new DesignProblem(4, "Each country needs a matching set of components: payment gateway, tax rules and receipt format."),
        new DesignProblem(5, "Add logging and fraud screening around payments without editing the payment class."),
        new DesignProblem(6, "SMS, email and push services must all be told whenever a flight is delayed."),
        new DesignProblem(7, "A single stock and a whole sub-portfolio must be valued the same way."),
        new DesignProblem(8, "A loan application must be routed to the right approver based on the amount.")
    );
}
