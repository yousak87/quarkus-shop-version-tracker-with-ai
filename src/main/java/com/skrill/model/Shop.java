package com.skrill.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;


import java.time.LocalDateTime;

@Entity
@Table(name = "shop")
public class Shop extends PanacheEntity {

    public long id;

    public String name;

    public String url;

    public String latest_version;

    public LocalDateTime last_run;

    public Integer running_state;

    public LocalDateTime last_update;

    public String repo_type;

    public Shop(){}

    public static void deleteShop(String shopId){
        delete("id", shopId);
    }

    public static Shop findByName(String shopName) {
        return find("name", shopName).firstResult();
    }

}
