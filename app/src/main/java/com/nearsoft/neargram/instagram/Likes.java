package com.nearsoft.neargram.instagram;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Likes {

    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("data")
    @Expose
    private List<Like> data = new ArrayList<Like>();

    /**
     * @return The count
     */
    public Integer getCount() {
        return count;
    }

    /**
     * @param count The count
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     * @return The data
     */
    public List<Like> getData() {
        return data;
    }

    /**
     * @param data The data
     */
    public void setData(List<Like> data) {
        this.data = data;
    }

}
