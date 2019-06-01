package ru.com.testcountries.mvp.country_profile;

import com.arellomobile.mvp.MvpView;

import ru.com.testcountries.core.data.model.Country;
import ru.com.testcountries.mvp.BaseView;

public interface CountryProfileFragmentView extends MvpView, BaseView {

    void showCountry(Country country);

}
