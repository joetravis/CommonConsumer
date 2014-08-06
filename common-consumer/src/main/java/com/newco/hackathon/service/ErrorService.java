package com.newco.hackathon.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.newco.hackathon.model.FormattedError;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import java.util.Set;

@Service
public class ErrorService {
    public String formatError(Set<ConstraintViolation<?>> validationErrors) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        FormattedError errors = new FormattedError();
        for (ConstraintViolation error : validationErrors) {
            errors.addError(error.getMessage());
        }

        return mapper.writeValueAsString(errors);
    }
}
