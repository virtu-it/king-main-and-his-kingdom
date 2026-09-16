package com.bnp.kingmainandhiskingdom.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiError {
    private String title;
    private int status;
    private String description;
    private String image;
    @JsonProperty("validation-errors")
    private List<ValidationError> validationErrors = new ArrayList<ValidationError>();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;

    public ApiError(String title, int status, String description) {
        this.title = title;
        this.status = status;
        this.description = description;
        this.image = "https://http.cat/" + status + ".jpg";
        this.timestamp = LocalDateTime.now();
    }
}
