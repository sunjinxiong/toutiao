package com.bawei.sjx.jinritoutiao.bean;

import java.util.List;

/**
 * dell 孙劲雄
 * 2017/8/17
 * 9:54
 */

public class Nwes {

    private  String url;
    private  String title;
    private  String list;
    private  int id;

    public Nwes(String url, String title,String list, int id) {
        this.url = url;
        this.title = title;
        this.list = list;
        this.id = id;
    }

    public Nwes() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getList() {
        return list;
    }

    public void setList(String list) {
        this.list = list;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Nwes{" +
                "url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", list='" + list + '\'' +
                ", id=" + id +
                '}';
    }
}
