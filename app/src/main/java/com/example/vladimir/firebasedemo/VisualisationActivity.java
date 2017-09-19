package com.example.vladimir.firebasedemo;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.firebase.auth.FirebaseAuth;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;

import static com.github.mikephil.charting.animation.Easing.EasingOption.EaseOutCirc;

public class VisualisationActivity extends AppCompatActivity {

    BarChart barChart;
    ArrayList<BarEntry> barEntries = new ArrayList<>();
    ArrayList<String> names = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualisation);

        names.add("Lecture 1");
        names.add("Lecture 2");
        names.add("Lecture 3");
        names.add("Lecture 4");
        names.add("Lecture 5");
        names.add("Lecture 6");
        names.add("Lecture 7");
        names.add("Lecture 8");

        Float[] data = FirebasePullDBHelper.getInstance(this).countStudentsPerLecture();
        for (int i = 0; i < 8; i++) {
            barEntries.add(new BarEntry(i + 1, data[i]));
        }
        barChart = (BarChart) findViewById(R.id.barChart);
        BarChartDraw();

    }

    private void BarChartDraw() {
        BarDataSet dataSet = new BarDataSet(barEntries, "Number of students"); // Description of legend
        dataSet.setColor(Color.rgb(63, 81, 181)); //Setting color for rectangles

        BarData barData = new BarData(dataSet);
        barChart.setData(barData);

        //X axis setting
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setLabelRotationAngle(45);
        xAxis.setDrawGridLines(false);
        //xAxis.setTextSize(10);
        xAxis.setValueFormatter(new XAsisValueFormatter(names));

        //Y axis setting
        YAxis yAxis = barChart.getAxisLeft();
        yAxis.setGranularity(1f);//interval 1
        barChart.getAxisRight().setEnabled(false);

        barChart.animateY(1500, EaseOutCirc);
        barChart.getDescription().setText("");
        dataSet.setValueTextSize(9); //Label text size
        dataSet.setValueFormatter(new MyValueFormatter());
    }

    //Formatting label number
    private class MyValueFormatter implements com.github.mikephil.charting.formatter.IValueFormatter {
        private DecimalFormat decimalFormat;

        MyValueFormatter() {
            decimalFormat = new DecimalFormat("###,###,###"); //Formatting numbers without decimal separator
        }

        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            return decimalFormat.format(value);
        }
    }

    //Setting string in x label
    private class XAsisValueFormatter implements IAxisValueFormatter {
        private ArrayList<String> mValues = null;

        XAsisValueFormatter(ArrayList<String> value) {
            mValues = value;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return mValues.get((int) value - 1);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_sign_out) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, LogInActivity.class));
            finish();
        } else if (item.getItemId() == R.id.menu_export) {
            exportDB();
        }
        return true;
    }

    public void exportDB() {

        FirebasePullDBHelper dbhelper = new FirebasePullDBHelper(getApplicationContext());
        File exportDir = new File(Environment.getExternalStorageDirectory(), "");
        if (!exportDir.exists()) {
            exportDir.mkdirs();
        }

        File file = new File(exportDir, "potpisi.csv");
        try {
            file.createNewFile();
            CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
            SQLiteDatabase db = dbhelper.getReadableDatabase();
            Cursor curCSV = db.rawQuery("SELECT * FROM students", null);
            csvWrite.writeNext(curCSV.getColumnNames());
            while (curCSV.moveToNext()) {
                //Which column you want to exprort
                String arrStr[] = {curCSV.getString(0), curCSV.getString(1), curCSV.getString(2), curCSV.getString(3), curCSV.getString(4), curCSV.getString(5), curCSV.getString(6), curCSV.getString(7), curCSV.getString(8), curCSV.getString(9), curCSV.getString(10)};
                csvWrite.writeNext(arrStr);
            }
            csvWrite.close();
            curCSV.close();
        } catch (Exception sqlEx) {
            Log.e("!!!!!!!!!!Export Error", sqlEx.getMessage(), sqlEx);
        }

        Uri u1 = null;
        u1 = Uri.fromFile(file);
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Export database");
        sendIntent.putExtra(Intent.EXTRA_STREAM, u1);
        sendIntent.setType("text/html");
        startActivity(sendIntent);
    }
}
