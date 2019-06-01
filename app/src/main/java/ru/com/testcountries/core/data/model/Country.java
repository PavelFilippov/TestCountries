package ru.com.testcountries.core.data;

import java.io.Serializable;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Country extends RealmObject implements Serializable {

    @PrimaryKey
    private String numericCode;

    private String name;
    private String flag;
    private RealmList<Currency> currencies;
    private RealmList<Language> languages;
    private RealmList<String> timezones;

}
