/*
 * INFS3634 Group Assignment 2021
 * By Jonathan Kwok, Salma Lakehal, and Gordon Xie
 *
 */
package au.edu.unsw.infs3634.unswgamifiedlearningapp.Model;

/*
 * This model class is used in LeaderboardFragment to retrieve the top 5 users'
 * names and dollar score.
 */

public class User {

    public String firstName, lastName, email;
    public int dollars;

    public User() {
    }

    public User(String firstName, String lastName, String email, int dollars) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dollars = dollars;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getDollars() {
        return dollars;
    }

    public void setDollars(int dollars) {
        this.dollars = dollars;
    }
}
