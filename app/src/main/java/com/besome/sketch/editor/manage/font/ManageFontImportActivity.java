package com.besome.sketch.editor.manage.font;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.beans.ProjectResourceBean;
import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.sketchware.remod.R;
import com.sketchware.remod.databinding.ManageFontImportBinding;

import java.util.ArrayList;
import java.util.Iterator;

import a.a.a.QB;
import a.a.a.bB;
import a.a.a.mB;
import a.a.a.uq;
import a.a.a.xB;

@SuppressLint("NotifyDataSetChanged")
public class ManageFontImportActivity extends BaseAppCompatActivity implements View.OnClickListener {

    private EditText r;
    private ItemAdapter itemAdapter;
    private ArrayList<ProjectResourceBean> projectFonts;
    private ArrayList<ProjectResourceBean> selectedCollection;
    private int x;
    private QB nameValidator;
    private ManageFontImportBinding binding;

    private boolean b(String var1) {
        for (ProjectResourceBean resourceBean : projectFonts) {
            if (resourceBean.resName.equals(var1)) {
                return true;
            }
        }
        return false;
    }

    private void f(int position) {
        String resFullName = selectedCollection.get(position).resFullName;
        binding.textFont.setTypeface(Typeface.createFromFile(resFullName));
        binding.textFont.setText(xB.b().a(getApplicationContext(), R.string.design_manager_font_description_example_sentence));
    }

    private ArrayList<String> getProjectResources() {
        ArrayList<String> names = new ArrayList<>();
        names.add("app_icon");
        for (ProjectResourceBean projectResourceBean : selectedCollection) {
            names.add(projectResourceBean.resName);
        }
        return names;
    }

    private ArrayList<String> m() {
        ArrayList<String> names = new ArrayList<>();
        names.add("app_icon");
        for (ProjectResourceBean projectResourceBean : projectFonts) {
            names.add(projectResourceBean.resName);
        }
        return names;
    }

    private boolean n() {
        ArrayList<String> var1 = new ArrayList<>();
        for (ProjectResourceBean var3 : selectedCollection) {
            if (var3.isDuplicateCollection) {
                var1.add(var3.resName);
            }
        }

        if (!var1.isEmpty()) {
            String var8 = xB.b().a(getApplicationContext(), R.string.common_message_name_unavailable);
            Iterator<String> var4 = var1.iterator();

            String var6;
            StringBuilder var7;
            for (var6 = ""; var4.hasNext(); var6 = var7.toString()) {
                String var5 = var4.next();
                String var9 = var6;
                if (!var6.isEmpty()) {
                    var9 = var6 + ", ";
                }

                var7 = new StringBuilder();
                var7.append(var9);
                var7.append(var5);
            }

            String message = var8 + "\n[" + var6 + "]";
            bB.a(getApplicationContext(), message, 1).show();
            return true;
        } else {
            return false;
        }
    }

    private boolean o() {
        return nameValidator.b();
    }

