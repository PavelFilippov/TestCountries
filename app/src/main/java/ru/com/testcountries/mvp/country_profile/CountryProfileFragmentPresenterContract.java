package ru.com.testcountries.mvp.country_profile;

import ru.com.testcountries.mvp.BasePresenter;

public interface CountryProfileFragmentPresenterContract extends BasePresenter {

    void loadCountry(String code);

}
