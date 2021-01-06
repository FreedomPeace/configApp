package com.pccc.library.viewgroup;

import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.BindingAdapter;
import android.databinding.BindingMethod;
import android.databinding.BindingMethods;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;


import com.pccc.library.R;
import com.pccc.library.dialog.validator.IValidator;
import com.pccc.library.dialog.validator.ValidatorUtil;

/**
 * <p>
 * version 1.03
 * 增加默认验证器，默认全部通过。
 * </p>
 * <p>version 1.02
 * 增加{@link ValidatorUtil}新增部分验证器
 * attr.xml中增加validator_length用于{@link ValidatorUtil#validator(int, int)}
 * 2020/11/3
 * </p>
 * <p>
 * created by liliangjun
 * 2020/10/23
 */
public class ViewGroupEditor extends BaseViewGroup {
    private String hint;
    private String text;
    private TextView actionView;
    private int validatorType;
    private int validatorLength;
    private IValidator validator;
    private IValidator DEFAULT_VALIDATOR = ValidatorUtil.validator(ValidatorUtil.CASE_LENGTH, 10);
    private int background;
    private int drawableId;
    private int drawableErrorId;
    private int color;
    private int colorError;
    private boolean clickable = true;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ViewGroupEditor(@NonNull Context context) {
        this(context, null, 0, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ViewGroupEditor(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ViewGroupEditor(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ViewGroupEditor(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    protected void findActionView() {
        actionView = findViewById(R.id.edit);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.view_group_edit;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void initStyle(AttributeSet attrs) {
        System.out.println("container.width = " + container.getWidth());
        System.out.println("container.height = " + container.getHeight());
        TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.ViewGroupEditor);
        if (attributes != null) {
            showMark = attributes.getBoolean(R.styleable.ViewGroupEditor_show_mark, false);
            initTag(attributes);
            System.out.println("-----------sssssss" + drawableErrorId);
            initEdit(attributes);
            int textSize = attributes.getDimensionPixelOffset(R.styleable.ViewGroupEditor_text_size, 32);
            tag.setTextSize(px2sp(textSize));
            actionView.setTextSize(px2sp(textSize));
            mark.setTextSize(px2dp(textSize));

            colorMark = attributes.getColor(R.styleable.ViewGroupEditor_mark_color, Color.RED);
            String text = attributes.getString(R.styleable.ViewGroupEditor_tag);
            colorOK = attributes.getColor(R.styleable.ViewGroupEditor_mark_ok_color, Color.GREEN);
            validatorType = attributes.getInt(R.styleable.ViewGroupEditor_validator, -1);
            validatorLength = attributes.getInt(R.styleable.ViewGroupEditor_validator_length, -1);
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

    private void initTag(@NonNull TypedArray attributes) {
        int tag_height = attributes.getDimensionPixelOffset(R.styleable.ViewGroupEditor_tag_height, ViewGroup.LayoutParams.WRAP_CONTENT);
        int tag_width = attributes.getDimensionPixelOffset(R.styleable.ViewGroupEditor_tag_width, ViewGroup.LayoutParams.WRAP_CONTENT);

        if (tag_width != ViewGroup.LayoutParams.WRAP_CONTENT) {
            tag.setWidth((int) (tag_width * widthScaleFactor));
        }
        if (tag_height != ViewGroup.LayoutParams.WRAP_CONTENT) {
            tag.setHeight((int) (tag_height * heightScaleFactor));
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initEdit(@NonNull TypedArray attributes) {
        drawableId = attributes.getResourceId(R.styleable.ViewGroupEditor_action_background_drawable, 0);
        if (drawableId != 0) {
            actionView.setBackground(getContext().getDrawable(drawableId));
        } else {
            color = attributes.getColor(R.styleable.ViewGroupEditor_action_background, Color.WHITE);
            actionView.setBackgroundColor(color);
        }
        drawableErrorId = attributes.getResourceId(R.styleable.ViewGroupEditor_action_background_drawable_error, 0);
        if (drawableErrorId == 0) {
            colorError = attributes.getColor(R.styleable.ViewGroupEditor_action_background_error, Color.RED);
        }
        int edit_height = (int) attributes.getDimension(R.styleable.ViewGroupEditor_action_height, ViewGroup.LayoutParams.WRAP_CONTENT);
        int edit_width = (int) attributes.getDimension(R.styleable.ViewGroupEditor_action_width, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (edit_width != ViewGroup.LayoutParams.WRAP_CONTENT) {
            actionView.setWidth((int) (edit_width * widthScaleFactor));
        }
        if (edit_height != ViewGroup.LayoutParams.WRAP_CONTENT) {
            actionView.setHeight((int) (edit_height * heightScaleFactor));
        }
        clickable = attributes.getBoolean(R.styleable.ViewGroupEditor_clickable, true);
        actionView.setClickable(clickable);
    }


    @Override
    protected void initUI() {
        showMark(showMark);

//        actionView.setOnClickListener(v -> {
//            if (validatorType != -1) {
//                validator = validatorLength == -1 ?
//                        ValidatorUtil.validator(validatorType) :
//                        ValidatorUtil.validator(validatorType, validatorLength);
//            }
//            if (callback == null) {
//                //测试用
//                showMessage("未设置callback");
//                return;
//            }
//            if (validator == null) {
//                //测试用
//                showMessage("未设置validator");
//                return;
//            }
//            DialogUtil.newEditDialogImp(getContext(), tag.getText().toString(), hint, actionView.getText().toString(), validator, callback);
//        });
        initDialog();
    }

    @Override
    protected void log() {
        //TODO log
    }

    private void initDialog() {

        if (validatorType != -1) {
            validator = ValidatorUtil.validator(validatorType);
        }

        actionView.setOnClickListener(v -> {
            if (callback == null) {
                //测试用
                showMessage("未设置callback");
                return;
            }
            if (validator == null) {
                //测试用
                showMessage("未设置validator,使用默认验证器");
                validator = DEFAULT_VALIDATOR;
            }
            Context context = getContext();
            Dialog dialog = new Dialog(context, R.style.FullHeightDialog);
            dialog.getWindow().setGravity(Gravity.TOP);
            View view = LayoutInflater.from(context).inflate(R.layout.dlg_edit, null);
            TextView title = view.findViewById(R.id.dialog_title);
            title.setText(tag.getText());
            EditText edit = view.findViewById(R.id.edit);
            String source = (String) ViewGroupEditor.this.actionView.getText();
            if (source != null && !TextUtils.isEmpty(source)) {
                edit.setText(source);
                edit.setSelection(source.length());
            }
            Button btnOK = view.findViewById(R.id.btn_ok);
            btnOK.setOnClickListener(button -> {
                String content = edit.getText().toString();
                try {
                    if (validator.validate(content)) {
                        markOK(true);
                        if (callback instanceof ViewCallback) {
                            ((ViewCallback) callback).done(this, content);
                        } else {
                            callback.done(content);
                        }
                        requestErrorFocus(false);
                        dialog.dismiss();
                    }
                } catch (IValidator.ValidationError validationError) {
                    markOK(false);
                    showMessage(validationError.getMessage());
                }
            });
            Button btnCancel = view.findViewById(R.id.btn_cancel);
            btnCancel.setOnClickListener(button ->
                    dialog.dismiss()
            );
            dialog.setContentView(view);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

        });
    }

    @Override
    public void showMark(boolean showMark) {
        if (showMark) {
            mark.setTextColor(actionView.getText().length() > 0 ? colorOK : colorMark);
        }
        mark.setVisibility(showMark ? VISIBLE : INVISIBLE);
    }

    public void setText(String text) {
        if (!TextUtils.isEmpty(text)) {
            actionView.setText(text);
            requestErrorFocus(false);
            showMark(showMark);
            postInvalidate();
        }
    }


    public TextView edit() {
        return actionView;
    }

    public String getText() {
        return actionView.getText().toString();
    }

    public void setValidator(@NonNull IValidator validator) {
        this.validator = validator;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void requestErrorFocus(boolean isError) {
        if (drawableId != 0 && drawableErrorId != 0)
            actionView.setBackground(getContext().getDrawable(isError ? drawableErrorId : drawableId));
        else
            actionView.setBackgroundColor(isError ? colorError : color);

    }


}
