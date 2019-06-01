package ru.com.testcountries.core.dagger;

import javax.inject.Singleton;

import dagger.Component;
import ru.com.testcountries.core.dagger.module.data.DataProviderModule;
import ru.com.testcountries.core.dagger.module.data.NetworkUtilsModule;
import ru.com.testcountries.core.dagger.module.data.RealmModule;
import ru.com.testcountries.core.dagger.module.navigation.LocalNavigationModule;
import ru.com.testcountries.core.dagger.module.navigation.NavigationModule;
import ru.com.testcountries.core.data.DataProvider;
import ru.com.testcountries.app.NetworkConnectivityAware;
import ru.com.testcountries.mvp.countries.CountriesFragmentPresenter;
import ru.com.testcountries.mvp.country_profile.CountryProfileFragmentPresenter;
import ru.com.testcountries.ui.screens.fragments.CountriesFragment;
import ru.com.testcountries.ui.screens.activities.MainActivity;
import ru.com.testcountries.ui.screens.fragments.CountryProfileFragment;

@Singleton
@Component(modules = {
        NavigationModule.class,
        LocalNavigationModule.class,
        DataProviderModule.class,
        RealmModule.class,
        NetworkUtilsModule.class

})

public interface AppComponent {

//Navigation injections

    void inject(MainActivity activity);

    void inject(CountriesFragment fragment);

    void inject(CountryProfileFragment fragment);

//Presenter injections

    void inject(CountriesFragmentPresenter presenter);

    void inject(CountryProfileFragmentPresenter presenter);

//Other injections

    void inject(DataProvider dataProvider);

    void inject(NetworkConnectivityAware connectivityAware);

}
