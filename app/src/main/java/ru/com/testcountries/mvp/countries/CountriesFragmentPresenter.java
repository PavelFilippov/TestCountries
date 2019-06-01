package ru.com.testcountries.mvp.countries;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import ru.com.testcountries.app.TheApplication;
import ru.com.testcountries.core.data.CountriesDb;
import ru.com.testcountries.core.data.DataProvider;
import ru.terrakok.cicerone.Router;

@InjectViewState
public class CountriesFragmentPresenter
        extends MvpPresenter<CountriesFragmentView>
        implements CountriesFragmentContract.CountriesFragmentPresenter {

    private static final String TAG = "CountriesFragmentPresenter";

    private Router router;

    public CountriesFragmentPresenter() {
        TheApplication.INSTANCE.getAppComponent().inject(this);
    }

    @Inject
    DataProvider dataProvider;

    @Inject
    CountriesDb countriesDb;

    @Override
    public void setRouter(Router router) {
        this.router = router;
    }

    @Override
    public void onBackPressed() {
        router.exit();
    }

    @Override
    public void loadCountries(boolean loadFromDb) {
        getViewState().showLoading();

        if(loadFromDb) {
            if (!countriesDb.isEmpty()) {
                loadCountriesFromDb();
            } else {
                loadCountriesFromNet();
            }
        } else {
            loadCountriesFromNet();
        }
    }

    @Override
    public void refreshDb() {
        dataProvider.clearCountriesInDb(throwable -> {
            getViewState().showDataBaseError(throwable);
        });
    }

// Internal methods

    private void loadCountriesFromDb() {
        dataProvider.getCountriesFromDb(countries -> {
            getViewState().hideLoading();
            getViewState().showCountries(countries);
        }, throwable -> {
            getViewState().hideLoading();
            getViewState().showDataBaseError(throwable);
        });
    }

    private void loadCountriesFromNet() {
        dataProvider.getAllCountries(countries -> {
            getViewState().hideLoading();
            dataProvider.updateCountriesInDb(countries, throwable -> {
                getViewState().showDataBaseError(throwable);
            });
            getViewState().showCountries(countries);
        }, throwable -> {
            getViewState().hideLoading();
            getViewState().showServerError(throwable);
        });
    }

}
