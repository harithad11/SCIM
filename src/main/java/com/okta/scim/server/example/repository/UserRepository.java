/**
 * UserRepository.java
 *
 * Description:
 *  Repository interface for managing {@link com.okta.scim.server.example.entity.UserEntity} instances.
 *  Provides database access methods for SCIM user records, including lookups by SCIM ID, username,
 *  and external (Okta) ID.
 *
 * Project: Okta SCIM Server Example
 */

package com.okta.scim.server.example.repository;

import com.okta.scim.server.example.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The {@code UserRepository} interface provides CRUD and query operations for
 * {@link UserEntity} objects using Spring Data JPA.
 * <p>
 * This interface extends {@link JpaRepository}, inheriting standard database operations
 * such as save, findAll, delete, and findById.
 * <p>
 * Custom query methods are defined to retrieve users based on SCIM-specific fields.
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

    /**
     * Finds a user by their username.
     *
     * @param userName the username to search for.
     * @return the matching {@link UserEntity}, or {@code null} if no match is found.
     */
    UserEntity findByUserName(String userName);

    /**
     * Finds a user by their SCIM ID.
     *
     * @param scimId the SCIM identifier to search for.
     * @return the matching {@link UserEntity}, or {@code null} if no match is found.
     */
    UserEntity findByScimId(String scimId);

    /**
     * Finds a user by their external (Okta) ID.
     *
     * @param externalId the external identifier assigned by Okta.
     * @return the matching {@link UserEntity}, or {@code null} if no match is found.
     */
    UserEntity findByExternalId(String externalId);
}
