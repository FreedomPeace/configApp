package com.pccc.library.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.pccc.library.R;
import com.pccc.library.dialog.validator.IValidator;
import com.pccc.library.dialog.wheelview.NumericWheelAdapter;
import com.pccc.library.dialog.wheelview.NumericWheelAdapterForFavor;
import com.pccc.library.dialog.wheelview.OnWheelChangedListener;
import com.pccc.library.dialog.wheelview.WheelView;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 新增{@link MutableLiveData}的各种dialog方法</br>
 * 2020/12/17 </br>
 * liliangjun </br>
 * </br>
 * </p>
 * <p>
 * 新增传入参数为{@link ObservableField}的各种dialog方法</br>
 * 2020/12/10 </br>
 * liliangjun </br>
 * </br>
 * </p>
 *
 * <p>
 * 新增editDialog置顶window </br>
 * 2020/11/4 </br>
 * liliangjun </br>
 * </br>
 * </p>
 * <p>
 * created by liliangjun </br>
 * 2020/10/19 </br>
 * </br>
 * </p>
 */
public class DialogUtil {
    private static final String TAG = "DialogUtil";
    private static final int START_YEAR = 1900;
    private static final int END_YEAR = 2100;

    private DialogUtil() {
        //防止外部调用构造器
    }

    private static Dialog wait_dlg;// 等待

    public static void waitingDialog(Activity con, String content) {
        AlertDialog.Builder b = new AlertDialog.Builder(con);
        b.setMessage(content);// 设置信息
        b.setCancelable(false);

        if (wait_dlg == null || !wait_dlg.isShowing()) {
            wait_dlg = b.create();// 创建对话框
            wait_dlg.show();// 显示对话框
            wait_dlg.setContentView(R.layout.loading_process_dialog_anim);
            wait_dlg.setCancelable(false);
            wait_dlg.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关
        }
    }

    /**
     * 界面隐藏等待对话框
     */
    public static void hideWaitingDialog() {
        if (wait_dlg != null) {
            wait_dlg.dismiss();
            wait_dlg = null;
        }
    }

