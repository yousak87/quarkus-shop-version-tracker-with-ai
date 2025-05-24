package com.skrill.dto;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import java.time.LocalDateTime;

public class ShopDTO {

    public String name;

    public String url;

    public String latest_version;

    public LocalDateTime last_run;

    public Integer running_state;

    public LocalDateTime last_update;

    public String repo_type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLatest_version() {
        return latest_version;
    }

    public void setLatest_version(String latest_version) {
        this.latest_version = latest_version;
    }

    public LocalDateTime getLast_run() {
        return last_run;
    }

    public void setLast_run(LocalDateTime last_run) {
        this.last_run = last_run;
    }

    public Integer getRunning_state() {
        return running_state;
    }

    public void setRunning_state(Integer running_state) {
        this.running_state = running_state;
    }

    public LocalDateTime getLast_update() {
        return last_update;
    }

    public void setLast_update(LocalDateTime last_update) {
        this.last_update = last_update;
    }

    public String getRepo_type() {
        return repo_type;
    }

    public void setRepo_type(String repo_type) {
        this.repo_type = repo_type;
    }
}
