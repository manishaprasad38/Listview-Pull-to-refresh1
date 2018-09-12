package com.demo.app.assignment.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class News {
    @SerializedName("title")
    private String title;
    @SerializedName("rows")
    private List<RowsItem> rows;

    public void setTitle(String title)
    {
        this.title = title;
    }
    public String getTitle()
    {
        return title;
    }
    public void setRows(List<RowsItem> rows)
    {
        this.rows = rows;
    }
    public List<RowsItem> getRows()
    {
        return rows;
    }
    public class RowsItem
    {
        @SerializedName("imageHref")
        private String imageHref;
        @SerializedName("description")
        private String description;
        @SerializedName("title")
        private String title;


        public void setImageHref(String imageHref)
        {
            this.imageHref = imageHref;
        }
        public String getImageHref()
        {
            return imageHref;
        }
        public void setDescription(String description)
        {
            this.description = description;
        }
        public String getDescription(){
            return description;
        }
        public void setTitle(String title)
        {
            this.title = title;
        }
        public String getTitle()
        {
            return title;
        }
    }
}