package com.bawei.sjx.jinritoutiao.fragemnt;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;
import com.bawei.sjx.jinritoutiao.Adapter.FragmentAdapter;
import com.bawei.sjx.jinritoutiao.R;
import com.bawei.sjx.jinritoutiao.WEbview.WebActivity;
import com.bwie.xlistviewlibrary.XListView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

/**
 * dell 孙劲雄
 * 2017/8/11
 * 9:50
 */
public class FragmentA extends Fragment {

private XListView xListView;
    private JSONArray arr;
    private  int page=1;
    private Handler handler=new Handler();
    private FragmentAdapter fragmentAdapter;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
  if(view==null){
      view = View.inflate(getActivity(), R.layout.ty,null);
  }
      ViewGroup viewGroup = (ViewGroup) view.getParent();
        if(viewGroup!=null){
            viewGroup.removeView(viewGroup);
        }
        xListView= (XListView) view.findViewById(R.id.y);
        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        ConnectivityManager manager= (ConnectivityManager) getActivity().getSystemService(getActivity().CONNECTIVITY_SERVICE);


        NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();

        if(activeNetworkInfo==null){

            Toast.makeText(getActivity(),"网络不给力，请检查网络设置",Toast.LENGTH_LONG).show();


        }else{

            xListView.setPullRefreshEnable(true);
            xListView.setPullLoadEnable(true);
            xListView.setXListViewListener(new XListView.IXListViewListener() {
                @Override
                public void onRefresh() {

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            xListView.stopRefresh();
                        }
                    },2000);

                }

                @Override
                public void onLoadMore() {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                               page++;
                            xListView.stopLoadMore();
                            getserviec();

                        }
                    },2000);


                }
            });

        }
        getserviec();
        xListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                JSONObject jsonObject = arr.optJSONObject(position-1);
                String title = jsonObject.optString("title");
                JSONArray image_list = jsonObject.optJSONArray("image_list");
                String share_url = jsonObject.optString("share_url");
                Intent it=new Intent(getActivity(), WebActivity.class);
                it.putExtra("image_list",image_list+"");
                it.putExtra("path",share_url);
                it.putExtra("title",title);
                startActivity(it);

            }
        });

    }
      public  void getserviec(){

          final String path=getArguments().getString("path");

          MyAsyncTask myAsyncTask=new MyAsyncTask();

          myAsyncTask.execute(path);
      }


    class MyAsyncTask extends AsyncTask<String,Integer,String>{
        @Override
        protected String doInBackground(String... params) {

            String url=params[0];

            HttpClient httpClient=new DefaultHttpClient();

            HttpGet get=new HttpGet(url);

            try {
                HttpResponse response=httpClient.execute(get);

                if(response.getStatusLine().getStatusCode()==200){

                    InputStream inputStream = response.getEntity().getContent();

                    ByteArrayOutputStream by=new ByteArrayOutputStream();

                    int len=0;
                    byte[] b=new byte[1024];

                    while((len=inputStream.read(b))!=-1){

                           by.write(b,0,len);

                    }
                    by.close();
                    inputStream.close();
                    String s=by.toString();
                    return  s;

                }

            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            super.onPostExecute(s);


            JSONObject json= null;
            try {
                json = new JSONObject(s);
                JSONArray data = json.optJSONArray("data");

             Log.i("+++++",data+"");
                if(page==1){

                    arr=new JSONArray();

                }
                for(int i=0;i<data.length();i++){

                    arr.put(data.get(i));
                }

                initData(arr);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
    private void initData(final JSONArray data) {
            //第一次创建
      if(fragmentAdapter==null) {

            fragmentAdapter = new FragmentAdapter(data, getActivity());
            xListView.setAdapter(fragmentAdapter);
              Log.i("++++++",data+"");
        }else{

            fragmentAdapter.notifyDataSetChanged();
        }
        fragmentAdapter = new FragmentAdapter(data, getActivity());
        xListView.setAdapter(fragmentAdapter);

    }

    public static Fragment newInstance(String path){
        FragmentA fragment1 = new FragmentA();
        Bundle bundle = new Bundle();
        bundle.putString("path", path);
        fragment1.setArguments(bundle);
        return fragment1;

    }
}
