package giovanni.security.Payloads;



import giovanni.security.Devices.DevicesStateEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import jakarta.validation.constraints.Size;

public record NewDevicesDTO(

        @NotEmpty(message = "The type is mandatory data!")
        @Size(min = 4, message = "The type must have at least 4 characters!")
        String type,

        @NotNull(message = "The state is mandatory data!")
        DevicesStateEnum state



) {
}

