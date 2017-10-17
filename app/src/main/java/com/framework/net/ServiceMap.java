package com.framework.net;


import com.bz.poverty.PointResult;
import com.bz.poverty.WeatherResult;
import com.framework.domain.response.BaseResult;
import com.framework.utils.Enums;

/**
 * @author zexu
 * //    组织关系转接http://dj.qfant.com/index.php/App/Index/zuzhi
//    数字化阵地http://dj.qfant.com/index.php/App/Index/camera
//    党建资讯http://dj.qfant.com/index.php/App/Index/news
//    组织生活http://dj.qfant.com/index.php/App/Index/group
//    党员信息http://dj.qfant.com/index.php/App/Index/dangyuan
 */
public enum ServiceMap implements Enums.IType {
    weather("weather", WeatherResult.class),
    towns("towns", PointResult.class),
    consult("consult", PointResult.class),
//
    ;

    private final String mType;
    private final Class<? extends BaseResult> mClazz;
    private final int mTaskType;
    private final static int NET_TASK_START = 0;
    public final static int NET_TASKTYPE_CONTROL = NET_TASK_START;
    public final static int NET_TASKTYPE_FILE = NET_TASKTYPE_CONTROL + 1;
    public final static int NET_TASKTYPE_ALL = NET_TASKTYPE_FILE + 1;

    ServiceMap(String type, Class<? extends BaseResult> clazz) {
        this(type, clazz, NET_TASKTYPE_CONTROL);
    }

    ServiceMap(String type, Class<? extends BaseResult> clazz, int taskType) {
        this.mType = type;
        this.mClazz = clazz;
        this.mTaskType = taskType;
    }

    /**
     * 创建接口对应的resp的Result的对象
     *
     * @return AbsResult或其子类的对象
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    @SuppressWarnings("unchecked")
    public <T extends BaseResult> T newResult() throws IllegalAccessException, InstantiationException {
        return (T) getClazz().newInstance();
    }

    @Override
    public String getDesc() {
        return mType;
    }

    public Class<? extends BaseResult> getClazz() {
        return mClazz;
    }

    @Override
    public int getCode() {
        return mTaskType;
    }
}
