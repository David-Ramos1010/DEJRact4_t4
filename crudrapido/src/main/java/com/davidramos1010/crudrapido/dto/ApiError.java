package com.davidramos1010.crudrapido.dto;

import java.util.Map;

public record ApiError(String timestamp, int status, String error, String message, Map<String, String> errors) {
}