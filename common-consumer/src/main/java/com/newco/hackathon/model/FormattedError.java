package com.newco.hackathon.model;

import java.util.ArrayList;
import java.util.List;

public class FormattedError {
    public List<String> errors = new ArrayList();

    public void addError(String error) {
        errors.add(error);
    }
}
