package com.example.owen.weathergo.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.example.owen.weathergo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by owen on 2017/5/12.
 */

public class MultiCityAddDialog extends Dialog {

    Context mContext;
    @BindView(R.id.to_select_city)
    TextView mCitySelect;
    @BindView(R.id.to_input_city)
    TextView mCityInput;
    /*@BindView(R.id.yes)
    Button yes;
    @BindView(R.id.no)
    Button no;*/

//    private String noStr, yesStr;
//    private String mCityStr;
    private onInputOnclickListener inputOnclickListener;
    private onSelectOnclickListener selectOnclickListener;

    public MultiCityAddDialog(@NonNull Context context) {
        super(context, R.style.MyDialog);
        mContext = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add_city);
        ButterKnife.bind(this);
        setCanceledOnTouchOutside(false);
        initView();
//        initData();
        initEvent();
    }

    private void initEvent() {
        mCitySelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectOnclickListener != null) {
                    selectOnclickListener.onSelectClick();
                }
            }
        });
        mCityInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputOnclickListener != null) {
                    inputOnclickListener.onInputClick();
                }
            }
        });
    }

    /*private void initData() {
        if (!yesStr.equals("")) {
            yes.setText(yesStr);
        }
        if (!noStr.equals("")) {
            no.setText(noStr);
        }
    }*/

    private void initView() {
    }

    public void setInputOnclickListener( onInputOnclickListener
            noOnclickListener) {
        /*if (!str.equals("")) {
            this.noStr = str;
        }*/
        this.inputOnclickListener = noOnclickListener;
    }

    public void setSelectOnclickListener(onSelectOnclickListener
            yesOnclickListener) {
        /*if (!str.equals("")) {
            this.yesStr = str;
        }*/
        this.selectOnclickListener = yesOnclickListener;
    }


    public interface onInputOnclickListener {
        void onInputClick();
    }

    public interface onSelectOnclickListener {
        void onSelectClick();
    }
}
