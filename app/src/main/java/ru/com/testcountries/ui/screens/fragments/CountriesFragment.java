package ru.com.testcountries.ui.screens.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.arellomobile.mvp.presenter.InjectPresenter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import javax.inject.Inject;

import ru.com.testcountries.R;
import ru.com.testcountries.app.TheApplication;
import ru.com.testcountries.core.data.model.Country;
import ru.com.testcountries.mvp.countries.CountriesFragmentPresenter;
import ru.com.testcountries.mvp.countries.CountriesFragmentView;
import ru.com.testcountries.ui.BaseMainFragment;
import ru.com.testcountries.ui.adapters.CountriesAdapter;
import ru.com.testcountries.ui.common.BackButtonListener;
import ru.com.testcountries.ui.common.RouterProvider;
import ru.terrakok.cicerone.Router;

@EFragment(R.layout.fragment_countries_list)
public class CountriesFragment extends BaseMainFragment implements CountriesFragmentView, BackButtonListener {

    private static final String TAG = "CountriesFragment";

//Set screen views

    @ViewById
    SwipeRefreshLayout srlRefresh;

    @ViewById
    View clError;

    @ViewById
    ProgressBar progressBar;

    @ViewById
    RecyclerView rvCountries;

//Set app navigation injections and it's management

    @Inject
    Router router;

//Set Bundles and Beans

    @InjectPresenter
    CountriesFragmentPresenter presenter;

    @App
    TheApplication application;

//Set Local variables

    private CountriesAdapter adapter;
    private int lastVisibleElement;
    private long mLastClickTime = 0;

//Main methods

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application.getAppComponent().inject(this);
    }

    @AfterViews
    public void afterViews() {
        if ((getParentFragment()) != null) {
            presenter.setRouter(((RouterProvider) getParentFragment()).getRouter());
        } else {
            presenter.setRouter(router);
        }

        setAdapter();
        setRefresh();
        loadCountries(true);
    }

    @Override
    public void showLoading() {
        hideErrorView();
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showServerError(Throwable throwable) {
        showErrorViewIfEmpty();
        getMainActivity().showServerError(throwable);
    }

    @Override
    public void showDataBaseError(Throwable throwable) {
        showErrorViewIfEmpty();
        getMainActivity().showDbError(throwable);
    }

    @Override
    public void showCountries(List<Country> countries) {
        if(countries.isEmpty()) {
            showErrorViewIfEmpty();
        } else {
            rvCountries.setVisibility(View.VISIBLE);
            adapter.setData(countries);
        }
    }

    @Override
    public boolean onBackPressed() {
        presenter.onBackPressed();
        return true;
    }

//Internal methods

    private void setAdapter() {

        if (adapter != null && rvCountries.getLayoutManager() != null) {
            rvCountries.getLayoutManager().scrollToPosition(lastVisibleElement);
        } else {
            adapter = new CountriesAdapter(getMainActivity());
            rvCountries.setLayoutManager(new LinearLayoutManager(getMainActivity()));
            rvCountries.setAdapter(adapter);
        }

        adapter.setRecyclerTouchListener((model, position) -> {
            if (isDoubleClick(mLastClickTime)) {
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            lastVisibleElement = position;
            presenter.goToCountryProfile(model.getAlpha3Code());
        });
    }

    private void setRefresh() {
        srlRefresh.setColorSchemeResources(R.color.colorPrimary);

        srlRefresh.setOnRefreshListener(() -> {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (srlRefresh != null) {
                        srlRefresh.setRefreshing(false);
                        adapter.clear();
                        presenter.refreshDb();
                        loadCountries(false);
                    }
                }
            }, 500);
        });
    }

    private void loadCountries(boolean loadFromDb) {
        presenter.loadCountries(loadFromDb);
    }

    private void showErrorViewIfEmpty() {
        if(adapter != null && adapter.isEmpty()) {
            rvCountries.setVisibility(View.GONE);
            clError.setVisibility(View.VISIBLE);
        }
    }

    private void hideErrorView() {
        clError.setVisibility(View.GONE);
    }

}
