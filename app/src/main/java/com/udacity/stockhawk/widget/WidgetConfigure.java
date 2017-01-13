package com.udacity.stockhawk.widget;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.RemoteViews;

import com.udacity.stockhawk.R;

// -------------------
// Add configuration to widget because of some weird issue
// I had no prior experience with widgets so was trying everything
// Finally it turned out to be a slight typo
// So the configuration does nothing for now
// Maybe in future we can do something
// ------------------

public class WidgetConfigure extends Activity {
    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget_configure);
        // launched by intent when adding a widget
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }
        // configuration code should go here
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        RemoteViews views = new RemoteViews(getPackageName(), R.layout.widget_provider_layout);
        appWidgetManager.updateAppWidget(mAppWidgetId, views);

        // update widget
        final Context cc = this;
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int[] ids = {mAppWidgetId};
                WidgetProvider myWidget = new WidgetProvider();
                myWidget.onUpdate(cc, AppWidgetManager.getInstance(cc),ids);
                // QuoteSyncJob.syncImmediately(cc); // sync
            }
        }, 1000); // delay before adding items as it leads to size issues in widget
        // TODO: if widget added when no local data (very less probability)
        // end update

        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        setResult(RESULT_OK, resultValue);
        finish();
    }
}
