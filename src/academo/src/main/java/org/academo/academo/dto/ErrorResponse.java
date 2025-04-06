package org.academo.academo.dto;

import org.springframework.http.HttpStatus;

public record ErrorResponse(String message, HttpStatus status) {}
