package com.example.owen.weathergo.modules.fragment;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;

import com.example.owen.weathergo.R;

/**
 * Created by owen on 2017/5/30.
 */

public class SettingFragment extends PreferenceFragment implements
        Preference.OnPreferenceChangeListener ,Preference.OnPreferenceClickListener{

    Preference mUpdateTimes;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_settings);
//        init();
    }

    private void init() {
        mUpdateTimes = findPreference("update_time");
        mUpdateTimes.setOnPreferenceClickListener(this);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        return false;
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        if (preference == mUpdateTimes){
            showUpdateDialog();
        }
        return true;
    }

    private void showUpdateDialog() {

    }
}
