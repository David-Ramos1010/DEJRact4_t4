package com.davidramos1010.crudrapido.dto;

public record AuthResponse(String token, String type, long expiresInMs) {
}