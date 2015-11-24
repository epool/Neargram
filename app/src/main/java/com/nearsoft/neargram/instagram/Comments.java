package com.nearsoft.neargram.instagram;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Comments {

    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("data")
    @Expose
    private List<Comment> data = new ArrayList<Comment>();

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
    public List<Comment> getData() {
        return data;
    }

    /**
     * @param data The data
     */
    public void setData(List<Comment> data) {
        this.data = data;
    }

}
