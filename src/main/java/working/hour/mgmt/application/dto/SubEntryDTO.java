package working.hour.mgmt.application.dto;

import lombok.Data;

import java.util.List;

@Data
public class SubEntryDTO {
    private String subProjectId;
    private boolean billable;
    private String locationCode;
    private List<EffortDTO> effortDTOList;
}
