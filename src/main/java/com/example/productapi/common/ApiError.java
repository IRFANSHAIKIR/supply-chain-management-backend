package com.example.productapi.common;

import java.time.LocalDateTime;
import java.util.List;

public record ApiError(LocalDateTime timestamp, int status, String error, String message, String path,
                       List<FieldErrorResponse> fieldErrors) {
}
