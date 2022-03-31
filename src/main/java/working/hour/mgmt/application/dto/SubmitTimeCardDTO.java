package working.hour.mgmt.application.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@Data
@NoArgsConstructor
public class SubmitTimeCardDTO {
    @NonNull
    private String employeeId;

    @NonNull
    List<EntryDTO> entries;

    //todo change xxxList to xxxs
}
