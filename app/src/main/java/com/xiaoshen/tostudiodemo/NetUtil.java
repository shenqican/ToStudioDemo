package com.xiaoshen.tostudiodemo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/5/16 0016.
 */
public class NetUtil {

    private static ConnectivityManager manager;
    private static NetworkInfo networkInfo;
  /*
   * @return boolean判断网络连接状态如果连接上返回true否则返回false
   * @param null
   */
    public static boolean isLianJie(Context context){
        if(context!=null){
            manager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            networkInfo=manager.getActiveNetworkInfo();
            if(networkInfo!=null){
                return  networkInfo.isConnected();
            }
        }

        return  false;
    }
    public static void showNetState(Context context){
        if(isLianJie(context)){
            Toast.makeText(context,"网络："+
                  getTypeName(context),Toast.LENGTH_LONG).show();
        }else{
            showNoNetWorkDialog(context);
        }
    }
    /*
     * @return boolean 判断是是否是Wifi
     * @param
     */
    public static boolean isWifi(Context context){
        if(context!=null){
            manager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            networkInfo=manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if(networkInfo!=null){
                return  networkInfo.isConnected();
            }
        }
        return  false;

    }
    /*
    * @return boolean 判断是不是Mobile
    * @param
    */
    public static boolean isMobile(Context context){
        if(context!=null){
            manager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            networkInfo=manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if(networkInfo!=null){
                return  networkInfo.isConnected();
            }
        }
        return  false;
    }
    /**
     * 当判断当前手机没有网络时选择是否打开网络设置
     * @param context
     */
    public static void showNoNetWorkDialog(final Context context){
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setIcon(R.drawable.ic_launcher)
                .setTitle(R.string.app_name)
                .setMessage("当前无网络").setPositiveButton("设置", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialogInterface, int i) {
                         // 跳转到系统的网络设置界面
                         Intent intent = null;
                         // 先判断当前系统版本
                         if(android.os.Build.VERSION.SDK_INT > 10){  // 3.0以上
                             intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                         }else{
                             intent = new Intent();
                             intent.setClassName("com.android.settings", "com.android.settings.WirelessSettings");
                         }
                         context.startActivity(intent);
                         dialogInterface.dismiss();
                     }
                 }).setNegativeButton("知道了", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).show();

    }
    /*
     * @return 判断是否是FastMobileNetWork，将3G或者3G以上的网络称为快速网络
     * @param
     */
    private static boolean isFastMobileNetwork(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        switch (telephonyManager.getNetworkType()) {
            case TelephonyManager.NETWORK_TYPE_1xRTT:
                return false; // ~ 50-100 kbps
            case TelephonyManager.NETWORK_TYPE_CDMA:
                return false; // ~ 14-64 kbps
            case TelephonyManager.NETWORK_TYPE_EDGE:
                return false; // ~ 50-100 kbps
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
                return true; // ~ 400-1000 kbps
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
                return true; // ~ 600-1400 kbps
            case TelephonyManager.NETWORK_TYPE_GPRS:
                return false; // ~ 100 kbps
            case TelephonyManager.NETWORK_TYPE_HSDPA:
                return true; // ~ 2-14 Mbps
            case TelephonyManager.NETWORK_TYPE_HSPA:
                return true; // ~ 700-1700 kbps
            case TelephonyManager.NETWORK_TYPE_HSUPA:
                return true; // ~ 1-23 Mbps
            case TelephonyManager.NETWORK_TYPE_UMTS:
                return true; // ~ 400-7000 kbps
            case TelephonyManager.NETWORK_TYPE_EHRPD:
                return true; // ~ 1-2 Mbps
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
                return true; // ~ 5 Mbps
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return true; // ~ 10-20 Mbps
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return false; // ~25 kbps
            case TelephonyManager.NETWORK_TYPE_LTE:
                return true; // ~ 10+ Mbps
            case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                return false;
            default:
                return false;
        }
    }
    /**
     * 得到当前的手机网络类型 null没有网络，wifi,2g,3g,4g
     *
     * @param context
     * @return String
     */
    public static String getTypeName(Context context) {
        String type = "";
         manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
         networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo == null) {
            type = "null";
        } else if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            type = "wifi";
        } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            int subType = networkInfo.getSubtype();
            if (subType == TelephonyManager.NETWORK_TYPE_CDMA || subType == TelephonyManager.NETWORK_TYPE_GPRS
                    || subType == TelephonyManager.NETWORK_TYPE_EDGE) {
                type = "2g";
            } else if (subType == TelephonyManager.NETWORK_TYPE_UMTS || subType == TelephonyManager.NETWORK_TYPE_HSDPA
                    || subType == TelephonyManager.NETWORK_TYPE_EVDO_A || subType == TelephonyManager.NETWORK_TYPE_EVDO_0
                    || subType == TelephonyManager.NETWORK_TYPE_EVDO_B) {
                type = "3g";
            } else if (subType == TelephonyManager.NETWORK_TYPE_LTE) {// LTE是3g到4g的过渡，是3.9G的全球标准  
                type = "4g";
            }
        }
        return type;
    }


}
