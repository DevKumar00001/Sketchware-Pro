package com.besome.sketch.help;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.widget.Toolbar;

import com.besome.sketch.editor.property.PropertySwitchItem;
import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.sketchware.remod.R;
import mod.SketchwareUtil;

import a.a.a.mB;
import mod.hey.studios.util.Helper;
import mod.tsd.ui.MaterialColorsHelper;

public class SystemSettingActivity extends BaseAppCompatActivity {

    private LinearLayout contentLayout;
    private LinearLayout themeLayout;
    private SharedPreferences.Editor preferenceEditor;
    private SharedPreferences MaterialTheme;
    private SharedPreferences MaterialThemeEnable;
    private View MaterialThemeInflator;

    private void addPreference(int key, int resName, int resDescription, boolean value) {
        PropertySwitchItem switchItem = new PropertySwitchItem(this);
        switchItem.setKey(key);
        switchItem.setName(Helper.getResString(resName));
        switchItem.setDesc(Helper.getResString(resDescription));
        switchItem.setValue(value);
        contentLayout.addView(switchItem);
    }

    @Override
    public void onBackPressed() {
        if (saveSettings()) {
            setResult(Activity.RESULT_OK, new Intent());
            finish();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mod.tsd.ui.AppThemeApply.setUpTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.system_settings);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Set Navigation Icon tint
        MaterialColorsHelper.setUpToolbarNavigationIconColor(this,toolbar.getNavigationIcon());
        findViewById(R.id.layout_main_logo).setVisibility(View.GONE);
        getSupportActionBar().setTitle(Helper.getResString(R.string.main_drawer_title_system_settings));
        toolbar.setNavigationOnClickListener(view -> {
            if (!mB.a()) onBackPressed();
        });

        contentLayout = findViewById(R.id.content);
        themeLayout = findViewById(R.id.themes);
        SharedPreferences preferences = getSharedPreferences("P12", Context.MODE_PRIVATE);
        preferenceEditor = preferences.edit();

        addPreference(0, R.string.system_settings_title_setting_vibration,
                R.string.system_settings_description_setting_vibration,
                preferences.getBoolean("P12I0", true));

        addPreference(1, R.string.system_settings_title_automatically_save,
                R.string.system_settings_description_automatically_save,
                preferences.getBoolean("P12I2", false));
        
        MaterialThemeInflator = getLayoutInflater().inflate(R.layout.app_material_themes, null);
        MaterialTheme = getSharedPreferences("MaterialTheme", Context.MODE_PRIVATE);
        MaterialThemeEnable = getSharedPreferences("MaterialThemeEnable",Context.MODE_PRIVATE);
        
        ((LinearLayout)MaterialThemeInflator.findViewById(R.id.theme1)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                MaterialTheme.edit().putString("MaterialTheme","BrownishLight").commit();
                MaterialTheme.edit().putString("MaterialThemeType", "Light").commit();
                MaterialThemeEnable.edit().putBoolean("MaterialThemeEnable",false).commit();
                // SketchwareUtil.showMessage(SystemSettingActivity.this,"App theme will be applied after a restart : ".concat(MaterialTheme.getString("MaterialTheme","Test")));
                recreate();
            }
        });
        ((LinearLayout)MaterialThemeInflator.findViewById(R.id.theme2)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                MaterialTheme.edit().putString("MaterialTheme","BrownishDark").commit();
                MaterialTheme.edit().putString("MaterialThemeType", "Dark").commit();
                MaterialThemeEnable.edit().putBoolean("MaterialThemeEnable",false).commit();
                // SketchwareUtil.showMessage(SystemSettingActivity.this,"App theme will be applied after a restart : ".concat(MaterialTheme.getString("MaterialTheme","Test")));
                recreate();
            }
        });
        ((LinearLayout)MaterialThemeInflator.findViewById(R.id.theme3)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                MaterialTheme.edit().putString("MaterialTheme","GreenLight").commit();
                MaterialTheme.edit().putString("MaterialThemeType", "Light").commit();
                MaterialThemeEnable.edit().putBoolean("MaterialThemeEnable",false).commit();
                // SketchwareUtil.showMessage(SystemSettingActivity.this,"App theme will be applied after a restart : ".concat(MaterialTheme.getString("MaterialTheme","Test")));
                recreate();
            }
        });
        ((LinearLayout)MaterialThemeInflator.findViewById(R.id.theme4)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                MaterialTheme.edit().putString("MaterialTheme","GreenDark").commit();
                MaterialTheme.edit().putString("MaterialThemeType", "Dark").commit();
                MaterialThemeEnable.edit().putBoolean("MaterialThemeEnable",false).commit();
                // SketchwareUtil.showMessage(SystemSettingActivity.this,"App theme will be applied after a restart : ".concat(MaterialTheme.getString("MaterialTheme","Test")));
                recreate();
            }
        });
        ((LinearLayout)MaterialThemeInflator.findViewById(R.id.theme5)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                MaterialTheme.edit().putString("MaterialTheme","LightBlueLight").commit();
                MaterialTheme.edit().putString("MaterialThemeType", "Light").commit();
                MaterialThemeEnable.edit().putBoolean("MaterialThemeEnable",false).commit();
                // SketchwareUtil.showMessage(SystemSettingActivity.this,"App theme will be applied after a restart : ".concat(MaterialTheme.getString("MaterialTheme","Test")));
                recreate();
            }
        });
        ((LinearLayout)MaterialThemeInflator.findViewById(R.id.theme6)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                MaterialTheme.edit().putString("MaterialTheme","LightBlueDark").commit();
                MaterialTheme.edit().putString("MaterialThemeType", "Dark").commit();
                MaterialThemeEnable.edit().putBoolean("MaterialThemeEnable",false).commit();
                // SketchwareUtil.showMessage(SystemSettingActivity.this,"App theme will be applied after a restart : ".concat(MaterialTheme.getString("MaterialTheme","Test")));
                recreate();
            }
        });
        contentLayout.addView(MaterialThemeInflator);
    }

    private boolean saveSettings() {
        for (int i = 0; i < contentLayout.getChildCount(); i++) {
            View childAtView = contentLayout.getChildAt(i);
            if (childAtView instanceof PropertySwitchItem) {
                PropertySwitchItem propertySwitchItem = (PropertySwitchItem) childAtView;
                if (0 == propertySwitchItem.getKey()) {
                    preferenceEditor.putBoolean("P12I0", propertySwitchItem.getValue());
                } else if (1 == propertySwitchItem.getKey()) {
                    preferenceEditor.putBoolean("P12I2", propertySwitchItem.getValue());
                }
            }
        }

        return preferenceEditor.commit();
    }
}
