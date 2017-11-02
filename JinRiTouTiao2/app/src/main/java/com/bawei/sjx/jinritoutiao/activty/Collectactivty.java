package com.bawei.sjx.jinritoutiao.activty;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bawei.sjx.jinritoutiao.Adapter.ShoucAdapter;
import com.bawei.sjx.jinritoutiao.Dao.Userdao;
import com.bawei.sjx.jinritoutiao.R;
import com.bawei.sjx.jinritoutiao.bean.Nwes;
import com.bwie.xlistviewlibrary.XListView;

import java.util.ArrayList;
import java.util.List;

public class Collectactivty extends AppCompatActivity {

    private XListView collect_xlistview;
    private  List<Nwes> list;
    private ImageView collect_fan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collectactivty);

        collect_xlistview= (XListView) findViewById(R.id.collect_listview);
        collect_fan= (ImageView) findViewById(R.id.collect_fan);


        Userdao userdao=new Userdao(this);
        list=new ArrayList<Nwes>();
        List<Nwes> selected = userdao.findSelected();
          for (Nwes li:selected){

             list.add(li);
          }
        collect_xlistview.setAdapter(new ShoucAdapter(list,this));

         collect_xlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 Toast.makeText(Collectactivty.this,"未开发",Toast.LENGTH_LONG).show();
             }
         });

      collect_fan.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
               finish();
         }
     });
    }
}
