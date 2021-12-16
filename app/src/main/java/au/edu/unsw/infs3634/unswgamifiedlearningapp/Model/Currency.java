/*
 * INFS3634 Group Assignment 2021
 * By Jonathan Kwok, Salma Lakehal, and Gordon Xie
 *
 */

package au.edu.unsw.infs3634.unswgamifiedlearningapp.Model;

/*
 * This model class is used in InsightsFragment to create Currency object from API call'
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Currency {

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("base")
    @Expose
    private String base;
    @SerializedName("rates")
    @Expose
    private CurrencyRates currencyRates;

    // getter and setter methods
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    // this method allows a new CurrencyRates object to be created to call for its characteristics
    public CurrencyRates getRates() {
        return currencyRates;
    }

    public void setRates(CurrencyRates currencyRates) {
        this.currencyRates = currencyRates;
    }

}