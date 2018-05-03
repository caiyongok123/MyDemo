package com.example.cy.myapplication.widget.view;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.cy.myapplication.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by cy on 2016/7/21.
 * 简单弹框
 */
@SuppressLint("ValidFragment")
public class SimpleDialog extends DialogFragment {

    @Bind(R.id.iv)
    ImageView iv;
    @Bind(R.id.et)
    EditText et;
    @Bind(R.id.right_cancel_btn)
    ImageView rightCancelBtn;

    public interface OnConfirmListener {
        void onConfirm(DialogInterface dialog);
    }

    public interface OnMiddleButtonListener {
        void onMiddleClick(DialogInterface dialog);
    }

    public interface OnEditConfirmListener {
        void OnEditConfirm(DialogInterface dialog, String text);
    }

    /**
     * view初始化完成回调
     */
    public interface OnViewInitListener {
        void initFinish(View view);
    }


    View view;

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_message)
    TextView tvMessage;
    @Bind(R.id.btn_middle)
    Button btnMiddle;
    @Bind(R.id.btn_cancel)
    Button btnCancel;
    @Bind(R.id.btn_confirm)
    Button btnConfirm;


    int drawableId;
    int colorId;
    int confirmButtonColorId;
    int minddleButtonColorId;
    int cancelButtonColorId;

    CharSequence titleStr, messageStr, cancelButtonStr, btnMiddleButtonStr, confirmButtonStr;

    int titleColor, messageColor, buttonTextColor;
    boolean editable = false;
    int maxTextNum = 0;
    boolean hasRightCancel;
    CharSequence hint;

    OnDismissListener onDismissListener;
    OnCancelListener onCancelListener;
    OnConfirmListener onConfirmListener;
    OnEditConfirmListener onEditConfirmListener;
    OnMiddleButtonListener onMiddleButtonListener;
    OnViewInitListener onViewInitListener;

    FragmentActivity activity;


    public SimpleDialog(FragmentActivity activity) {
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawableResource(R.color.transparent);
        //设置偏向操作
        //getDialog().getWindow().setGravity(Gravity.TOP);
        view = inflater.inflate(R.layout.dialog_simple_result, container);
        ButterKnife.bind(this, view);
        setResource();

        if(onViewInitListener != null){
            onViewInitListener.initFinish(view);
        }

        return view;
    }

    //全屏宽度操作
