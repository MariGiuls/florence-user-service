package com.example.florence.user.service.repository.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "address")
public class DBAddress {
    @Id
    private final String id;
    @Column(nullable = false)
    private final String street;
    @Column(nullable = false)
    private final String streetNumber;
    @Column(nullable = false)
    private final String city;
    @Column(nullable = false)
    private final String cap;
    @Column(nullable = false)
    private final String province;
    @Column(nullable = false)
    private final String country;

    private DBAddress(final Builder builder) {
        this.id = builder.id;
        this.street = builder.street;
        this.streetNumber = builder.streetNumber;
        this.city = builder.city;
        this.cap = builder.cap;
        this.province = builder.province;
        this.country = builder.country;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String id;
        private String street;
        private String streetNumber;
        private String city;
        private String cap;
        private String province;
        private String country;

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withStreet(String street) {
            this.street = street;
            return this;
        }

        public Builder withStreetNumber(String streetNumber) {
            this.streetNumber = streetNumber;
            return this;
        }

        public Builder withCity(String city) {
            this.city = city;
            return this;
        }

        public Builder withCap(String cap) {
            this.cap = cap;
            return this;
        }

        public Builder withProvince(String province) {
            this.province = province;
            return this;
        }

        public Builder withCountry(String country) {
            this.country = country;
            return this;
        }

        public DBAddress build() {
            return new DBAddress(this);
        }
    }
}
