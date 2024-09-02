package com.works.archisproject.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record AuthRequest(
        @NotNull
        @Email
        String email,

        String password
) {
}
