package ru.com.testcountries.core.data;

import java.io.Serializable;

import io.realm.RealmObject;

public class Currency extends RealmObject implements Serializable {

    private String code;
    private String name;
    private String symbol;

}
