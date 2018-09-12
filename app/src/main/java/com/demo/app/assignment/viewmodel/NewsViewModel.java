package com.demo.app.assignment.viewmodel;

import android.support.annotation.NonNull;

import com.demo.app.assignment.model.News;


public class NewsViewModel  {
    private News news;
    @NonNull
    public String title;
    @NonNull
    public String description;
    @NonNull
    public String imageHref;


    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    public void setDescription(@NonNull String description) {
        this.description = description;
    }

    @NonNull
    public String getImageHref() {
        return imageHref;
    }

    public void setImageHref(@NonNull String imageHref) {
        this.imageHref = imageHref;
    }

    public static boolean isEmpty(String s) {
        return (s == null || s.isEmpty());
    }

    public static boolean isNotEmpty(String s) {
        return !isEmpty(s);
    }
}
