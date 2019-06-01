package ru.com.testcountries.core.data.model;

import java.io.Serializable;

import io.realm.RealmObject;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Language implements Serializable {

    private String name;
    private String nativeName;

}
