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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment by database
    @Column(name = "scim_id", nullable = false, unique = true)
    private Long scimId; // 👈 change to Long or Integer for auto-increment

    @Column(name = "external_id", unique = true)
    private String externalId;

    @Column(nullable = false, unique = true)
    private String userName;

    @Column
    private String givenName;

    @Column
    private String familyName;

    @Column
    private String email;

    @Column(nullable = false)
    private Boolean active = true;

    public UserEntity() {
        this.active = true;
        // ❌ Do NOT assign scimId here anymore — database will handle it.
    }

    // Getters and Setters
    public Long getScimId() { return scimId; }
    public void setScimId(Long scimId) { this.scimId = scimId; }

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

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
}










