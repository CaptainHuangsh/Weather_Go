package com.example.owen.weathergo.modules.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.owen.weathergo.R;
import com.example.owen.weathergo.modules.dao.CityViewHolder;

import java.util.ArrayList;


/**
 * Created by owen on 2017/4/25.
 */

public class CityAdapter extends RecyclerView.Adapter<CityViewHolder> implements View.OnClickListener {
    private Context mContext;
    private ArrayList<String> mDataList;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public CityAdapter(Context context, ArrayList<String> dataList) {
        Log.i("CityAdapter","con");
        mContext = context;
        this.mDataList = dataList;
    }

    @Override
    public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.city_items,parent,false);
        CityViewHolder cvh = new CityViewHolder(view);
        view.setOnClickListener(this);
        return cvh;
    }

    @Override
    public void onBindViewHolder(final CityViewHolder holder, final int position) {

        Log.i("CityAdaptergetItem",""+mDataList.get(position));
        holder.bind(mDataList.get(position));
//        holder.mCardView.setOnClickListener(v -> mOnItemClickListener.onItemClick(v, position));
//        holder.mCardView.setOnClickListener(mOnItemClickListener.onItemClick(v,position));
        holder.mCardView.setTag(position);
    }

    @Override
    public int getItemCount() {
        Log.i("CityAdaptergetItem",""+mDataList.size());
        return mDataList.size();
    }

    //绑定recycleView点击事件参考自 http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2015/0327/2647.html
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public void onClick(View v) {
        if(mOnItemClickListener != null){
            mOnItemClickListener.onItemClick(v,(int)v.getTag());
        }
    }


    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int pos);
    }
}
