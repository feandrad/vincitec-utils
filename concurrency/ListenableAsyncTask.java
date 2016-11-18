package br.com.pontualmobile.utils.concurrency;

import android.os.AsyncTask;

/**
 * Created by feandrad on 01/07/16.
 */
public abstract class ListenableAsyncTask<Params, Progress, Result>
		extends AsyncTask<Params, Progress, Result> {

	TaskListener listener;

	public ListenableAsyncTask(TaskListener<Params, Progress, Result> listener) {
		this.listener = listener;
	}

	public ListenableAsyncTask(final Runnable preRunnable, final Runnable postRunnable) {
		this(new TaskListener<Params, Progress, Result>() {
			@Override public void onPreExecute() {
				if (preRunnable != null) {
					preRunnable.run();
				}
			}

			@Override public void onPostExecute(Result result) {
				if (postRunnable != null) {
					postRunnable.run();
				}
			}
		});
	}

	@Override protected void onPreExecute() {
		super.onPreExecute();
		this.listener.onPreExecute();
	}

	@Override protected void onPostExecute(Result result) {
		super.onPostExecute(result);
		this.listener.onPostExecute(result);
	}
}