/*
    @Override
    public void onStart() {
        super.onStart();
        ViewGroup.LayoutParams lp= view.getLayoutParams();
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics( dm );
        lp.width=dm.widthPixels;
        view.setLayoutParams(lp);
    }*/

    private void setResource() {
        if (!TextUtils.isEmpty(titleStr)) {
            tvTitle.setText(titleStr);
            tvTitle.setVisibility(View.VISIBLE);
        } else {
            tvTitle.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(messageStr)) {
            tvMessage.setText(messageStr);
            tvMessage.setVisibility(View.VISIBLE);
        } else {
            tvMessage.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(cancelButtonStr)) {
            btnCancel.setVisibility(View.VISIBLE);
            btnCancel.setText(cancelButtonStr);
        } else {
            btnCancel.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(btnMiddleButtonStr)) {
            btnMiddle.setVisibility(View.VISIBLE);
            btnMiddle.setText(btnMiddleButtonStr);
        } else {
            btnMiddle.setVisibility(View.GONE);
        }


        if (!TextUtils.isEmpty(confirmButtonStr))
            btnConfirm.setText(confirmButtonStr);
        if (colorId != 0) {
            try {
                btnCancel.setTextColor(colorId);
                btnConfirm.setTextColor(colorId);
            } catch (Exception e) {
            }
        }

        if (confirmButtonColorId != 0) {
            btnConfirm.setTextColor(confirmButtonColorId);
        }
        if (cancelButtonColorId != 0) {
            btnCancel.setTextColor(cancelButtonColorId);
        }
        if (minddleButtonColorId != 0) {
            btnMiddle.setTextColor(minddleButtonColorId);
        }

        if (titleColor != 0)
            tvTitle.setTextColor(titleColor);
        if (messageColor != 0)
            tvMessage.setTextColor(messageColor);
        if (buttonTextColor != 0) {
            btnCancel.setTextColor(buttonTextColor);
            btnConfirm.setTextColor(buttonTextColor);
        }
        if (drawableId != 0) {
            iv.setImageResource(drawableId);
            iv.setVisibility(View.VISIBLE);
        }

        if (editable) {
            et.setHint(hint);
            et.setVisibility(View.VISIBLE);
        }
        if (maxTextNum > 0) {
            et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxTextNum)});//限制最大输入maxTextNum字符
        }

        if (hasRightCancel) {
            rightCancelBtn.setVisibility(View.VISIBLE);
            rightCancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        } else {
            rightCancelBtn.setVisibility(View.GONE);
        }

    }

    @Override
    public void onCancel(DialogInterface dialog) {
        if (onCancelListener == null)
            super.onCancel(dialog);
        else
            onCancelListener.onCancel(dialog);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {

        if (onDismissListener == null)
            super.onDismiss(dialog);
        else
            onDismissListener.onDismiss(dialog);
    }

    public SimpleDialog setTitleText(CharSequence text) {
        titleStr = text;
        return this;
    }

    public SimpleDialog setTitleColor(int ColorId) {
        tvTitle.setTextColor(ColorId);
        return this;
    }

    public SimpleDialog setMessageText(CharSequence text) {
        messageStr = text;
        return this;
    }

    public SimpleDialog setMessageColor(int ColorId) {
        messageColor = ColorId;
        return this;
    }

    public SimpleDialog setConfirmButtonText(CharSequence text) {
        confirmButtonStr = text;
        return this;
    }

    public SimpleDialog setCancelButtonText(CharSequence text) {
        cancelButtonStr = text;
        return this;
    }

    public SimpleDialog setMiddleButtonStr(CharSequence text) {
        btnMiddleButtonStr = text;
        return this;
    }


    public SimpleDialog setButtonTextColor(int colorId) {
        this.colorId = colorId;
        return this;
    }

    public SimpleDialog setConfirmButtonColor(int colorId) {
        this.confirmButtonColorId = colorId;
        return this;
    }

    public SimpleDialog setCancelButtonColor(int colorId) {
        this.cancelButtonColorId = colorId;
        return this;
    }

    public SimpleDialog setMinddleButtonColorId(int minddleButtonColorId) {
        this.minddleButtonColorId = minddleButtonColorId;
        return this;
    }

    public SimpleDialog setDrawableId(int drawableId) {
        this.drawableId = drawableId;
        return this;
    }

    public SimpleDialog setEditable(CharSequence hint) {
        this.editable = true;
        this.hint = hint;
        return this;
    }

    public SimpleDialog setEditMaxTextNum(int size) {
        this.maxTextNum = size;
        return this;
    }

    public SimpleDialog setRightCancel(boolean hasRightCancel) {
        this.hasRightCancel = hasRightCancel;
        return this;
    }

    public SimpleDialog setOnConfirmListener(OnConfirmListener onConfirmListener) {
        this.onConfirmListener = onConfirmListener;
        return this;
    }

    public SimpleDialog setOnEditConfirmListener(OnEditConfirmListener onEditConfirmListener) {
        this.onEditConfirmListener = onEditConfirmListener;
        return this;
    }

    public SimpleDialog setOnCancelListener(OnCancelListener onCancelListener) {
        this.onCancelListener = onCancelListener;
        return this;
    }

    public SimpleDialog setOnMiddleButtonListener(OnMiddleButtonListener onMiddleButtonListener) {
        this.onMiddleButtonListener = onMiddleButtonListener;
        return this;
    }

    public SimpleDialog setOnDismissListener(OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
        return this;
    }

    public SimpleDialog setOnViewInitListener(OnViewInitListener onViewInitListener) {
        this.onViewInitListener = onViewInitListener;
        return this;
    }

    @Nullable
    @Override
    public View getView() {
        return view;
    }

    public void show() {
        show(activity.getSupportFragmentManager(), activity.getClass().getName() + "");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.btn_cancel, R.id.btn_middle, R.id.btn_confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                getDialog().cancel();
                break;
            case R.id.btn_middle:
                if (onMiddleButtonListener != null) {
                    onMiddleButtonListener.onMiddleClick(getDialog());
                }
                break;
            case R.id.btn_confirm:
                if (onEditConfirmListener != null) {
                    onEditConfirmListener.OnEditConfirm(getDialog(), et.getText().toString());
                } else if (onConfirmListener != null) {
                    onConfirmListener.onConfirm(getDialog());
                } else {
                    dismiss();
                }

                break;
        }
    }
}
