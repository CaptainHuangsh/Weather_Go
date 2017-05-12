package com.example.owen.weathergo.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.owen.weathergo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by owen on 2017/5/12.
 */

public class CityDialog extends Dialog {

    Context mContext;
    @BindView(R.id.city_name)
    public EditText mCityEdit;
    @BindView(R.id.yes)
    Button yes;
    @BindView(R.id.no)
    Button no;

    private String noStr, yesStr;
    private String mCityStr;
    private onNoOnclickListener noOnclickListener;
    private onYesOnclickListener yesOnclickListener;

    public CityDialog(@NonNull Context context) {
        super(context, R.style.MyDialog);
        mContext = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_search_city);
        ButterKnife.bind(this);
        setCanceledOnTouchOutside(false);
        initView();
        initData();
        initEvent();
    }

    private void initEvent() {
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (yesOnclickListener != null) {
                    yesOnclickListener.onYesClick();
                }
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(noOnclickListener!=null){
                    noOnclickListener.onNoClick();
                }
            }
        });
    }

    private void initData() {
        if (!yesStr.equals("")) {
            yes.setText(yesStr);
        }
        if (!noStr.equals("")) {
            no.setText(noStr);
        }
    }

    private void initView() {
    }

    public void setNoOnclickListener(String str, onNoOnclickListener
            noOnclickListener) {
        if (!str.equals("")) {
            this.noStr = str;
        }
        this.noOnclickListener = noOnclickListener;
    }

    public void setYesOnclickListener(String str, onYesOnclickListener
            yesOnclickListener) {
        if (!str.equals("")) {
            this.yesStr = str;
        }
        this.yesOnclickListener = yesOnclickListener;
    }


    public interface onNoOnclickListener {
        public void onNoClick();
    }

    public interface onYesOnclickListener {
        public void onYesClick();
    }
}