package br.com.pontualmobile.utils.service;

import android.app.Service;
import android.content.Intent;
import android.os.*;

/**
 * Created by feandrad on 03/08/16.
 * <p>
 * Modified version of Android's {@link android.app.IntentService} wich will not stop after the execution is done.
 * All other functionalities remains the same.
 * <p>
 * This class and android.app.IntentService are licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * {@link android.app.IntentService} is Copyrighted (C) by 2008 The Android Open Source Project
 */
public abstract class BackgroundService extends Service {
	
	private volatile Looper         mServiceLooper;
	private volatile ServiceHandler mServiceHandler;
	private          String         mName;
	private          boolean        mRedelivery;
	
	private final class ServiceHandler extends Handler {
		public ServiceHandler(Looper looper) {
			super(looper);
		}
		
		@Override public void handleMessage(Message msg) {
			onHandleIntent((Intent) msg.obj);
//			stopSelf(msg.arg1);
		}
	}
	
	/**
	 * Creates an IntentService.  Invoked by your subclass's constructor.
	 *
	 * @param name Used to name the worker thread, important only for debugging.
	 */
	public BackgroundService(String name) {
		super();
		mName = name;
	}
	
	/**
	 * Sets intent redelivery preferences.  Usually called from the constructor
	 * with your preferred semantics.
	 * <p>
	 * <p>If enabled is true,
	 * {@link #onStartCommand(Intent, int, int)} will return
	 * {@link Service#START_REDELIVER_INTENT}, so if this process dies before
	 * {@link #onHandleIntent(Intent)} returns, the process will be restarted
	 * and the intent redelivered.  If multiple Intents have been sent, only
	 * the most recent one is guaranteed to be redelivered.
	 * <p>
	 * <p>If enabled is false (the default),
	 * {@link #onStartCommand(Intent, int, int)} will return
	 * {@link Service#START_NOT_STICKY}, and if the process dies, the Intent
	 * dies along with it.
	 */
	public void setIntentRedelivery(boolean enabled) {
		mRedelivery = enabled;
	}
	
	@Override public void onCreate() {
		super.onCreate();
		HandlerThread thread = new HandlerThread("TimerService[" + mName + "]");
		thread.start();
		
		mServiceLooper = thread.getLooper();
		mServiceHandler = new ServiceHandler(mServiceLooper);
	}
	
	@Override public void onStart(Intent intent, int startId) {
		Message msg = mServiceHandler.obtainMessage();
		msg.arg1 = startId;
		msg.obj = intent;
		mServiceHandler.sendMessage(msg);
	}
	
	/**
	 * You should not override this method for your IntentService. Instead,
	 * override {@link #onHandleIntent}, which the system calls when the IntentService
	 * receives a start request.
	 *
	 * @see android.app.Service#onStartCommand
	 */
	@Override public int onStartCommand(Intent intent, int flags, int startId) {
		onStart(intent, startId);
		return mRedelivery ? START_REDELIVER_INTENT : START_NOT_STICKY;
	}
	
	@Override public void onDestroy() {
		mServiceLooper.quit();
	}
	
	/**
	 * Unless you provide binding for your service, you don't need to implement this
	 * method, because the default implementation returns null.
	 *
	 * @see android.app.Service#onBind
	 */
	@Override public IBinder onBind(Intent intent) {
		return null;
	}
	
	/**
	 * This method is invoked on the worker thread with a request to process.
	 * Only one Intent is processed at a time, but the processing happens on a
	 * worker thread that runs independently from other application logic.
	 * So, if this code takes a long time, it will hold up other requests to
	 * the same IntentService, but it will not hold up anything else.
	 * When all requests have been handled, the IntentService stops itself,
	 * so you should not call {@link #stopSelf}.
	 *
	 * @param intent The value passed to {@link
	 *               android.content.Context#startService(Intent)}.
	 */
	protected abstract void onHandleIntent(Intent intent);
}
