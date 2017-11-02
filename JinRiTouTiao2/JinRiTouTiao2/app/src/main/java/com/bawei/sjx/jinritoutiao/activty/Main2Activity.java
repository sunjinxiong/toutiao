package com.bawei.sjx.jinritoutiao.activty;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.bawei.sjx.jinritoutiao.R;
import com.bawei.sjx.jinritoutiao.bean.Path;
import com.bawei.sjx.jinritoutiao.fragemnt.FragmentA;
import com.bwie.imageloaderlibrary.ImageLoaderUtil;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.mob.MobSDK;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.connect.share.QQShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.ContactsPage;
import cn.smssdk.gui.RegisterPage;

public class Main2Activity extends FragmentActivity {

    private Button du;
    private TextView log_text,tuichudelu,dianji;
    private ImageView log_ima;
    private  ImageView v;
    private ImageView weibodelu;
    private ImageView phondelu;
    private ImageView imageView2;
    private ImageView qqdelu;
    private  ImageView lv;
    private ImageView shezhi;
    public static List<Path>  listtab=new ArrayList<Path>();
    public static List<Path> lview=new ArrayList<Path>();
    private  TabLayout tabLayout;
    private  ViewPager viewPager;
    private ListView listView;
    private  List<String> xlist;
    private  List<Integer> imalist;
    private  SlidingMenu slidingMenu;
   private UserInfo mUserInfo;
    private Tencent mTencent;
    private  BaseUiListener m;
    private SharedPreferences sp;
    private View layout;
    private static final String TAG = "MainActivity";
    private static final String APP_ID = "1105602574";//官方aapi
    int theme = R.style.AppTheme;
    private View view;
    private String phoneNumber = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            //恢复临时数据
            theme = savedInstanceState.getInt("theme");
            //setTheme必须放在setContentView前面
            setTheme(theme);
        }
        setContentView(R.layout.activity_main2);
//侧滑


        slidingMenu = new SlidingMenu(this);
        view = View.inflate(this, R.layout.user, null);
        viewid();
        night();
       phone();

//点击第三方登录
        disan();
        getlist();
        listView.setAdapter(new MyAdapter());


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(position==0){

                }else if(position==2){

                    Intent it=new Intent(Main2Activity.this,Collectactivty.class);

                    startActivity(it);

                }
            }
        });

        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setBehindOffset(150);
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        slidingMenu.setMenu(view);
        initview();
        getlit();

        dianji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it=new Intent(Main2Activity.this,Tv.class);
                startActivity(it);

            }
        });

        MobSDK.init(this, "1c0e2609bb4aa",
                "a941cdb1b2e606adc23902d0f08b60cf");
//记住用户登录

     dt();

    }
 //手机登录
 public  void phone(){
        phondelu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                register();

            }
        });
    }
    public void  night(){

        shezhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                theme = (theme == R.style.AppTheme) ? R.style.NightAppTheme : R.style.AppTheme;
                //重走activity生命周期
                recreate();
            }
        });}
//监听第三方登录
public void disan() {
        mTencent = Tencent.createInstance(APP_ID, Main2Activity.this.getApplicationContext());

        qqdelu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                m = new BaseUiListener();

                mTencent.login(Main2Activity.this, "all", m);

            }
        });

    }
//记住登录退出状态
public  void  dt(){
        sp = getSharedPreferences("config", MODE_APPEND);
        tuichudelu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
                layout.setVisibility(View.GONE);
                qqdelu.setVisibility(View.VISIBLE);
                phondelu.setVisibility(View.VISIBLE);
                weibodelu.setVisibility(View.VISIBLE);
                du.setVisibility(View.VISIBLE);
                lv.setImageResource(R.drawable.mine_titlebar_normal);

                SharedPreferences.Editor edit = sp.edit();
                Toast.makeText(Main2Activity.this,"tuicu",Toast.LENGTH_LONG).show();
                edit.putBoolean("sjx",true).commit();
            }
        });

        boolean name1 = sp.getBoolean("sjx", false);
        Log.i("++++",name1+"");
        boolean check = sp.getBoolean("check",false);
        if (check) {
            String name = sp.getString("name", null);
            String touxaing = sp.getString("touxaing", null);
            menu(name, touxaing);
            sp.edit().putBoolean("sjx",false).commit();

        }
        if(name1){
            Toast.makeText(Main2Activity.this,"点击了",Toast.LENGTH_LONG);

            layout.setVisibility(View.GONE);
            qqdelu.setVisibility(View.VISIBLE);
            phondelu.setVisibility(View.VISIBLE);
            weibodelu.setVisibility(View.VISIBLE);
            du.setVisibility(View.VISIBLE);
            sp.edit().putBoolean("check",false).commit();
            lv.setVisibility(View.GONE);
        }
    }
