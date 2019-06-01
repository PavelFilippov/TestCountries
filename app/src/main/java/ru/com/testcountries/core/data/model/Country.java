package ru.com.testcountries.core.data.model;

import java.io.Serializable;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Country extends RealmObject implements Serializable {

    @PrimaryKey
    private String alpha3Code;
    private String name;
    private String flag;

    @Ignore
    private List<Currency> currencies;
    @Ignore
    private List<Language> languages;
    @Ignore
    private List<String> timezones;

}
