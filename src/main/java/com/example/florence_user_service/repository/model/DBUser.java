package com.example.florence_user_service.repository.model;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "user")
public class DBUser {
    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false)
    private String email;

    @Column(name = "fiscalCode", unique = true, nullable = false)
    private String fiscalCode;

    @JoinColumn(name = "address_id", referencedColumnName = "id", nullable = false)
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private DBAddress address;

    private DBUser(final Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.surname = builder.surname;
        this.email = builder.email;
        this.fiscalCode = builder.fiscalCode;
        this.address = builder.address;
    }

    protected DBUser() {}

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String id;
        private String name;
        private String surname;
        private String email;
        private String fiscalCode;
        private DBAddress address;

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withSurname(String surname) {
            this.surname = surname;
            return this;
        }

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder withFiscalCode(String fiscalCode) {
            this.fiscalCode = fiscalCode;
            return this;
        }

        public Builder withAddress(DBAddress address) {
            this.address = address;
            return this;
        }

        public DBUser build() {
            return new DBUser(this);
        }
    }
}