//集合
public  void getlist(){


    xlist=new ArrayList<String>();
    imalist=new ArrayList<Integer>();
    xlist.add("好有动态");
    xlist.add("我的话题");
    xlist.add("收藏");
    xlist.add("活动");
    xlist.add("商城");
    xlist.add("反馈");
    xlist.add("我要爆料");
    imalist.add(R.drawable.left1);
    imalist.add(R.drawable.left2);
    imalist.add(R.drawable.left3);
    imalist.add(R.drawable.left4);
    imalist.add(R.drawable.left5);
    imalist.add(R.drawable.lef6);
    imalist.add(R.drawable.left1);
}
//侧拦id
public void viewid(){
        shezhi= (ImageView)view.findViewById(R.id.she);
        layout=view.findViewById(R.id.inde);
        log_ima= (ImageView) view.findViewById(R.id.log_ima);
        tuichudelu= (TextView) view.findViewById(R.id.tuichudelu);
        log_text=(TextView)view.findViewById(R.id.lod_text);
        listView= (ListView) view.findViewById(R.id.listview);
        du= (Button) view.findViewById(R.id.many);
        weibodelu=(ImageView) view.findViewById(R.id.w);
        phondelu=(ImageView) view.findViewById(R.id.p);
        qqdelu= (ImageView) view.findViewById(R.id.q);

    }
//id资源
 public  void initview(){

        imageView2= (ImageView) findViewById(R.id.so);
      tabLayout= (TabLayout) findViewById(R.id.tab);
        viewPager= (ViewPager) findViewById(R.id.vp);



       lv= (ImageView) findViewById(R.id.iv);

     v= (ImageView) findViewById(R.id.lv);
     dianji=(TextView)findViewById(R.id.dianji);

    }
//记住
 public void menu(String name,String image){

        layout.setVisibility(View.VISIBLE);
        qqdelu.setVisibility(View.GONE);
        phondelu.setVisibility(View.GONE);
        weibodelu.setVisibility(View.GONE);
        du.setVisibility(View.GONE);

        DisplayImageOptions o = ImageLoaderUtil.getOptions2();
        ImageLoader.getInstance().displayImage(image, log_ima, o);
        ImageLoader.getInstance().displayImage(image, lv, o);
        log_text.setText(name);

    }
