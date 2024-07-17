package com.exostar.assesment.service;

import com.exostar.assesment.dto.UploadResponseDto;
import com.exostar.assesment.entites.User;
import com.exostar.assesment.repository.UserRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * UserServiceImpl is a service class that implements the UserService interface.
 * It provides methods to process users from a CSV file and get the count of users.
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    /**
     * Constructor for UserServiceImpl.
     *
     * @param userRepository UserRepository object for performing operations on the User entity.
     */
    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Processes users from a CSV file.
     * Reads the CSV file, creates User objects and saves them to the database.
     *
     * @param inputStream InputStream of the CSV file.
     * @return UploadResponseDto object containing the status and message of the operation.
     */
    @Override
    @Transactional
    public UploadResponseDto processUsersFromCsv(InputStream inputStream) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {

            List<User> users = new ArrayList<>();
            int totalRecords = 0;
            int successfulRecords = 0;
            int failedRecords = 0;

            for (CSVRecord csvRecord : csvParser) {
                totalRecords++;
                try {
                    String email = csvRecord.get("Email");
                    Optional<User> existingUser = userRepository.findByEmail(email);
                    if (existingUser.isEmpty()) {
                        User user = new User();
                        user.setFirstName(csvRecord.get("FirstName"));
                        user.setLastName(csvRecord.get("LastName"));
                        user.setEmail(email);
                        users.add(user);
                        successfulRecords++;
                    } else {
                        System.out.println("User with email " + email + " already exists. Skipping.");
                        failedRecords++;
                    }
                } catch (IllegalArgumentException e) {

                    // add failed record to the response
                    failedRecords++;

                    // Log the error and continue processing
                    System.err.println("Error processing record: " + e.getMessage());
                }
            }

            userRepository.saveAll(users);
            String message = String.format("Processed %d records. Successfully imported %d users.",
                    totalRecords, successfulRecords);
            return new UploadResponseDto("Success", message, totalRecords, successfulRecords, failedRecords);

        } catch (IOException e) {
            return new UploadResponseDto("Failed", "Error processing CSV file: " + e.getMessage());
        }
    }

    /**
     * Returns the count of User entities in the database.
     *
     * @return long value representing the count of User entities.
     */
    @Override
    public long getUserCount() {
        return userRepository.count();
    }
}