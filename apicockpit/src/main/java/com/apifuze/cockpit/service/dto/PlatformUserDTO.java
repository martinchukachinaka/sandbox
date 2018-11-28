package com.apifuze.cockpit.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the PlatformUser entity.
 */
public class PlatformUserDTO implements Serializable {

    private Long id;

    @NotNull
    private String phoneNumber;

    private Long userId;

    private Long apiPublisherProfileId;

    private Long apiConsumerProfileId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getApiPublisherProfileId() {
        return apiPublisherProfileId;
    }

    public void setApiPublisherProfileId(Long apiPublisherProfileId) {
        this.apiPublisherProfileId = apiPublisherProfileId;
    }

    public Long getApiConsumerProfileId() {
        return apiConsumerProfileId;
    }

    public void setApiConsumerProfileId(Long apiConsumerProfileId) {
        this.apiConsumerProfileId = apiConsumerProfileId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PlatformUserDTO platformUserDTO = (PlatformUserDTO) o;
        if (platformUserDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), platformUserDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PlatformUserDTO{" +
            "id=" + getId() +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", user=" + getUserId() +
            ", apiPublisherProfile=" + getApiPublisherProfileId() +
            ", apiConsumerProfile=" + getApiConsumerProfileId() +
            "}";
    }
}
