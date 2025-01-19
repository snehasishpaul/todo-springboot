package com.snehasish.web.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Response<T> {
    private T content;
    private LocalDateTime timestamp;
    private String message;
    private HttpStatus httpStatus;

    // Ensure the builder supports generics
    public static class ResponseBuilder<T> {
    }
}
