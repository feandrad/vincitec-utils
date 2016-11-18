package br.com.pontualmobile.utils.android;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by feandrad on 18/05/16.
 */
public class PermissionUtils {

	public static boolean validate(Activity activity, int requestCode, String... permissions) {
		List<String> list = new ArrayList<>();
		for (String permission : permissions) {
			if (!checkPermission(activity, permission)) {
				list.add(permission);
			}
		}
		if (list.isEmpty()) {
			return true;
		}

		String[] newPermissions = new String[list.size()];
		list.toArray(newPermissions);

		ActivityCompat.requestPermissions(activity, newPermissions, 1);

		return false;
	}

	// FIXME: Está retornando true mesmo com a permissão desativada nas configurações do Android
	public static boolean checkPermission(Context context, String permission) {
		boolean ok = ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
		return ok;
	}

	public static boolean hasGPSPermission(Context context) {
		boolean ok1 = checkPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);
		boolean ok2 = checkPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION);
		return ok1 && ok2;
	}
}