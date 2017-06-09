package com.example.owen.weathergo.modules.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.owen.weathergo.R;
import com.example.owen.weathergo.modules.dao.MultiCityViewHolder;

import java.util.ArrayList;


/**
 * Created by owen on 2017/4/25.
 */

public class MultiCityAdapter extends RecyclerView.Adapter<MultiCityViewHolder> implements View.OnClickListener {
    private Context mContext;
    private ArrayList<String> mDataList;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public MultiCityAdapter(Context context, ArrayList<String> dataList) {
        mContext = context;
        this.mDataList = dataList;
    }

    @Override
    public MultiCityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.items_city, parent, false);
        MultiCityViewHolder cvh = new MultiCityViewHolder(view);
        view.setOnClickListener(this);
        return cvh;
    }

    @Override
    public void onBindViewHolder(final MultiCityViewHolder holder, final int position) {

        holder.bind(mDataList.get(position));
        holder.mCardView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    //绑定recycleView点击事件参考自 http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2015/0327/2647.html
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }


    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int pos);
    }
}
