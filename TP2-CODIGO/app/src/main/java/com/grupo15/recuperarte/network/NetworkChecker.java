package com.grupo15.recuperarte.network;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.annotation.NonNull;

public class NetworkChecker {
    @NonNull
    private final ConnectivityManager connectivityManager;

    public NetworkChecker(@NonNull final ConnectivityManager cm) { this.connectivityManager = cm; }

    public boolean isConnected() {
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
