package com.example.silver_screen;

public class slide {

    private int Image;
    private String Title;


    public slide(int image, String title) {
        Image = image;
        Title = title;
    }

    public int getImage() {
        return Image;
    }


    public String getTitle() {
        return Title;
    }

    public void setImage(int image) {
        Image = image;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
