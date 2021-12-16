/*
 * INFS3634 Group Assignment 2021
 * By Jonathan Kwok, Salma Lakehal, and Gordon Xie
 *
 */

package au.edu.unsw.infs3634.unswgamifiedlearningapp.Model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/*
 * This interface class is used in InsightsFragment to create the NewQuote API call'
 */

public interface NewQuoteService {

    // NewQuote call through the link below
    @GET("https://inspiration.goprogram.ai/")
    Call<NewQuote> getQuoteCall();

    // retrieve the NewQuote based on the quote's string
    @GET("https://inspiration.goprogram.ai/{quote}")
    Call<NewQuote> getQuote(@Path("quote") String quote);

    // retrieve the NewQuote based on the name of author
    @GET("https://inspiration.goprogram.ai/{author}")
    Call<NewQuote> getAuthor(@Path("author") String author);
}
