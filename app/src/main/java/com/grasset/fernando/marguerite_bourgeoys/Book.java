package com.grasset.fernando.marguerite_bourgeoys;

public class Book {
    String auteur;
    String title;
    String year;
    String dtappointment;

    public Book(String title, String auteur, String year) {
        this.auteur = auteur;
        this.title = title;
        this.year = year;
    }

    public Book(String title, String auteur, String year, String dtappointment) {
        this.auteur = auteur;
        this.title = title;
        this.year = year;
        this.dtappointment = dtappointment;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDtappointment() {
        return dtappointment;
    }

    public void setDtappointment(String dtappointment) {
        this.dtappointment = dtappointment;
    }
}
