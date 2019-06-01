package ru.com.testcountries.ui.screens;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import ru.com.testcountries.ui.screens.activities.MainActivity_;
import ru.com.testcountries.ui.screens.fragments.CountriesFragment_;
import ru.com.testcountries.ui.screens.fragments.CountryProfileFragment;
import ru.com.testcountries.ui.screens.fragments.CountryProfileFragment_;
import ru.terrakok.cicerone.android.support.SupportAppScreen;

public class Screens {

//-----------------------------Main Container---------------------------------

    public static final class MainScreen extends SupportAppScreen {
        @Override
        public Intent getActivityIntent(Context context) {
            return new Intent(context, MainActivity_.class);
        }
    }

//-----------------------------Countries screen---------------------------------

    public static final class CountriesScreen extends SupportAppScreen {
        private Context context;

        @Override
        public Intent getActivityIntent(Context context) {
            this.context = context;
            return super.getActivityIntent(context);
        }

        @Override
        public Fragment getFragment() {
            return Fragment.instantiate(context, CountriesFragment_.class.getName());
        }
    }
//-----------------------------Country profile screen---------------------------------

    public static final class CountryProfileScreen extends SupportAppScreen {
        private Context context;
        private final String code;

        public CountryProfileScreen(String code) {
            this.code = code;
        }

        @Override
        public Intent getActivityIntent(Context context) {
            this.context = context;
            return super.getActivityIntent(context);
        }

        @Override
        public Fragment getFragment() {
            return Fragment.instantiate(context, CountryProfileFragment_.class.getName(), CountryProfileFragment.getBundle(code));
        }
    }

}
