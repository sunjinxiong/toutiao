package com.bawei.sjx.jinritoutiao.Adapter;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bawei.sjx.jinritoutiao.R;
import com.example.mylibrary.Utils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * dell 孙劲雄
 * 2017/8/11
 * 11:54
 */
public class FragmentAdapter extends BaseAdapter {

    private final  int TEXT=0;
    private final  int VIDEO=1;
    private final  int IMA=2;
    private final  int IMAGE=3;
    private JSONArray data;
    private Context  context;

    public FragmentAdapter(JSONArray data, Context context) {
        this.data = data;
        this.context = context;

    }
    @Override
    public int getCount() {

            return data==null?0:data.length();
    }

    @Override
    public Object getItem(int position) {
        try {
            return data.get(position);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewholder;
        int type=getItemViewType(position);
        if(convertView==null){
            viewholder=new ViewHolder();
            switch (type){

                case TEXT:
                   convertView=View.inflate(context, R.layout.item,null);
                    viewholder.text= (TextView) convertView.findViewById(R.id.item_text);
                    viewholder.text2= (TextView) convertView.findViewById(R.id.item_text2);

                    break;
                case VIDEO:
                    convertView=View.inflate(context, R.layout.item2,null);
                    viewholder.text= (TextView) convertView.findViewById(R.id.item2_text);
                    viewholder.text2= (TextView) convertView.findViewById(R.id.item2_text2);
                    viewholder.video= (WebView) convertView.findViewById(R.id.view);
                    break;
                case IMA:
                    convertView=View.inflate(context, R.layout.item3,null);
                    viewholder.text2= (TextView) convertView.findViewById(R.id.item3_text2);
                    viewholder.text= (TextView) convertView.findViewById(R.id.item3_text);
                    viewholder.imageView=(ImageView) convertView.findViewById(R.id.item3_ima);
                    break;
                case IMAGE:
                    convertView=View.inflate(context, R.layout.item4,null);
                    viewholder.text2= (TextView) convertView.findViewById(R.id.item4_text2);
                    viewholder.text= (TextView) convertView.findViewById(R.id.item4_text);
                    viewholder.imageView=(ImageView) convertView.findViewById(R.id.item4_ima);
                    viewholder.imageView2= (ImageView) convertView.findViewById(R.id.item4_ima2);
                    viewholder.imageView3= (ImageView) convertView.findViewById(R.id.item4_ima3);
                    break;
            }

            convertView.setTag(viewholder);


        }else {
            viewholder = (ViewHolder) convertView.getTag();
        }
        JSONObject Object = data.optJSONObject(position);

        String title = Object.optString("title");
        Log.i("oooo",title+"");
        String media_name = Object.optString("media_name");
        JSONArray image_list = Object.optJSONArray("image_list");
        String display_url = Object.optString("article_url");

        switch (type){

           case TEXT:
               viewholder.text.setText(title);
               viewholder.text2.setText(media_name);
               break;
           case VIDEO:
               viewholder.text.setText(title);
               viewholder.text2.setText(media_name);
               viewholder.video.getSettings().setJavaScriptEnabled(true);
               viewholder.video.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
               viewholder.video.setWebChromeClient(new WebChromeClient());
               viewholder.video.setWebViewClient(new WebViewClient());
             viewholder.video.loadUrl(display_url);
               break;
           case IMA:
               viewholder.text.setText(title);
               viewholder.text2.setText(media_name);
               DisplayImageOptions op= Utils.getoptions();
              ImageLoader.getInstance().displayImage(image_list.optJSONObject(0).optString("url"),viewholder.imageView,op);
               break;
           case IMAGE:
               DisplayImageOptions op1= Utils.getoptions();
                viewholder.text.setText(title);
               viewholder.text2.setText(media_name);
               ImageLoader.getInstance().displayImage(image_list.optJSONObject(0).optString("url"),viewholder.imageView,op1);
               ImageLoader.getInstance().displayImage(image_list.optJSONObject(1).optString("url"),viewholder.imageView2,op1);
               ImageLoader.getInstance().displayImage(image_list.optJSONObject(2).optString("url"),viewholder.imageView3,op1);
               break;

       }
        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return 4;
    }

    @Override
    public int getItemViewType(int position) {

        JSONObject jsonObject = data.optJSONObject(position);

        boolean has_image = jsonObject.optBoolean("has_image");
        boolean has_video = jsonObject.optBoolean("has_video");

        try {
            if(has_image&&jsonObject.getJSONArray("image_list").length()==1){
                return IMA;
            }else if(has_image==false&&has_video==false) {

                return TEXT;
            }else if(has_image&&jsonObject.getJSONArray("image_list").length()==3){

                return IMAGE;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return VIDEO;
    }

    class  ViewHolder{

        TextView text,text2;
        private ImageView imageView;ImageView imageView2;ImageView imageView3;
        WebView video;
    }


}
