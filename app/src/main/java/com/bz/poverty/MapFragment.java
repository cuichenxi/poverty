package com.bz.poverty;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.bz.poverty.PointResult.PointItem;
import com.framework.activity.BaseFragment;
import com.framework.domain.param.BaseParam;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.framework.utils.BitmapHelper;

import java.util.List;

/**
 * Created by chenxi.cui on 2017/9/30.
 */

public class MapFragment extends BaseFragment  implements BaiduMap.OnMarkerClickListener {
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private MapStatus mMapStatus;
    LatLng centerPoint = new LatLng(33.747383, 115.785038);
    int pZoom = 12;
    int cZoom = 15;
    boolean isChild;
    private List<PointItem> pointItems;
    public String name = "谯城区";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SDKInitializer.initialize(getActivity().getApplicationContext());
        return onCreateViewWithTitleBar(inflater, container, R.layout.fragement_map);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mMapView = (MapView) getView().findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setOnMarkerClickListener(this);
        titleBarClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view != null && view.getId() == R.id.title_left_btn) {
                    recoverStatus(name, false, centerPoint, pZoom);
                    isChild = false;
                    addOvers(pointItems);
                }
            }
        };
        recoverStatus(name, false, centerPoint, pZoom);
        Request.startRequest(new BaseParam(), ServiceMap.towns, mHandler);
    }


    private void recoverStatus(String name ,boolean hasBack,LatLng cenpt,int zoom) {
        setTitleBar(name, hasBack);
//        LatLng cenpt = new LatLng(33.850643, 115.785038);
        //定义地图状态
        mMapStatus = new MapStatus.Builder()
                .target(cenpt)
                .zoom(zoom)
                .build();
        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        mBaiduMap.setMapStatus(mMapStatusUpdate);
    }


    @Override
    public boolean onMarkerClick(Marker marker) {

        Bundle extraInfo = marker.getExtraInfo();
        if (extraInfo == null) {
            return false;
        }
        PointItem item = (PointItem) extraInfo.getSerializable("item");
        if (!isChild) {
            LatLng point = new LatLng(item.lon, item.lat);
            recoverStatus(item.name, true, point, cZoom);
            isChild = true;
            addOvers(item.children);
            return false;
        }
        if (item == null) {
            return false;
        }
        LinearLayout layout = new LinearLayout(getContext());
        layout.setBackgroundResource(R.drawable.hotel_map_marker_hotel_bg);
        layout.setPadding(BitmapHelper.dip2px(getContext(), 8), BitmapHelper.dip2px(getContext(), 8), BitmapHelper.dip2px(getContext(), 8), BitmapHelper.dip2px(getContext(), 8));
        TextView textView = new TextView(getContext());
        layout.addView(textView);
        textView.setText(item.baseinfo);
        textView.setTextColor(getResources().getColor(R.color.pub_color_black));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 28);
        InfoWindow mInfoWindow = new InfoWindow(layout, marker.getPosition(), BitmapHelper.dip2px(getContext(),-100));
        mBaiduMap.showInfoWindow(mInfoWindow);
        return false;
    }

    private void addOvers(List<PointItem> pointItems) {
        if (pointItems == null) {
            return;
        }
        mBaiduMap.clear();
//        List<OverlayOptions> ls = new ArrayList<>();
        for (PointItem item : pointItems) {
            //定义Maker坐标点
            Bundle bundle = new Bundle();
            bundle.putSerializable("item", item);
            LatLng point = new LatLng(item.lon, item.lat);
            View inflate = LayoutInflater.from(getContext()).inflate(R.layout.map_text_option, null);
            TextView text = (TextView) inflate.findViewById(R.id.text);
//            ImageView imageView = (ImageView) inflate.findViewById(R.id.image);
            text.setText(item.name);
//            if (!isChild) {
//                text.setVisibility(View.GONE);
//            }else {
                text.setVisibility(View.VISIBLE);
//            }
//构建Marker图标
            BitmapDescriptor bitmap = BitmapDescriptorFactory
                    .fromView(inflate);
//构建MarkerOption，用于在地图上添加Marker
            OverlayOptions option = new MarkerOptions()
                    .position(point)
                    .extraInfo(bundle)
                    .icon(bitmap);
            mBaiduMap.addOverlay(option);
        }
//在地图上添加Marker，并显示
//        mBaiduMap.addOverlays(ls);
    }
    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (ServiceMap.towns == param.key) {
            PointResult result = (PointResult) param.result;
            pointItems = result.data;
            isChild = false;
            addOvers(pointItems);
        }
        return super.onMsgSearchComplete(param);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    protected void onShow() {
        mMapView.onResume();
        Request.startRequest(new BaseParam(), ServiceMap.towns, mHandler);
        super.onShow();
    }

    @Override
    protected void onHide() {
        super.onHide();
        mMapView.onPause();
    }


}
