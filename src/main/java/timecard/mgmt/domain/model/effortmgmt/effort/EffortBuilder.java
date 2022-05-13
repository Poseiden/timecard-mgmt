package timecard.mgmt.domain.model.effortmgmt.effort;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Enumerated;
import java.time.LocalDate;

import static javax.persistence.EnumType.STRING;

@Data
@NoArgsConstructor
public class EffortBuilder {
    private String employeeId;
    private LocalDate workingDay;
    private int workingHours;
    private String locationId;
    private boolean billable;
    private String note;

    private String subProjectId;
    private String projectId;
    @Enumerated(STRING)
    private EffortStatus effortStatus;

    public EffortBuilder employeeId(String employeeId) {
        this.employeeId = employeeId;
        return this;
    }
    public EffortBuilder workingHours(int workingHours) {
        this.workingHours = workingHours;
        return this;
    }
    public EffortBuilder workingDay(LocalDate workingDay) {
        this.workingDay = workingDay;
        return this;
    }
    public EffortBuilder locationId(String locationId) {
        this.locationId = locationId;
        return this;
    }
    public EffortBuilder billable(boolean billable) {
        this.billable = billable;
        return this;
    }
    public EffortBuilder note(String note) {
        this.note = note;
        return this;
    }
    public EffortBuilder subProjectId(String subProjectId) {
        this.subProjectId = subProjectId;
        return this;
    }
    public EffortBuilder projectId(String projectId) {
        this.projectId = projectId;
        return this;
    }
    public EffortBuilder effortStatus(EffortStatus effortStatus) {
        this.effortStatus = effortStatus;
        return this;
    }

    public Effort build() {
        return new Effort(this);
    }
}
