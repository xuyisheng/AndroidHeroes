package com.imooc.xpuzzle.adapter;

import android.R.color;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.imooc.xpuzzle.util.ScreenUtil;

import java.util.List;

/**
 * 程序主界面数据适配器
 *
 * @author xys
 */
public class GridPicListAdapter extends BaseAdapter {

    // 映射List
    private List<Bitmap> picList;
    private Context context;

    public GridPicListAdapter(Context context, List<Bitmap> picList) {
        this.context = context;
        this.picList = picList;
    }

    @Override
    public int getCount() {
        return picList.size();
    }

    @Override
    public Object getItem(int position) {
        return picList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        ImageView iv_pic_item = null;
        int density = (int) ScreenUtil.getDeviceDensity(context);
        if (convertView == null) {
            iv_pic_item = new ImageView(context);
            // 设置布局 图片
            iv_pic_item.setLayoutParams(new GridView.LayoutParams(
                    80 * density,
                    100 * density));
            // 设置显示比例类型
            iv_pic_item.setScaleType(ImageView.ScaleType.FIT_XY);
        } else {
            iv_pic_item = (ImageView) convertView;
        }
        iv_pic_item.setBackgroundColor(color.black);
        iv_pic_item.setImageBitmap(picList.get(position));
        return iv_pic_item;
    }
}
