package com.bz.poverty;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;
import com.bz.poverty.PointResult.PointItem;
import com.framework.activity.BaseFragment;
import com.framework.domain.param.BaseParam;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.framework.utils.ArrayUtils;
import com.framework.utils.BitmapHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenxi.cui on 2017/9/30.
 */

public class MapFragment extends BaseFragment implements BaiduMap.OnMarkerClickListener {
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private MapStatus mMapStatus;
//    LatLng centerPoint = new LatLng(33.995929, 115.676379);//古井镇
//    LatLng centerPoint = new LatLng(34.009637, 115.78515);//芦庙镇
//    LatLng centerPoint = new LatLng(33.853446, 115.754576);//经开区
//    LatLng centerPoint = new LatLng(33.687446, 115.913639);//经开区
//    LatLng centerPoint = new LatLng(33.905994, 115.807437);//汤陵社区
//    LatLng centerPoint = new LatLng(33.804226, 116.023224);//观堂镇
//    LatLng centerPoint = new LatLng(33.982975, 115.616583);//牛集镇镇
//    LatLng centerPoint = new LatLng(33.876154, 115.672725);//十八里镇
//    LatLng centerPoint = new LatLng(33.875315, 115.795716);////薛阁街道
//    LatLng centerPoint = new LatLng(33.940715, 115.786694);////华佗镇
    LatLng centerPoint = new LatLng(33.931317,115.937223);////张店乡
    int pZoom = 14;
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


    private void recoverStatus(String name, boolean hasBack, LatLng cenpt, int zoom) {
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
        final PointItem item = (PointItem) extraInfo.getSerializable("item");
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
        requstVillageInfo(item.id);
        return false;
    }

    private void showInfoWindow( final PointItem item) {
        View layout = LinearLayout.inflate(getContext(), R.layout.map_marker_window_info, null);
        TextView tvInfo = (TextView) layout.findViewById(R.id.tv_info);
        TextView tvLink = (TextView) layout.findViewById(R.id.tv_link);
        TextView tvTitle = (TextView) layout.findViewById(R.id.tv_title);
        TextView tvClose = (TextView) layout.findViewById(R.id.tv_close);
        TextView tvLink1 = (TextView) layout.findViewById(R.id.tv_link1);
        TextView tvLink2 = (TextView) layout.findViewById(R.id.tv_link2);
        TextView tvLink3 = (TextView) layout.findViewById(R.id.tv_link3);
        tvLink.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线
        tvLink1.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线
        tvLink2.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线
        tvLink3.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线

        if(item.type==0){
            tvLink.setText(R.string.map_baseinfo_village);
            tvLink1.setText(R.string.map_appearance_village);
        }else if(item.type==1){
            tvLink.setText(R.string.map_baseinfo_community);
            tvLink1.setText(R.string.map_appearance_community);
        }else {
            tvLink.setText(R.string.map_baseinfo_company);
            tvLink1.setText(R.string.map_appearance_company);
        }
        tvTitle.setText(item.name);
        StringBuffer sb = new StringBuffer();
        sb.append("主管单位: ");
        sb.append(item.manager);
        sb.append("\n");
        sb.append("\n");
        sb.append("党建负责人:");
        sb.append("  ");
        sb.append(item.contact);
        sb.append("  ");
        sb.append(item.tel);
        tvInfo.setText(sb.toString());
        tvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBaiduMap.hideInfoWindow();
            }
        });
//        tvLink.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final EditText et = new EditText(getContext());
//                new AlertDialog.Builder(getContext(), R.style.list_dialog_style)
//                        .setTitle("请输入您想要咨询或预约办理的事项及您的联系方式")
//                        .setView(et)
//                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                String input = et.getText().toString();
//                                MapParam mapParam = new MapParam();
//                                if (TextUtils.isEmpty(input)) {
//                                    showToast("请输入您想要咨询或预约办理的事项及您的联系方式");
//                                    return;
//                                }
//                                mapParam.content = input;
//                                mapParam.villageid = item.id;
//                                Request.startRequest(mapParam, ServiceMap.consult, mHandler, Request.RequestFeature.BLOCK);
//                            }
//                        })
//                        .setNegativeButton("取消", null)
//                        .show();
//            }
//        });
        tvLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("title", item.name);
                bundle.putString("url", "http://dj.qfant.com/index.php/App/Index/village/type/1/id/" + item.id);
                qStartActivity(WebActivity.class, bundle);
            }
        });
        tvLink1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("title", item.name);
                bundle.putString("url", "http://dj.qfant.com/index.php/App/Index/village/type/2/id/" + item.id);
                qStartActivity(WebActivity.class, bundle);
            }
        });
        tvLink2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("title", item.name);

                bundle.putString("url", "http://dj.qfant.com/index.php/App/Index/village/type/3/id/" + item.id);
                qStartActivity(WebActivity.class, bundle);
            }
        });
        tvLink3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("title", item.name);
                bundle.putString("url", "http://dj.qfant.com/index.php/App/Index/village/type/4/id/" + item.id);
                qStartActivity(WebActivity.class, bundle);
            }
        });
        LatLng point = new LatLng(item.lon, item.lat);
        InfoWindow mInfoWindow = new InfoWindow(layout, point, BitmapHelper.dip2px(getContext(), -100));
        mBaiduMap.showInfoWindow(mInfoWindow);
    }

    public static class VillageInfoParam extends BaseParam {
        //    villageid content
        public String id;
    }

    private void requstVillageInfo(String id) {
        VillageInfoParam param = new VillageInfoParam();
        param.id = id;
        Request.startRequest(param, ServiceMap.villageInfo, mHandler, Request.RequestFeature.BLOCK);
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
            addPloygo(item.boundary);
        }
    }

    private void addPloygo(List<PointItem> ps) {
        if (ArrayUtils.isEmpty(ps) || ps.size() <= 2) {
            return;
        }
        List<LatLng> pts = new ArrayList<LatLng>();
        for (PointItem item : ps) {
            LatLng point = new LatLng(item.lon, item.lat);
            pts.add(point);
        }
//构建用户绘制多边形的Option对象
        OverlayOptions polygonOption = new PolygonOptions()
                .points(pts)
                .stroke(new Stroke(5, 0xFFB42031))
                .fillColor(0x00ffffff);
//在地图上添加多边形Option，用于显示
        mBaiduMap.addOverlay(polygonOption);
    }

    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (ServiceMap.towns == param.key) {
            PointResult result = (PointResult) param.result;
            pointItems = result.data;
            isChild = false;
            addOvers(pointItems);
        }else if (ServiceMap.villageInfo == param.key) {
            VillageInfoResult result = (VillageInfoResult) param.result;
            showInfoWindow(result.data);
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
