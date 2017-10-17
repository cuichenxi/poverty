package com.framework.view.tab;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mapapi.map.Text;
import com.bz.poverty.R;
import com.framework.view.IFView;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by yx on 16/4/3.
 */

public class TabView extends LinearLayout implements View.OnClickListener {

    ImageView icon;
    TextView text;
    View view_line;
    private TabItem tabItem;

    public TabView(Context context) {
        super(context);
        initView(context);
    }

    public TabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public TabView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    private void initView(Context context) {
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
//        setBackgroundResource(R.drawable.pub_tabview_bg_selector);
        LinearLayout.inflate(context, R.layout.pub_tabview_layout, this);
        icon = (ImageView) findViewById(R.id.icon);
        view_line = findViewById(R.id.view_line);
        text = (TextView) findViewById(R.id.text);
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        if (!selected) {
            view_line.setBackgroundResource(R.color.transparent);
            text.setTextColor(getResources().getColor(R.color.common_color_white));
        } else {
            view_line.setBackgroundResource(R.color.pub_color_red);
            text.setTextColor(getResources().getColor(R.color.pub_color_red));
        }
    }

    public void initData(TabItem tabItem) {
        this.tabItem = tabItem;
        Log.v("TabView", tabItem.text);
        text.setText(tabItem.text);
        icon.setImageResource(tabItem.icon);
    }

    @Override
    public void onClick(View v) {

    }

    public void onDataChanged(int badgeCount) {

    }
}
