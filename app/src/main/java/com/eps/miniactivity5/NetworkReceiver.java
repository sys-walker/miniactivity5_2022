package com.eps.miniactivity5;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

public class NetworkReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        final PendingResult pendingResult = goAsync();
        Task asyncTask = new Task(pendingResult);
        asyncTask.execute();
    }

    private static class Task extends AsyncTask<String, Integer, Integer> {

        private final PendingResult pendingResult;

        private Task(PendingResult pendingResult) {
            super();
            this.pendingResult = pendingResult;
        }

        @Override
        protected Integer doInBackground(String... strings) {
            ConnectivityManager connectivity = (ConnectivityManager) MainActivity.getInstace().getSystemService(Context.CONNECTIVITY_SERVICE);
            Resources res = MainActivity.getInstace().getResources();
            if (connectivity != null) {
                NetworkInfo networkInfo = connectivity.getActiveNetworkInfo();
                if (networkInfo != null) {
                    String text = String.format(res.getString(R.string.toast_message), getStringNetwork(networkInfo.getType()));
                    MainActivity.getInstace().showToast(text);
                }else {
                    MainActivity.getInstace().showToast(res.getString(R.string.toast_error));
                }
            }else {
                MainActivity.getInstace().showToast(res.getString(R.string.toast_error));
            }
            return 1;
        }

        @Override
        protected void onPostExecute(Integer s) {
            super.onPostExecute(s);
            // Must call finish() so the BroadcastReceiver can be recycled.
            pendingResult.finish();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        public String getStringNetwork(Integer connectionType){
            switch(connectionType){
                case ConnectivityManager.TYPE_MOBILE:
                    return  MainActivity.getInstace().getString(R.string.mobile_data);
                case ConnectivityManager.TYPE_WIFI:
                    return MainActivity.getInstace().getString(R.string.wifi);
            }
            return MainActivity.getInstace().getString(R.string.unknown);

        }
    }
}
