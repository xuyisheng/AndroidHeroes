package com.imooc.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ChatItemListViewAdapter extends BaseAdapter {

    private List<ChatItemListViewBean> mData;
    private LayoutInflater mInflater;

    public ChatItemListViewAdapter(Context context,
                                   List<ChatItemListViewBean> data) {
        this.mData = data;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        ChatItemListViewBean bean = mData.get(position);
        return bean.getType();
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            if (getItemViewType(position) == 0) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(
                        R.layout.chat_item_itemin, null);
                holder.icon = (ImageView) convertView.findViewById(
                        R.id.icon_in);
                holder.text = (TextView) convertView.findViewById(
                        R.id.text_in);
            } else {
                holder = new ViewHolder();
                convertView = mInflater.inflate(
                        R.layout.chat_item_itemout, null);
                holder.icon = (ImageView) convertView.findViewById(
                        R.id.icon_out);
                holder.text = (TextView) convertView.findViewById(
                        R.id.text_out);
            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.icon.setImageBitmap(mData.get(position).getIcon());
        holder.text.setText(mData.get(position).getText());
        return convertView;
    }

    public final class ViewHolder {
        public ImageView icon;
        public TextView text;
    }
}
