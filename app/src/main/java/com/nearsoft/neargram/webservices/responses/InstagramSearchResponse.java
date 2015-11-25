package com.nearsoft.neargram.webservices.responses;

import com.nearsoft.neargram.instagram.Photo;

import java.util.List;

/**
 * Instagram popular response.
 * Created by epool on 11/23/15.
 */
public class InstagramSearchResponse {
    private List<Photo> data;

    public List<Photo> getData() {
        return data;
    }

    public void setData(List<Photo> data) {
        this.data = data;
    }
}
