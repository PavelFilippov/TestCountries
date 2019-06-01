package ru.com.testcountries.core.data;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import ru.com.testcountries.core.data.model.Country;

public class CountriesDb {

    private RealmConfiguration mRealmConfiguration;

    public CountriesDb() {
        mRealmConfiguration = Realm.getDefaultConfiguration();
    }

    public Single<List<Country>> getCountries() {
        Realm realm = Realm.getInstance(mRealmConfiguration);
        List<Country> countries = realm.copyFromRealm(realm.where(Country.class).findAll());
        realm.close();
        return Single.just(countries);
    }

    public Completable updateCountries(List<Country> countries) {
        return Completable.fromAction(() -> {
            Realm realm = Realm.getInstance(mRealmConfiguration);
            realm.executeTransaction(realm1 -> {
                realm.insertOrUpdate(countries);
            });
            realm.close();
            Realm.compactRealm(mRealmConfiguration);
        });
    }

    public Completable clearAllCountries() {
        return Completable.fromAction(() -> {
            Realm realm = Realm.getInstance(mRealmConfiguration);
            realm.executeTransaction(realm1 -> {
                realm.deleteAll();
            });
            realm.close();
            Realm.compactRealm(mRealmConfiguration);
        });
    }

    public boolean isEmpty() {
        Realm realm = Realm.getInstance(mRealmConfiguration);
        boolean isEmpty = realm.isEmpty();
        realm.close();
        return isEmpty;
    }
}
