package com.apifuze.cockpit.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the GrovyProcConfig entity.
 */
public class GrovyProcConfigDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String description;

    private Boolean active;

    @NotNull
    private Instant dateCreated;

    @NotNull
    private String pScript;

    private Long configId;

    private String configName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Instant getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getpScript() {
        return pScript;
    }

    public void setpScript(String pScript) {
        this.pScript = pScript;
    }

    public Long getConfigId() {
        return configId;
    }

    public void setConfigId(Long apiSvcProcConfigId) {
        this.configId = apiSvcProcConfigId;
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String apiSvcProcConfigName) {
        this.configName = apiSvcProcConfigName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GrovyProcConfigDTO grovyProcConfigDTO = (GrovyProcConfigDTO) o;
        if (grovyProcConfigDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), grovyProcConfigDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GrovyProcConfigDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", active='" + isActive() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", pScript='" + getpScript() + "'" +
            ", config=" + getConfigId() +
            ", config='" + getConfigName() + "'" +
            "}";
    }
}
