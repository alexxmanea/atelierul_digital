package com.atelierul_digital;

import java.util.ArrayList;
import java.util.List;

class Name {
    private String firstName;
    private String lastName;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Name(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}

public class DataSource {
    public List<Name> getNames() {
        List<Name> names = new ArrayList<>();

        for (int i = 0; i < 200; ++i) {
            names.add(new Name("FirstName " + i, "LastName " + i));
        }

        return names;
    }
}
