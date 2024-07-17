package com.exostar.assesment.service;

import com.exostar.assesment.dto.UploadResponseDto;

import java.io.InputStream;

/**
 * UserService is an interface that provides methods to process users from a CSV file and get the user count.
 */
public interface UserService {

    /**
     * Processes users from a CSV file.
     *
     * @param inputStream the InputStream of the CSV file.
     * @return an UploadResponseDto containing the result of the processing.
     */
    UploadResponseDto processUsersFromCsv(InputStream inputStream);

    /**
     * Gets the count of users.
     *
     * @return the count of users.
     */
    long getUserCount();
}