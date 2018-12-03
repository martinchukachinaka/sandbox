package com.apifuze.cockpit.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A ApiProject.
 */
@Entity
@Table(name = "api_project")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ApiProject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "active")
    private Boolean active;

    @NotNull
    @Column(name = "date_created", nullable = false)
    private Instant dateCreated;

    @OneToOne    @JoinColumn(unique = true)
    private ApiProjectAuthConfig apiKey;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "api_project_api",
               joinColumns = @JoinColumn(name = "api_projects_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "apis_id", referencedColumnName = "id"))
    private Set<ApiProjectService> apis = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("projects")
    private ApiConsumerProfile owner;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ApiProject name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public ApiProject description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isActive() {
        return active;
    }

    public ApiProject active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Instant getDateCreated() {
        return dateCreated;
    }

    public ApiProject dateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
    }

    public ApiProjectAuthConfig getApiKey() {
        return apiKey;
    }

    public ApiProject apiKey(ApiProjectAuthConfig apiProjectAuthConfig) {
        this.apiKey = apiProjectAuthConfig;
        return this;
    }

    public void setApiKey(ApiProjectAuthConfig apiProjectAuthConfig) {
        this.apiKey = apiProjectAuthConfig;
    }

    public Set<ApiProjectService> getApis() {
        return apis;
    }

    public ApiProject apis(Set<ApiProjectService> apiProjectServices) {
        this.apis = apiProjectServices;
        return this;
    }

    public ApiProject addApi(ApiProjectService apiProjectService) {
        this.apis.add(apiProjectService);
        return this;
    }

    public ApiProject removeApi(ApiProjectService apiProjectService) {
        this.apis.remove(apiProjectService);
        return this;
    }

    public void setApis(Set<ApiProjectService> apiProjectServices) {
        this.apis = apiProjectServices;
    }

    public ApiConsumerProfile getOwner() {
        return owner;
    }

    public ApiProject owner(ApiConsumerProfile apiConsumerProfile) {
        this.owner = apiConsumerProfile;
        return this;
    }

    public void setOwner(ApiConsumerProfile apiConsumerProfile) {
        this.owner = apiConsumerProfile;
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
        ApiProject apiProject = (ApiProject) o;
        if (apiProject.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), apiProject.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ApiProject{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", active='" + isActive() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            "}";
    }
}
