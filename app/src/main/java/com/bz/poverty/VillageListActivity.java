package com.bz.poverty;

import android.app.Service;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.framework.activity.BaseActivity;
import com.framework.adapter.utils.QSimpleAdapter;
import com.framework.domain.param.BaseParam;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.framework.utils.inject.From;

/**
 * Created by chenxi.cui on 2018/1/3.
 */

public class VillageListActivity extends BaseActivity {
    @From(R.id.list)
    ListView listView;
    private String villageId;
    private VillageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.village_list_activity);
        villageId = myBundle.getString("villageId");
        String title = myBundle.getString("title");
        setTitleBar(title, true);
        VillageParam param = new VillageParam();
        param.villageId = villageId;
        Request.startRequest(param, ServiceMap.webequipments, mHandler, Request.RequestFeature.BLOCK);
        adapter = new VillageAdapter(this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        super.onItemClick(adapterView, view, i, l);
        QuipmentResult.QuipmentData item = (QuipmentResult.QuipmentData) adapterView.getAdapter().getItem(i);
        Bundle bundle = new Bundle();
        bundle.putString("rtmp", item.rtmp);
        qStartActivity(PlayerActivity.class, bundle);
    }

    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (ServiceMap.webequipments == param.key) {
            QuipmentResult result = (QuipmentResult) param.result;
            if (result.bstatus.code == 0) {
                setTitleBar(result.description, true);
                adapter.setData(result.data);
            }
        }
        return super.onMsgSearchComplete(param);
    }

    public static class VillageAdapter extends QSimpleAdapter<QuipmentResult.QuipmentData> {

        public VillageAdapter(Context context) {
            super(context);
        }

        @Override
        protected View newView(Context context, ViewGroup parent) {
            return inflate(R.layout.village_list_item, null, false);
        }

        @Override
        protected void bindView(View view, Context context, QuipmentResult.QuipmentData item, int position) {
            TextView text = (TextView) view.findViewById(R.id.text);
            text.setText(item.name);
        }
    }

    public static class VillageParam extends BaseParam {

        public String villageId;
    }
}
