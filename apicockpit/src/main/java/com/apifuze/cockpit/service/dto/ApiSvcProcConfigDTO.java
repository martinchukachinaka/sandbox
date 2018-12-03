package com.apifuze.cockpit.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.apifuze.cockpit.domain.enumeration.ProcessorType;

/**
 * A DTO for the ApiSvcProcConfig entity.
 */
public class ApiSvcProcConfigDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String description;

    private Boolean active;

    @NotNull
    private Instant dateCreated;

    @NotNull
    private ProcessorType processorType;

    private Integer order;

    private Long serviceConfigId;

    private String serviceConfigName;

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

    public ProcessorType getProcessorType() {
        return processorType;
    }

    public void setProcessorType(ProcessorType processorType) {
        this.processorType = processorType;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Long getServiceConfigId() {
        return serviceConfigId;
    }

    public void setServiceConfigId(Long apiServiceConfigId) {
        this.serviceConfigId = apiServiceConfigId;
    }

    public String getServiceConfigName() {
        return serviceConfigName;
    }

    public void setServiceConfigName(String apiServiceConfigName) {
        this.serviceConfigName = apiServiceConfigName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ApiSvcProcConfigDTO apiSvcProcConfigDTO = (ApiSvcProcConfigDTO) o;
        if (apiSvcProcConfigDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), apiSvcProcConfigDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ApiSvcProcConfigDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", active='" + isActive() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", processorType='" + getProcessorType() + "'" +
            ", order=" + getOrder() +
            ", serviceConfig=" + getServiceConfigId() +
            ", serviceConfig='" + getServiceConfigName() + "'" +
            "}";
    }
}
