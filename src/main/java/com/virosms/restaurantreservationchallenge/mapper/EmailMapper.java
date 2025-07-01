package com.virosms.restaurantreservationchallenge.mapper;

import com.virosms.restaurantreservationchallenge.model.email.Email;
import org.springframework.stereotype.Component;

@Component
public class EmailMapper {

    public Email toEmail(String value){
        return value == null ? null : new Email(value);
    }

    public String fromEmail(Email email){
        return email == null ? null : email.getValue();
    }
}
