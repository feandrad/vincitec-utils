package br.com.pontualmobile.utils.location;

import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;

/**
 * Created by feandrad on 29/08/16.
 * <p>
 * Will get GPS locations until a location with a minimum accuracy was found.
 * After that, automaticaly stops listening to GPS.
 */
public abstract class LocationFindTask {
	
	public static final String LOG_TAG = LocationFindTask.class.getSimpleName();
	
	protected Activity activity;
	
	protected final CountDownTimer countdownTask;
	
	
	protected LocationManager  locationManager;
	protected LocationListener locationListener;
	
	protected Location bestLocation;
	protected String   provider;
	protected long     minTimeBwUpdates;
	protected float    minDistanceBwUpdates;
	
	public LocationFindTask(Activity activity, long locationInterval, long fastestInterval, long millisInFuture,
			long countDownInterval) {
		
		this.activity = activity;
		
		
		this.countdownTask = new CountDownTimer(millisInFuture, countDownInterval) {
			@Override public void onTick(long millisUntilFinished) {
				// TODO: Can show some Countdown feedback here (Map?, ProgressBar?)
				// TODO: Adicionar condição timeout
			}
			
			@Override public void onFinish() {
				
				if (bestLocation == null) {
					onFailure(null);
				} else {
					onSuccess(bestLocation);
				}
			}
		};
	}
	
	public abstract void onSuccess(Location bestLocation);
	
	public abstract void onFailure(@Nullable Location coarseLocation);
	
	
}

