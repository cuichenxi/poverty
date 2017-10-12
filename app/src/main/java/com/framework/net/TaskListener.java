package com.framework.net;

/**
 *
 * @author zexu
 *
 */
public interface TaskListener {
    public void onTaskComplete(NetworkTask task);

    public void onTaskCancel(NetworkTask task);

}
