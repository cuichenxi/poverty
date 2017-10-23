package com.bz.poverty;

import com.framework.domain.response.BaseResult;

import java.util.List;

/**
 * Created by chenxi.cui on 2017/10/23.
 */
public class EquipmentResult extends BaseResult {
    public List<EquipmentData> data;

    public static class EquipmentData implements BaseData {
        public String name;
        public String url;

    }
}
