package com.denis.pavlovich.weatherapp.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

import com.denis.pavlovich.weatherapp.R;

public class UnitTextView extends android.support.v7.widget.AppCompatTextView {

    private String unit;

    private String value;

    private String template;

    public UnitTextView(Context context) {
        super(context);
    }

    private void processAttrs(Context context, AttributeSet attrs) {
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.UnitTextView);
        template = attributes.getString(R.styleable.UnitTextView_template);
        unit = attributes.getString(R.styleable.UnitTextView_unit);
        if (template == null || !template.contains("%s")) {
            template = "%s %s";
        }
        attributes.recycle();
    }

    public UnitTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        processAttrs(context, attrs);
    }

    public UnitTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        processAttrs(context, attrs);
    }

    private void initText() {
        setText(String.format(template, value, unit), TextView.BufferType.SPANNABLE);
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
        initText();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
        initText();
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }
}
