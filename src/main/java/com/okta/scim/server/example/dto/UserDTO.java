/**
 * UserDTO.java
 *
 * Data Transfer Object for SCIM user data.
 * Used to transfer user information between API and service layers.
 */
package com.okta.scim.server.example.dto;

import com.okta.scim.util.model.SCIMUser;
import com.okta.scim.util.model.Name;
import com.okta.scim.util.model.Email;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Collection;

/**
 * Represents a SCIM user for input/output operations.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO {

    private String id;             // Optional DB ID
    private String scimId;         // SCIM unique ID
    private String userName;
    private Name name;
    private Collection<Email> emails;
    private Boolean active;
    private String externalId;

    /**
     * Default constructor.
     */
    public UserDTO() {}

    /**
     * Constructs UserDTO from a SCIMUser object.
     *
     * @param user SCIMUser object
     */
    public UserDTO(SCIMUser user) {
        this.scimId = user.getId();
        this.userName = user.getUserName();
        this.name = user.getName();
        this.emails = user.getEmails();
        try {
            this.active = user.isActive();
        } catch (NoSuchMethodError e) {
            this.active = null;
        }
    }

    // ------------------ Getters & Setters ------------------ //

    public String getScimId() { return scimId; }
    public void setScimId(String scimId) { this.scimId = scimId; }

    public String getExternalId() { return externalId; }
    public void setExternalId(String externalId) { this.externalId = externalId; }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public Name getName() { return name; }
    public void setName(Name name) { this.name = name; }

    public Collection<Email> getEmails() { return emails; }
    public void setEmails(Collection<Email> emails) { this.emails = emails; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }

    /**
     * Gets the given (first) name from Name object.
     *
     * @return given name or null
     */
    public String getGivenName() { return name != null ? name.getGivenName() : null; }

    /**
     * Gets the family (last) name from Name object.
     *
     * @return family name or null
     */
    public String getFamilyName() { return name != null ? name.getFamilyName() : null; }

    public String getFirstName() { return getGivenName(); }
    public String getLastName() { return getFamilyName(); }
}
