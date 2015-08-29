package com.imooc.xpuzzle.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.List;

/**
 * 拼图主界面数据适配器
 *
 * @author xys
 */
public class GridItemsAdapter extends BaseAdapter {

    // 映射List
    private List<Bitmap> mBitmapItemLists;
    private Context mContext;

    public GridItemsAdapter(Context mContext, List<Bitmap> picList) {
        this.mContext = mContext;
        this.mBitmapItemLists = picList;
    }

    @Override
    public int getCount() {
        return mBitmapItemLists.size();
    }

    @Override
    public Object getItem(int position) {
        return mBitmapItemLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView iv_pic_item = null;
        if (convertView == null) {
            iv_pic_item = new ImageView(mContext);
            // 设置布局 图片
            iv_pic_item.setLayoutParams(new GridView.LayoutParams(
                    mBitmapItemLists.get(position).getWidth(),
                    mBitmapItemLists.get(position).getHeight()));
            // 设置显示比例类型
            iv_pic_item.setScaleType(ImageView.ScaleType.FIT_CENTER);
        } else {
            iv_pic_item = (ImageView) convertView;
        }
        iv_pic_item.setImageBitmap(mBitmapItemLists.get(position));
        return iv_pic_item;
    }
}
