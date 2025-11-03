/**
 * UserMapper.java
 *
 * Maps between {@link UserDTO} and {@link UserEntity}.
 */
package com.okta.scim.server.example.mapper;

import com.okta.scim.server.example.dto.UserDTO;
import com.okta.scim.server.example.entity.UserEntity;
import com.okta.scim.util.model.Email;
import org.springframework.stereotype.Component;

/**
 * Converts SCIM user DTOs to entities and updates entities from DTOs.
 */
@Component
public class UserMapper {

    /**
     * Converts a {@link UserDTO} to a new {@link UserEntity}.
     *
     * @param dto source DTO
     * @return new UserEntity
     */
    public UserEntity toEntity(UserDTO dto) {
        UserEntity entity = new UserEntity(); // ❌ Do NOT assign scimId manually
        entity.setUserName(dto.getUserName());
        entity.setExternalId(dto.getExternalId());
        entity.setActive(dto.getActive() != null ? dto.getActive() : true);

        if (dto.getName() != null) {
            entity.setGivenName(dto.getName().getGivenName());
            entity.setFamilyName(dto.getName().getFamilyName());
        }

        if (dto.getEmails() != null && !dto.getEmails().isEmpty()) {
            Email firstEmail = dto.getEmails().stream().findFirst().orElse(null);
            if (firstEmail != null) {
                entity.setEmail(firstEmail.getValue());
            }
        }

        return entity;
    }

    /**
     * Updates an existing {@link UserEntity} from a {@link UserDTO}.
     *
     * @param dto    source DTO
     * @param entity entity to update
     */
    public void updateEntityFromDTO(UserDTO dto, UserEntity entity) {
        if (dto.getName() != null) {
            entity.setGivenName(dto.getName().getGivenName());
            entity.setFamilyName(dto.getName().getFamilyName());
        }

        if (dto.getEmails() != null && !dto.getEmails().isEmpty()) {
            Email firstEmail = dto.getEmails().stream().findFirst().orElse(null);
            if (firstEmail != null) {
                entity.setEmail(firstEmail.getValue());
            }
        }

        entity.setActive(dto.getActive() != null ? dto.getActive() : entity.getActive());
        entity.setExternalId(dto.getExternalId());
    }
}
