/*
 * INFS3634 Group Assignment 2021
 * By Jonathan Kwok, Salma Lakehal, and Gordon Xie
 *
 */
package au.edu.unsw.infs3634.unswgamifiedlearningapp;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import au.edu.unsw.infs3634.unswgamifiedlearningapp.Model.Currency;
import au.edu.unsw.infs3634.unswgamifiedlearningapp.Model.CurrencyService;
import au.edu.unsw.infs3634.unswgamifiedlearningapp.Model.NewQuote;
import au.edu.unsw.infs3634.unswgamifiedlearningapp.Model.NewQuoteService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class InsightsFragment extends Fragment{

    // adding card components to be clickable
    private View view;
    TextView dataDisplay;
    TextView authorDisplay;
    TextView usdLabel;
    TextView audLabel;
    TextView jpyLabel;
    TextView cnyLabel;
    TextView dateLabel;
    private static String TAG = "Updates Fragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_insights, container, false);
        // method to run quote api
        getQuoteApi();
        // method to run exchange rate api
        getRatesApi();
        return view;

    }

    private void getRatesApi(){

        // initialise TextViews
        usdLabel = (TextView) view.findViewById(R.id.usd_label);
        dateLabel = (TextView) view.findViewById(R.id.date_label);
        audLabel = (TextView) view.findViewById(R.id.aud_label);
        jpyLabel = (TextView) view.findViewById(R.id.jpy_label);
        cnyLabel = (TextView) view.findViewById(R.id.cny_label);

        // Create a Gson from the base URL of exchange rate API in retrofit object
        Retrofit retrofitCurrency = new Retrofit.Builder()
                .baseUrl("https://api.vatcomply.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CurrencyService service = retrofitCurrency.create(CurrencyService.class);
        // make a call through the CurrencyService interface
        Call<Currency> currencyServiceCall = service.getCurrencyCall();
        currencyServiceCall.enqueue(new Callback<Currency>() {
            @Override
            public void onResponse(Call<Currency> call, Response<Currency> response) {
                // create a Currency object
                Currency currency = response.body();

                // set the TextViews with the exchange rates
                dateLabel.setText(currency.getDate());
                usdLabel.setText("$" + String.valueOf(currency.getRates().getUsd()));
                audLabel.setText("$" + String.valueOf(currency.getRates().getAud()));
                jpyLabel.setText("¥" + String.valueOf(currency.getRates().getJpy()));
                cnyLabel.setText("¥" + String.valueOf(currency.getRates().getCny()));
            }

            @Override
            public void onFailure(Call<Currency> call, Throwable t) {

            }
        });

    }


    // using retrofit to create api connection
    private void getQuoteApi(){

        // initialise TextViews
        dataDisplay = (TextView) view.findViewById(R.id.data_test_text);
        authorDisplay = (TextView) view.findViewById(R.id.author_text);

        // Create a Gson from the base URL of inspirational quotes API in retrofit object
        Retrofit retrofitQuote = new Retrofit.Builder()
                .baseUrl("https://inspiration.goprogram.ai/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        NewQuoteService service = retrofitQuote.create(NewQuoteService.class);
        // make a call through the NewQuoteService interface
        Call<NewQuote> quotesCall = service.getQuoteCall();
        quotesCall.enqueue(new Callback<NewQuote>() {
            @Override
            public void onResponse(Call<NewQuote> call, Response<NewQuote> response) {

                // testing condition if there is a NewQuote object from quotesCall
                // troubleshooting --> https://github.com/square/okhttp/issues/3635
                if(response.body() != null){
                    Log.d(TAG, "response.body() for quote is successful");
                    // create NewQuote object
                    NewQuote quotesBody = response.body();
                    // set the TextViews with the inspirational quote and author
                    dataDisplay.setText(quotesBody.getQuote());
                    authorDisplay.setText("-" + quotesBody.getAuthor());
                    Log.d(TAG, "API successful with author as: " + quotesBody.getAuthor());
                }
                else{
                    Log.d(TAG, "response.body() for quote is unsuccessful");
                }



            }

            @Override
            public void onFailure(Call<NewQuote> call, Throwable t) {

            }
        });


    }




}
