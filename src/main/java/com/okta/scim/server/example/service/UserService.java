/**
 * UserService.java
 *
 * Provides CRUD and SCIM operations for users.
 */
package com.okta.scim.server.example.service;

import com.okta.scim.server.example.dto.UserDTO;
import com.okta.scim.server.example.entity.UserEntity;
import com.okta.scim.server.example.mapper.UserMapper;
import com.okta.scim.server.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Handles user creation, update, retrieval, patch, and soft deletion.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    /**
     * Creates a new user or reactivates an existing one.
     *
     * @param userDTO input user data
     * @return saved UserEntity
     */
    public UserEntity createOrReactivateUser(UserDTO userDTO) {
        String username = userDTO.getUserName() != null ? userDTO.getUserName().trim() : null;
        if (username == null) throw new IllegalArgumentException("Username is required");

        UserEntity existing = userRepository.findByUserName(username);
        if (existing != null) {
            existing.setActive(true);
            userMapper.updateEntityFromDTO(userDTO, existing);
            return userRepository.save(existing);
        }

        UserEntity newEntity = userMapper.toEntity(userDTO);
        newEntity.setScimId(UUID.randomUUID().toString());
        return userRepository.save(newEntity);
    }

    /**
     * Updates an existing user or creates if not found.
     *
     * @param scimId  SCIM user ID
     * @param userDTO input user data
     * @return updated or newly created UserEntity
     */
    public UserEntity updateUser(String scimId, UserDTO userDTO) {
        UserEntity found = userRepository.findByUserName(userDTO.getUserName());
        if (found == null) {
            UserEntity newEntity = userMapper.toEntity(userDTO);
            newEntity.setScimId(UUID.randomUUID().toString());
            return userRepository.save(newEntity);
        } else {
            userMapper.updateEntityFromDTO(userDTO, found);
            return userRepository.save(found);
        }
    }

    /**
     * Retrieves users based on optional filter or all active users.
     *
     * @param filter SCIM filter string
     * @return map containing SCIM user list
     */
    public Map<String, Object> getUsers(String filter) {
        List<Map<String, Object>> resources = new ArrayList<>();

        if (filter != null && filter.contains("externalId eq")) {
            String externalId = filter.split("eq")[1].trim().replace("\"", "");
            UserEntity user = userRepository.findByExternalId(externalId);
            if (user != null && Boolean.TRUE.equals(user.getActive()))
                resources.add(buildSCIMResponse(user));
        } else if (filter != null && filter.contains("userName eq")) {
            String userName = filter.split("eq")[1].trim().replace("\"", "");
            UserEntity user = userRepository.findByUserName(userName);
            if (user != null && Boolean.TRUE.equals(user.getActive()))
                resources.add(buildSCIMResponse(user));
        } else {
            List<UserEntity> activeUsers = userRepository.findAll()
                    .stream()
                    .filter(u -> Boolean.TRUE.equals(u.getActive()))
                    .collect(Collectors.toList());
            resources = activeUsers.stream().map(this::buildSCIMResponse).toList();
        }

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("schemas", List.of("urn:ietf:params:scim:api:messages:2.0:ListResponse"));
        response.put("totalResults", resources.size());
        response.put("Resources", resources);
        return response;
    }

    /**
     * Retrieves a user by SCIM ID.
     *
     * @param scimId SCIM user ID
     * @return UserEntity or null
     */
    public UserEntity getByScimId(String scimId) {
        return userRepository.findByScimId(scimId);
    }

    /**
     * Applies PATCH operations to a user (currently supports "active" field).
     *
     * @param scimId       SCIM user ID
     * @param patchRequest map of operations
     * @return updated UserEntity or null if not found
     */
    public UserEntity patchUser(String scimId, Map<String, Object> patchRequest) {
        UserEntity existing = userRepository.findByScimId(scimId);
        if (existing == null) return null;

        if (patchRequest.containsKey("Operations")) {
   // This part of the code is implementing a method to soft delete (deactivate) a user in the SCIM
   // system.
            List<Map<String, Object>> operations = (List<Map<String, Object>>) patchRequest.get("Operations");
            for (Map<String, Object> op : operations) {
                String opType = ((String) op.get("op")).toLowerCase();
                Object value = op.get("value");

                if ("replace".equals(opType) && value instanceof Map) {
                    Map<String, Object> valueMap = (Map<String, Object>) value;
                    if (valueMap.containsKey("active")) {
                        Object activeVal = valueMap.get("active");
                        existing.setActive(Boolean.valueOf(activeVal.toString()));
                    }
                }
            }
        }

        return userRepository.save(existing);
    }


    /**
     * Builds a SCIM-compliant response map for a user.
     *
     * @param user UserEntity
     * @return map representing SCIM user
     */
    public Map<String, Object> buildSCIMResponse(UserEntity user) {
        Map<String, Object> resp = new LinkedHashMap<>();
        resp.put("schemas", List.of("urn:ietf:params:scim:schemas:core:2.0:User"));
        resp.put("id", user.getScimId());
        resp.put("externalId", user.getExternalId());
        resp.put("userName", user.getUserName());
        resp.put("active", user.getActive());

        Map<String, Object> nameMap = Map.of(
                "givenName", user.getGivenName(),
                "familyName", user.getFamilyName()
        );
        resp.put("name", nameMap);

        if (user.getEmail() != null) {
            resp.put("emails", List.of(Map.of("value", user.getEmail(), "primary", true)));
        }

        resp.put("meta", Map.of("resourceType", "User", "location", "/scim/v2/Users/" + user.getScimId()));
        return resp;
    }
}
