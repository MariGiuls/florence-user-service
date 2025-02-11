package com.example.florence.user.service.repository.model;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "address")
public class DBAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private String streetNumber;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String cap;

    @Column(nullable = false)
    private String province;

    @Column(nullable = false)
    private String country;

    private DBAddress(final Builder builder) {
        this.id = builder.id;
        this.street = builder.street;
        this.streetNumber = builder.streetNumber;
        this.city = builder.city;
        this.cap = builder.cap;
        this.province = builder.province;
        this.country = builder.country;
    }

    protected DBAddress() {}

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Integer id;
        private String street;
        private String streetNumber;
        private String city;
        private String cap;
        private String province;
        private String country;

        public Builder withId(Integer id) {
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
