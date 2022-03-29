package working.hour.mgmt.application.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@Data
@NoArgsConstructor
public class SubEntryDTO {
    @NonNull
    private String subProjectId;
    @NonNull
    private boolean billable;
    @NonNull
    private String locationCode;
    @NonNull
    private List<EffortDTO> effortDTOList;
}
