package com.example.b07demosummer2024;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

public class EcoGaugeFragment extends Fragment {
    private TextView totalEmissions;
    private ImageView breakdownChart, trendGraph, comparisonChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.eco_gauge_fragment, container, false);

        totalEmissions = view.findViewById(R.id.total_emissions);
        breakdownChart = view.findViewById(R.id.breakdown_chart);
        trendGraph = view.findViewById(R.id.trend_graph);
        comparisonChart = view.findViewById(R.id.comparison_chart);

        setupData();

        return view;
    }

    private void setupData() {
        totalEmissions.setText("723 kg CO2e");

        breakdownChart.setImageResource(R.drawable.ic_pie_chart);
        trendGraph.setImageResource(R.drawable.ic_line_graph);
        comparisonChart.setImageResource(R.drawable.ic_bar_chart);
    }
}
