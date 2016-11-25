package com.example.mamshad.movietune;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HorizontalViewAdapter extends ArrayAdapter<HorizontalItem> {

    private Context mContext;
    private int layoutResourceId;
    private ArrayList<HorizontalItem> mGridData = new ArrayList<HorizontalItem>();

    public HorizontalViewAdapter(Context mContext, int layoutResourceId, ArrayList<HorizontalItem> mGridData) {
        super(mContext, layoutResourceId, mGridData);
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.mGridData = mGridData;
    }

    /**
     * Updates grid data and refresh grid items.
     *
     * @param mGridData
     */
    public void setGridData(ArrayList<HorizontalItem> mGridData) {
        this.mGridData = mGridData;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        GridViewAdapter.ViewHolder holder;

        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new GridViewAdapter.ViewHolder();
            holder.imageView = (ImageView) row.findViewById(R.id.image_view);
            row.setTag(holder);
        } else {
            holder = (GridViewAdapter.ViewHolder) row.getTag();
        }

        HorizontalItem item = mGridData.get(position);

        Picasso.with(mContext).load(item.getImage()).into(holder.imageView);
        return row;
    }

    static class ViewHolder {
        ImageView imageView;
    }
}
