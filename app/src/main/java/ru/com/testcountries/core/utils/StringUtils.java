package ru.com.testcountries.core.utils;

import java.util.List;

import ru.com.testcountries.core.data.model.Currency;
import ru.com.testcountries.core.data.model.Language;

public class StringUtils {

    public static String makeStringFromStringList(List<String> strings) {

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < strings.size(); i++) {
            if(i == 0) sb.append(strings.get(i));
            else sb.append(", ").append(strings.get(i));
        }

        return sb.toString();
    }

    public static String makeStringFromCurrenciesList(List<Currency> currencies) {

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < currencies.size(); i++) {
            if(i == 0) sb.append(currencies.get(i).getName());
            else sb.append(", ").append(currencies.get(i).getName());
        }

        return sb.toString();
    }

    public static String makeStringFromLanguagesList(List<Language> languages) {

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < languages.size(); i++) {
            if(i == 0) sb.append(languages.get(i).getName());
            else sb.append(", ").append(languages.get(i).getName());
        }

        return sb.toString();
    }

}
