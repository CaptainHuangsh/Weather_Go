package com.example.owen.weathergo.modules.dao;

import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.owen.weathergo.R;
import com.example.owen.weathergo.base.BaseViewHolder;

/**
 * Created by owen on 2017/4/25.
 */

public class CityViewHolder extends BaseViewHolder<String> {

    TextView mItemCity;
    public CardView mCardView;

    public CityViewHolder(View itemView) {
        super(itemView);
        mCardView = (CardView) itemView.findViewById(R.id.city_cardView);
        mItemCity = (TextView) itemView.findViewById(R.id.city_txt);
    }

    @Override
    public void bind(String s) {
        Log.i("CityViewHolderBind",""+s);
//        mItemCity.setText("城市");
        mItemCity.setText(s);
    }
}
