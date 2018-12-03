package com.apifuze.cockpit.initializer.dto;

import com.apifuze.cockpit.service.dto.ApiPublisherProfileDTO;
import com.apifuze.cockpit.service.dto.ApiServiceConfigDTO;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class SystemInitDTO implements Serializable {

    private ApiPublisherProfileDTO publisherInfo;

    private List<ApiServiceConfigDTO> serviceList;

    public ApiPublisherProfileDTO getPublisherInfo() {
        return publisherInfo;
    }

    public void setPublisherInfo(ApiPublisherProfileDTO publisherInfo) {
        this.publisherInfo = publisherInfo;
    }

    public List<ApiServiceConfigDTO> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<ApiServiceConfigDTO> serviceList) {
        this.serviceList = serviceList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SystemInitDTO that = (SystemInitDTO) o;
        return Objects.equals(publisherInfo, that.publisherInfo) &&
            Objects.equals(serviceList, that.serviceList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(publisherInfo, serviceList);
    }

}
