package com.example.a704;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.model.value.IntegerValue;
import com.google.firebase.functions.FirebaseFunctions;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.Series;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Temperature extends AppCompatActivity {
    private static final String TAG = "Temperature";
    GraphView tempGraph;
    GraphView humidityGraph;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("sensors");
    LineGraphSeries<DataPoint> series, series2 ;
    int time, time2 = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.temp_home);
        tempGraph = findViewById(R.id.temp_graph);
        humidityGraph = findViewById(R.id.humidity_graph);
        series = new LineGraphSeries<>();
        series2 = new LineGraphSeries<>();
        // activate horizontal zooming and scrolling
        tempGraph.getViewport().setScalable(true);

// activate horizontal scrolling
        tempGraph.getViewport().setScrollable(true);

// activate horizontal and vertical zooming and scrolling
        tempGraph.getViewport().setScalableY(true);

// activate vertical scrolling
        tempGraph.getViewport().setScrollableY(true);

        tempGraph.getViewport().setXAxisBoundsManual(true);
        tempGraph.getViewport().setMinX(0.5);
        tempGraph.getViewport().setMaxX(800);
        tempGraph.getGridLabelRenderer().setHorizontalAxisTitle("Time (sec)");
        tempGraph.getGridLabelRenderer().setVerticalAxisTitle("Temperature (F)");

        // activate horizontal zooming and scrolling
        humidityGraph.getViewport().setScalable(true);

// activate horizontal scrolling
        humidityGraph.getViewport().setScrollable(true);

// activate horizontal and vertical zooming and scrolling
        humidityGraph.getViewport().setScalableY(true);

// activate vertical scrolling
        humidityGraph.getViewport().setScrollableY(true);

        humidityGraph.getViewport().setXAxisBoundsManual(true);
        humidityGraph.getViewport().setMinX(0.5);
        humidityGraph.getViewport().setMaxX(800);
        humidityGraph.getGridLabelRenderer().setHorizontalAxisTitle("Time (sec)");
        humidityGraph.getGridLabelRenderer().setVerticalAxisTitle("Percentage of humidity (%)");


        tempGraph.addSeries(series);
        humidityGraph.addSeries(series2);

        Log.d("NAME", myRef.getKey() );

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            Iterable ds = dataSnapshot.getChildren();
            while(ds.iterator().hasNext()){
                //List<String> rr = Arrays.asList(ds.iterator().next().toString().split(" "));
                List<String> temp = Arrays.asList(dataSnapshot.child("temperature").getValue().toString());
                List<String> humidity = Arrays.asList(dataSnapshot.child("humidity").getValue().toString());

                ds.iterator().next();
                for(int i=0;i<temp.size();i++){

                    time+= 2;
                    DataPoint point = new DataPoint(time, Double.parseDouble(temp.get(i)));
                    series.appendData(point, true, 1000);
                    Log.d("temp:", temp.get(i).toString());
                    Log.d("time:", ""+time);

                }
                for(int i=0;i<humidity.size();i++){

                    time2+= 2;
                    DataPoint point = new DataPoint(time2, Double.parseDouble(humidity.get(i)));
                    series2.appendData(point, true, 1000);
                    Log.d("humidity:", humidity.get(i).toString());
                    Log.d("time:", ""+time);

                }

            }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
             
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
