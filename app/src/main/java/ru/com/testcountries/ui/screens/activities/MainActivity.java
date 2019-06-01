package ru.com.testcountries.ui.screens;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EActivity;

import javax.inject.Inject;

import ru.com.testcountries.R;
import ru.com.testcountries.app.TheApplication;
import ru.com.testcountries.ui.BaseActivity;
import ru.com.testcountries.ui.common.BackButtonListener;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.android.support.SupportAppNavigator;
import ru.terrakok.cicerone.commands.Command;
import ru.terrakok.cicerone.commands.Replace;

@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    //Set app navigation injections and it's management

    @Inject
    NavigatorHolder navigatorHolder;

    private Navigator navigator = new SupportAppNavigator(this, R.id.mainContainer) {
        @Override
        public void applyCommands(Command[] commands) {
            super.applyCommands(commands);
            getSupportFragmentManager().executePendingTransactions();
        }
    };

    @App
    TheApplication application;

//Main methods

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        application.getAppComponent().inject(this);
        super.onCreate(savedInstanceState);

        navigator.applyCommands(new Command[]{new Replace(new Screens.CountriesScreen())});

    }

    @Override
    protected void onResume() {
        super.onResume();
        navigatorHolder.setNavigator(navigator);
    }

    @Override
    protected void onPause() {
        navigatorHolder.removeNavigator();
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.mainContainer);
        if (!(fragment instanceof BackButtonListener)
                || !((BackButtonListener) fragment).onBackPressed()) super.onBackPressed();
    }

}
