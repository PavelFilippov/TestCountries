package ru.com.testcountries.ui.screens.fragments;

import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.load.resource.file.FileToStreamDecoder;
import com.caverock.androidsvg.SVG;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.io.InputStream;

import javax.inject.Inject;

import ru.com.testcountries.R;
import ru.com.testcountries.app.TheApplication;
import ru.com.testcountries.core.data.model.Country;
import ru.com.testcountries.core.utils.StringUtils;
import ru.com.testcountries.core.utils.svg.SvgDecoder;
import ru.com.testcountries.core.utils.svg.SvgDrawableTranscoder;
import ru.com.testcountries.core.utils.svg.SvgSoftwareLayerSetter;
import ru.com.testcountries.mvp.country_profile.CountryProfileFragmentPresenter;
import ru.com.testcountries.mvp.country_profile.CountryProfileFragmentView;
import ru.com.testcountries.ui.BaseMainFragment;
import ru.com.testcountries.ui.common.BackButtonListener;
import ru.com.testcountries.ui.common.RouterProvider;
import ru.terrakok.cicerone.Router;

@EFragment(R.layout.fragment_country_profile)
public class CountryProfileFragment extends BaseMainFragment implements CountryProfileFragmentView, BackButtonListener {

    private static final String TAG = "CountryProfileFragment";
    private static final String CODE = "CountryCode";

//Set screen views

    @ViewById
    AppCompatImageView imgBack;

    @ViewById
    View clError;

    @ViewById
    ProgressBar progressBar;

    @ViewById
    View clMainContainer;

    @ViewById
    AppCompatImageView imgFlag;

    @ViewById
    TextView txtCountryName;

    @ViewById
    TextView txtCurrencies;

    @ViewById
    TextView txtLanguages;

    @ViewById
    TextView txtTimeZones;

//Set app navigation injections and it's management

    @Inject
    Router router;

//Set Bundles and Beans

    @InjectPresenter
    CountryProfileFragmentPresenter presenter;

    @App
    TheApplication application;

    @FragmentArg(CODE)
    String code;

    public static Bundle getBundle(String code) {
        Bundle args = new Bundle();
        args.putString(CODE, code);
        return args;
    }

//Set Local variables

    private GenericRequestBuilder<Uri, InputStream, SVG, PictureDrawable> requestBuilder;

    Country country;

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

        presenter.loadCountry(code);

    }

    @Override
    public void showCountry(Country country) {
        this.country = country;

        clMainContainer.setVisibility(View.VISIBLE);

        generateRequestBuilder();
        requestBuilder
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .load(Uri.parse(country.getFlag()))
                .into(imgFlag);

        txtCountryName.setText(country.getName());

        if(country.getCurrencies() != null && country.getCurrencies().size() > 0) {
            txtCurrencies.setText(country.getCurrencies().size() == 1 ?
                    String.format(getString(R.string.currency_), country.getCurrencies().get(0).getName()) :
                    String.format(getString(R.string.currencies_), StringUtils.makeStringFromCurrenciesList(country.getCurrencies())));
        }

        if(country.getLanguages() != null && country.getLanguages().size() > 0) {
            txtLanguages.setText(country.getLanguages().size() == 1 ?
                    String.format(getString(R.string.language_), country.getLanguages().get(0).getName()) :
                    String.format(getString(R.string.languages_), StringUtils.makeStringFromLanguagesList(country.getLanguages())));
        }

        if(country.getTimezones() != null && country.getTimezones().size() > 0) {
            txtTimeZones.setText(country.getTimezones().size() == 1 ?
                    String.format(getString(R.string.time_zone_), country.getTimezones().get(0)) :
                    String.format(getString(R.string.time_zones_), StringUtils.makeStringFromStringList(country.getTimezones())));
        }

    }

    @Override
    public void showLoading() {
        hideErrorView();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showServerError(Throwable throwable) {
        showErrorView();
        getMainActivity().showServerError(throwable);
    }

    @Override
    public boolean onBackPressed() {
        onBackClick();
        return true;
    }

//SetViewClicks

    @Click(R.id.imgBack)
    void onBackClick() {
        presenter.onBackPressed();
    }

//Internal methods

    private void showErrorView() {
        clMainContainer.setVisibility(View.GONE);
        clError.setVisibility(View.VISIBLE);
    }

    private void hideErrorView() {
        clError.setVisibility(View.GONE);
    }

    private void generateRequestBuilder() {
        requestBuilder = Glide.with(getMainActivity())
                .using(Glide.buildStreamModelLoader(Uri.class, getMainActivity()), InputStream.class)
                .from(Uri.class)
                .as(SVG.class)
                .transcode(new SvgDrawableTranscoder(), PictureDrawable.class)
                .sourceEncoder(new StreamEncoder())
                .cacheDecoder(new FileToStreamDecoder<SVG>(new SvgDecoder()))
                .decoder(new SvgDecoder())
                .placeholder(R.drawable.rounded_corners_empty_image)
                .error(R.drawable.rounded_corners_empty_image)
                .animate(android.R.anim.fade_in)
                .listener(new SvgSoftwareLayerSetter<Uri>());
    }

}
