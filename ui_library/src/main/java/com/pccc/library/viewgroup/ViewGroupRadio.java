package com.pccc.library.viewgroup;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.pccc.library.R;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * created by liliangjun
 * 2020/10/23
 */
public class ViewGroupRadio extends BaseViewGroup {
    private RadioGroup group;
    private RadioButton yes;
    private RadioButton no;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ViewGroupRadio(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ViewGroupRadio(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ViewGroupRadio(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void findActionView() {
        group = findViewById(R.id.radioGroup);
        yes = findViewById(R.id.radioYes);
        no = findViewById(R.id.radioNo);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.view_group_radio;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void initStyle(AttributeSet attrs) {
        TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.ViewGroupRadio);
        if (attributes != null) {
            showMark = attributes.getBoolean(R.styleable.ViewGroupRadio_show_mark, false);
            initTag(attributes);
            initRadios(attributes);
            int textSize = attributes.getDimensionPixelOffset(R.styleable.ViewGroupRadio_text_size, 32);
            tag.setTextSize(px2sp(textSize));
            yes.setTextSize(px2sp(textSize));
            no.setTextSize(px2sp(textSize));
            mark.setTextSize(px2dp(textSize));

            String text = attributes.getString(R.styleable.ViewGroupRadio_tag);
            colorMark = attributes.getColor(R.styleable.ViewGroupRadio_mark_color, Color.RED);
            colorOK = attributes.getColor(R.styleable.ViewGroupRadio_mark_ok_color, Color.GREEN);
            if (!TextUtils.isEmpty(text)) {
                tag.setTextColor(Color.BLACK);
                tag.setText(text);
            }
        }
        //TypedArray用完需要回收
        if (attributes != null) {
            attributes.recycle();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initRadios(TypedArray attributes) {
        int drawbleId = attributes.getResourceId(R.styleable.ViewGroupRadio_action_background_drawable, 0);
        if (drawbleId != 0) {
            yes.setBackground(getContext().getDrawable(drawbleId));
            no.setBackground(getContext().getDrawable(drawbleId));
        } else {
            int color = attributes.getColor(R.styleable.ViewGroupRadio_action_background, Color.TRANSPARENT);
            yes.setBackgroundColor(color);
            no.setBackgroundColor(color);
        }
        int radio_height = (int) attributes.getDimension(R.styleable.ViewGroupRadio_action_height, ViewGroup.LayoutParams.WRAP_CONTENT);
        int radio_width = (int) attributes.getDimension(R.styleable.ViewGroupRadio_action_width, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (radio_width != ViewGroup.LayoutParams.WRAP_CONTENT) {
            yes.setWidth((int) (radio_width * widthScaleFactor));
            no.setWidth((int) (radio_width * widthScaleFactor));
        }
        if (radio_height != ViewGroup.LayoutParams.WRAP_CONTENT) {
            yes.setHeight((int) (radio_height * heightScaleFactor));
            no.setHeight((int) (radio_height * heightScaleFactor));
        }
    }

    private void initTag(TypedArray attributes) {
        int tag_height = attributes.getDimensionPixelOffset(R.styleable.ViewGroupRadio_tag_height, ViewGroup.LayoutParams.WRAP_CONTENT);
        int tag_width = attributes.getDimensionPixelOffset(R.styleable.ViewGroupRadio_tag_width, ViewGroup.LayoutParams.WRAP_CONTENT);

        if (tag_width != ViewGroup.LayoutParams.WRAP_CONTENT) {
            tag.setWidth((int) (tag_width * widthScaleFactor));
        }
        if (tag_height != ViewGroup.LayoutParams.WRAP_CONTENT) {
            tag.setHeight((int) (tag_height * heightScaleFactor));
        }
    }

    @Override
    protected void initUI() {
        showMark(showMark);
        performAction();
    }

    private void performAction() {
        group.setOnCheckedChangeListener((group1, checkedId) -> {
            if (callback == null) {
                //测试用
                showMessage("未设置callback");
                return;
            }
            View v;
            String result = checkedId == R.id.radioYes ? "true" : "false";
            if (checkedId == R.id.radioYes) {
                yes.setChecked(true);
            } else {
                no.setChecked(true);
            }
            if (callback instanceof ViewCallback) {
                ((ViewCallback) callback).done(this, result);
            } else {
                callback.done(result);
            }
        });

    }

    @Override
    public void showMark(boolean showMark) {
        if (showMark) {
            mark.setTextColor(group.isSelected() ? colorOK : colorMark);
        }
        mark.setVisibility(showMark ? VISIBLE : INVISIBLE);
    }

    @Override
    public void requestErrorFocus(boolean isError) {
        //TODO 显示错误的样式
    }

    /**
     * 选择是或否
     *
     * @param isTrue
     */
    public void setSelect(boolean isTrue) {
        yes.setChecked(isTrue);
        no.setChecked(!isTrue);
    }

    /**
     * 根据id设置该radio为选中
     *
     * @param id
     */
    public void setSelect(int id) {
        if (id == R.id.radioYes) {
            yes.setChecked(true);
        }
        if (id == R.id.radioNo) {
            no.setChecked(true);
        }
    }

    /**
     * radio是的id
     *
     * @return
     */
    public RadioButton getRadioYes() {
        return yes;
    }

    /**
     * radio否的id
     *
     * @return
     */
    public RadioButton getRadioNoId() {
        return no;
    }

    @Override
    protected void log() {
        //TODO log
    }
}
