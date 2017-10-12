package com.bz.poverty;

import com.framework.domain.response.BaseResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenxi.cui on 2017/9/30.
 */

public class UrlResult extends BaseResult {
    public List<PointItem> data;


    public static class PointItem implements BaseData {
    }
}
