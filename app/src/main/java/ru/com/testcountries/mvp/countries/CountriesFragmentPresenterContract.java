package ru.com.testcountries.mvp.countries;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import ru.com.testcountries.core.data.model.Country;
import ru.com.testcountries.mvp.BasePresenter;
import ru.com.testcountries.mvp.BaseView;

public interface CountriesFragmentPresenterContract extends BasePresenter{

    void loadCountries(boolean loadFromDb);

    void refreshDb();

}
