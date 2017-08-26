package com.android.settings.custom;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.Preference.OnPreferenceChangeListener;
import android.provider.Settings;

import com.android.internal.logging.nano.MetricsProto.MetricsEvent;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

public class CustomSettings extends SettingsPreferenceFragment implements
        Preference.OnPreferenceChangeListener {
    private static final String TAG = "CustomSettings";

    public static final String CATEGORY_CUSTOM = "com.android.settings.category.ia.custom";

    private static final String SCREEN_OFF_ANIMATION = "screen_off_animation";
    private static final String KEY_DEVICE_PARTS = "device_parts";

    private ListPreference mScreenOffAnimation;

    @Override
    public int getMetricsCategory() {
        return MetricsEvent.CUSTOMSETTINGS;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.custom_settings);

        ContentResolver resolver = getActivity().getContentResolver();

        mScreenOffAnimation = (ListPreference) findPreference(SCREEN_OFF_ANIMATION);
        int screenOffStyle = Settings.System.getInt(resolver,
                Settings.System.SCREEN_OFF_ANIMATION, 0);
        mScreenOffAnimation.setValue(String.valueOf(screenOffStyle));
        mScreenOffAnimation.setSummary(mScreenOffAnimation.getEntry());
        mScreenOffAnimation.setOnPreferenceChangeListener(this);

        if (!isDevicePartsSupported(getContext())) {
            Preference pref = getPreferenceScreen().findPreference(KEY_DEVICE_PARTS);
            if (pref != null) {
                getPreferenceScreen().removePreference(pref);
            }
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        ContentResolver resolver = getActivity().getContentResolver();
        if (preference == mScreenOffAnimation) {
            String value = (String) newValue;
            Settings.System.putInt(resolver,
                    Settings.System.SCREEN_OFF_ANIMATION, Integer.valueOf(value));
            int valueIndex = mScreenOffAnimation.findIndexOfValue(value);
            mScreenOffAnimation.setSummary(mScreenOffAnimation.getEntries()[valueIndex]);
            return true;
        }
        return false;
    }

    private static boolean isDevicePartsSupported(Context context) {
        boolean devicePartsSupported = false;
        try {
            devicePartsSupported = context.getPackageManager().getPackageInfo(
                    "org.omnirom.device", 0).versionCode > 0;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return devicePartsSupported;
    }
}
