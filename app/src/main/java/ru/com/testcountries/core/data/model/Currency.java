package ru.com.testcountries.core.data.model;

import java.io.Serializable;

import io.realm.RealmObject;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Currency implements Serializable {

    private String code;
    private String name;
    private String symbol;

}
