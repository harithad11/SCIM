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
 * Repository interface for managing {@link UserEntity} instances.
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    /**
     * Finds a user by their username.
     */
    UserEntity findByUserName(String userName);

    /**
     * Finds a user by their SCIM ID.
     */
    UserEntity findByScimId(Long scimId);

    /**
     * Finds a user by their external (Okta) ID.
     */
    UserEntity findByExternalId(String externalId);
}
