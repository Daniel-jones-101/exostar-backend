package com.exostar.assesment.repository;

import com.exostar.assesment.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * UserRepository is an interface that extends JpaRepository.
 * It provides methods to perform CRUD operations on User entities.
 * It uses UUID as the type of the id of the User entity.
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    /**
     * Finds a User entity by its email.
     *
     * @param email the email of the User entity to retrieve.
     * @return an Optional that may contain the User entity if found.
     */
    Optional<User> findByEmail(String email);

    /**
     * Finds all User entities with a specific last name.
     *
     * @param lastName the last name to search for.
     * @return a List of User entities with the given last name.
     */
    List<User> findByLastName(String lastName);

    /**
     * Checks if a User entity with a specific email exists.
     *
     * @param email the email to search for.
     * @return a boolean indicating whether a User entity with the given email exists.
     */
    boolean existsByEmail(String email);

    /**
     * Counts the number of User entities with a specific last name.
     *
     * @param lastName the last name to count.
     * @return the number of User entities with the given last name.
     */
    long countByLastName(String lastName);

    /**
     * Finds all User entities with a specific first and last name.
     *
     * @param firstName the first name to search for.
     * @param lastName  the last name to search for.
     * @return a List of User entities with the given first and last name.
     */
    List<User> findByFirstNameAndLastName(String firstName, String lastName);

    /**
     * Finds all User entities with an email ending with a specific domain.
     *
     * @param domain the domain to search for.
     * @return a List of User entities with an email ending with the given domain.
     */
    List<User> findByEmailEndingWith(String domain);

}