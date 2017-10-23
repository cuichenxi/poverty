package com.bz.poverty;

import com.framework.domain.response.BaseResult;

/**
 * Created by chenxi.cui on 2017/10/23.
 */
public class EquipResult extends BaseResult {
    public EquipData data;

    public static class EquipData implements BaseData {
        public String name;
        public String rtmp;
    }
}
