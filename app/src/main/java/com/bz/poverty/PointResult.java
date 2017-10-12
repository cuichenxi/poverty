package com.bz.poverty;

import com.framework.domain.response.BaseResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenxi.cui on 2017/9/30.
 */

public class PointResult extends BaseResult {
    public List<PointItem> data;


    public static class PointItem implements BaseData {
        public String baseinfo;
        public List<PointItem> children = new ArrayList<>();
        public String name;
        public String id;
        public String longitude;
        public String latitude;
        public double lon;
        public double lat;

        public void setLatitude(String latitude) {
            this.lon = Double.parseDouble(latitude);
        }

        public void setLongitude(String longitude) {
            this.lat =  Double.parseDouble(longitude);
        }
    }
}
