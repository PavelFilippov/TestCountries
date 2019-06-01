package ru.com.testcountries.core.dagger.module.http;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.com.testcountries.core.data.CountriesDb;

@Module
public class RealmModule {
    @Provides
    @Singleton
    public CountriesDb provideDbService() {
            return new CountriesDb();
    }
}
