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
    private EquipmentResult result;
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
                if (item instanceof EquipmentResult.EquipmentData) {
                    EquipmentResult.EquipmentData data = (EquipmentResult.EquipmentData) item;
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("item", data);
                    qStartActivity(PlayerActivity.class, bundle);
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
                result = (EquipmentResult) param.result;
                mAdapter.setData(result.data);
            }
        }
        return super.onMsgSearchComplete(param);
    }

    public static class DigitParam extends BaseParam {
        public int townid = 24;
    }

    private class MAdapter extends BaseAdapter {

        private List<EquipmentResult.EquipmentData> data;

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
            EquipmentResult.EquipmentData equipmentData = data.get(i);
            if (equipmentData != null) {
                textView.setText(equipmentData.name);
            }else {
                textView.setText("");
            }

            return inflate;
        }

        public void setData(List<EquipmentResult.EquipmentData> data) {
            this.data = data;
            notifyDataSetChanged();
        }
    }
}
