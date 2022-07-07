package org.equinoxprojects.luxurystaff.support;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

public class Report
{
    private final @Getter UUID target;
    private final @Getter UUID reported;
    private final @Getter String reason;
    private final @Getter int id;
    private @Getter @Setter ReportStatus status = ReportStatus.OPEN;

    public Report(final UUID target, final UUID reported, final String reason, final int id)
    {
        this.target = target;
        this.reported = reported;
        this.reason = reason;
        this.id = id;
    }

}
