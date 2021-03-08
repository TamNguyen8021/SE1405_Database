package com.example.database.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;

import com.example.database.R;

public class StudentPreferenceActivity extends PreferenceActivity
        implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.student_preference);
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.database_preferences", MODE_PRIVATE);
        for (int i = 0; i < getPreferenceScreen().getPreferenceCount(); i++) {
            initData(getPreferenceScreen().getPreference(i), sharedPreferences);
        }
    }

    private void initData(Preference preference, SharedPreferences sharedPreferences) {
        if (preference instanceof PreferenceCategory) {
            PreferenceCategory category = (PreferenceCategory) preference;
            for (int i = 0; i < category.getPreferenceCount(); i++) {
                initData(category.getPreference(i), sharedPreferences);
            }
        } else {
            updatePref(preference, sharedPreferences);
        }
    }

    private void updatePref(Preference preference, SharedPreferences sharedPreferences) {
        if (preference instanceof EditTextPreference) {
            EditTextPreference edt = (EditTextPreference) preference;
            preference.setSummary(edt.getText());
        } else if (preference instanceof CheckBoxPreference) {
            CheckBoxPreference chk = (CheckBoxPreference) preference;
            String gender = "Male";
            if (!chk.isChecked()) {
                gender = "Female";
            }
            preference.setSummary(gender);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        updatePref(findPreference(key), sharedPreferences);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }
}