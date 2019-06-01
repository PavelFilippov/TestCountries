package ru.com.testcountries.mvp;

import ru.terrakok.cicerone.Router;

public interface BasePresenter {

    void setRouter(Router router);

    void onBackPressed();

}
