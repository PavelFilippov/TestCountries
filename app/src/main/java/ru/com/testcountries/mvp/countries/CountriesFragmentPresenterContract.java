package ru.com.testcountries.mvp.countries;

import ru.com.testcountries.mvp.BasePresenter;

public interface CountriesFragmentPresenterContract extends BasePresenter{

    void loadCountries(boolean loadFromDb);

    void refreshDb();

    void goToCountryProfile(String code);

}
