package ru.com.testcountries.core.data;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import ru.com.testcountries.app.TheApplication;
import ru.com.testcountries.core.data.model.Country;
import ru.com.testcountries.app.NoNetworkException;
import ru.com.testcountries.app.Api;
import ru.com.testcountries.core.utils.NetworkUtils;
import ru.com.testcountries.core.utils.RxUtils;

public class DataProvider {

    private Api api;

    public DataProvider(Api api) {
        this.api = api;
        TheApplication.INSTANCE.getAppComponent().inject(this);

    }

    @Inject
    NetworkUtils networkUtils;

    @Inject
    CountriesDb countriesDb;

//HTTP methods

    public Disposable getAllCountries(Consumer<List<Country>> onComplete, Consumer<Throwable> onError) {
        if (!hasNetwork()) return createNoNetworkSubscription(onComplete, onError);
        return api.getCountries()
                .compose(RxUtils.applySchedulers())
                .subscribe(onComplete, onError);
    }

    public Disposable getCountry(String code, Consumer<Country> onComplete, Consumer<Throwable> onError) {
        if (!hasNetwork()) return createNoNetworkSubscription(onComplete, onError);
        return api.getCountryByAlphaCode(code)
                .compose(RxUtils.applySchedulers())
                .subscribe(onComplete, onError);
    }

//DB methods

    public Disposable getCountriesFromDb(Consumer<List<Country>> onComplete, Consumer<Throwable> onError) {
        return countriesDb.getCountries()
                .compose(RxUtils.applySchedulerSingle())
                .subscribe(onComplete, onError);
    }

    public Disposable updateCountriesInDb(List<Country> countries, Consumer<Throwable> onError) {
        return countriesDb.updateCountries(countries)
                .doOnError(onError)
                .subscribe();
    }

    public Disposable clearCountriesInDb(Consumer<Throwable> onError) {
        return countriesDb.clearAllCountries()
                .doOnError(onError)
                .subscribe();
    }


//Network methods

    private boolean hasNetwork() {
        return true;
    }

    private <T> Disposable createNoNetworkSubscription(Consumer<T> onComplete, Consumer<Throwable> onError) {
        return RxUtils.makeObservable((Callable<T>) () -> {
            throw new NoNetworkException();
        }).subscribe(onComplete, onError);
    }
}
