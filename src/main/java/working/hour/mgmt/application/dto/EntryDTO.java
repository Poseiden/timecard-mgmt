package working.hour.mgmt.application.dto;

import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
public class EntryDTO {
    @NonNull
    private String projectId;
    @NonNull
    private List<SubEntryDTO> subEntryDTOList;
}
