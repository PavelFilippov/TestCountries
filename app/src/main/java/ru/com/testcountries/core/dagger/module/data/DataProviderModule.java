package ru.com.testcountries.core.dagger.module.http;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.com.testcountries.core.data.DataProvider;
import ru.com.testcountries.app.Api;

@Module(includes = {ApiModule.class})
public class DataProviderModule {
    @Provides
    @Singleton
    public DataProvider provideNetService(Api api) {
        return new DataProvider(api);
    }
}
