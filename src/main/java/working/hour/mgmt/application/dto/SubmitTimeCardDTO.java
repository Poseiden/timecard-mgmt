package working.hour.mgmt.application.dto;

import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
public class SubmitTimeCardDTO {
    @NonNull
    private String employeeId;

    @NonNull
    List<EntryDTO> entryDTOList;
}
