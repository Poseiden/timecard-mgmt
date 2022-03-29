package working.hour.mgmt.domain.model.working_hour_mgmt.effort;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "effort")
public class Effort {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Generated(GenerationTime.INSERT)
    private String id;
    private String employeeId;
    private LocalDate workingDay;
    private int workingHours;
    private String locationId;
    private boolean billable;
    private String note;

    private String subProjectId;

    public Effort(String employeeId, LocalDate workingDay, int workingHours, String locationId,
                  boolean billable, String note, String subProjectId) {
        this.employeeId = employeeId;
        this.workingDay = workingDay;
        this.workingHours = workingHours;
        this.locationId = locationId;
        this.billable = billable;
        this.note = note;
        this.subProjectId = subProjectId;
    }
}
