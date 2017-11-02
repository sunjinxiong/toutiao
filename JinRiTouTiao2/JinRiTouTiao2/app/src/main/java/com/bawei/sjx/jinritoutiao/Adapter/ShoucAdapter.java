package com.bawei.sjx.jinritoutiao.Adapter;
;import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.sjx.jinritoutiao.R;
import com.bawei.sjx.jinritoutiao.bean.Nwes;
import com.example.mylibrary.Utils;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * dell 孙劲雄
 * 2017/8/18
 * 8:56
 */

public class ShoucAdapter extends BaseAdapter {

    private final  int TEXT=0;
    private final  int IMA=1;
    private final  int IMAGE=2;

    private List<Nwes> data;
    private Context context;

    public ShoucAdapter(List<Nwes> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        int type=getItemViewType(position);
           ViewHolder2 viewHolder2=new ViewHolder2();
        if(convertView==null){
            switch (type){

                case TEXT:
                    convertView=View.inflate(context, R.layout.item,null);
                    viewHolder2.text= (TextView) convertView.findViewById(R.id.item_text);
                    viewHolder2.text2= (TextView) convertView.findViewById(R.id.item_text2);

                    break;
                case IMA:
                    convertView=View.inflate(context, R.layout.item3,null);
                    viewHolder2.text2= (TextView) convertView.findViewById(R.id.item3_text2);
                    viewHolder2.text= (TextView) convertView.findViewById(R.id.item3_text);
                    viewHolder2.imageView=(ImageView) convertView.findViewById(R.id.item3_ima);
                    break;
                case IMAGE:
                    convertView=View.inflate(context, R.layout.item4,null);
                    viewHolder2.text2= (TextView) convertView.findViewById(R.id.item4_text2);
                    viewHolder2.text= (TextView) convertView.findViewById(R.id.item4_text);
                    viewHolder2.imageView=(ImageView) convertView.findViewById(R.id.item4_ima);
                    viewHolder2.imageView2= (ImageView) convertView.findViewById(R.id.item4_ima2);
                    viewHolder2.imageView3= (ImageView) convertView.findViewById(R.id.item4_ima3);
                    break;
            }

            convertView.setTag(viewHolder2);
        }else {
           viewHolder2= (ViewHolder2) convertView.getTag();
        }

        try {
            JSONArray js=new JSONArray(data.get(position).getList());
        switch (type){
            case TEXT:
                viewHolder2.text.setText(data.get(position).getTitle());
                break;
            case IMA:
                viewHolder2.text.setText(data.get(position).getTitle());
                DisplayImageOptions op= Utils.getoptions();
                ImageLoader.getInstance().displayImage(js.optJSONObject(position).optString("url"),viewHolder2.imageView,op);
                break;
            case IMAGE:
                DisplayImageOptions op1= Utils.getoptions();
                viewHolder2.text.setText(data.get(position).getTitle());
                ImageLoader.getInstance().displayImage(js.optJSONObject(0).optString("url"),viewHolder2.imageView,op1);
                ImageLoader.getInstance().displayImage(js.optJSONObject(1).optString("url"),viewHolder2.imageView2,op1);
                ImageLoader.getInstance().displayImage(js.optJSONObject(2).optString("url"),viewHolder2.imageView3,op1);
                break;

        }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        try {
            JSONArray js=new JSONArray(data.get(position).getList());
             Log.i("+++",js+"");
            if(js==null){
                return TEXT;
            }else if(js.length()==1){
                return IMA;
            }if(js.length()==3) {
                return IMAGE;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return TEXT;
    }
    class  ViewHolder2{
        TextView text,text2;
        private ImageView imageView;ImageView imageView2;ImageView imageView3;
        WebView video;
     }

}
