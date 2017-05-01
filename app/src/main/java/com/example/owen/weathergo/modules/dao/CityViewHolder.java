package com.example.owen.weathergo.modules.dao;

import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.owen.weathergo.R;
import com.example.owen.weathergo.common.base.BaseViewHolder;
import com.example.owen.weathergo.modules.adapter.CityAdapter;

/**
 * Created by owen on 2017/4/25.
 */

public class CityViewHolder extends BaseViewHolder<String> implements View.OnClickListener {

    TextView mItemCity;
    public CardView mCardView;
    private CityAdapter.OnRecyclerViewItemClickListener mOnItemClickListener = null;


    public CityViewHolder(View itemView) {
        super(itemView);
        mCardView = (CardView) itemView.findViewById(R.id.city_cardView);
        mItemCity = (TextView) itemView.findViewById(R.id.city_txt);
//        mCardView.setOnClickListener((View.OnClickListener) this);

    }

    @Override
    public void bind(String s) {
        mItemCity.setText(s);
    }


    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, (Integer) itemView.getTag());
        }
    }


    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int pos);
    }
}
