/*
 * INFS3634 Group Assignment 2021
 * By Jonathan Kwok, Salma Lakehal, and Gordon Xie
 *
 */

package au.edu.unsw.infs3634.unswgamifiedlearningapp.Model;

/*
 * This model class is used in InsightsFragment to create NewQuote object from API call'
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewQuote {

    @SerializedName("quote")
    @Expose
    private String quote;
    @SerializedName("author")
    @Expose
    private String author;

    // getter and setter methods for the quote and quote's author
    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

}