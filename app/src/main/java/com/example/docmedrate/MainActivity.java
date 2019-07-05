package com.example.docmedrate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText editText;
    private ImageView search_btn;
    private ImageView img;
    private TextView tv1,tv2,title;
    private String search_head,flag,local,city;
    private int n;
    private ArrayList<String> listn;
    private ArrayList<City_name> itemList;
    private RecyclerView mRecyclerView;
    private cityListAdapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*Intent intent1=getIntent();
        search_head=intent1.getStringExtra("text");
        listn=intent1.getStringArrayListExtra("city");
        flag=intent1.getStringExtra("flag");
        local=intent1.getStringExtra("local");
        city=intent1.getStringExtra("cityName");*/
        listn=new ArrayList<>();
        listn.add("amarpur");
        listn.add("amba");
        listn.add("amnour");
        listn.add("arajoo");
        listn.add("araria");
        listn.add("areraj");
        listn.add("argada");
        listn.add("arrah");
        listn.add("aurangabad");
        listn.add("bagha");
        listn.add("banka");
        listn.add("bara chakia");
        listn.add("barh");
        listn.add("barhariya");
        listn.add("bettiah");
        listn.add("buxar");
        listn.add("chapra");
        listn.add("darbhanga");
        listn.add("gaya");
        listn.add("gopal ganj");
        listn.add("gopalganj");
        listn.add("hajipur");
        listn.add("jamui");
        listn.add("kishanganj");
        listn.add("lakhisarai");
        listn.add("madhubani");
        listn.add("munger");
        listn.add("muzaffarpur");
        listn.add("muzaffarnagar");
        listn.add("nalanda");
        listn.add("patna");
        listn.add("purnea");
        listn.add("pupri");
        listn.add("saharsa");
        listn.add("samastipur");
        listn.add("sasaram");
        listn.add("sheohar");
        listn.add("sitamarhi");
        listn.add("siwan");
        listn.add("supaul");
        listn.add("vaishali");
        n=listn.size();
        img=(ImageView)findViewById(R.id.imageView7);
        tv1=(TextView)findViewById(R.id.textView3);
        title=(TextView)findViewById(R.id.search_title);
        tv2=(TextView)findViewById(R.id.textView9);
        editText=(EditText)findViewById(R.id.search_text);
        search_btn=(ImageView)findViewById(R.id.search_icon);
        createExampleList();
        buildRecyclerView();

        editText.addTextChangedListener(new TextWatcher() {
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
    }
    private void filter(String text) {
        ArrayList<City_name> filteredList = new ArrayList<>();
        img.setVisibility(View.INVISIBLE);
        tv1.setVisibility(View.INVISIBLE);
        tv2.setVisibility(View.INVISIBLE);

        for (City_name item : itemList) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        adapter.filterList(filteredList);
        if(filteredList.size()==0){
            img.setVisibility(View.VISIBLE);
            tv1.setVisibility(View.VISIBLE);
            tv2.setVisibility(View.VISIBLE);
        }
    }
    private void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        adapter = new cityListAdapter(this,itemList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(adapter);
    }
    private void createExampleList(){
        itemList = new ArrayList<>();
        for(int i=0;i<n;i++){
            itemList.add(new City_name(listn.get(i)));
        }


    }
}
