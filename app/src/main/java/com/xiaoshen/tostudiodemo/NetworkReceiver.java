package com.xiaoshen.tostudiodemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 作者：申齐灿
 * Email:baitoulao0406@163.com
 * 2016/5/16 0016 下午 9:45
 */
public class NetworkReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NetUtil.showNetState(context);
    }
}
