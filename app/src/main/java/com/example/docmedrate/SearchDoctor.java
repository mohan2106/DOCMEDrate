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
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_doctor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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
        final String tv=i.getStringExtra("City_name");

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

        mDatabase.child("0").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Map<String , Object> map = (Map<String , Object>) child.getValue();
                    String name = (String) map.get("name");
                    String category = (String) map.get("description_0");
                    String city = (String) map.get("description_1");
                    itemList.add(new doctor_details_class(name,city,category));

                    /*ArrayListClass_product_item_firebase retailers=new ArrayListClass_product_item_firebase();
                    for(DataSnapshot child1 : dataSnapshot.child("Pricing Details").child("myList").getChildren())
                    {
                        Map<String , Object> map1 = (Map<String , Object>) child1.getValue();
                        float item_price = (Float) map1.get("price");
                        String item_retailername = (String) map1.get("retailername");
                        int item_stock = (Integer) map1.get("stock");
                        product_item_firebase item=new product_item_firebase(item_retailername,item_price,item_stock);
                        retailers.myList.add(item);
                    }
                    ArrayList<String> desc_value=new ArrayList<>();
                    desc_value.add(os);desc_value.add(RAM);desc_value.add(ROM);desc_value.add(size);
                    product_item product_item=new product_item(name,image,desc_key,desc_value,retailers);
                    item_list.add(product_item);*/
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                bar.dismiss();
                Toast.makeText(SearchDoctor.this, databaseError.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });

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
