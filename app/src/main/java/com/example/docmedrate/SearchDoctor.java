package com.example.docmedrate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.type.LatLng;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SearchDoctor extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<doctor_details_class> itemList;
    private ArrayList<String> cityList;
    private LinearLayout locality;
    private doctor_adapter adapter;
    private String City;
    private String local;
    private TextView city_view,loc_text;
    private String flag;
    private EditText search_et;
    public static Activity book_appointment;
    private ArrayList<String> localityList;
    private TextView locality_view;
    private LatLng loc=null;
    private ProgressDialog bar;
    private Button sel_city,category;
    public static Activity act;
    private int i=0;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_doctor);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        search_et=(EditText)findViewById(R.id.search_et);
        book_appointment=this;
        city_view=(TextView)findViewById(R.id.city_view);
        bar=new ProgressDialog(this);
        act=this;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Doctors");
        Intent i=getIntent();
        City=i.getStringExtra("City_name");

        search_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
        /*category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BookAppointment.this,CategoryActivity.class);
                intent.putExtra("city",City);
                intent.putExtra("local",local);
                startActivity(intent);
            }
        });*/
        city_view.setText(City);
        //locality_view.setText(local);

        bar.setMessage("Loding doctors in "+City+"...");
        bar.show();
        //firebaseFirestore=FirebaseFirestore.getInstance();
        recyclerView=(RecyclerView)findViewById(R.id.dr_list_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemList=new ArrayList<>();
        adapter=new doctor_adapter(itemList,this);
        recyclerView.setAdapter(adapter);
        itemList.clear();


        /*firebaseFirestore.collection("Doctors").document("India").collection("Guwahati").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot documentSnapshot: task.getResult()){
                        String able=documentSnapshot.getString("able");
                        if(able.equals("1")){
                            itemList.add(new doctor_details_class(documentSnapshot.get("Name").toString(),documentSnapshot.get("Address").toString(),documentSnapshot.get("Fee").toString(),documentSnapshot.get("Image").toString(),documentSnapshot.get("Experience").toString(),documentSnapshot.get("Speciality").toString(),(GeoPoint)documentSnapshot.get("Location"),documentSnapshot.get("id").toString()));
                        }

                    }
                    adapter.notifyDataSetChanged();
                }
                else{
                    String error=task.getException().getMessage();
                    Toast.makeText(SearchDoctor.this, error, Toast.LENGTH_SHORT).show();
                }
                bar.dismiss();
            }
        });*/
    }

    @Override
    protected void onStart() {
        super.onStart();
        mDatabase.child("doctors").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    i++;
                    Map<String , Object> map = (Map<String , Object>) child.getValue();
                    String name = (String) map.get("name");
                    String category = (String) map.get("description_0");
                    String city = (String) map.get("description_1");
                    if(TextUtils.equals(city,City)){
                        itemList.add(new doctor_details_class(name,city,category,String.valueOf(i)));
                    }

                }
                bar.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                bar.dismiss();
                Toast.makeText(SearchDoctor.this, databaseError.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void filter(String text) {
        List<doctor_details_class> filteredList = new ArrayList<>();

        for (doctor_details_class item : itemList) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        adapter.filterList(filteredList);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
