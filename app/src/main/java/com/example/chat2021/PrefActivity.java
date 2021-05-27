package com.example.chat2021;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.annotation.Nullable;

public class PrefActivity extends PreferenceActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO : utiliser des fragments
        //noinspection deprecation
        addPreferencesFromResource(R.xml.preferences);
    }
}
