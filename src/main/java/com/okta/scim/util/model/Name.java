package com.okta.scim.util.model;

public class Name {
    private String givenName;
    private String familyName;
    private String firstName;
    private String lastName;
    private String formattedName;

    // Constructors
    public Name() {}

    public Name(String givenName, String familyName) {
        this.givenName = givenName;
        this.familyName = familyName;
    }

    // Getters
    public String getGivenName() { return givenName; }
    public String getFamilyName() { return familyName; }
    

    // Setters
    public void setGivenName(String givenName) { this.givenName = givenName; }
    public void setFamilyName(String familyName) { this.familyName = familyName; }
   
}
