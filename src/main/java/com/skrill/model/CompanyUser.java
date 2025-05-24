package com.skrill.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "company_user")
public class CompanyUser extends PanacheEntityBase {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    public UUID id;

    @Column(name = "email_address", nullable = false, unique = true)
    public String emailAddress;

    @Column(name = "primary_receiver", nullable = false)
    public boolean primaryReceiver = false; // Defaults to false

    @Column(name = "last_updated", nullable = false)
    @UpdateTimestamp
    public LocalDateTime lastUpdated;

    // Constructors
    public CompanyUser() {
    }

    public CompanyUser(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    // Static methods for querying
    public static CompanyUser findByEmail(String email) {
        return find("emailAddress", email).firstResult();
    }

    public static List<CompanyUser> findPrimaryReceivers() {
        return list("primaryReceiver", true);
    }
}
