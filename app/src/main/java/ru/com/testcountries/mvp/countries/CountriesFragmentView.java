package ru.com.testcountries.mvp.countries;

import com.arellomobile.mvp.MvpView;

import java.util.List;

import ru.com.testcountries.core.data.model.Country;
import ru.com.testcountries.mvp.BaseView;

public interface CountriesFragmentView extends MvpView, BaseView {

    void showCountries(List<Country> countries);

    void showDataBaseError(Throwable throwable);

}
