package com.bz.poverty;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.framework.view.tab.TabItem;
import com.framework.view.tab.TabLayout;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenxi.cui on 2017/9/30.
 */

public class MainActivity1 extends MainTabActivity implements OnMenuItemClickListener {
    TabLayout tlTab;
    private ContextMenuDialogFragment mMenuDialogFragment;
    private View imageMenu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tab1);
        tlTab = (TabLayout) findViewById(R.id.tl_tab);
        imageMenu = findViewById(R.id.image_menu);
        imageMenu.setOnClickListener(this);

        tabLayout = tlTab;
        addTab("党建地图", MapFragment.class, myBundle, R.drawable.icon_mark);
        //十八里镇
        addTab("基本情况", WebFragment.class, getBundle("基本情况","http://dj.qfant.com/index.php/App/Index/baseinfo/id/28"), R.drawable.icon_online);
        addTab("标准化建设", WebFragment.class, getBundle("标准化建设","http://dj.qfant.com/index.php/App/Index/news/catid/479"), R.drawable.icon_feature);
        addTab("工作动态", WebFragment.class, getBundle("工作动态","http://dj.qfant.com/index.php/App/Index/news/catid/623"), R.drawable.icon_digitize);
        addTab("组织生活", WebFragment.class, getBundle("组织生活","http://dj.qfant.com/index.php/App/Index/grouplife/catid/72"), R.drawable.icon_twolearn);
        addTab("党员信息", WebFragment.class, getBundle("党员信息","http://dj.qfant.com/index.php/App/Index/openinfo/id/49"), R.drawable.icon_userinfo);
        addTab("数字化阵地", DigitFragment.class, getBundle("数字化阵地","http://dj.qfant.com/index.php/App/Index/cameras/id/28"), R.drawable.iocn_base);
        addTab("组织关系转接", WebFragment.class, getBundle("组织关系转接","http://dj.qfant.com/index.php/App/Index/groupindex"), R.drawable.icon_connection);
        addTab("微信党支部", WebFragment.class, getBundle("微信党支部","http://dj.qfant.com/index.php/App/Index/dangyuan"), R.drawable.iocn_signin);
        addTab("个人中心", WebFragment.class, getBundle("个人中心","http://dj.qfant.com/index.php/App/User/userinfo"), R.drawable.icon_usercenter);
        onPostCreate();
        initMenu();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.equals(imageMenu)) {
            if (fragmentManager.findFragmentByTag(ContextMenuDialogFragment.TAG) == null) {
                mMenuDialogFragment.show(fragmentManager, ContextMenuDialogFragment.TAG);
            }
        }
    }

    private void initMenu() {

        MenuObject close = new MenuObject();
        close.setResource(R.drawable.icn_close);
        List<MenuObject> menuObjects = new ArrayList<>();
        menuObjects.add(close);
        for (TabItem item : mTabs) {
            MenuObject menuObject = new MenuObject(item.text);
            menuObject.setResource(item.icon);
            menuObject.setMenuTextAppearanceStyle(R.style.TextViewStyle);
            menuObjects.add(menuObject);
        }
        MenuParams menuParams = new MenuParams();
        menuParams.setActionBarSize((int) getResources().getDimension(R.dimen.tool_bar_height));
        menuParams.setMenuObjects(menuObjects);
        menuParams.setClosableOutside(true);
        // set other settings to meet your needs
        mMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams);
        mMenuDialogFragment.setItemClickListener(this);
    }


    @Override
    public void onBackPressed() {
        if (mMenuDialogFragment != null && mMenuDialogFragment.isAdded()) {
            mMenuDialogFragment.dismiss();
        } else {
            finish();
        }
    }

    @Override
    public void onMenuItemClick(View clickedView, int position) {
        if (position > 0) {
            tabLayout.setCurrentTab(position - 1);
        }
    }

    @Override
    public void onTabClick(TabItem tabItem) {

        if ("微信党支部".equals(tabItem.text)) {
            try {
                showToast("打开微信");
                Intent intent = new Intent();
                ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
                intent.setAction(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setComponent(cmp);
                startActivity(intent);
            } catch (Exception e) {
                showToast("未安装微信");
            }

        } else {
            super.onTabClick(tabItem);
        }
    }

    private Bundle getBundle(String title, String url) {
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        bundle.putString("title", title);
        return bundle;
    }
}
