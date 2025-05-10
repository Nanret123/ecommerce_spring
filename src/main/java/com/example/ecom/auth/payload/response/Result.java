package com.example.ecom.auth.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Result", description = "Standard API response wrapper")
public class Result {
    
    @Schema(description = "Indicates if the operation was successful", example = "true")
    private boolean success;
    
    @Schema(description = "Response message", example = "User registered successfully")
    private String message;
    
    @Schema(description = "Response payload data (can be null)")
    private Object data;
}