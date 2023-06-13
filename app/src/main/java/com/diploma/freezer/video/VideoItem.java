package com.diploma.freezer.video;

import androidx.annotation.NonNull;


import java.util.List;

public class VideoItem {
    private String caption;
    private String description;

    public VideoItem(){}

    public VideoItem(String calories, String caption, String description, List<String> ingredients){
        this.caption = caption;
        this.description = description;
    }


    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