    private static void newEditDialogImp(@NonNull final Context context, @Nullable String title, @Nullable String hint, @Nullable String source,
                                         final IValidator validator, final Callback callback, final ObservableField<String> observable,
                                         final MutableLiveData<String> liveData) {
        final Dialog dialog = newDialog(context, null, R.style.FullHeightDialog);

        View view = LayoutInflater.from(context).inflate(R.layout.dlg_edit, null);
        TextView tvTitle = view.findViewById(R.id.dialog_title);
        if (title != null && !TextUtils.isEmpty(title)) {

            tvTitle.setText(title);
        }
        final EditText edit = view.findViewById(R.id.edit);
        if (source != null) {
            edit.setText(source);
            edit.setSelection(source.length());
        }
        Button btnOK = view.findViewById(R.id.btn_ok);
        // effective final
//        final TextInputLayout finalLayout = layout;
//        finalLayout.setHint(hint);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = edit.getEditableText().toString();
                try {
                    if (validator.validate(content)) {
                        if (callback != null) {
                            callback.done(content);
                        }
                        if (observable != null) {
                            observable.set(content);
                        }
                        if (liveData != null) {
                            liveData.postValue(content);
                        }
                        dialog.dismiss();
                    }
                } catch (IValidator.ValidationError validationError) {
                    newConfirmDialog(context, "提示", validationError.message);
                }
            }
        });
        Button btnCancel = view.findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             dialog.dismiss();
                                         }
                                     }
        );
        try {
            dialog.getWindow().setGravity(Gravity.TOP);
        } catch (NullPointerException e) {
            // nothing to do
        }

        setupDialogAndShow(context, dialog, view);
    }

    /**
     * <p>新建输入型Dialog，通过validator验证输入是否正确，验证后正确的通过{@link Callback#done(String)}传递，
     * 错误的通过{@link #newConfirmDialog(Context, String, String)}告知错误信息</p>
     *
     * @param context   {@link Context}最好为
     * @param title     提示标题 {@code dialog.setTitle(title)}
     * @param hint      输入类型 {@code txtInputLayout.setHint(hint)}
     * @param source    原界面中已输入的值
     * @param validator {@link IValidator}验证数据是否合法
     * @param callback  {@link Callback}回调函数
     */
    public static void newEditDialog(@NonNull final Context context, @Nullable String title, @Nullable String hint, @Nullable String source,
                                     final IValidator validator, final Callback callback) {
        newEditDialogImp(context, title, hint, source, validator, callback, null, null);
    }


    /**
     * <p>新建输入型Dialog，通过validator验证输入是否正确，验证后正确的通过{@link ObservableField#set(Object)}传递，
     * 错误的通过{@link #newConfirmDialog(Context, String, String)}告知错误信息</p>
     *
     * @param context
     * @param title
     * @param hint
     * @param source
     * @param validator
     * @param observable
     */
    public static void newEditDialog(@NonNull final Context context, @Nullable String title, @Nullable String hint, @Nullable String source,
                                     final IValidator validator, final ObservableField<String> observable) {
        newEditDialogImp(context, title, hint, source, validator, null, observable, null);
    }

    /**
     * <p>新建输入型Dialog，通过validator验证输入是否正确，验证后正确的通过{@link MutableLiveData#postValue(Object)}传递，
     * 错误的通过{@link #newConfirmDialog(Context, String, String)}告知错误信息</p>
     *
     * @param context
     * @param title
     * @param hint
     * @param source
     * @param validator
     * @param liveData
     */
    public static void newEditDialog(@NonNull final Context context, @Nullable String title, @Nullable String hint, @Nullable String source,
                                     final IValidator validator, final MutableLiveData<String> liveData) {
        newEditDialogImp(context, title, hint, source, validator, null, null, liveData);
    }


    /**
     * <p>新建列表型Dialog，通过{@link Callback#done(String)}告知选中的选项</p>
     *
     * @param context  {@link Context}最好为实现
     * @param title    {@code dialog.setTitle(title)}
     * @param contents 列表展示的内容
     * @param callback {@link Callback}回调函数
     */
    public static void newListDialog(@NonNull Context context, @Nullable String title, @NonNull final List<String> contents,
                                     final Callback callback) {
        newListDialogImp(context, title, contents, callback, null, null);
    }

    /**
     * <p>新建列表型Dialog，通过{@link ObservableField#set(Object)}告知选中的选项</p>
     *
     * @param context    {@link Context}最好为实现
     * @param title      {@code dialog.setTitle(title)}
     * @param contents   列表展示的内容
     * @param observable
     */
    public static void newListDialog(@NonNull Context context, @Nullable String title, @NonNull final List<String> contents,
                                     final ObservableField<String> observable) {
        newListDialogImp(context, title, contents, null, observable, null);
    }

    /**
     * <p>新建列表型Dialog，通过{@link MutableLiveData#postValue(Object)}告知选中的选项</p>
     *
     * @param context  {@link Context}最好为实现
     * @param title    {@code dialog.setTitle(title)}
     * @param contents 列表展示的内容
     * @param liveData
     */
    public static void newListDialog(@NonNull Context context, @Nullable String title, @NonNull final List<String> contents,
                                     final MutableLiveData<String> liveData) {
        newListDialogImp(context, title, contents, null, null, liveData);
    }


    private static void newListDialogImp(@NonNull Context context, @Nullable String title, @NonNull final List<String> contents,
                                         final Callback callback, final ObservableField<String> observable,
                                         final MutableLiveData<String> liveData) {
        final Dialog dialog = newDialog(context, title);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_list, null);
        ListView listView = view.findViewById(R.id.list);
        listView.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, contents));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view1, int position, long id) {
                String data = contents.get(position);
                if (observable != null) {
                    observable.set(data);
                }
                if (callback != null) {
                    callback.done(data);
                }
                if (liveData != null) {
                    liveData.postValue(data);
                }
                dialog.dismiss();
            }
        });
        Button cancel = view.findViewById(R.id.btn_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        setupDialogAndShow(context, dialog, view);
    }

    /**
     * <p>新建通知型Dialog</p>
     *
     * @param context {@link Context}最好为实现{@linkty
     * @param title   {@code dialog.setTitle(title)}
     * @param content {@link Callback}回调函数
     */
    public static void newConfirmDialog(Context context, @Nullable String title, @Nullable String content) {
        final Dialog dialog = newDialog(context, title);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_text, null);
        if (content != null) {
            TextView tvContent = view.findViewById(R.id.content);
            tvContent.setText(content);
        }
        Button btnOK = view.findViewById(R.id.btn_ok);
        btnOK.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         dialog.dismiss();
                                     }
                                 }
        );
        setupDialogAndShow(context, dialog, view);
    }

    /**
     * <p>系统默认DatePickerDialog</p>
     *
     * @param context
     */
    public static void newDatePickerDialog(Context context, final Callback callback) {
        final Dialog dialog = new Dialog(context, android.R.style.Theme_Material_Dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_datepicker, null);
        final DatePicker picker = view.findViewById(R.id.datePicker);
        picker.setCalendarViewShown(false);
        Button btnOk = view.findViewById(R.id.btn_ok);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = picker.getYear();
                int month = picker.getMonth();
                int day = picker.getDayOfMonth();
                callback.done(year + "-" + month + "-" + day);
                dialog.dismiss();

                //测试用
//            newConfirmDialog(context, "选择的时间", year + "-" + month + "-" + day);
            }
        });
        setupDialogAndShow(context, dialog, view);
    }

    /**
     * <p>日期时间对话框(年月日时分秒)</>
     * <p>去除*标TextView,该操作应放在Callback中</>
     *
     * @param context  {@link Context}最好为实现
     * @param source   原来的时间
     * @param title    dialog title
     * @param callback 回调
     */
    public static void newDateTimeDialog(Context context, String source, String title, final Callback callback) {
        newDateTimeDialogImp(context, source, title, callback, null, null);
    }

    /**
     * <p>日期时间对话框(年月日时分秒)</>
     * <p>去除*标TextView,通过{@link ObservableField#set(Object)}传递</>
     *
     * @param context    {@link Context}最好为实现
     * @param source     原来的时间
     * @param title      dialog title
     * @param observable
     */
    public static void newDateTimeDialog(Context context, String source,
                                         String title, final ObservableField<String> observable) {
        newDateTimeDialogImp(context, source, title, null, observable, null);
    }

    /**
     * <p>日期时间对话框(年月日时分秒)</>
     * <p>去除*标TextView,通过{@link MutableLiveData#postValue(Object)}传递</>
     *
     * @param context  {@link Context}最好为实现
     * @param source   原来的时间
     * @param title    dialog title
     * @param liveData
     */
    public static void newDateTimeDialog(Context context, String source,
                                         String title, final MutableLiveData<String> liveData) {
        newDateTimeDialogImp(context, source, title, null, null, liveData);
    }

    /**
     * <p>日期时间对话框(年月日时分秒)</>
     * <p>去除*标TextView,该操作应放在Callback中</>
     *
     * @param context  {@link Context}最好为实现
     * @param source   原来的时间
     * @param title    dialog title
     * @param callback 回调
     */
    private static void newDateTimeDialogImp(Context context, String source, String title,
                                             final Callback callback, final ObservableField<String> observable,
                                             final MutableLiveData<String> liveData) {
        final Dialog dialog = newDialog(context, null, R.style.FullHeightDialog);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        // 添加大小月月份并将其转换为list,方便之后的判断
        String[] months_big = {"1", "3", "5", "7", "8", "10", "12"};
        String[] months_little = {"4", "6", "9", "11"};

        final List<String> list_big = Arrays.asList(months_big);
        final List<String> list_little = Arrays.asList(months_little);

        // 找到dialog的布局文件
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_date_time, null);
        TextView tvTitle = (TextView) view.findViewById(R.id.date_time_title);
        tvTitle.setText(title);

        // 年
        final WheelView wv_year = (WheelView) view
                .findViewById(R.id.date_time_year);
        wv_year.setAdapter(new NumericWheelAdapter(START_YEAR, END_YEAR));// 设置"年"的显示数据
        wv_year.setCyclic(true);// 可循环滚动
        wv_year.setLabel("年");// 添加文字
        final WheelView wv_month = (WheelView) view
                .findViewById(R.id.date_time_month);
        // 月
        wv_month.setAdapter(new NumericWheelAdapter(1, 12));
        wv_month.setCyclic(true);
        wv_month.setLabel("月");
        // 日
        final WheelView wv_day = (WheelView) view
                .findViewById(R.id.date_time_day);
        wv_day.setCyclic(true);
        // 判断大小月及是否闰年,用来确定"日"的数据
        if (list_big.contains(String.valueOf(month + 1))) {
            wv_day.setAdapter(new NumericWheelAdapter(1, 31));
        } else if (list_little.contains(String.valueOf(month + 1))) {
            wv_day.setAdapter(new NumericWheelAdapter(1, 30));
        } else {
            // 闰年
            if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
                wv_day.setAdapter(new NumericWheelAdapter(1, 29));
            else
                wv_day.setAdapter(new NumericWheelAdapter(1, 28));
        }
        wv_day.setLabel("日");
        // 时
        final WheelView wv_hour = (WheelView) view
                .findViewById(R.id.date_time_hour);
        wv_hour.setAdapter(new NumericWheelAdapter(0, 23));
        wv_hour.setCyclic(true);
        wv_hour.setLabel("时");
        // 分
        final WheelView wv_minute = (WheelView) view
                .findViewById(R.id.date_time_minute);
        wv_minute.setAdapter(new NumericWheelAdapter(0, 59));
        wv_minute.setCyclic(true);
        wv_minute.setLabel("分");
        // 秒
        final WheelView wv_second = (WheelView) view
                .findViewById(R.id.date_time_second);
        wv_second.setAdapter(new NumericWheelAdapter(0, 59));
        wv_second.setCyclic(true);
        wv_second.setLabel("秒");

        String dateTv = source;
        // 初始化时显示的数据
        if (dateTv != null && dateTv.length() > 0) {
            String[] date_time = dateTv.split(" ");
            String[] date = date_time[0].split("-");
            wv_year.setCurrentItem(Integer.parseInt(date[0]) - START_YEAR);
            wv_month.setCurrentItem(Integer.parseInt(date[1]) - 1);
            wv_day.setCurrentItem(Integer.parseInt(date[2]) - 1);

            String[] time = date_time[1].split(":");
            wv_hour.setCurrentItem(Integer.parseInt(time[0]));
            wv_minute.setCurrentItem(Integer.parseInt(time[1]));
            wv_second.setCurrentItem(Integer.parseInt(time[2]));
        } else {
            wv_year.setCurrentItem(year - START_YEAR);
            wv_month.setCurrentItem(month);
            wv_day.setCurrentItem(day - 1);
            wv_hour.setCurrentItem(hour);
            wv_minute.setCurrentItem(minute);
            wv_second.setCurrentItem(second);
        }

        // 添加"年"监听
        OnWheelChangedListener wheelListener_year = new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                int year_num = newValue + START_YEAR;
                // 判断大小月及是否闰年,用来确定"日"的数据
                if (list_big
                        .contains(String.valueOf(wv_month.getCurrentItem() + 1))) {
                    wv_day.setAdapter(new NumericWheelAdapter(1, 31));
                } else if (list_little.contains(String.valueOf(wv_month
                        .getCurrentItem() + 1))) {
                    wv_day.setAdapter(new NumericWheelAdapter(1, 30));
                } else {
                    if ((year_num % 4 == 0 && year_num % 100 != 0)
                            || year_num % 400 == 0) {
                        wv_day.setAdapter(new NumericWheelAdapter(1, 29));
                        wv_day.setCurrentItem(0);
                    } else {
                        wv_day.setAdapter(new NumericWheelAdapter(1, 28));
                        wv_day.setCurrentItem(0);
                    }

                }
            }
        };
        // 添加"月"监听
        OnWheelChangedListener wheelListener_month = new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                int month_num = newValue + 1;
                // 判断大小月及是否闰年,用来确定"日"的数据
                if (list_big.contains(String.valueOf(month_num))) {
                    wv_day.setAdapter(new NumericWheelAdapter(1, 31));
                    wv_day.setCurrentItem(0);
                } else if (list_little.contains(String.valueOf(month_num))) {
                    wv_day.setAdapter(new NumericWheelAdapter(1, 30));
                    wv_day.setCurrentItem(0);
                } else {
                    if (((wv_year.getCurrentItem() + START_YEAR) % 4 == 0 && (wv_year
                            .getCurrentItem() + START_YEAR) % 100 != 0)
                            || (wv_year.getCurrentItem() + START_YEAR) % 400 == 0) {
                        wv_day.setAdapter(new NumericWheelAdapter(1, 29));
                        wv_day.setCurrentItem(0);
                    } else {
                        wv_day.setAdapter(new NumericWheelAdapter(1, 28));
                        wv_day.setCurrentItem(0);
                    }

                }
            }
        };

        wv_year.addChangingListener(wheelListener_year);
        wv_month.addChangingListener(wheelListener_month);

        // 根据屏幕密度来指定选择器字体的大

        wv_day.TEXT_SIZE = (int) context.getResources().getDimension(
                R.dimen.neirong);
        wv_month.TEXT_SIZE = (int) context.getResources().getDimension(
                R.dimen.neirong);
        wv_year.TEXT_SIZE = (int) context.getResources().getDimension(
                R.dimen.neirong);

        Button btn_sure = (Button) view.findViewById(R.id.date_time_sure);
        Button btn_cancel = (Button) view.findViewById(R.id.date_time_cancel);
        // 确定
        btn_sure.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                dialog.dismiss();
                // 如果是个数,则显示为"02"的样式
                String day = String.valueOf(wv_day.getCurrentItem() + 1);
                String year = String.valueOf(wv_year.getCurrentItem()
                        + START_YEAR);
                String month = String.valueOf(wv_month.getCurrentItem() + 1);
                String hour = String.valueOf(wv_hour.getCurrentItem());
                String minute = String.valueOf(wv_minute.getCurrentItem());
                String second = String.valueOf(wv_second.getCurrentItem());
                if (month.length() < 2) {
                    month = 0 + month;
                }
                if (day.length() < 2) {
                    day = 0 + day;
                }
                if (hour.length() < 2) {
                    hour = 0 + hour;
                }
                if (minute.length() < 2) {
                    minute = 0 + minute;
                }
                if (second.length() < 2) {
                    second = 0 + second;
                }
                String data = year + "-" + month + "-" + day + " " + hour + ":"
                        + minute + ":" + second;
                if (observable != null) {
                    observable.set(data);
                }
                if (callback != null) {
                    callback.done(data);
                }
                if (liveData != null) {
                    liveData.postValue(data);
                }
            }
        });
        // 取消
        btn_cancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });
        // 设置dialog的布局,并显示
        setupDialogAndShow(context, dialog, view);
    }

    /**
     * <p>日期时间对话框(时分秒)</p>
     * <p>去除*标TextView,该操作应放在Callback中</>
     *
     * @param context
     * @param source   原来的时间
     * @param title    dialog title
     * @param callback 回调
     */
    public static void newTimeDialog(Context context, String source, String title,
                                     final Callback callback) {
        newTimeDialogImp(context, source, title, callback, null, null);
    }

    /**
     * <p>日期时间对话框(时分秒)</p>
     * <p>通过{@link ObservableField#set(Object)}传递结果</p>
     *
     * @param context
     * @param source     原来的时间
     * @param title      dialog title
     * @param observable
     */
    public static void newTimeDialog(Context context, String source, String title,
                                     final ObservableField<String> observable) {
        newTimeDialogImp(context, source, title, null, observable, null);
    }

    /**
     * <p>日期时间对话框(时分秒)</p>
     * <p>通过{@link MutableLiveData#postValue(Object)}传递结果</p>
     *
     * @param context
     * @param source   原来的时间
     * @param title    dialog title
     * @param liveData
     */
    public static void newTimeDialog(Context context, String source, String title,
                                     final MutableLiveData<String> liveData) {
        newTimeDialogImp(context, source, title, null, null, liveData);
    }


    private static void newTimeDialogImp(Context context, String source, String title,
                                         final Callback callback, final ObservableField<String> observable,
                                         final MutableLiveData<String> liveData) {
        final Dialog dialog = newDialog(context, null, R.style.FullHeightDialog);

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // 找到dialog的布局文件
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_time, null);
        TextView tvTitle = (TextView) view.findViewById(R.id.date_time_title_01);
        tvTitle.setText(title);

        // 时
        final WheelView wv_hour = (WheelView) view
                .findViewById(R.id.date_time_hour_01);
        wv_hour.setAdapter(new NumericWheelAdapter(0, 23));
        wv_hour.setCyclic(true);
        wv_hour.setLabel("时");
        // 分
        final WheelView wv_minute = (WheelView) view
                .findViewById(R.id.date_time_minute_01);
        wv_minute.setAdapter(new NumericWheelAdapterForFavor(0, 30));
        wv_minute.setCyclic(true);
        wv_minute.setLabel("分");
        // 秒
        final WheelView wv_second = (WheelView) view
                .findViewById(R.id.date_time_second_01);
        wv_second.setAdapter(new NumericWheelAdapter(0, 59));
        wv_second.setCyclic(true);
        wv_second.setLabel("秒");
        wv_second.setVisibility(View.GONE);

        String dateTv = source;
        // 初始化时显示的数据
        if (dateTv != null && dateTv.length() > 0) {
            String[] time = dateTv.split(":");
            wv_hour.setCurrentItem(Integer.parseInt(time[0]));
            wv_minute.setCurrentItem(Integer.parseInt(time[1]));
        } else {
            wv_hour.setCurrentItem(hour);
            wv_minute.setCurrentItem(minute);
        }


        Button btn_sure = (Button) view.findViewById(R.id.date_time_sure_01);
        Button btn_cancel = (Button) view.findViewById(R.id.date_time_cancel_01);
        // 确定
        btn_sure.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                dialog.dismiss();
                // 如果是个数,则显示为"02"的样式
                String hour = String.valueOf(wv_hour.getCurrentItem());
                String minute = String.valueOf(wv_minute.getCurrentItemForFavor());
