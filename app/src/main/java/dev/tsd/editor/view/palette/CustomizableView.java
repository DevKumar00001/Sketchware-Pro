package dev.tsd.editor.view.palette;

import android.content.Context;
import android.view.ViewGroup;

import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.view.palette.IconBase;
import com.sketchware.remod.R;

import mod.agus.jcoderz.beans.ViewBeans;

public class CustomizableView extends IconBase {

    public CustomizableView(Context context) {
        super(context);
        setWidgetImage(R.drawable.widget_youtube);
        setWidgetName("CustomizableView");
    }

    @Override
    public ViewBean getBean() {
        ViewBean viewBean = new ViewBean();
        viewBean.type = ViewBeans.VIEW_TYPE_WIDGET_CUSTOMIZED_1;
        viewBean.text.text = getName();
        viewBean.convert = "";
        viewBean.inject = "";
        return viewBean;
    }
}
