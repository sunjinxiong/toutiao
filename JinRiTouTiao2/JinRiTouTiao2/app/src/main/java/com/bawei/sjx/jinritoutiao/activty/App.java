package com.bawei.sjx.jinritoutiao.activty;

import android.app.Application;

import com.bwie.imageloaderlibrary.ImageLoaderUtil;
import com.example.mylibrary.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * dell 孙劲雄
 * 2017/8/11
 * 20:39
 */

public class App extends Application {


    @Override
    public void onCreate() {
        super.onCreate();


        ImageLoaderConfiguration configuration = Utils.getconfiguration(this);
        ImageLoader.getInstance().init(configuration);
    }






}
