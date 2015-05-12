package com.foda.campus.model;


import java.io.Serializable;

public class LostAndFoundData implements Serializable {

    public String id;
    public String title;
    public String content;
    public String publishTime;
    public String publisher;
    public String parentId;
    public int type;  // 0：失物 ，1：招领
}
