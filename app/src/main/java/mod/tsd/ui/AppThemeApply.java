package mod.tsd.ui;

import com.sketchware.remod.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;

import com.google.android.material.color.DynamicColors;

public class AppThemeApply {
    public static void setUpTheme(Context context){
        SharedPreferences MaterialThemeEnable = context.getSharedPreferences("MaterialThemeEnable",Context.MODE_PRIVATE);
        SharedPreferences MaterialTheme = context.getSharedPreferences("MaterialTheme", Context.MODE_PRIVATE);
        
        if (MaterialThemeEnable.getBoolean("MaterialThemeEnable",false)){
            // DynamicColors will be automatically applied using SketchApplication Application class
        } else {
            context.setTheme(getCurrentTheme(context));
        }
    }
    
    public static int getCurrentTheme(Context context) {
        SharedPreferences MaterialTheme = context.getSharedPreferences("MaterialTheme", Context.MODE_PRIVATE);
        if (MaterialTheme.getString("MaterialTheme","BrownishLight").equals("BrownishLight")){
            return R.style.BrownishLight;
        } else if (MaterialTheme.getString("MaterialTheme","BrownishLight").equals("BrownishDark")) {
            return R.style.BrownishDark;
        } else if (MaterialTheme.getString("MaterialTheme","BrownishLight").equals("GreenLight")) {
            return R.style.GreenLight;
        } else if (MaterialTheme.getString("MaterialTheme","BrownishLight").equals("GreenDark")) {
            return R.style.GreenDark;
        } else if (MaterialTheme.getString("MaterialTheme","BrownishLight").equals("LightBlueLight")) {
            return R.style.LightBlueLight;
        } else if (MaterialTheme.getString("MaterialTheme","BrownishLight").equals("LightBlueDark")) {
            return R.style.LightBlueDark;
        } else {
            return R.style.BrownishLight;
        }
    }
}