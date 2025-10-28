/**
 * UserEntity.java
 *
 * Description:
 *  Represents a user record in the SCIM (System for Cross-domain Identity Management) system.
 *  This entity maps directly to the "users" table in the database.
 *  It contains user identifiers, profile attributes, and status flags used for provisioning
 *  and synchronization between Okta and the SCIM service.
 *
 * Project: Okta SCIM Server Example
 */

package com.okta.scim.server.example.entity;

import jakarta.persistence.*;
import java.util.UUID;

/**
 * The {@code UserEntity} class defines the schema for the "users" table.
 * It serves as the persistence model for SCIM user objects and contains
 * identifying information such as SCIM ID, external (Okta) ID, name, and email.
 */
@Entity
@Table(name = "users")
public class UserEntity {

    /**
     * The unique SCIM identifier for the user.
     * This field acts as the primary key in the database.
     * It is generated as a random UUID if not provided.
     */
    @Id
    @Column(name = "scim_id", nullable = false, unique = true)
    private String scimId;

    /**
     * The external identifier associated with the user in Okta.
     * Used to map between Okta and SCIM user records.
     */
    @Column(name = "external_id", unique = true)
    private String externalId;

    /**
     * The username (unique) associated with this user account.
     */
    @Column(nullable = false, unique = true)
    private String userName;

    /**
     * The given (first) name of the user.
     */
    @Column
    private String givenName;

    /**
     * The family (last) name of the user.
     */
    @Column
    private String familyName;

    /**
     * The email address of the user.
     */
    @Column
    private String email;

    /**
     * Indicates whether the user account is active.
     * Defaults to {@code true} for all newly created users.
     */
    @Column(nullable = false)
    private Boolean active  = true;

    // --------------------------------------------------------
    // Constructors
    // --------------------------------------------------------

    /**
     * Default constructor.
     * <p>
     * If no SCIM ID is provided, this constructor automatically generates a
     * random UUID for the {@code scimId} field and sets {@code active } to {@code true}.
     */
    public UserEntity() {
        if (this.scimId == null) {
            this.scimId = UUID.randomUUID().toString();
        }
        this.active  = true;
    }


     // --------------------------------------------------------
    // Getters and Setters
    // --------------------------------------------------------

    public String getScimId() { return scimId; }
    public void setScimId(String scimId) { this.scimId = scimId; }

    public String getExternalId() { return externalId; }
    public void setExternalId(String externalId) { this.externalId = externalId; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getGivenName() { return givenName; }
    public void setGivenName(String givenName) { this.givenName = givenName; }

    public String getFamilyName() { return familyName; }
    public void setFamilyName(String familyName) { this.familyName = familyName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Boolean getActive() { return active ; }
    public void setActive(Boolean active ) { this.active  = active ; }
}
