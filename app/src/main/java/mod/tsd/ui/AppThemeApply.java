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
            if (MaterialTheme.getString("MaterialTheme","BrownishLight").equals("BrownishLight")){
                context.setTheme(R.style.BrownishLight);
            }
            else if (MaterialTheme.getString("MaterialTheme","BrownishLight").equals("BrownishDark")) {
                context.setTheme(R.style.BrownishDark);
            }
            else if (MaterialTheme.getString("MaterialTheme","BrownishLight").equals("GreenLight")) {
                context.setTheme(R.style.GreenLight);
            }
            else if (MaterialTheme.getString("MaterialTheme","BrownishLight").equals("GreenDark")) {
                context.setTheme(R.style.GreenDark);
            }
            else if (MaterialTheme.getString("MaterialTheme","BrownishLight").equals("LightBlueLight")) {
                context.setTheme(R.style.LightBlueLight);
            }
            else if (MaterialTheme.getString("MaterialTheme","BrownishLight").equals("LightBlueDark")) {
                context.setTheme(R.style.LightBlueDark);
            }
            else {
                context.setTheme(R.style.BrownishLight);
            }
        }
    }
}