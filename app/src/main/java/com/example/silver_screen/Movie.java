package com.example.silver_screen;

public class Movie {

    private  String title, studio, rating, streaming_link;
    private  String description;

    private  String tumbnail;

    public Movie(String title, String studio, String rating, String streaming_link, String description, String  tumbnail) {
        this.title = title;
        this.studio = studio;
        this.rating = rating;
        this.streaming_link = streaming_link;
        this.description = description;
        this.tumbnail = tumbnail;
    }

    public Movie(String title, String tumbnail){
        this.title = title;
        this.tumbnail = tumbnail;
    }

    public Movie(String title, String tumbnail, String streaming_link){
        this.title = title;
        this.tumbnail = tumbnail;
        this.streaming_link = streaming_link;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setStreaming_link(String streaming_link) {
        this.streaming_link = streaming_link;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTumbnail(String tumbnail) {
        this.tumbnail = tumbnail;
    }

    public String getTitle() {
        return title;
    }

    public String getStudio() {
        return studio;
    }

    public String getRating() {
        return rating;
    }

    public String getStreaming_link() {
        return streaming_link;
    }

    public String getDescription() {
        return description;
    }

    public String  getTumbnail() {
        return tumbnail;
    }





}
