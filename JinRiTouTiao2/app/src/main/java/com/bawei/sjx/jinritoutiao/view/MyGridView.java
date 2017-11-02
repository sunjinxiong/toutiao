package com.bawei.sjx.jinritoutiao.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * dell 孙劲雄
 * 2017/8/18
 * 19:41
 */

public class MyGridView extends GridView {

    public MyGridView(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //makeMeasureSpec根据提供的大小值和模式创建一个测量值(格式)
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}
