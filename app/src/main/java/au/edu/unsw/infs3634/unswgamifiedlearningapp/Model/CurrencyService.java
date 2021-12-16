/*
 * INFS3634 Group Assignment 2021
 * By Jonathan Kwok, Salma Lakehal, and Gordon Xie
 *
 */

package au.edu.unsw.infs3634.unswgamifiedlearningapp.Model;

/*
 * This interface class is used in InsightsFragment to create the Currency API call'
 */

import retrofit2.Call;
import retrofit2.http.GET;


public interface CurrencyService {

    // create a Currency call based on the link in @GET
    @GET("https://api.vatcomply.com/rates")
    Call<Currency> getCurrencyCall();

}
