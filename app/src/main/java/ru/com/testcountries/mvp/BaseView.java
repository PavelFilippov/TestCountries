package ru.com.testcountries.mvp;

public interface BaseView {

    void showLoading();

    void hideLoading();

    void showServerError(Throwable throwable);

}
