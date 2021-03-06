package com.example.b07projectgroup4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.content.Intent;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Date;

public class DoctorsAvailActivity extends AppCompatActivity {

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors_avail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        //Get Intent
        Intent intent = getIntent();
        //Get doctor and patient from previous intent
        Patient patient = (Patient)intent.getSerializableExtra("patient");
        Doctor doctor = (Doctor)intent.getSerializableExtra("doctor");
        //Get current date
        Date currentDate = new Date();
        //Create listview
        listView = findViewById(R.id.listview);
        //Create ArrayList of sessions
        ArrayList<Timeslot> timeslots = new ArrayList<>();
        //Append doctor availabilities to sessions
//        for (String s : doctor.getAvailabilities()){
//            sessions.add(s);
//        }
        //Create adapter
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,timeslots);
        listView.setAdapter(adapter);
        //Create OnItemClick Listener to pass session doctor and patient to the confirmation screen
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(DoctorsAvailActivity.this, ConfirmAppointmentActivity.class);
                intent.putExtra("timeslot",timeslots.get(i));
                intent.putExtra("doctor",doctor);
                intent.putExtra("patient",patient);
                startActivity(intent);
            }
        });

        //Updating Data when appointments are booked
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("doctors").child(doctor.getUsername()).child("availabilities");
        ValueEventListener listener= new ValueEventListener() {
            @Override
            //Recreate sessions arraylist when updated.
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                timeslots.clear();
                for(DataSnapshot child: dataSnapshot.getChildren()){
                    Timeslot timeslot = child.getValue(Timeslot.class);
                    if(timeslot == null){
                        return;
                    }
                    if(timeslot.getIs_available().equals("true")){
                        timeslots.add(timeslot);
                    }
                    //Remove timeslots that passed current time
                    if(timeslot.convertToDate().compareTo(currentDate)<0){
                        timeslots.remove(timeslot);
                    }
                }
                if(adapter != null){
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("warning", "loadPost:onCancelled", databaseError.toException());
            }
        };
        ref.addValueEventListener(listener);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home: {
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}