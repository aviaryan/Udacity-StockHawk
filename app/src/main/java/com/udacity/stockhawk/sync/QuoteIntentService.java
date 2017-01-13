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
        // http://stackoverflow.com/questions/3455123/programmatically-update-widget-from-activity-service-receiver

//        Intent wIntent = new Intent(this, WidgetProvider.class);
//        wIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
//        int[] ids = {R.xml.appwidget_provider_info};
//        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
//        sendBroadcast(intent);

        // works per logs
//        int widgetIDs[] = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(
//                new ComponentName(getApplication(), WidgetProvider.class));
//        for (int id : widgetIDs)
//            AppWidgetManager.getInstance(getApplication()).notifyAppWidgetViewDataChanged(id, R.id.widgetCollectionList);

        // works when widget deleted and re-added
        int[] ids = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), WidgetProvider.class));
        WidgetProvider myWidget = new WidgetProvider();
        myWidget.onUpdate(this, AppWidgetManager.getInstance(this),ids);
    }
}
