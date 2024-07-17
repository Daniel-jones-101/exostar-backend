package com.exostar.assesment;

import com.exostar.assesment.controller.UserController;
import com.exostar.assesment.dto.UploadResponseDto;
import com.exostar.assesment.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserControllerTest {

    private UserService userService;
    private UserController userController;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        userController = new UserController(userService);
    }

    @Test
    void uploadUsers_shouldReturnBadRequest_whenFileIsEmpty() throws IOException {
        MockMultipartFile file = new MockMultipartFile("file", "", "text/csv", "".getBytes());

        ResponseEntity<UploadResponseDto> response = userController.uploadUsers(file);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Failed", response.getBody().getStatus());
        assertEquals("File is empty", response.getBody().getMessage());
    }

    @Test
    void uploadUsers_shouldReturnBadRequest_whenFileIsNotCsv() throws IOException {
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "test data".getBytes());

        ResponseEntity<UploadResponseDto> response = userController.uploadUsers(file);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Failed", response.getBody().getStatus());
        assertEquals("File must be a CSV", response.getBody().getMessage());
    }

    @Test
    void uploadUsers_shouldReturnOk_whenFileIsProcessedSuccessfully() throws IOException {
        MockMultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", "test data".getBytes());
        when(userService.processUsersFromCsv(any())).thenReturn(new UploadResponseDto("Success", "File processed successfully"));

        ResponseEntity<UploadResponseDto> response = userController.uploadUsers(file);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Success", response.getBody().getStatus());
        assertEquals("File processed successfully", response.getBody().getMessage());
    }

    @Test
    void uploadUsers_shouldReturnInternalServerError_whenProcessingFails() throws IOException {
        MockMultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", "test data".getBytes());
        when(userService.processUsersFromCsv(any())).thenThrow(new IOException("Processing failed"));

        ResponseEntity<UploadResponseDto> response = userController.uploadUsers(file);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Failed", response.getBody().getStatus());
        assertEquals("Error processing file: Processing failed", response.getBody().getMessage());
    }
}