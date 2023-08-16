package com.task.Bank.Verification.Number.Application.dtos;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDto {
        @NotBlank(message = "First name is required")
        private String firstName;
        @NotBlank(message = "Last name is required")
        private String lastName;
        @NotNull
        @NotBlank(message = "Email is required")
        @Email(message = "Email is not valid",
                regexp = "[A-Za-z0-9][A-Za-z0-9!.#$%&'*+=?^_{|}~-]{1,50}@[A_Za-z]+[.][A-Z.a-z]{2,6}"
        )
        private String email;
        @NotBlank(message = "Password is required")
        @Size(min = 6, message = "Password must be at least 6 characters long")
        private String password;
}
