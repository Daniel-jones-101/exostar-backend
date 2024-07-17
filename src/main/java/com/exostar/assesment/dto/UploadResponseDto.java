package com.exostar.assesment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UploadResponseDto is a Data Transfer Object (DTO) that encapsulates the response details of the upload operation.
 * It includes the status and message of the operation, as well as the total, successful, and failed record counts.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UploadResponseDto {

    private String status;
    private String message;

    private int totalRecords;
    private int successfulRecords;
    private int failedRecords;

    /**
     * Constructor for creating an UploadResponseDto with status and message.
     * This constructor can be used when the record counts are not available or not necessary.
     *
     * @param status  The status of the operation.
     * @param message The message of the operation.
     */
    public UploadResponseDto(String status, String message) {
        this.status = status;
        this.message = message;
    }
}