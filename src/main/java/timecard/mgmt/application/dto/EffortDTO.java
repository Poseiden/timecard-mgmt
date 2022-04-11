package timecard.mgmt.application.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class EffortDTO {
    @NonNull
    private String date;
    @NonNull
    private int workingHours;
    @NonNull
    private String note;
}
