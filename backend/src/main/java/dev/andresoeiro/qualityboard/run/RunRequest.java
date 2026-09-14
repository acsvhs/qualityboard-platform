package dev.andresoeiro.qualityboard.run;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RunRequest(
        @NotBlank @Size(max = 120) String name,
        @NotBlank @Size(max = 500) String targetUrl,
        @NotNull TestFramework framework
) {
}
