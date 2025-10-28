/**
 * UserController.java
 *
 * Handles SCIM user-related API endpoints.
 */
package com.okta.scim.server.example.controller;

import com.okta.scim.server.example.dto.UserDTO;
import com.okta.scim.server.example.entity.UserEntity;
import com.okta.scim.server.example.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * REST controller for SCIM User operations such as create, update, delete, and query.
 */
@RestController
@RequestMapping("/scim/v2/Users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    /**
     * Creates or reactivates a SCIM user.
     *
     * @param userDTO user data
     * @return SCIM response with created or updated user
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> createUser(@RequestBody UserDTO userDTO) {
        logger.info("POST /Users called");
        logAsJson("Incoming userDTO", userDTO);

        UserEntity savedEntity = userService.createOrReactivateUser(userDTO);
        boolean isNew = savedEntity.getScimId() == null;

        logAsJson("Saved to DB", savedEntity);
        logger.info("POST /Users end");
        return ResponseEntity.status(isNew ? HttpStatus.CREATED : HttpStatus.OK)
                .body(userService.buildSCIMResponse(savedEntity));
    }

    /**
     * Updates an existing SCIM user.
     *
     * @param scimId  SCIM user ID
     * @param userDTO updated user data
     * @return updated SCIM user response
     */
    @PutMapping("/{scimId}")
    public ResponseEntity<Map<String, Object>> putUser(@PathVariable String scimId, @RequestBody UserDTO userDTO) {
        logger.info("PUT /Users/{} called", scimId);
        UserEntity updated = userService.updateUser(scimId, userDTO);
        logger.info("PUT /Users end");
        return ResponseEntity.ok(userService.buildSCIMResponse(updated));
    }

    /**
     * Gets all users or filters by query parameter.
     *
     * @param filter optional filter (e.g., userName eq "john")
     * @return list of matching users
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getUsers(@RequestParam(required = false) String filter) {
        logger.info("GET /Users filter: {}", filter);
        Map<String, Object> response = userService.getUsers(filter);
        logger.info("GET /Users end");
        return ResponseEntity.ok(response);
    }

    /**
     * Gets a SCIM user by ID.
     *
     * @param scimId SCIM user ID
     * @return user details if found
     */
    @GetMapping("/{scimId}")
    public ResponseEntity<Map<String, Object>> getUser(@PathVariable String scimId) {
        UserEntity user = userService.getByScimId(scimId);
        if (user == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(userService.buildSCIMResponse(user));
    }

    /**
     * Soft deletes (deactivates) a user.
     *
     * @param scimId        SCIM user ID
     * @param patchRequest  patch operations
     * @return patched user response
     */
    @PatchMapping("/{scimId}")
    public ResponseEntity<Map<String, Object>> patchUser(@PathVariable String scimId, @RequestBody Map<String, Object> patchRequest) {
        logger.info("PATCH /Users/{} payload: {}", scimId, patchRequest);
        UserEntity patched = userService.patchUser(scimId, patchRequest);
        if (patched == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        logger.info("PATCH /Users end");
        return ResponseEntity.ok(userService.buildSCIMResponse(patched));
    }


    /**
     * Logs object as JSON for debugging.
     */
    private void logAsJson(String prefix, Object obj) {
        try {
            logger.info("{}: {}", prefix, objectMapper.writeValueAsString(obj));
        } catch (JsonProcessingException e) {
            logger.warn("{}: [could not serialize]", prefix);
        }
    }
    
}
