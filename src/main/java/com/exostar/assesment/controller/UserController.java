package com.exostar.assesment.controller;

import com.exostar.assesment.dto.UploadResponseDto;
import com.exostar.assesment.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * UserController is a REST controller that handles user-related requests.
 * It provides endpoints for uploading user data and retrieving the count of users.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    /**
     * Constructor for UserController.
     *
     * @param userService The service to be used for user-related operations.
     */
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Endpoint for uploading user data.
     * The data should be in a CSV file.
     *
     * @param file The CSV file containing user data.
     * @return A ResponseEntity containing an UploadResponseDto with the result of the operation.
     */

    @PostMapping("/upload")
    public ResponseEntity<UploadResponseDto> uploadUsers(@RequestParam("file") MultipartFile file) {

        // Log that a request to upload users has been received
        log.info("Received request to upload users");

        try {
            // Check if the uploaded file is empty
            if (file.isEmpty()) {

                // Log a warning that an empty file has been received
                log.warn("Received empty file");

                // Return a bad request response indicating that the file is empty
                return ResponseEntity.badRequest().body(new UploadResponseDto("Failed", "File is empty"));
            }

            // Check if the uploaded file is not a CSV file
            if (!file.getOriginalFilename().endsWith(".csv")) {

                // Log a warning that a non-CSV file has been received
                log.warn("Received non-CSV file: {}", file.getOriginalFilename());

                // Return a bad request response indicating that the file must be a CSV
                return ResponseEntity.badRequest().body(new UploadResponseDto("Failed", "File must be a CSV"));
            }

            // Process the users from the CSV file
            log.info("Processing CSV file: {}", file.getOriginalFilename());
            UploadResponseDto result = userService.processUsersFromCsv(file.getInputStream());
            log.info("Successfully processed CSV file");

            // Return a successful response with the result of the operation
            return ResponseEntity.ok(result);

        } catch (IOException e) {
            // Log an error if there was an issue processing the file
            log.error("Error processing file: {}", e.getMessage());
            // Return an internal server error response indicating that there was an error processing the file
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new UploadResponseDto("Failed", "Error processing file: " + e.getMessage()));
        }
    }

    /**
     * Endpoint for retrieving the count of users.
     *
     * @return A ResponseEntity containing the count of users.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> getUserCount() {
        long count = userService.getUserCount();
        return ResponseEntity.ok(count);
    }
}