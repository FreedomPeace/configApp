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


/**
 * created by liliangjun
 * 2020/10/23
 */
public class ViewGroupSpinner extends BaseViewGroup {
    private TextView actionView;
    private String[] items;
    private int drawableId;
    private int drawableErrorId;
    private int color;
    private int colorError;
    private String text;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ViewGroupSpinner(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ViewGroupSpinner(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ViewGroupSpinner(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

    }

    @Override
    protected void findActionView() {
        actionView = findViewById(R.id.spinner);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.view_group_spinner;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void initStyle(AttributeSet attrs) {
        TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.ViewGroupSpinner);
        if (attributes != null) {
            showMark = attributes.getBoolean(R.styleable.ViewGroupSpinner_show_mark, false);
            initTag(attributes);
            initSpinner(attributes);
            int textSize = attributes.getDimensionPixelOffset(R.styleable.ViewGroupSpinner_text_size, 32);
            tag.setTextSize(px2sp(textSize));
            actionView.setTextSize(px2sp(textSize));
            mark.setTextSize(px2dp(textSize));

            String text = attributes.getString(R.styleable.ViewGroupSpinner_tag);
            colorMark = attributes.getColor(R.styleable.ViewGroupSpinner_mark_color, Color.RED);
            colorOK = attributes.getColor(R.styleable.ViewGroupSpinner_mark_ok_color, Color.GREEN);

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
    private void initSpinner(TypedArray attributes) {
        int itemsId = attributes.getResourceId(R.styleable.ViewGroupSpinner_items, 0);
        if (itemsId != 0) {
            items = getContext().getResources().getStringArray(itemsId);
        }
        drawableId = attributes.getResourceId(R.styleable.ViewGroupSpinner_action_background_drawable, 0);
        if (drawableId != 0) {
            actionView.setBackground(getContext().getDrawable(drawableId));
        } else {
            color = attributes.getColor(R.styleable.ViewGroupSpinner_action_background, Color.WHITE);
            actionView.setBackgroundColor(color);
        }
        drawableErrorId = attributes.getResourceId(R.styleable.ViewGroupSpinner_action_background_drawable_error, 0);
        if (drawableErrorId == 0) {
            colorError = attributes.getColor(R.styleable.ViewGroupSpinner_action_background_error, Color.RED);
        }

        int rightIconId = attributes.getResourceId(R.styleable.ViewGroupSpinner_right_icon, R.mipmap.down);
        Drawable drawableRight = ContextCompat.getDrawable(getContext(), rightIconId);
        drawableRight.setBounds(0, 0, 26, 20);

        actionView.setCompoundDrawables(null, null, drawableRight, null);
        actionView.setCompoundDrawablePadding(20);

        int spinner_height = (int) attributes.getDimension(R.styleable.ViewGroupSpinner_action_height, ViewGroup.LayoutParams.WRAP_CONTENT);
        int spinner_width = (int) attributes.getDimension(R.styleable.ViewGroupSpinner_action_width, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (spinner_height != ViewGroup.LayoutParams.WRAP_CONTENT) {
            actionView.setHeight((int) (spinner_height * heightScaleFactor));
        }
        if (spinner_width != ViewGroup.LayoutParams.WRAP_CONTENT) {
            actionView.setWidth((int) (spinner_width * widthScaleFactor));
        }
    }

    private void initTag(TypedArray attributes) {
        int tag_height = attributes.getDimensionPixelOffset(R.styleable.ViewGroupSpinner_tag_height, ViewGroup.LayoutParams.WRAP_CONTENT);
        int tag_width = attributes.getDimensionPixelOffset(R.styleable.ViewGroupSpinner_tag_width, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (tag_height != ViewGroup.LayoutParams.WRAP_CONTENT) {
            tag.setHeight((int) (tag_height * heightScaleFactor));
        }
        if (tag_width != ViewGroup.LayoutParams.WRAP_CONTENT) {
            tag.setWidth((int) (tag_width * widthScaleFactor));
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void initUI() {
        showMark(showMark);
        performAction();
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void performAction() {

        actionView.setOnClickListener(spinner -> {
//            if (callback == null) {
//                //测试用
//                showMessage("未设置callback");
//                return;
//            }
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
                    if (callback instanceof ViewCallback) {
                        ((ViewCallback) callback).done(this, items[position]);
                    } else {
                        callback.done(items[position]);
                    }
                }
                requestErrorFocus(false);
                dialog.dismiss();
            });
            Button cancel = view.findViewById(R.id.btn_cancel);
            cancel.setOnClickListener(v -> dialog.dismiss());

            dialog.setContentView(view);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);

            dialog.show();

        });
    }

    public void showMark(boolean showMark) {
        if (showMark) {
            mark.setTextColor(actionView.getText().length() > 0 ? colorOK : colorMark);
        }
        mark.setVisibility(showMark ? VISIBLE : INVISIBLE);
    }

    public TextView spinner() {
        return actionView;
    }

    public String getText() {
        return actionView.getText().toString();
    }

    /**
     * 设置spinner的内容
     *
     * @param text
     */
    public void setText(String text) {
        if (!TextUtils.isEmpty(text)) {
            actionView.setText(text);
            requestErrorFocus(false);
            showMark(showMark);
            postInvalidate();
        }
    }

    /**
     * 设置listView的items
     *
     * @param items String[]
     */
    public void setItems(String[] items) {
        this.items = items;
    }

    /**
     * listView的items
     *
     * @return
     */
    public String[] getItems() {
        return items;
    }

    @Override
    protected void log() {
        //TODO log
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void requestErrorFocus(boolean isError) {
        if (drawableId != 0 && drawableErrorId != 0)
            actionView.setBackground(getContext().getDrawable(isError ? drawableErrorId : drawableId));
        else
            actionView.setBackgroundColor(isError ? colorError : color);

    }
}
