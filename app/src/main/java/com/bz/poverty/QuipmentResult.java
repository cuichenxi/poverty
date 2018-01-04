package com.bz.poverty;

import com.framework.domain.response.BaseResult;

import java.util.List;

/**
 * Created by chenxi.cui on 2018/1/3.
 */

public class QuipmentResult extends BaseResult {

    public List<QuipmentData> data;
    public String description;

    public static class QuipmentData implements BaseData {
        public String name;
        public String rtmp;
    }
}
