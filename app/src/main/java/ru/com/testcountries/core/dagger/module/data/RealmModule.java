package ru.com.testcountries.core.dagger.module.data;

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
