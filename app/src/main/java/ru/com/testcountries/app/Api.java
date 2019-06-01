package ru.com.testcountries.app;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import ru.com.testcountries.core.data.model.Country;

public interface Api {

    @GET("all")
    Observable<List<Country>> getCountries();

    @GET("alpha/{code}")
    Observable<Country> getCountryByAlphaCode(
            @Path("code") String code
    );

}
