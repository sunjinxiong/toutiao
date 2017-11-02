package com.bawei.sjx.jinritoutiao.WEbview;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.sjx.jinritoutiao.Dao.Userdao;
import com.bawei.sjx.jinritoutiao.R;
import com.bawei.sjx.jinritoutiao.activty.Main2Activity;
import com.bawei.sjx.jinritoutiao.bean.Nwes;
import com.bwie.imageloaderlibrary.ImageLoaderUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.share.QQShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WebActivity extends AppCompatActivity {

    private WebView webView;
    private ImageView web_fan,web_shou,web_fenxaing,web_pinlun;
    private EditText web_pin;
    private Userdao userdao;
    private List<String> list;
    private int i=0;
    private Tencent mTencent;
    private String title;
    private String image_list;
    private String path;
    private static final String APP_ID = "1105602574";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web);
         init();
         web();

         chuan();
        shouc();
         fan();


        mTencent =Tencent.createInstance(APP_ID,WebActivity.this);


    }
    public void chuan(){
        userdao=new Userdao(this);
        title = getIntent().getStringExtra("title");
        image_list = getIntent().getStringExtra("image_list");
        path = getIntent().getStringExtra("path");
        webView.loadUrl(path);

    }
    public  void init(){
        webView=(WebView)findViewById(R.id.web);
        web_shou= (ImageView) findViewById(R.id.web_shou);
         web_fan= (ImageView) findViewById(R.id.web_fan);

        web_fenxaing=(ImageView)findViewById(R.id.web_fenxaing);
    }
    public void shouc(){
        web_shou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i++;
                if(i%2==1){
                    userdao.insertDetsilNews(title,path,image_list);
                    Toast.makeText(WebActivity.this,"收藏成功",Toast.LENGTH_SHORT).show();
                    web_shou.setImageResource(R.drawable.love_jokebar_textpage_selected_normal);
                }else {
                    userdao.del(title);
                    Toast.makeText(WebActivity.this,"取消收藏",Toast.LENGTH_SHORT).show();
                    web_shou.setImageResource(R.drawable.love_jokebar_textpage_pressed_night);

                }
            }
        });
    }

    public void  web(){

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());
    }

    public void fan(){


        web_fan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                finish();
            }
        });

        web_fenxaing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                     share();
            }
        });

    }
    private class BaseUiListener implements IUiListener {
        @Override
        public void onComplete(final Object response) {
            Toast.makeText(WebActivity.this, "授权成功", Toast.LENGTH_SHORT).show();

            if(response!=null) {
                JSONObject obj = (JSONObject) response;
                try {
                    if (obj.optInt("ret")==0) {
                        String openID = obj.getString("openid");
                        String accessToken = obj.getString("access_token");
                        String expires = obj.getString("expires_in");
                        mTencent.setOpenId(openID);
                        mTencent.setAccessToken(accessToken, expires);
                        QQToken qqToken = mTencent.getQQToken();


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onError(UiError uiError) {

        }

        @Override
        public void onCancel() {

        }


    }
    public void share()
    {
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY,  "要分享的摘要");
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,path);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,path);
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME,  "测试应用222222");
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, 0);//0代表QQ好友 1代表QQ空间
        mTencent.shareToQQ(WebActivity.this, params, new BaseUiListener());
    }

}
