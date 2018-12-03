package com.apifuze.cockpit.initializer.dto;

import java.io.Serializable;

public class ProjectDTO implements Serializable {

    private String name;

    private String description;

    private Long[] requiredApis;



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

    public Long[] getRequiredApis() {
        return requiredApis;
    }

    public void setRequiredApis(Long[] requiredApis) {
        this.requiredApis = requiredApis;
    }
}
