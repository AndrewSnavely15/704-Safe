package com.example.a704;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Arrays;
import java.util.List;


public class Gas extends AppCompatActivity {
    GraphView gasGraph;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("sensors");
    LineGraphSeries<DataPoint> series;
    int time = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gas_home);
        gasGraph = findViewById(R.id.gas_graph);
        series = new LineGraphSeries<>();
        // activate horizontal zooming and scrolling
        gasGraph.getViewport().setScalable(true);

// activate horizontal scrolling
        gasGraph.getViewport().setScrollable(true);

// activate horizontal and vertical zooming and scrolling
        gasGraph.getViewport().setScalableY(true);

// activate vertical scrolling
        gasGraph.getViewport().setScrollableY(true);
        gasGraph.getViewport().setXAxisBoundsManual(true);
        gasGraph.getViewport().setMinX(0.5);
        gasGraph.getViewport().setMaxX(600);
        gasGraph.getGridLabelRenderer().setHorizontalAxisTitle("Time (sec)");
        gasGraph.getGridLabelRenderer().setVerticalAxisTitle("Parts per million (ppm)");
        gasGraph.addSeries(series);

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Iterable ds = dataSnapshot.getChildren();
                while(ds.iterator().hasNext()){
                    //List<String> rr = Arrays.asList(ds.iterator().next().toString().split(" "));
                    List<String> gas = Arrays.asList(dataSnapshot.child("res0").getValue().toString());

                    ds.iterator().next();
                    for(int i=0;i<gas.size();i++){

                        time+= 2;
                        DataPoint point = new DataPoint(time, Double.parseDouble(gas.get(i)));
                        series.appendData(point, true, 1000);
                        Log.d("gas level:", gas.get(i).toString());
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