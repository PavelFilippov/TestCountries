package ru.com.testcountries.ui;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import ru.com.testcountries.R;
import ru.com.testcountries.app.NetworkConnectivityAware_;
import ru.com.testcountries.core.data.events.NetworkData;
import ru.com.testcountries.app.NoNetworkException;

public abstract class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity";

    private NetworkConnectivityAware_ receiver;

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        receiver = new NetworkConnectivityAware_();
        registerReceiver(receiver, filter);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
    }

    @Override
    protected void onStop() {
        if (receiver != null) {
            unregisterReceiver(receiver);
        }
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe
    public void onNetworkStateChanged(NetworkData networkData) {
        Crouton.cancelAllCroutons();
        if (networkData.isConnected()) {
            Crouton.makeText(this, getString(R.string.network_connection_established), new Style.Builder().setBackgroundColorValue(getResources().getColor(R.color.colorDeepSkyBlue)).build()).show();
        } else {
            Crouton.makeText(this, getString(R.string.network_connection_lost), new Style.Builder().setBackgroundColorValue(getResources().getColor(R.color.colorPrimaryDark)).build()).show();
        }
    }

    public void showServerError(Throwable throwable) {
        if (throwable instanceof NoNetworkException) {
            EventBus.getDefault().post(new NetworkData(false));
        } else {
            onServerError();
        }
    }

    public void showDbError(Throwable throwable) {
        Crouton.makeText(this, getString(R.string.database_error), new Style.Builder()
                .setBackgroundColorValue(getResources().getColor(R.color.colorPrimaryDark))
                .build())
                .show();
    }

    private void onServerError() {
        Crouton.makeText(this, getString(R.string.server_error), new Style.Builder()
                .setBackgroundColorValue(getResources().getColor(R.color.colorPrimaryDark))
                .build())
                .show();
    }

}
