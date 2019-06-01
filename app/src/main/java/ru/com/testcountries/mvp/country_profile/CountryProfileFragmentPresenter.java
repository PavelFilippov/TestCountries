package ru.com.testcountries.mvp.country_profile;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import ru.com.testcountries.app.TheApplication;
import ru.com.testcountries.core.data.DataProvider;
import ru.terrakok.cicerone.Router;

@InjectViewState
public class CountryProfileFragmentPresenter
        extends MvpPresenter<CountryProfileFragmentView>
        implements CountryProfileFragmentPresenterContract {

    private static final String TAG = "CountryProfileFragmentPresenter";

    private Router router;

    public CountryProfileFragmentPresenter() {
        TheApplication.INSTANCE.getAppComponent().inject(this);
    }

    @Inject
    DataProvider dataProvider;

    @Override
    public void loadCountry(String code) {
        getViewState().showLoading();
        dataProvider.getCountry(code, country -> {
            getViewState().hideLoading();
            getViewState().showCountry(country);
        }, throwable -> {
            getViewState().hideLoading();
            getViewState().showServerError(throwable);
        });
    }

    @Override
    public void setRouter(Router router) {
        this.router = router;
    }

    @Override
    public void onBackPressed() {
        router.exit();
    }
}