//表头定义
public  void  getlit(){

//    listtab.add(new Path("推荐","http://ic.snssdk.com/2/article/v25/stream/?count=20&min_behot_time=1455521444&bd_city=%E5%8C%97%E4%BA%AC%E5%B8%82\n" +
//            "&bd_latitude=40.049317&bd_longitude=116.296499&bd_loc_time=1455521401&loc_mode=5&la\n" +
//            "c=4527&cid=28883&iid=3642583580&d\n" +
//            "evice_id=11131669133&ac=wifi&channel=baidu&aid=13&app_name=news_article&\n" +
//            "version_code=460&device_platform=android&d\n" +
//            "evice_type=SCH-I919U&os_api=19&os_version=4.4.2&uuid=285592931621751&openudid=AC9E172CE2490000\n"));
    listtab.add(new Path("财经","http://ic.snssdk.com/2/article/v25/stream/?category=news_finance&count=20&min_behot_time=1455522899&bd_city=%E5%8C%97%E4%BA%AC%E5%B8%82&bd_latitude=40.049317&bd_longitude=116.296499&bd_loc_time=1455523440&loc_mode=5&lac=4527&cid=28883&iid=3642583580&device_id=11131669133&ac=wifi&channel=baidu&aid=13&app_name=news_article&version_code=460&device_platform=android&device_type=SCH-I919U&os_api=19&os_version=4.4.2&uuid=285592931621751&openudid=AC9E172CE2490000"));
    listtab.add(new Path("军事","http://ic.snssdk.com/2/article/v25/stream/?category=news_military&count=20&min_behot_time=1455522991&bd_city=%E5%8C%97%E4%BA%AC%E5%B8%82&bd_latitude=40.049317&bd_longitude=116.296499&bd_loc_time=1455523440&loc_mode=5&lac=4527&cid=28883&iid=3642583580&device_id=11131669133&ac=wifi&channel=baidu&aid=13&app_name=news_article&version_code=460&device_platform=android&device_type=SCH-I919U&os_api=19&os_version=4.4.2&uuid=285592931621751&openudid=AC9E172CE2490000"));
    listtab.add(new Path("国际","http://ic.snssdk.com/2/article/v25/stream/?category=news_world&count=20&min_behot_time=1455523059&bd_city=%E5%8C%97%E4%BA%AC%E5%B8%82&bd_latitude=40.049317&bd_longitude=116.296499&bd_loc_time=1455523440&loc_mode=5&lac=4527&cid=28883&iid=3642583580&device_id=11131669133&ac=wifi&channel=baidu&aid=13&app_name=news_article&version_code=460&device_platform=android&device_type=SCH-I919U&os_api=19&os_version=4.4.2&uuid=285592931621751&openudid=AC9E172CE2490000"));
    listtab.add(new Path("城市","http://ic.snssdk.com/2/article/v25/stream/?count=20&min_behot_time=1455521444&bd_city=%E5%8C%97%E4%BA%AC%E5%B8%82&bd_latitude=40.049317&bd_longitude=116.296499&bd_loc_time=1455521401&loc_mode=5&lac=4527&cid=28883&iid=3642583580&device_id=11131669133&ac=wifi&channel=baidu&aid=13&app_name=news_article&version_code=460&device_platform=android&device_type=SCH-I919U&os_api=19&os_version=4.4.2&uuid=285592931621751&openudid=AC9E172CE2490000"));
    listtab.add(new Path("社会","http://ic.snssdk.com/2/article/v25/stream/?category=news_society&count=20&min_behot_time=1455521720&bd_city=%E5%8C%97%E4%BA%AC%E5%B8%82&bd_latitude=40.049317&bd_longitude=116.296499&bd_loc_time=1455522107&loc_mode=5&lac=4527&cid=28883&iid=3642583580&device_id=11131669133&ac=wifi&channel=baidu&aid=13&app_name=news_article&version_code=460&device_platform=android&device_type=SCH-I919U&os_api=19&os_version=4.4.2&uuid=285592931621751&openudid=AC9E172CE2490000"));
    listtab.add(new Path("娱乐","http://ic.snssdk.com/2/article/v25/stream/?category=news_entertainment&count=20&min_behot_time=1455522338&bd_city=%E5%8C%97%E4%BA%AC%E5%B8%82&bd_latitude=40.049317&bd_longitude=116.296499&bd_loc_time=1455522784&loc_mode=5&lac=4527&cid=28883&iid=3642583580&device_id=11131669133&ac=wifi&channel=baidu&aid=13&app_name=news_article&version_code=460&device_platform=android&device_type=SCH-I919U&os_api=19&os_version=4.4.2&uuid=285592931621751&openudid=AC9E172CE2490000"));
    listtab.add(new Path("趣图","http://ic.snssdk.com/2/article/v25/stream/?category=image_funny&count=20&min_behot_time=1455524031&bd_city=%E5%8C%97%E4%BA%AC%E5%B8%82&bd_latitude=40.049317&bd_longitude=116.296499&bd_loc_time=1455523440&loc_mode=5&lac=4527&cid=28883&iid=3642583580&device_id=11131669133&ac=wifi&channel=baidu&aid=13&app_name=news_article&version_code=460&device_platform=android&device_type=SCH-I919U&os_api=19&os_version=4.4.2&uuid=285592931621751&openudid=AC9E172CE2490000"));
    listtab.add(new Path("科技","http://ic.snssdk.com/2/article/v25/stream/?category=news_tech&count=20&min_behot_time=1455522427&bd_city=%E5%8C%97%E4%BA%AC%E5%B8%82&bd_latitude=40.049317&bd_longitude=116.296499&bd_loc_time=1455522784&loc_mode=5&lac=4527&cid=28883&iid=3642583580&device_id=11131669133&ac=wifi&channel=baidu&aid=13&app_name=news_article&version_code=460&device_platform=android&device_type=SCH-I919U&os_api=19&os_version=4.4.2&uuid=285592931621751&openudid=AC9E172CE2490000"));
    listtab.add(new Path("汽车","http://ic.snssdk.com/2/article/v25/stream/?category=news_car&count=20&bd_city=%E5%8C%97%E4%BA%AC%E5%B8%82&bd_latitude=40.049317&bd_longitude=116.296499&bd_loc_time=1455522784&loc_mode=5&lac=4527&cid=28883&iid=3642583580&device_id=11131669133&ac=wifi&channel=baidu&aid=13&app_name=news_article&version_code=460&device_platform=android&device_type=SCH-I919U&os_api=19&os_version=4.4.2&uuid=285592931621751&openudid=AC9E172CE2490000"));
    listtab.add(new Path("体育","http://ic.snssdk.com/2/article/v25/stream/?category=news_sports&count=20&min_behot_time=1455522629&bd_city=%E5%8C%97%E4%BA%AC%E5%B8%82&bd_latitude=40.049317&bd_longitude=116.296499&bd_loc_time=1455522784&loc_mode=5&lac=4527&cid=28883&iid=3642583580&device_id=11131669133&ac=wifi&channel=baidu&aid=13&app_name=news_article&version_code=460&device_platform=android&device_type=SCH-I919U&os_api=19&os_version=4.4.2&uuid=285592931621751&openudid=AC9E172CE2490000"));
    listtab.add(new Path("军事","http://ic.snssdk.com/2/article/v25/stream/?category=news_military&count=20&min_behot_time=1455522991&bd_city=%E5%8C%97%E4%BA%AC%E5%B8%82&bd_latitude=40.049317&bd_longitude=116.296499&bd_loc_time=1455523440&loc_mode=5&lac=4527&cid=28883&iid=3642583580&device_id=11131669133&ac=wifi&channel=baidu&aid=13&app_name=news_article&version_code=460&device_platform=android&device_type=SCH-I919U&os_api=19&os_version=4.4.2&uuid=285592931621751&openudid=AC9E172CE2490000"));
    listtab.add(new Path("国际","http://ic.snssdk.com/2/article/v25/stream/?category=news_world&count=20&min_behot_time=1455523059&bd_city=%E5%8C%97%E4%BA%AC%E5%B8%82&bd_latitude=40.049317&bd_longitude=116.296499&bd_loc_time=1455523440&loc_mode=5&lac=4527&cid=28883&iid=3642583580&device_id=11131669133&ac=wifi&channel=baidu&aid=13&app_name=news_article&version_code=460&device_platform=android&device_type=SCH-I919U&os_api=19&os_version=4.4.2&uuid=285592931621751&openudid=AC9E172CE2490000"));
    listtab.add(new Path("段子","http://ic.snssdk.com/2/article/v25/stream/?category=essay_joke&count=20&bd_city=%E5%8C%97%E4%BA%AC%E5%B8%82&bd_latitude=40.049317&bd_longitude=116.296499&bd_loc_time=1455523440&loc_mode=5&lac=4527&cid=28883&iid=3642583580&device_id=11131669133&ac=wifi&channel=baidu&aid=13&app_name=news_article&version_code=460&device_platform=android&device_type=SCH-I919U&os_api=19&os_version=4.4.2&uuid=285592931621751&openudid=AC9E172CE2490000"));
    listtab.add(new Path("美女","http://ic.snssdk.com/2/article/v25/stream/?category=image_ppmm&count=20&min_behot_time=1455524172&bd_city=%E5%8C%97%E4%BA%AC%E5%B8%82&bd_latitude=40.049317&bd_longitude=116.296499&bd_loc_time=1455524092&loc_mode=5&lac=4527&cid=28883&iid=3642583580&device_id=11131669133&ac=wifi&channel=baidu&aid=13&app_name=news_article&version_code=460&device_platform=android&device_type=SCH-I919U&os_api=19&os_version=4.4.2&uuid=285592931621751&openudid=AC9E172CE2490000"));
    listtab.add(new Path("房产","http://ic.snssdk.com/2/article/v25/stream/?category=image_ppmm&count=20&min_behot_time=1455524172&bd_city=%E5%8C%97%E4%BA%AC%E5%B8%82&bd_latitude=40.049317&bd_longitude=116.296499&bd_loc_time=1455524092&loc_mode=5&lac=4527&cid=28883&iid=3642583580&device_id=11131669133&ac=wifi&channel=baidu&aid=13&app_name=news_article&version_code=460&device_platform=android&device_type=SCH-I919U&os_api=19&os_version=4.4.2&uuid=285592931621751&openudid=AC9E172CE2490000"));
    lview.add(new Path("房产","http://ic.snssdk.com/2/article/v25/stream/?category=image_ppmm&count=20&min_behot_time=1455524172&bd_city=%E5%8C%97%E4%BA%AC%E5%B8%82&bd_latitude=40.049317&bd_longitude=116.296499&bd_loc_time=1455524092&loc_mode=5&lac=4527&cid=28883&iid=3642583580&device_id=11131669133&ac=wifi&channel=baidu&aid=13&app_name=news_article&version_code=460&device_platform=android&device_type=SCH-I919U&os_api=19&os_version=4.4.2&uuid=285592931621751&openudid=AC9E172CE2490000"));

    tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

    //循环添加fragment
   for(int i=0;i<listtab.size();i++){
         tabLayout.addTab(tabLayout.newTab().setText(listtab.get(i).getName()));
//        Fragment fraement =FragmentA.newInstance(listtab.get(i).getUri());
//        lview.add(fraement);

    }
    //viewpager
    MyViewPager myViewPager=new MyViewPager(getSupportFragmentManager());
    viewPager.setAdapter(myViewPager);
//tablyout和viewpager相关联
    tabLayout.setupWithViewPager(viewPager);
    tabLayout.setTabsFromPagerAdapter(myViewPager);

    //点击监听侧滑
    lv.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            slidingMenu.toggle();
        }
    });
    v.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            slidingMenu.toggle();
        }
    });
}
//viewpager 设配器
class MyViewPager extends FragmentPagerAdapter {
    public MyViewPager(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return listtab.get(position).getName();
    }

