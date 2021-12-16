/*
 * INFS3634 Group Assignment 2021
 * By Jonathan Kwok, Salma Lakehal, and Gordon Xie
 *
 */

package au.edu.unsw.infs3634.unswgamifiedlearningapp.Model;

/*
 * This model class is used in InsightsFragment to get the currency exchange rates from API call'
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrencyRates {

    // CurrencyRates object stores all the currency conversions
    @SerializedName("EUR")
    @Expose
    private Integer eur;
    @SerializedName("USD")
    @Expose
    private Double usd;
    @SerializedName("JPY")
    @Expose
    private Double jpy;
    @SerializedName("BGN")
    @Expose
    private Double bgn;
    @SerializedName("CZK")
    @Expose
    private Double czk;
    @SerializedName("DKK")
    @Expose
    private Double dkk;
    @SerializedName("GBP")
    @Expose
    private Double gbp;
    @SerializedName("HUF")
    @Expose
    private Double huf;
    @SerializedName("PLN")
    @Expose
    private Double pln;
    @SerializedName("RON")
    @Expose
    private Double ron;
    @SerializedName("SEK")
    @Expose
    private Double sek;
    @SerializedName("CHF")
    @Expose
    private Double chf;
    @SerializedName("ISK")
    @Expose
    private Double isk;
    @SerializedName("NOK")
    @Expose
    private Double nok;
    @SerializedName("HRK")
    @Expose
    private Double hrk;
    @SerializedName("RUB")
    @Expose
    private Double rub;
    @SerializedName("TRY")
    @Expose
    private Double _try;
    @SerializedName("AUD")
    @Expose
    private Double aud;
    @SerializedName("BRL")
    @Expose
    private Double brl;
    @SerializedName("CAD")
    @Expose
    private Double cad;
    @SerializedName("CNY")
    @Expose
    private Double cny;
    @SerializedName("HKD")
    @Expose
    private Double hkd;
    @SerializedName("IDR")
    @Expose
    private Double idr;
    @SerializedName("ILS")
    @Expose
    private Double ils;
    @SerializedName("INR")
    @Expose
    private Double inr;
    @SerializedName("KRW")
    @Expose
    private Double krw;
    @SerializedName("MXN")
    @Expose
    private Double mxn;
    @SerializedName("MYR")
    @Expose
    private Double myr;
    @SerializedName("NZD")
    @Expose
    private Double nzd;
    @SerializedName("PHP")
    @Expose
    private Double php;
    @SerializedName("SGD")
    @Expose
    private Double sgd;
    @SerializedName("THB")
    @Expose
    private Double thb;
    @SerializedName("ZAR")
    @Expose
    private Double zar;

    public Integer getEur() {
        return eur;
    }

    public void setEur(Integer eur) {
        this.eur = eur;
    }

    public Double getUsd() {
        return usd;
    }

    public void setUsd(Double usd) {
        this.usd = usd;
    }

    public Double getJpy() {
        return jpy;
    }

    public void setJpy(Double jpy) {
        this.jpy = jpy;
    }

    public Double getBgn() {
        return bgn;
    }

    public void setBgn(Double bgn) {
        this.bgn = bgn;
    }

    public Double getCzk() {
        return czk;
    }

    public void setCzk(Double czk) {
        this.czk = czk;
    }

    public Double getDkk() {
        return dkk;
    }

    public void setDkk(Double dkk) {
        this.dkk = dkk;
    }

    public Double getGbp() {
        return gbp;
    }

    public void setGbp(Double gbp) {
        this.gbp = gbp;
    }

    public Double getHuf() {
        return huf;
    }

    public void setHuf(Double huf) {
        this.huf = huf;
    }

    public Double getPln() {
        return pln;
    }

    public void setPln(Double pln) {
        this.pln = pln;
    }

    public Double getRon() {
        return ron;
    }

    public void setRon(Double ron) {
        this.ron = ron;
    }

    public Double getSek() {
        return sek;
    }

    public void setSek(Double sek) {
        this.sek = sek;
    }

    public Double getChf() {
        return chf;
    }

    public void setChf(Double chf) {
        this.chf = chf;
    }

    public Double getIsk() {
        return isk;
    }

    public void setIsk(Double isk) {
        this.isk = isk;
    }

    public Double getNok() {
        return nok;
    }

    public void setNok(Double nok) {
        this.nok = nok;
    }

    public Double getHrk() {
        return hrk;
    }

    public void setHrk(Double hrk) {
        this.hrk = hrk;
    }

    public Double getRub() {
        return rub;
    }

    public void setRub(Double rub) {
        this.rub = rub;
    }

    public Double getTry() {
        return _try;
    }

    public void setTry(Double _try) {
        this._try = _try;
    }

    public Double getAud() {
        return aud;
    }

    public void setAud(Double aud) {
        this.aud = aud;
    }

    public Double getBrl() {
        return brl;
    }

    public void setBrl(Double brl) {
        this.brl = brl;
    }

    public Double getCad() {
        return cad;
    }

    public void setCad(Double cad) {
        this.cad = cad;
    }

    public Double getCny() {
        return cny;
    }

    public void setCny(Double cny) {
        this.cny = cny;
    }

    public Double getHkd() {
        return hkd;
    }

    public void setHkd(Double hkd) {
        this.hkd = hkd;
    }

    public Double getIdr() {
        return idr;
    }

    public void setIdr(Double idr) {
        this.idr = idr;
    }

    public Double getIls() {
        return ils;
    }

    public void setIls(Double ils) {
        this.ils = ils;
    }

    public Double getInr() {
        return inr;
    }

    public void setInr(Double inr) {
        this.inr = inr;
    }

    public Double getKrw() {
        return krw;
    }

    public void setKrw(Double krw) {
        this.krw = krw;
    }

    public Double getMxn() {
        return mxn;
    }

    public void setMxn(Double mxn) {
        this.mxn = mxn;
    }

    public Double getMyr() {
        return myr;
    }

    public void setMyr(Double myr) {
        this.myr = myr;
    }

    public Double getNzd() {
        return nzd;
    }

    public void setNzd(Double nzd) {
        this.nzd = nzd;
    }

    public Double getPhp() {
        return php;
    }

    public void setPhp(Double php) {
        this.php = php;
    }

    public Double getSgd() {
        return sgd;
    }

    public void setSgd(Double sgd) {
        this.sgd = sgd;
    }

    public Double getThb() {
        return thb;
    }

    public void setThb(Double thb) {
        this.thb = thb;
    }

    public Double getZar() {
        return zar;
    }

    public void setZar(Double zar) {
        this.zar = zar;
    }

}