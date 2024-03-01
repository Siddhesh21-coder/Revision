package com.codersarena.revision.model;

import java.util.ArrayList;

public class Topic {
    String Link;
    String Name;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    ArrayList<Image> Images;

    public Topic() {
    }

    public Topic(ArrayList<Image> images,String link,String name) {
        this.Link = link;
        this.Images = images;
        this.Name = name;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        this.Link = link;
    }

    public ArrayList<Image> getImages() {
        return Images;
    }

    public void setImages(ArrayList<Image> images) {
        this.Images = images;
    }
}