    @Override
    public Fragment getItem(int position) {
        return FragmentA.newInstance(listtab.get(position).getUri());
    }

    @Override
    public int getCount() {
        return listtab.size();
    }
    //viewpager对应的页面跟tablayout的选项卡一一对应

    }
 //返回提醒客户
 @Override
public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
        super.onKeyDown(keyCode, event);
        if (keyCode == KeyEvent.KEYCODE_BACK) {
// 创建退出对话框
            AlertDialog isExit = new AlertDialog.Builder(this).create();
// 设置对话框标题
            isExit.setTitle("提示");
// 对话框显示的内容
            isExit.setMessage("亲，真的要退出么？");
// 给提示框里的按钮添加监听
            isExit.setButton("确定", hello);

            isExit.setButton2("取消", hello);
// 显示对话框
            isExit.show();
        }
        return false;
    }
    // 给对话框里的按钮注册事件
    DialogInterface.OnClickListener hello = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case AlertDialog.BUTTON_POSITIVE:// 点击 确认，退出程序
                    finish();
                    break;
                case AlertDialog.BUTTON_NEGATIVE:// 点击 取消 取消对话框
                    break;
                default:
                    break;
            }
        }
    };

    //左边侧拦适配器
class  MyAdapter extends BaseAdapter{


        @Override
        public int getCount() {
            return xlist.size();
        }

