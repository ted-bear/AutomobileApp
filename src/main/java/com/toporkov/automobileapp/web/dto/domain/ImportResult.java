package com.toporkov.automobileapp.web.dto.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ImportResult {
    private int successCount;
    private int failedCount;
    private List<String> validationErrors;
}
