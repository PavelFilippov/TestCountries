package ru.com.testcountries.core.dagger.module.http;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.com.testcountries.core.utils.NetworkUtils;

@Module
public class NetworkUtilsModule {
    @Provides
    @Singleton
    public NetworkUtils getNetworkUtils() {
        return new NetworkUtils();
    }
}