        @Override
        public Object getItem(int position) {
            return xlist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if(convertView==null){

                viewHolder=new ViewHolder();

                convertView=View.inflate(Main2Activity.this,R.layout.listview,null);

                viewHolder.imageView= (ImageView) convertView.findViewById(R.id.tile_ima);
                viewHolder.textView= (TextView) convertView.findViewById(R.id.tile_text);

                convertView.setTag(viewHolder);

            }else{


                viewHolder= (ViewHolder) convertView.getTag();
            }

            viewHolder.imageView.setImageResource(imalist.get(position));
            viewHolder.textView.setText(xlist.get(position));

            return convertView;
        }
    }
 //
 class  ViewHolder{

        TextView textView;
        ImageView imageView;

    }
//自定义接口
public class BaseUiListener implements IUiListener{
        @Override
        public void onComplete(final Object response) {
            Toast.makeText(Main2Activity.this, "授权成功", Toast.LENGTH_SHORT).show();



              layout.setVisibility(View.VISIBLE);
             qqdelu.setVisibility(View.GONE);
             phondelu.setVisibility(View.GONE);
             weibodelu.setVisibility(View.GONE);
            du.setVisibility(View.GONE);
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
                         mUserInfo = new UserInfo(getApplicationContext(),qqToken);
                        mUserInfo.getUserInfo(new IUiListener() {
                            @Override
                            public void onComplete(final Object response) {

                                JSONObject json = (JSONObject) response;

                                DisplayImageOptions o = ImageLoaderUtil.getOptions2();
                                ImageLoader.getInstance().displayImage(json.optString("figureurl_qq_2"), log_ima, o);
                                ImageLoader.getInstance().displayImage(json.optString("figureurl_qq_2"), lv, o);
                                log_text.setText(json.optString("nickname"));

                                JSONObject object= null;
                                try {
                                    object = new JSONObject(response.toString());
                                    final SharedPreferences.Editor edit = sp.edit();
                                    edit.putBoolean("check",true);

                                    edit.putString("name",object.optString("nickname"));
                                    edit.putString("touxaing",object.optString("figureurl_qq_2"));
                                    edit.commit();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }



                            }
                            @Override
                            public void onError(UiError uiError) {
                                Log.e(TAG, "登录失败" + uiError.toString());
                            }

                            @Override
                            public void onCancel() {



                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onError(UiError uiError) {
            Toast.makeText(Main2Activity.this, "授权失败", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onCancel() {
            Toast.makeText(Main2Activity.this, "授权取消", Toast.LENGTH_SHORT).show();

        }

    }
 //第三方登录 回掉方法
@Override
 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Constants.REQUEST_LOGIN){
            Tencent.onActivityResultData(requestCode,resultCode,data,m);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
//QQ分享
 public void share()
 {
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, "要分享的标题");
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY,  "要分享的摘要");


        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,  "http://www.qq.com/news/1.html");
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,"http://imgcache.qq.com/qzone/space_item/pre/0/66768.gif");
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME,  "测试应用222222");
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, 0);//0代表QQ好友 1代表QQ空间
        mTencent.shareToQQ(Main2Activity.this, params, new BaseUiListener());
    }
 @Override
 protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("theme",theme);

    }
//恢复临时数据
 @Override
 protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        theme = savedInstanceState.getInt("theme");
    }
 //退出登录
public void logout()
    {

            mTencent.logout(this);

    }
//phone登录
public void register() {
        // 打开注册页面
        RegisterPage registerPage = new RegisterPage();
        registerPage.setRegisterCallback(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                // 解析注册结果
                if (result == SMSSDK.RESULT_COMPLETE) {
                    @SuppressWarnings("unchecked")
                    HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
                    String country = (String) phoneMap.get("country");
                    String phone = (String) phoneMap.get("phone");

                    phoneNumber = phone;

                    Log.e("PhoneNumber", phone);
                    // 提交用户信息（此方法可以不调用）
                    // registerUser(country, phone);

                }
            }
        });
        registerPage.show(this);

//        Intent intent = new Intent(this, Main2Activity.class);
//        //注册成功以后跳转到我的页面MyActivity，并且在MyActivity显示注册的手机号码
//        intent.putExtra("phone", phoneNumber);
//        startActivity(intent);
    }
//好友
public void friends() {
        // 打开通信录好友列表页面
        ContactsPage contactsPage = new ContactsPage();
        contactsPage.show(this);
    }



 }






