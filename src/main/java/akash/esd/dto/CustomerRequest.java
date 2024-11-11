package akash.esd.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

public record CustomerRequest
        (
            @NotNull() @NotEmpty() @NotBlank() @JsonProperty("first_name")
            String firstName,

            @JsonProperty("last_name")
            String lastName,

            @NotNull() @Email() @JsonProperty("email")
            String email,

            @NotNull() @NotEmpty() @NotBlank() @Size(min = 5, max = 20) @JsonProperty("password")
            String password
        ){
}
