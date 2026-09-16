package com.bnp.kingmainandhiskingdom.exception;

import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Map<Integer, byte[]> IMAGE_CACHE = new ConcurrentHashMap<>();
    private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(3))
            .build();

    private byte[] getImageForStatus(int status) {
        return IMAGE_CACHE.computeIfAbsent(status, s -> {
            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("https://http.cat/" + s + ".jpg"))
                        .timeout(Duration.ofSeconds(3))
                        .GET()
                        .build();
                HttpResponse<byte[]> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofByteArray());
                if (response.statusCode() == 200) {
                    return response.body();
                }
            } catch (Exception ignored) {
            }
            return new byte[0];
        });
    }

    @ExceptionHandler(PersonNotFoundException.class)
    public ResponseEntity<byte[]> handlePersonNotFoundException(PersonNotFoundException ex) {
        byte[] image = getImageForStatus(HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.IMAGE_JPEG)
                .body(image);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<byte[]> handleIllegalArgumentException(IllegalArgumentException ex) {
        byte[] image = getImageForStatus(HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.IMAGE_JPEG)
                .body(image);
    }

    @Override
    protected @Nullable ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        byte[] image = getImageForStatus(status.value());
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(image, responseHeaders, status);
    }
}