    @Override
    public void onBackPressed() {
        setResult(-1);
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == binding.btnDecide.getId()) {
            String resName = r.getText().toString();
            if (!o()) {
                ProjectResourceBean var7 = selectedCollection.get(x);
                r.setText(var7.resName);
                return;
            }

            if (!binding.chkSamename.isChecked()) {
                ProjectResourceBean resourceBean = selectedCollection.get(x);
                resourceBean.resName = resName;
                resourceBean.isDuplicateCollection = false;
                nameValidator.a(getProjectResources());
                itemAdapter.notifyDataSetChanged();
            } else {
                ProjectResourceBean projectResourceBean;
                for (i = 0; i < selectedCollection.size(); projectResourceBean.isDuplicateCollection = false) {
                    projectResourceBean = selectedCollection.get(i);
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(resName);
                    stringBuilder.append("_");
                    i++;
                    stringBuilder.append(i);
                    projectResourceBean.resName = stringBuilder.toString();
                }

                nameValidator.a(getProjectResources());
                itemAdapter.notifyDataSetChanged();
            }
        } else if (i == binding.imgBackbtn.getId()) {
            onBackPressed();
        } else if (i == binding.tvSendbtn.getId() && !n()) {
            Intent var5 = new Intent();
            var5.putParcelableArrayListExtra("results", selectedCollection);
            setResult(-1, var5);
            finish();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!super.j()) {
            finish();
        }
        binding = ManageFontImportBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.imgBackbtn.setOnClickListener(this);
        binding.tvSendbtn.setText(xB.b().a(getApplicationContext(), R.string.common_word_import).toUpperCase());
        binding.tvSendbtn.setOnClickListener(this);
        binding.tvSamename.setText(xB.b().a(getApplicationContext(), R.string.design_manager_font_title_apply_same_naming));
        itemAdapter = new ItemAdapter();
        binding.recyclerList.setHasFixedSize(true);
        binding.recyclerList.setAdapter(itemAdapter);
        binding.recyclerList.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));
        projectFonts = getIntent().getParcelableArrayListExtra("project_fonts");
        selectedCollection = getIntent().getParcelableArrayListExtra("selected_collections");
        binding.tvCurrentnum.setText(String.valueOf(1));
        binding.tvTotalnum.setText(String.valueOf(selectedCollection.size()));
        r = binding.edInput.getEditText();
        r.setText(selectedCollection.get(0).resName);
        r.setPrivateImeOptions("defaultInputmode=english;");
        binding.edInput.setHint(xB.b().a(this, R.string.design_manager_font_hint_enter_font_name));
        nameValidator = new QB(getApplicationContext(), binding.edInput.getTextInputLayout(), uq.b, m(), getProjectResources());
        binding.chkSamename.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                nameValidator.c(null);
                nameValidator.a(selectedCollection.size());
            } else {
                nameValidator.c(selectedCollection.get(x).resName);
                nameValidator.a(1);
            }
        });
        Button btnDecide = findViewById(R.id.btn_decide);
        btnDecide.setText(xB.b().a(getApplicationContext(), R.string.design_manager_change_name_button));
        btnDecide.setOnClickListener(this);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        p();
        f(0);
        itemAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!super.j()) {
            finish();
        }
    }

    private void p() {
        ArrayList<ProjectResourceBean> var1 = new ArrayList<>();
        ArrayList<ProjectResourceBean> var2 = new ArrayList<>();
        Iterator<ProjectResourceBean> var3 = selectedCollection.iterator();

        ProjectResourceBean var4;
        while (var3.hasNext()) {
            var4 = var3.next();
            if (b(var4.resName)) {
                var4.isDuplicateCollection = true;
                var1.add(var4);
            } else {
                var4.isDuplicateCollection = false;
                var2.add(var4);
            }
        }

        if (!var1.isEmpty()) {
            bB.b(getApplicationContext(), xB.b().a(getApplicationContext(), R.string.design_manager_message_collection_name_conflict), 1).show();
        } else {
            bB.a(getApplicationContext(), xB.b().a(getApplicationContext(), R.string.design_manager_message_collection_name_no_conflict), 0).show();
        }

        selectedCollection = new ArrayList<>();

        for (ProjectResourceBean projectResourceBean : var1) {
            var4 = projectResourceBean;
            selectedCollection.add(var4);
        }

        selectedCollection.addAll(var2);
    }

    public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

        public ItemAdapter() {
        }

        @Override
        public int getItemCount() {
            return selectedCollection.size();
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
            if (selectedCollection.get(position).isDuplicateCollection) {
                viewHolder.imgConflict.setImageResource(R.drawable.ic_cancel_48dp);
            } else {
                viewHolder.imgConflict.setImageResource(R.drawable.ic_ok_48dp);
            }

            if (position == x) {
                viewHolder.img.setBackgroundResource(R.drawable.bg_outline_dark_yellow);
            } else {
                viewHolder.img.setBackgroundResource(R.drawable.bg_outline);
            }
            viewHolder.img.setImageResource(R.drawable.ic_font_48dp);
            viewHolder.tvName.setText(selectedCollection.get(position).resName);
        }

        @Override
        @NonNull
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_import_list_item, parent, false));
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public LinearLayout layoutItem;
            public ImageView imgConflict;
            public ImageView img;
            public TextView tvName;

            public ViewHolder(View itemView) {
                super(itemView);
                layoutItem = itemView.findViewById(R.id.layout_item);
                imgConflict = itemView.findViewById(R.id.img_conflict);
                img = itemView.findViewById(R.id.img);
                tvName = itemView.findViewById(R.id.tv_name);
                img.setOnClickListener(view -> {
                    if (!mB.a()) {
                        x = getLayoutPosition();
                        f(x);
                        binding.tvCurrentnum.setText(String.valueOf(getLayoutPosition() + 1));
                        r.setText(selectedCollection.get(getLayoutPosition()).resName);
                        if (binding.chkSamename.isChecked()) {
                            nameValidator.c(null);
                            nameValidator.a(selectedCollection.size());
                        } else {
                            nameValidator.c(selectedCollection.get(getLayoutPosition()).resName);
                            nameValidator.a(1);
                        }

                        itemAdapter.notifyDataSetChanged();
                    }
                });
            }
        }
    }
}