//				String second = String.valueOf(wv_second.getCurrentItem());
                if (hour.length() < 2) {
                    hour = 0 + hour;
                }
                if (minute.length() < 2) {
                    minute = 0 + minute;
                }
                String data = hour + ":"
                        + minute + ":00";
                if (observable != null) {
                    observable.set(data);
                }
                if (callback != null) {
                    callback.done(data);
                }
                if (liveData != null) {
                    liveData.postValue(data);
                }
            }
        });
        // 取消
        btn_cancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });
        // 设置dialog的布局,并显示
        setupDialogAndShow(context, dialog, view);
    }


    /**
     * <p>年月日时间对话框(年月日),附带日期验证，是否晚于今日</p>
     * <p>去除*标TextView,该操作应放在Callback中</>
     *
     * @param title
     * @param bl    是否比今天晚
     */
    public static void newDatePicker(final Context context, final String source, String title,
                                     final boolean bl, final Callback callback) {
        newDatePickerImp(context, source, title, bl, callback, null, null);
    }

    /**
     * <p>年月日时间对话框(年月日),附带日期验证，是否晚于今日</p>
     * <p>通过{@link ObservableField#set(Object)}传递结果</p>
     *
     * @param title
     * @param bl    是否比今天晚
     */
    public static void newDatePicker(final Context context, final String source, String title,
                                     final boolean bl, final ObservableField<String> observable) {
        newDatePickerImp(context, source, title, bl, null, observable, null);
    }

    /**
     * <p>年月日时间对话框(年月日),附带日期验证，是否晚于今日</p>
     * <p>通过{@link MutableLiveData#postValue(Object)}传递结果</p>
     *
     * @param title
     * @param bl    是否比今天晚
     */
    public static void newDatePicker(final Context context, final String source, String title,
                                     final boolean bl, final MutableLiveData<String> liveData) {
        newDatePickerImp(context, source, title, bl, null, null, liveData);
    }

    /**
     * <p>年月日时间对话框(年月日),附带日期验证，是否晚于今日</p>
     * <p>去除*标TextView,该操作应放在Callback中</>
     *
     * @param title
     * @param bl    是否比今天晚
     */
    private static void newDatePickerImp(final Context context, final String source, String title, final boolean bl,
                                         final Callback callback, final ObservableField<String> observable,
                                         final MutableLiveData<String> liveData) {
        final Dialog dialog = newDialog(context, null, R.style.FullHeightDialog);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);

        // 添加大小月月份并将其转换为list,方便之后的判断
        String[] months_big = {"1", "3", "5", "7", "8", "10", "12"};
        String[] months_little = {"4", "6", "9", "11"};

        final List<String> list_big = Arrays.asList(months_big);
        final List<String> list_little = Arrays.asList(months_little);


        // 找到dialog的布局文件
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dlg_time, null);
        ((TextView) view.findViewById(R.id.title)).setText(title);

        // 年
        final WheelView wv_year = (WheelView) view.findViewById(R.id.year);
        wv_year.setAdapter(new NumericWheelAdapter(START_YEAR, END_YEAR));// 设置"年"的显示数据
        wv_year.setCyclic(true);// 可循环滚动
        wv_year.setLabel("年");// 添加文字
        final WheelView wv_month = (WheelView) view.findViewById(R.id.month);
        // 月
        wv_month.setAdapter(new NumericWheelAdapter(1, 12));
        wv_month.setCyclic(true);
        wv_month.setLabel("月");
        final WheelView wv_day = (WheelView) view.findViewById(R.id.day);
        // 日
        wv_day.setCyclic(true);
        // 判断大小月及是否闰年,用来确定"日"的数据
        if (list_big.contains(String.valueOf(month + 1))) {
            wv_day.setAdapter(new NumericWheelAdapter(1, 31));
        } else if (list_little.contains(String.valueOf(month + 1))) {
            wv_day.setAdapter(new NumericWheelAdapter(1, 30));
        } else {
            // 闰年
            if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
                wv_day.setAdapter(new NumericWheelAdapter(1, 29));
            else
                wv_day.setAdapter(new NumericWheelAdapter(1, 28));
        }
        wv_day.setLabel("日");

        String dateTv = source;
        // 初始化时显示的数据
        if (dateTv != null && dateTv.length() > 0) {
            String[] date = dateTv.split("-");
            wv_year.setCurrentItem(Integer.parseInt(date[0]) - START_YEAR);
            wv_month.setCurrentItem(Integer.parseInt(date[1]) - 1);
            wv_day.setCurrentItem(Integer.parseInt(date[2]) - 1);
        } else {
            wv_year.setCurrentItem(year - START_YEAR);
            wv_month.setCurrentItem(month);
            wv_day.setCurrentItem(day - 1);
        }

        // 添加"年"监听
        OnWheelChangedListener wheelListener_year = new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                int year_num = newValue + START_YEAR;
                // 判断大小月及是否闰年,用来确定"日"的数据
                if (list_big
                        .contains(String.valueOf(wv_month.getCurrentItem() + 1))) {
                    wv_day.setAdapter(new NumericWheelAdapter(1, 31));
                } else if (list_little.contains(String.valueOf(wv_month
                        .getCurrentItem() + 1))) {
                    wv_day.setAdapter(new NumericWheelAdapter(1, 30));
                } else {
                    if ((year_num % 4 == 0 && year_num % 100 != 0)
                            || year_num % 400 == 0) {
                        wv_day.setAdapter(new NumericWheelAdapter(1, 29));
                        wv_day.setCurrentItem(0);
                    } else {
                        wv_day.setAdapter(new NumericWheelAdapter(1, 28));
                        wv_day.setCurrentItem(0);
                    }

                }
            }
        };
        // 添加"月"监听
        OnWheelChangedListener wheelListener_month = new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                int month_num = newValue + 1;
                // 判断大小月及是否闰年,用来确定"日"的数据
                if (list_big.contains(String.valueOf(month_num))) {
                    wv_day.setAdapter(new NumericWheelAdapter(1, 31));
                    wv_day.setCurrentItem(0);
                } else if (list_little.contains(String.valueOf(month_num))) {
                    wv_day.setAdapter(new NumericWheelAdapter(1, 30));
                    wv_day.setCurrentItem(0);
                } else {
                    if (((wv_year.getCurrentItem() + START_YEAR) % 4 == 0 && (wv_year
                            .getCurrentItem() + START_YEAR) % 100 != 0)
                            || (wv_year.getCurrentItem() + START_YEAR) % 400 == 0) {
                        wv_day.setAdapter(new NumericWheelAdapter(1, 29));
                        wv_day.setCurrentItem(0);
                    } else {
                        wv_day.setAdapter(new NumericWheelAdapter(1, 28));
                        wv_day.setCurrentItem(0);
                    }

                }
            }
        };

        wv_year.addChangingListener(wheelListener_year);
        wv_month.addChangingListener(wheelListener_month);

        // 根据屏幕密度来指定选择器字体的大

        wv_year.TEXT_SIZE = wv_month.TEXT_SIZE = wv_day.TEXT_SIZE = (int) context.getResources().getDimension(
                R.dimen.neirong);

        Button btn_sure = (Button) view.findViewById(R.id.btn_datetime_sure);
        Button btn_cancel = (Button) view
                .findViewById(R.id.btn_datetime_cancel);
        // 确定
        btn_sure.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                // 如果是个数,则显示为"02"的样式

                String day = String.valueOf(wv_day.getCurrentItem() + 1);
                String year = String.valueOf(wv_year.getCurrentItem()
                        + START_YEAR);
                String month = String.valueOf(wv_month.getCurrentItem() + 1);
                if (month.length() < 2) {
                    month = 0 + month;
                }
                if (day.length() < 2) {
                    day = 0 + day;
                }
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HHmmss");
                String[] format = sdf.format(date).split("-");
                int year1 = Integer.parseInt(format[0]);
                int month1 = Integer.parseInt(format[1]);
                int day1 = Integer.parseInt(format[2]);
                int tv1_year = Integer.parseInt(year);
                int tv1_mouth = Integer.parseInt(month);
                int tv1_day = Integer.parseInt(day);
                if ((tv1_year * 371 + tv1_mouth * 31 + tv1_day) < (year1 * 371
                        + month1 * 31 + day1) && bl) {
                    newConfirmDialog(context, "提示", "必须晚于今日");
                } else if ((tv1_year * 371 + tv1_mouth * 31 + tv1_day) > (year1 * 371
                        + month1 * 31 + day1) && !bl) {
                    newConfirmDialog(context, "提示", "必须早于今日");
                } else {
                    String data = year + "-" + month + "-" + day;
                    if (observable != null) {
                        observable.set(data);
                    }
                    if (callback != null) {
                        callback.done(data);
                    }
                    if (liveData != null) {
                        liveData.postValue(data);
                    }
                    dialog.dismiss();
                }
            }
        });
        // 取消
        btn_cancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });
        // 设置dialog的布局,并显示
        setupDialogAndShow(context, dialog, view);
    }


    /**
     * <p>年月日时间对话框(年月日,可比较两个textview中的日期间隔</p>
     * <p>去除*标TextView,该操作应放在Callback中</>
     *
     * @param title
     */
    public static void showDatePicker2(Context context, final TextView tv, String title, final Callback callback) {
        final Dialog dialog = newDialog(context, null);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);

        // 添加大小月月份并将其转换为list,方便之后的判断
        String[] months_big = {"1", "3", "5", "7", "8", "10", "12"};
        String[] months_little = {"4", "6", "9", "11"};

        final List<String> list_big = Arrays.asList(months_big);
        final List<String> list_little = Arrays.asList(months_little);


        // 找到dialog的布局文件
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dlg_time, null);
        ((TextView) view.findViewById(R.id.title)).setText(title);

        // 年
        final WheelView wv_year = (WheelView) view.findViewById(R.id.year);
        wv_year.setAdapter(new NumericWheelAdapter(START_YEAR, END_YEAR));// 设置"年"的显示数据
        wv_year.setCyclic(true);// 可循环滚动
        wv_year.setLabel("年");// 添加文字
        final WheelView wv_month = (WheelView) view.findViewById(R.id.month);
        // 月
        wv_month.setAdapter(new NumericWheelAdapter(1, 12));
        wv_month.setCyclic(true);
        wv_month.setLabel("月");
        final WheelView wv_day = (WheelView) view.findViewById(R.id.day);
        // 日
        wv_day.setCyclic(true);
        // 判断大小月及是否闰年,用来确定"日"的数据
        if (list_big.contains(String.valueOf(month + 1))) {
            wv_day.setAdapter(new NumericWheelAdapter(1, 31));
        } else if (list_little.contains(String.valueOf(month + 1))) {
            wv_day.setAdapter(new NumericWheelAdapter(1, 30));
        } else {
            // 闰年
            if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
                wv_day.setAdapter(new NumericWheelAdapter(1, 29));
            else
                wv_day.setAdapter(new NumericWheelAdapter(1, 28));
        }
        wv_day.setLabel("日");

        String dateTv = tv.getText().toString();
        // 初始化时显示的数据
        if (dateTv != null && dateTv.length() > 0) {
            String[] date = dateTv.split("-");
            wv_year.setCurrentItem(Integer.parseInt(date[0]) - START_YEAR);
            wv_month.setCurrentItem(Integer.parseInt(date[1]) - 1);
            wv_day.setCurrentItem(Integer.parseInt(date[2]) - 1);
        } else {
            wv_year.setCurrentItem(year - START_YEAR);
            wv_month.setCurrentItem(month);
            wv_day.setCurrentItem(day - 1);
        }

        // 添加"年"监听
        OnWheelChangedListener wheelListener_year = new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                int year_num = newValue + START_YEAR;
                // 判断大小月及是否闰年,用来确定"日"的数据
                if (list_big
                        .contains(String.valueOf(wv_month.getCurrentItem() + 1))) {
                    wv_day.setAdapter(new NumericWheelAdapter(1, 31));
                } else if (list_little.contains(String.valueOf(wv_month
                        .getCurrentItem() + 1))) {
                    wv_day.setAdapter(new NumericWheelAdapter(1, 30));
                } else {
                    if ((year_num % 4 == 0 && year_num % 100 != 0)
                            || year_num % 400 == 0) {
                        wv_day.setAdapter(new NumericWheelAdapter(1, 29));
                        wv_day.setCurrentItem(0);
                    } else {
                        wv_day.setAdapter(new NumericWheelAdapter(1, 28));
                        wv_day.setCurrentItem(0);
                    }

                }
            }
        };
        // 添加"月"监听
        OnWheelChangedListener wheelListener_month = new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                int month_num = newValue + 1;
                // 判断大小月及是否闰年,用来确定"日"的数据
                if (list_big.contains(String.valueOf(month_num))) {
                    wv_day.setAdapter(new NumericWheelAdapter(1, 31));
                    wv_day.setCurrentItem(0);
                } else if (list_little.contains(String.valueOf(month_num))) {
                    wv_day.setAdapter(new NumericWheelAdapter(1, 30));
                    wv_day.setCurrentItem(0);
                } else {
                    if (((wv_year.getCurrentItem() + START_YEAR) % 4 == 0 && (wv_year
                            .getCurrentItem() + START_YEAR) % 100 != 0)
                            || (wv_year.getCurrentItem() + START_YEAR) % 400 == 0) {
                        wv_day.setAdapter(new NumericWheelAdapter(1, 29));
                        wv_day.setCurrentItem(0);
                    } else {
                        wv_day.setAdapter(new NumericWheelAdapter(1, 28));
                        wv_day.setCurrentItem(0);
                    }

                }
            }
        };

        wv_year.addChangingListener(wheelListener_year);
        wv_month.addChangingListener(wheelListener_month);

        // 根据屏幕密度来指定选择器字体的大

        wv_year.TEXT_SIZE = wv_month.TEXT_SIZE = wv_day.TEXT_SIZE = (int) context.getResources().getDimension(
                R.dimen.neirong);

        Button btn_sure = (Button) view.findViewById(R.id.btn_datetime_sure);
        Button btn_cancel = (Button) view
                .findViewById(R.id.btn_datetime_cancel);
        // 确定
        btn_sure.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                // 如果是个数,则显示为"02"的样式

                String day = String.valueOf(wv_day.getCurrentItem() + 1);
                int year = wv_year.getCurrentItem()
                        + START_YEAR;
                String month = String.valueOf(wv_month.getCurrentItem() + 1);
                if (month.length() < 2) {
                    month = 0 + month;
                }
                if (day.length() < 2) {
                    day = 0 + day;
                }
                String date = year + "-" + month + "-" + day;
                tv.setText(date);
                //选择合作开始日期后，合作截止日期年数自动+1
                if (!TextUtils.isEmpty(date)) {
                    callback.done((year + 1) + "-" + month + "-" + day);
                }
                dialog.dismiss();
            }
        });
        // 取消
        btn_cancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });
        // 设置dialog的布局,并显示
        setupDialogAndShow(context, dialog, view);
    }

    private static Dialog newDialog(Context context, @Nullable String title) {
        return newDialog(context, title, 0);
    }

    private static Dialog newDialog(Context context, @Nullable String title, int style) {
        Dialog dialog;
        if (style != 0) {
            dialog = new Dialog(context, style);
        } else {
            dialog = new Dialog(context);
        }
        if (title != null && !TextUtils.isEmpty(title)) {

            dialog.setTitle(title);
        }
        return dialog;
    }


    private static void setupDialogAndShow(@NonNull Context context, @NonNull Dialog dialog, @NonNull View view) {
        dialog.setContentView(view);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public interface Callback {
        void done(String data);
    }
}
