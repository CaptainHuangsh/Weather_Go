package com.example.owen.weathergo.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.example.owen.weathergo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by owen on 2017/5/12.
 */

public class MultiCityAddDialog extends Dialog {

    @BindView(R.id.to_select_city)
    TextView mCitySelect;
    @BindView(R.id.to_input_city)
    TextView mCityInput;

    private onInputOnclickListener inputOnclickListener;
    private onSelectOnclickListener selectOnclickListener;

    public MultiCityAddDialog(@NonNull Context context) {
        super(context, R.style.MyDialog);
        Context mContext = context;
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
        mCitySelect.setOnClickListener(v-> {
                if (selectOnclickListener != null) {
                    selectOnclickListener.onSelectClick();
                }

        });
        mCityInput.setOnClickListener(v-> {
                if (inputOnclickListener != null) {
                    inputOnclickListener.onInputClick();
            }
        });
    }

    /*private void initData() {

    }*/

    private void initView() {
    }

    public void setInputOnclickListener(onInputOnclickListener
                                                noOnclickListener) {
        this.inputOnclickListener = noOnclickListener;
    }

    public void setSelectOnclickListener(onSelectOnclickListener
                                                 yesOnclickListener) {
        this.selectOnclickListener = yesOnclickListener;
    }


    public interface onInputOnclickListener {
        void onInputClick();
    }

    public interface onSelectOnclickListener {
        void onSelectClick();
    }
}
