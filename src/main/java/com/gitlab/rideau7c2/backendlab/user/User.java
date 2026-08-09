package com.gitlab.rideau7c2.backendlab.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record User(
        Long id,
        @NotNull
        @Size()
        String name,
        @NotNull
        @Email
        String email
) { }
