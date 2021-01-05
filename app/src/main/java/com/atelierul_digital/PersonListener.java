package com.atelierul_digital;

import java.util.List;

public interface PersonListener {
    void onPersonsFetchedFromServer(List<Person> personList);
}
