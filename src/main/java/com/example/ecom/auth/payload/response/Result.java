package com.example.ecom.auth.payload.response;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "Result", description = "Standard API response wrapper")
public class Result<T> {

    @Schema(description = "Indicates if the operation was successful", example = "true")
    private boolean success;

    @Schema(description = "Response message")
    private String message;

    @Schema(description = "Response payload data (can be null)")
    private T data;

    public static <T> Result<T> success(String message, T data) {
    return Result.<T>builder()
        .success(true)
        .message(message)
        .data(data)
        .build();
  }

  public static <T> Result<T> error(String message) {
    return Result.<T>builder()
        .success(false)
        .message(message)
        .data(null)
        .build();
  }
}