package com.pccc.library.viewgroup;

import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.pccc.library.R;
import com.pccc.library.dialog.DialogUtil;

import java.util.List;

/**
 * created by liliangjun
 * 2020/11/4
 */
public class ViewGroup3Spinners extends BaseViewGroup {

    private List<String> items1;
    private List<String> items2;
    private List<String> items3;

    private TextView spinner1;
    private TextView spinner2;
    private TextView spinner3;
    private ViewCallback callback;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ViewGroup3Spinners(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ViewGroup3Spinners(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ViewGroup3Spinners(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    protected void findActionView() {
        spinner1 = findViewById(R.id.spinner);
        spinner2 = findViewById(R.id.spinner2);
        spinner3 = findViewById(R.id.spinner3);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.view_group_3spinners;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void initStyle(AttributeSet attrs) {
        TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.ViewGroup3Spinners);
        if (attributes != null) {
            showMark = attributes.getBoolean(R.styleable.ViewGroup3Spinners_show_mark, false);
            initTag(attributes);
            initSpinner(attributes);
            int textSize = attributes.getDimensionPixelOffset(R.styleable.ViewGroup3Spinners_text_size, 32);
            tag.setTextSize(px2sp(textSize));
            spinner1.setTextSize(px2sp(textSize));
            spinner2.setTextSize(px2sp(textSize));
            spinner3.setTextSize(px2sp(textSize));
            mark.setTextSize(px2dp(textSize));

            String text = attributes.getString(R.styleable.ViewGroup3Spinners_tag);
            colorMark = attributes.getColor(R.styleable.ViewGroup3Spinners_mark_color, Color.RED);
            colorOK = attributes.getColor(R.styleable.ViewGroup3Spinners_mark_ok_color, Color.GREEN);

            if (!TextUtils.isEmpty(text)) {
                tag.setTextColor(Color.BLACK);
                tag.setText(text);
            }
        }
    }

    @Override
    protected void initUI() {
        showMark(showMark);
    }


    @Override
    protected void log() {
        //TODO log
    }

    @Override
    public void showMark(boolean showMark) {
        if (showMark) {
            mark.setTextColor(
                    spinner1.getText().length() > 0
                            && spinner2.getText().length() > 0
                            && spinner3.getText().length() > 0
                            ? colorOK : colorMark);
        }
        mark.setVisibility(showMark ? VISIBLE : INVISIBLE);
    }

    @Override
    public void requestErrorFocus(boolean isError) {
        //TODO 显示错误的样式
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initSpinner(TypedArray attributes) {
        int drawbleId = attributes.getResourceId(R.styleable.ViewGroup3Spinners_action_background_drawable, 0);

        if (drawbleId != 0) {
            spinner1.setBackground(getContext().getDrawable(drawbleId));
            spinner2.setBackground(getContext().getDrawable(drawbleId));
            spinner3.setBackground(getContext().getDrawable(drawbleId));
        } else {
            int color = attributes.getColor(R.styleable.ViewGroup3Spinners_action_background, Color.WHITE);
            spinner1.setBackgroundColor(color);
            spinner2.setBackgroundColor(color);
            spinner3.setBackgroundColor(color);
        }
        int rightIconId = attributes.getResourceId(R.styleable.ViewGroup3Spinners_right_icon, R.mipmap.down);
        Drawable drawableRight = ContextCompat.getDrawable(getContext(), rightIconId);
        drawableRight.setBounds(0, 0, 26, 20);
        spinner1.setCompoundDrawables(null, null, drawableRight, null);
        spinner1.setCompoundDrawablePadding(20);
        spinner2.setCompoundDrawables(null, null, drawableRight, null);
        spinner2.setCompoundDrawablePadding(20);
        spinner3.setCompoundDrawables(null, null, drawableRight, null);
        spinner3.setCompoundDrawablePadding(20);

        int spinner_height = (int) attributes.getDimension(R.styleable.ViewGroup3Spinners_action_height, ViewGroup.LayoutParams.WRAP_CONTENT);
        int spinner_width = (int) attributes.getDimension(R.styleable.ViewGroup3Spinners_action_width, ViewGroup.LayoutParams.WRAP_CONTENT);
        initLayoutParams(spinner1, spinner_height, spinner_width);
        initLayoutParams(spinner2, spinner_height, spinner_width);
        initLayoutParams(spinner3, spinner_height, spinner_width);
        String hint1 = attributes.getString(R.styleable.ViewGroup3Spinners_hint1);
        spinner1.setHint(hint1);
        String hint2 = attributes.getString(R.styleable.ViewGroup3Spinners_hint2);
        spinner2.setHint(hint2);
        String hint3 = attributes.getString(R.styleable.ViewGroup3Spinners_hint3);
        spinner3.setHint(hint3);
    }

    private void initLayoutParams(TextView v, int h, int w) {
        v.setWidth((int) (w * widthScaleFactor));
        v.setHeight((int) (h * heightScaleFactor));
    }


    private void initTag(TypedArray attributes) {
        int tag_height = attributes.getDimensionPixelOffset(R.styleable.ViewGroup3Spinners_tag_height, ViewGroup.LayoutParams.WRAP_CONTENT);
        int tag_width = attributes.getDimensionPixelOffset(R.styleable.ViewGroup3Spinners_tag_width, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (tag_height != ViewGroup.LayoutParams.WRAP_CONTENT) {
            tag.setHeight((int) (tag_height * heightScaleFactor));
        }
        if (tag_width != ViewGroup.LayoutParams.WRAP_CONTENT) {
            tag.setWidth((int) (tag_width * widthScaleFactor));
        }
    }

    public ViewGroup3Spinners setItems1(List<String> items1) {
        if (items1 != null) {
            this.items1 = items1;
            setSpinnerAction(spinner1, items1);
        }
        return this;
    }

    public ViewGroup3Spinners setItems2(List<String> items2) {
        if (items2 != null) {
            this.items2 = items2;
            setSpinnerAction(spinner2, items2);
        }
        return this;
    }

    public ViewGroup3Spinners setItems3(List<String> items3) {
        if (items3 != null) {
            this.items3 = items3;
            setSpinnerAction(spinner3, items3);
        }
        return this;
    }

    public TextView getSpinner1() {
        return spinner1;
    }

    public TextView getSpinner2() {
        return spinner2;
    }

    public TextView getSpinner3() {
        return spinner3;
    }

    public void setCallBack(BaseViewGroup.ViewCallback callback) {
        this.callback = callback;
    }

    private void setSpinnerAction(TextView spinner, List<String> items) {
        spinner.setOnClickListener(v -> {
            Context context = getContext();
            Dialog dialog = new Dialog(context, R.style.FullHeightDialog);
            dialog.setTitle(tag.getText());
            View view = LayoutInflater.from(context).inflate(R.layout.dialog_list, null);
            ListView listView = view.findViewById(R.id.list);
            if (items != null) {
                listView.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, items));
            } else {
                throw new NullPointerException(getClass().getName() + ": " + "items is null");
            }
            listView.setOnItemClickListener((parent, view1, position, id) -> {
                if (callback != null) {
                    markOK(true);
                    callback.done(v,items.get(position));
                }
                dialog.dismiss();
            });
            Button cancel = view.findViewById(R.id.btn_cancel);
            cancel.setOnClickListener(view1 -> dialog.dismiss());

            dialog.setContentView(view);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);

            dialog.show();
        });
    }


}