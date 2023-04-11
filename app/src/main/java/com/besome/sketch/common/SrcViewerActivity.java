package com.besome.sketch.common;

import android.app.AlertDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.besome.sketch.beans.SrcCodeBean;
import com.besome.sketch.ctrls.CommonSpinnerItem;
import com.sketchware.remod.R;

import java.util.ArrayList;

import a.a.a.bB;
import a.a.a.jC;
import a.a.a.yq;
import io.github.rosemoe.sora.langs.java.JavaLanguage;
import io.github.rosemoe.sora.widget.CodeEditor;
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme;
import mod.hey.studios.util.Helper;
import mod.jbk.code.CodeEditorColorSchemes;
import mod.jbk.code.CodeEditorLanguages;
import mod.tsd.utils.SketchwareNotification;
import mod.hilal.saif.activities.tools.ConfigActivity;

public class SrcViewerActivity extends AppCompatActivity {

    private String sc_id;
    private Spinner filesListSpinner;
    private ImageView changeFontSize;
    private LinearLayout progressContainer;
    private ArrayList<SrcCodeBean> srcCodeBean;
    /**
     * Corresponds to the filename of which layout or activity the user is currently in.
     */
    private String currentPageFileName;
    private int sourceCodeFontSize = 12;
    private CodeEditor codeViewer;
    private boolean isActivityVisible = true;
    private int NotificationState = 0;
    private int NotificationID = 1;
    private SketchwareNotification sourceCodeNotification = new SketchwareNotification(this,"Show Source Code", "Receive Notifications when show source code is clicked and you switch to another app.");
        ;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.src_viewer);
        
		sourceCodeNotification.initNotificationManager();
		currentPageFileName = getIntent().hasExtra("current") ? getIntent().getStringExtra("current") : "";

        codeViewer = new CodeEditor(this);
        codeViewer.setTypefaceText(Typeface.MONOSPACE);
        codeViewer.setEditable(false);
        codeViewer.setTextSize(sourceCodeFontSize);
        codeViewer.setPinLineNumber(true);
        setCorrectCodeEditorLanguage();

        LinearLayout contentLayout = (LinearLayout) (findViewById(R.id.pager_soruce_code).getParent());
        contentLayout.removeAllViews();
        contentLayout.addView(codeViewer);

        sc_id = (savedInstanceState != null) ? savedInstanceState.getString("sc_id") : getIntent().getStringExtra("sc_id");

        changeFontSize = findViewById(R.id.imgv_src_size);
        changeFontSize.setOnClickListener((v -> showChangeFontSizeDialog()));

        filesListSpinner = findViewById(R.id.spn_src_list);
        filesListSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SrcCodeBean bean = srcCodeBean.get(position);
                codeViewer.setText(bean.source);
                currentPageFileName = bean.srcFileName;
                setCorrectCodeEditorLanguage();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        LinearLayout layoutSrcList = findViewById(R.id.layout_srclist);
        for (int i = 0; i < layoutSrcList.getChildCount(); i++) {
            View child = layoutSrcList.getChildAt(i);

            if (child instanceof LinearLayout) {
                // Found the LinearLayout containing the ProgressBar and TextView!
                progressContainer = (LinearLayout) child;

                filesListSpinner.setVisibility(View.GONE);
                changeFontSize.setVisibility(View.GONE);
                progressContainer.setVisibility(View.VISIBLE);
                // Notification State
                NotificationState = 0;
                showNotification(NotificationState);
            }
        }

        new Thread(() -> {
            srcCodeBean = new yq(getBaseContext(), sc_id).a(jC.b(sc_id), jC.a(sc_id), jC.c(sc_id));

            try {
                runOnUiThread(() -> {
                    if (srcCodeBean == null) {
                        bB.b(getApplicationContext(), Helper.getResString(R.string.common_error_unknown), bB.TOAST_NORMAL).show();
                    } else {
                        filesListSpinner.setAdapter(new FilesListSpinnerAdapter());
                        for (SrcCodeBean src : srcCodeBean) {
                            if (src.srcFileName.equals(currentPageFileName)) {
                                filesListSpinner.setSelection(srcCodeBean.indexOf(src));
                                break;
                            }
                        }
                        codeViewer.setText(srcCodeBean.get(filesListSpinner.getSelectedItemPosition()).source);

                        progressContainer.setVisibility(View.GONE);
                        filesListSpinner.setVisibility(View.VISIBLE);
                        changeFontSize.setVisibility(View.VISIBLE);
                        // Notification State
                        NotificationState = 1;
                        showNotification(NotificationState);
                    }
                });
            } catch (Exception ignored) {
                // May occur if the activity is killed
            }
        }).start();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("sc_id", sc_id);
        super.onSaveInstanceState(outState);
    }

    private void setCorrectCodeEditorLanguage() {
        if (currentPageFileName.endsWith(".xml")) {
            codeViewer.setColorScheme(CodeEditorColorSchemes.loadTextMateColorScheme(CodeEditorColorSchemes.THEME_GITHUB));
            codeViewer.setEditorLanguage(CodeEditorLanguages.loadTextMateLanguage(CodeEditorLanguages.SCOPE_NAME_XML));
        } else {
            codeViewer.setColorScheme(new EditorColorScheme());
            codeViewer.setEditorLanguage(new JavaLanguage());
        }
    }

    private void showChangeFontSizeDialog() {
        NumberPicker picker = new NumberPicker(this);
        picker.setMinValue(8);
        picker.setMaxValue(30);
        picker.setWrapSelectorWheel(false);
        picker.setValue(sourceCodeFontSize);

        LinearLayout layout = new LinearLayout(this);
        layout.addView(picker, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER));

        new AlertDialog.Builder(this)
                .setTitle("Select font size")
                .setIcon(R.drawable.ic_font_48dp)
                .setView(layout)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    sourceCodeFontSize = picker.getValue();
                    codeViewer.setTextSize(sourceCodeFontSize);
                })
                .setNegativeButton(android.R.string.cancel, null)
                .show();
    }

    public class FilesListSpinnerAdapter extends BaseAdapter {

        private View getCustomSpinnerView(int position, View view, boolean isCurrentlyViewingFile) {
            CommonSpinnerItem spinnerItem = (view != null) ? (CommonSpinnerItem) view :
                    new CommonSpinnerItem(SrcViewerActivity.this);
            spinnerItem.a((srcCodeBean.get(position)).srcFileName, isCurrentlyViewingFile);
            return spinnerItem;
        }

        @Override
        public int getCount() {
            return srcCodeBean.size();
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            boolean isCheckmarkVisible = (filesListSpinner.getSelectedItemPosition() == position);
            return getCustomSpinnerView(position, convertView, isCheckmarkVisible);
        }

        @Override
        public Object getItem(int position) {
            return srcCodeBean.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomSpinnerView(position, convertView, false);
        }
    }
    
    public void showNotification(int stage) {
    	if (isActivityVisible) {
    		
    	} else {
    		if (ConfigActivity.isSettingEnabled(ConfigActivity.SETTING_PROJECT_SOURCE_CODE_LOADING_NOTIFICATION)) {
    			if (stage == 0) {
    				sourceCodeNotification.setIcon(R.drawable.sketch_app_icon);
    				sourceCodeNotification.setTitle("Source Code");
    				sourceCodeNotification.setDescription("Source code is loading be patient");
    				sourceCodeNotification.setCancelable(false);
    				sourceCodeNotification.setProgress(0,100,true);
    				sourceCodeNotification.setSilent(true);
    				sourceCodeNotification.showNotification(NotificationID);
    			} else {
    				sourceCodeNotification.setIcon(R.drawable.sketch_app_icon);
    				sourceCodeNotification.setTitle("Source Code");
    				sourceCodeNotification.setDescription("Source code is ready to view now.Go to Sketchware to view it.");
    				sourceCodeNotification.setCancelable(true);
    				sourceCodeNotification.setProgressDisabled();
    				sourceCodeNotification.setSilent(false);
    				sourceCodeNotification.showNotification(NotificationID);
    			}
    		}
    	}
    }
    
    @Override
    public void onPause(){
    	super.onPause();
    	isActivityVisible = false;
    	if (NotificationState == 0) {
    		showNotification(NotificationState);
    	}
    }
    
    @Override
    public void onDestroy(){
    	super.onDestroy();
    	sourceCodeNotification.dismissNotification(NotificationID);
    }
    
    @Override
    public void onResume(){
    	super.onResume();
    	isActivityVisible = true;
    	sourceCodeNotification.dismissNotification(NotificationID);
    }
}
