package com.bz.poverty;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.framework.activity.BaseFragment;
import com.framework.domain.param.BaseParam;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;

import java.util.List;

/**
 * Created by chenxi.cui on 2017/9/30.
 */
public class DigitFragment extends BaseFragment {

    private GridView gridView;
    private EquipmentsResult result;
    private MAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return onCreateViewWithTitleBar(inflater, container, R.layout.fragemtn_digit);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String url = myBundle.getString("url");
        final String title = myBundle.getString("title");
        setTitleBar(title, false);
        gridView = (GridView) getView().findViewById(R.id.gridview);
        mAdapter = new MAdapter();
        gridView.setAdapter(mAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Object item = adapterView.getAdapter().getItem(i);
                if (item instanceof EquipmentsResult.EquipmentData) {
                    EquipmentsResult.EquipmentData data = (EquipmentsResult.EquipmentData) item;
                    UrlParam urlParam = new UrlParam();
                    urlParam.id = data.id;
                    Request.startRequest(urlParam, ServiceMap.equipment, mHandler, Request.RequestFeature.BLOCK);
                }
            }
        });
        Request.startRequest(new DigitParam(), ServiceMap.equipments, mHandler);
    }

    @Override
    public void onResume() {
        Log.v("digit", "onResume");
        super.onResume();
    }

    @Override
    protected void onShow() {
        Request.startRequest(new DigitParam(), ServiceMap.equipments, mHandler);
        Log.v("digit", "onShow");
        super.onShow();
    }

    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (param.key == ServiceMap.equipments) {
            if (param.result.bstatus.code == 0) {
                result = (EquipmentsResult) param.result;
                mAdapter.setData(result.data);
            }
        } else if (param.key == ServiceMap.equipment) {
            if (param.result.bstatus.code == 0) {
                EquipResult equipResult = (EquipResult) param.result;
                Bundle bundle = new Bundle();
                bundle.putSerializable("item", equipResult.data);
                qStartActivity(PlayerActivity.class, bundle);
            } else {
                showToast(param.result.bstatus.des);
            }


        }
        return super.onMsgSearchComplete(param);
    }

    public static class DigitParam extends BaseParam {
//        public int townid = 6;//古井
//        public int townid = 17;//芦庙镇
//        public int townid = 14;//观堂镇
//        public int townid = 24;//汤陵社区
//        public int townid = 2;//大杨镇
//        public int townid = 12;//牛集镇
//        public int townid = 28;//十八里
//        public int townid = 25;//薛阁街道
//        public int townid = 1;//华佗镇
//        public int townid = 21;//张店乡
        public int townid = 30;//西阳镇
    }

    public static class UrlParam extends BaseParam {
//        public int id = 6;//古井
//        public int id = 17;//芦庙镇
//        public int id = 14;//观堂镇
//        public int id = 24;//汤陵社区
//        public int id = 2;//大杨镇
//        public int id = 12;//牛集镇
//        public int id = 28;//十八里
//        public int id = 25;//薛阁街道
//        public int townid = 1;//华佗镇
//        public int id = 21;//张店乡
        public int id = 30;//西阳镇
    }

    private class MAdapter extends BaseAdapter {

        private List<EquipmentsResult.EquipmentData> data;

        @Override
        public int getCount() {
            return data == null ? 0 : data.size();
        }

        @Override
        public Object getItem(int i) {
            return data.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View inflate = LinearLayout.inflate(getContext(), R.layout.digit_item, null);
            TextView textView = (TextView) inflate.findViewById(R.id.text_title);
            EquipmentsResult.EquipmentData equipmentData = data.get(i);
            if (equipmentData != null) {
                textView.setText(equipmentData.name);
            } else {
                textView.setText("");
            }

            return inflate;
        }

        public void setData(List<EquipmentsResult.EquipmentData> data) {
            this.data = data;
            notifyDataSetChanged();
        }
    }
}
