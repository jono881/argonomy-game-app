/*
 * INFS3634 Group Assignment 2021
 * By Jonathan Kwok, Salma Lakehal, and Gordon Xie
 *
 */

package au.edu.unsw.infs3634.unswgamifiedlearningapp.Model;

import com.google.firebase.firestore.DocumentId;

/*
 * This model class is used in QuizQuestionActivity to retrieve the questions (stored in Firestore)
 * in the selected quiz list.
 */

public class Question {

    @DocumentId
    String questionID;
    String question, answer, optiona, optionb, optionc, optiond;
    Long timer;

    // For the database
    public Question() {
    }

    public Question(String questionID, String question, String answer, String optiona, String optionb, String optionc, String optiond, Long timer) {
        this.questionID = questionID;
        this.question = question;
        this.answer = answer;
        this.optiona = optiona;
        this.optionb = optionb;
        this.optionc = optionc;
        this.optiond = optiond;
        this.timer = timer;
    }

    public String getQuestionID() {
        return questionID;
    }

    public void setQuestionID(String questionID) {
        this.questionID = questionID;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getOptiona() {
        return optiona;
    }

    public void setOptiona(String optiona) {
        this.optiona = optiona;
    }

    public String getOptionb() {
        return optionb;
    }

    public void setOptionb(String optionb) {
        this.optionb = optionb;
    }

    public String getOptionc() {
        return optionc;
    }

    public void setOptionc(String optionc) {
        this.optionc = optionc;
    }

    public String getOptiond() {
        return optiond;
    }

    public void setOptiond(String optiond) {
        this.optiond = optiond;
    }

    public Long getTimer() {
        return timer;
    }

    public void setTimer(Long timer) {
        this.timer = timer;
    }
}
