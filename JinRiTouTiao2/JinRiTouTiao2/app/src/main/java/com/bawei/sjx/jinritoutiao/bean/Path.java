package com.bawei.sjx.jinritoutiao.bean;

/**
 * dell 孙劲雄
 * 2017/8/11
 * 9:32
 */

public class Path {

      private  String name;
    private  String uri;

    public Path(String name, String uri) {
        this.name = name;
        this.uri = uri;
    }

    public Path() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
