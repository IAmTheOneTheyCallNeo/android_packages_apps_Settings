package com.android.settings.custom;

import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.Preference.OnPreferenceChangeListener;

import com.android.internal.logging.nano.MetricsProto.MetricsEvent;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

public class CustomSettings extends SettingsPreferenceFragment implements
        Preference.OnPreferenceChangeListener {
    private static final String TAG = "CustomSettings";

    public static final String CATEGORY_CUSTOM = "com.android.settings.category.ia.custom";

    @Override
    public int getMetricsCategory() {
        return MetricsEvent.CUSTOMSETTINGS;
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        addPreferencesFromResource(R.xml.custom_settings);
    }

    public boolean onPreferenceChange(Preference preference, Object objValue) {

        return true;
    }
}
