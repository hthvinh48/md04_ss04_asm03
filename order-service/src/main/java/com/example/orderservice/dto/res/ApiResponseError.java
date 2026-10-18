package com.example.orderservice.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ApiResponseError {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String path;

    public static ApiResponseError of(int status, String error, String path) {
        return new ApiResponseError(LocalDateTime.now(), status, error, path);
    }
}
