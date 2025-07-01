package com.virosms.restaurantreservationchallenge.model.email;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.virosms.restaurantreservationchallenge.infra.exception.ValidateEmailException;
import com.virosms.restaurantreservationchallenge.utils.Constants;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;


@Embeddable
@JsonDeserialize(using = EmailDeserializer.class)
public class Email {


    @Column(name = "email")
    private String value;

    protected Email() {
    }

    public Email(String email) {
        System.out.println("Email: " + email);

        if (!validateEmail(email)) {
            System.out.println("Email validation failed");
            throw new ValidateEmailException(Constants.INVALID_EMAIL_FORMAT);
        }
        System.out.println("Email validation successful");
        setEmail(email);
    }

    private boolean validateEmail(String email) {
        return email != null && !email.isBlank() && email.matches(Constants.EMAIL_REGEX);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return this.value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email1 = (Email) o;
        return Objects.equals(value, email1.value);
    }

    protected void setEmail(String email) {
        this.value = email;
    }

    public String getValue() {
        return this.value;
    }
}
