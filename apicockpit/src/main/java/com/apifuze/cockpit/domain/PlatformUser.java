package com.apifuze.cockpit.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A PlatformUser.
 */
@Entity
@Table(name = "platform_user")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PlatformUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @OneToOne    @JoinColumn(unique = true)
    private User user;

    @OneToOne    @JoinColumn(unique = true)
    private ApiPublisherProfile apiPublisherProfile;

    @OneToOne    @JoinColumn(unique = true)
    private ApiConsumerProfile apiConsumerProfile;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public PlatformUser phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public User getUser() {
        return user;
    }

    public PlatformUser user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ApiPublisherProfile getApiPublisherProfile() {
        return apiPublisherProfile;
    }

    public PlatformUser apiPublisherProfile(ApiPublisherProfile apiPublisherProfile) {
        this.apiPublisherProfile = apiPublisherProfile;
        return this;
    }

    public void setApiPublisherProfile(ApiPublisherProfile apiPublisherProfile) {
        this.apiPublisherProfile = apiPublisherProfile;
    }

    public ApiConsumerProfile getApiConsumerProfile() {
        return apiConsumerProfile;
    }

    public PlatformUser apiConsumerProfile(ApiConsumerProfile apiConsumerProfile) {
        this.apiConsumerProfile = apiConsumerProfile;
        return this;
    }

    public void setApiConsumerProfile(ApiConsumerProfile apiConsumerProfile) {
        this.apiConsumerProfile = apiConsumerProfile;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PlatformUser platformUser = (PlatformUser) o;
        if (platformUser.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), platformUser.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PlatformUser{" +
            "id=" + getId() +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            "}";
    }
}
