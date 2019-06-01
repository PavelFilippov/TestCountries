package ru.com.testcountries.core.data;

import java.io.Serializable;

import io.realm.RealmObject;

public class Language extends RealmObject implements Serializable {

    private String name;
    private String nativeName;

}
