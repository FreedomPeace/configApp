package com.bank.library.view;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bank.library.R;

public class CustomInputView extends FrameLayout {

    private final TextView labelView;
    private final TextView valueView;
    private final TextView starView;



    /**
     * 是否已经录入了必输值
     */
    public boolean isInputValue() {
        return !TextUtils.isEmpty(getValue());
    }

    public CustomInputView setLabel(String label) {
        labelView.setText(label);
        return this;
    }
    public CustomInputView setValue(String value) {
        valueView.setText(value);
        if (TextUtils.isEmpty(value)) {
            starView.setTextColor(Color.RED);
        } else {
            starView.setTextColor(Color.GREEN);
        }
        return this;
    }
    public String getValue() {
        return valueView.getText().toString().trim();
    }

    public CustomInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_text_input, null);
        labelView = view.findViewById(R.id.input_label);
        valueView = view.findViewById(R.id.input_value);
        starView = view.findViewById(R.id.input_star);
        valueView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 弹出输入框，等待用户输入信息
            }
        });
        addView(view);
    }

  /*  public static class Build {

    }*/

}
