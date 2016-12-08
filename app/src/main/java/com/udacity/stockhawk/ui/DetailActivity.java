package com.udacity.stockhawk.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.udacity.stockhawk.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.graph) GraphView graph;
    @BindView(R.id.stock_name) TextView stockNameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);

        stockNameView.setText(getIntent().getStringExtra("symbol"));

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        graph.addSeries(series);
    }
}
