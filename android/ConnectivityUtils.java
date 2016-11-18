package br.com.pontualmobile.utils.android;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by feandrad on 31/08/16.
 */
public class ConnectivityUtils {
	
	public static boolean isConnected(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		
		return netInfo != null && netInfo.isConnected();
	}
}
