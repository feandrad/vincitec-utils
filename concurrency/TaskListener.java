package br.com.pontualmobile.utils.concurrency;

/**
 * Created by feandrad on 01/07/16.
 */
public interface TaskListener<Params, Progress, Result> {
	void onPreExecute();
	void onPostExecute(Result result);
}
