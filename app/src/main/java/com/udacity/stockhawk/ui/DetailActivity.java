package com.udacity.stockhawk.ui;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.udacity.stockhawk.R;
import com.udacity.stockhawk.data.Contract;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.graph) GraphView graph;
    @BindView(R.id.stock_name) TextView stockNameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);

        String symbol = getIntent().getStringExtra("symbol");
        stockNameView.setText(symbol);

        createGraph(getDatapoints(symbol));
    }

    void createGraph(ArrayList<DataPoint> dataPoints){
        // http://www.android-graphview.org/dates-as-labels/
        // create series and make graph
        Collections.reverse(dataPoints);
        int size = dataPoints.size();
        DataPoint dpArray [] = new DataPoint[size];
        dpArray = dataPoints.toArray(dpArray);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dpArray);
        graph.addSeries(series);

        // set date label formatter
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this));
        graph.getGridLabelRenderer().setNumHorizontalLabels(4); // only 4 because of the space

        // set manual x bounds to have nice steps
        graph.getViewport().setMinX((long) dpArray[0].getX());
        graph.getViewport().setMaxX((long) dpArray[size-1].getX());
        graph.getViewport().setXAxisBoundsManual(true);

        // as we use dates as labels, the human rounding to nice readable numbers
        // is not necessary
        graph.getGridLabelRenderer().setHumanRounding(false);
    }

    ArrayList<DataPoint> getDatapoints(String symbol){
        ContentResolver cr = getContentResolver();
        String [] projection = new String[1]; projection[0] = Contract.Quote.COLUMN_HISTORY;
        Cursor cursor = cr.query(
                Contract.Quote.makeUriForStock(symbol),
                projection,
                null, null, null
        );
        ArrayList<DataPoint> dataPoints = new ArrayList<>();
        if (cursor != null && cursor.moveToNext()){
            String history = cursor.getString(0);
            for (String record: history.split("\n")){
                dataPoints.add(new DataPoint(
                        new Date(Long.parseLong(record.split(",")[0].trim())),
                        Double.parseDouble(record.split(",")[1].trim())
                ));
            }
            cursor.close();
        }
        return dataPoints;
    }
}
