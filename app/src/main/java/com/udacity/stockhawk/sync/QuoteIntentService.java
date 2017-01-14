package com.udacity.stockhawk.sync;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.util.Log;

import com.udacity.stockhawk.R;
import com.udacity.stockhawk.widget.WidgetProvider;

import timber.log.Timber;


public class QuoteIntentService extends IntentService {

    public QuoteIntentService() {
        super(QuoteIntentService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Timber.d("Intent handled");
        Log.v("WDP", "here");
        QuoteSyncJob.getQuotes(getApplicationContext());

        // update widget
        Intent cintent = new Intent(getApplicationContext(), WidgetProvider.class);
        cintent.setAction(WidgetProvider.UPDATE_WIDGET);
        sendBroadcast(cintent);
    }
}
