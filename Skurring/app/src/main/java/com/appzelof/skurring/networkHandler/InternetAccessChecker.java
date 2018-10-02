package com.appzelof.skurring.networkHandler;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

public class InternetAccessChecker {

    public boolean haveInternetConnection(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (wifiManager != null) {
            if (wifiManager.isWifiEnabled()) {
                //Wifi is on
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                if (wifiInfo.getNetworkId() == -1) {
                    //Not connected
                    return false;
                } else {
                    //Connected
                    return true;
                }
            } else {
                //Wifi not enabled
                return false;
            }
        } else {
            return false;
        }

    }

}
