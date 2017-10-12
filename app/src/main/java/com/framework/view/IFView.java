package com.framework.view;

import android.content.Context;
import android.support.v7.widget.AppCompatCheckedTextView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

import com.framework.app.MainApplication;
import com.framework.utils.ArrayUtils;

/**
 * Created by chenxi.cui on 2017/8/14.
 */

public class IFView extends AppCompatCheckedTextView {
    private CharSequence[] texts;
    private OnCheckedChangeListener onCheckedChangeListener;

    public IFView(Context context) {
        this(context, (AttributeSet) null, 0);
    }

    public IFView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IFView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setGravity(Gravity.CENTER_VERTICAL);
        if (isInEditMode()) {
            return;
        }
        this.setTypeface(MainApplication.getIconFont());
    }


    /**
     * [0] 默认
     * [1] 选中
     *
     * @param texts
     */
    public void setText(CharSequence[] texts) {
        this.texts = texts;
        if (!ArrayUtils.isEmpty(texts) && texts.length == 1) {
            super.setText(texts[0]);
        }
    }

    /**
     * @param checked
     */
    @Override
    public void setChecked(boolean checked) {
        if (onCheckedChangeListener != null) {
            if (this.isChecked() != checked) {
                onCheckedChangeListener.onCheckedChanged(this, checked);
            }
        }
        super.setChecked(checked);
        if (!ArrayUtils.isEmpty(texts)) {
            if (!this.isChecked()) {
                setText(texts[0]);
            } else {
                if (texts.length > 1) {
                    setText(texts[1]);
                } else {
                    setText(texts[0]);
                }
            }
        }
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.onCheckedChangeListener = onCheckedChangeListener;
    }

    public static interface OnCheckedChangeListener {
        void onCheckedChanged(TextView textView, boolean isChecked);
    }

}
