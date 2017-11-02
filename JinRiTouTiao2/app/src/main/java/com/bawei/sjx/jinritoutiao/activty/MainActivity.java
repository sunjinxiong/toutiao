package com.bawei.sjx.jinritoutiao.activty;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import com.bawei.sjx.jinritoutiao.R;
public class  MainActivity extends AppCompatActivity {
    private Handler handler=new Handler();
    int i=1;
    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            if(i==0){

                Intent intent=new Intent(MainActivity.this,Main2Activity.class);

                startActivity(intent);
                finish();
            }
             if(i>0){
                 i--;
                 handler.postDelayed(runnable,1000);
             }else{
                 handler.removeCallbacks(runnable);
             }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏状态栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);


          handler.postDelayed(runnable,1000);


    }



}
