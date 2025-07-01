package com.virosms.restaurantreservationchallenge.model.email;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class EmailDeserializer extends JsonDeserializer<Email> {

    @Override
    public Email deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, IOException {
        String emailStr = p.getValueAsString();
        return new Email(emailStr);
    }
}