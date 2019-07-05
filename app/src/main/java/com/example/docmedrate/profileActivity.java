package com.example.docmedrate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class profileActivity extends AppCompatActivity {

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView reviewRecycler;
    private reviewAdapter review_adapter;
    private List<review_model> review_list;
    private Button date;
    private TextView pr_name,special,addr,exper,fee;
    private Calendar c;
    private ArrayList<Button> btnList;
    private int pos=100;
    private LinearLayout call_for_detail;
    private TextView time_show;
    private int x=100;
    private DatePickerDialog dbg;
    private ImageView mapImg;
    public static Activity doctor_profile;
    private Button cont_btn;
    private String d,m,y;
    private int flag=99;
    private LinearLayout lay;
    private TextView date_view;
    private ConstraintLayout v;
    //private List<time_model> itemList;
    private CircleImageView img;
    private FirebaseFirestore firestore=FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private TextView pRating,pNoOfRating,pReco,pNoOfReco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Intent i=getIntent();
        final String name=i.getStringExtra("name");
        //final String fee=i.getStringExtra("fee");
        //String experience=i.getStringExtra("experience");
        final String address=i.getStringExtra("address");
        final String speciality=i.getStringExtra("speciality");
        //final String image=i.getStringExtra("image");
        //final String lat=i.getStringExtra("lat");
        //final String lng=i.getStringExtra("long");
        final String id=i.getStringExtra("id");
        doctor_profile=this;
        v=findViewById(R.id.view_review);
        //v.getViewTreeObserver()
        //      .addOnGlobalLayoutListener(new OnViewGlobalLayoutListener(v));
        pRating=findViewById(R.id.profile_rating);
        pNoOfRating=findViewById(R.id.profile_no_of_rating);
        pReco=findViewById(R.id.profile_recomendation);
        pNoOfReco=findViewById(R.id.profile_no_of_votes_recomended);
        pr_name=(TextView)findViewById(R.id.profile_name);
        lay=findViewById(R.id.no_review_layout);
        special=(TextView)findViewById(R.id.profile_category);
        addr=(TextView)findViewById(R.id.profile_address);
        exper=(TextView)findViewById(R.id.profile_experience);
        img=(CircleImageView)findViewById(R.id.circleImageView);
        mapImg=(ImageView)findViewById(R.id.profile_map);
        reviewRecycler=(RecyclerView)findViewById(R.id.public_review);
        reviewRecycler.setHasFixedSize(true);
        reviewRecycler.setLayoutManager(new LinearLayoutManager(this));
        review_list=new ArrayList<>();
        firestore.collection("Doctors").document("India").collection("bihar").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    double rat= Double.parseDouble(documentSnapshot.getString("rating"));
                    pRating.setText(String.format("%.1f", rat) + " STAR RATING");
                    Long recom= (Long) documentSnapshot.get("recomendation");
                    Long tr= (Long) documentSnapshot.get("totalReview");
                    pNoOfRating.setText("("+String.valueOf(tr)+" rating)");
                    double per=((recom*0.1)/tr)*1000 ;
                    pReco.setText(String.format("%.1f", per)+"% RECOMENDATION");
                    pNoOfReco.setText("("+String.valueOf(recom)+" votes)");
                }
                else{

                    pRating.setText("NO RATING");
                    pNoOfRating.setText("(0 ratings)");
                    pReco.setText("NOBODY RECOMENDED");
                    pNoOfReco.setText("0 recomedation");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
        firestore.collection("Doctors").document("India").collection("bihar").document(id).collection("Review").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (DocumentSnapshot snapshot:task.getResult()){
                        review_list.add(new review_model(snapshot.getString("Date"),snapshot.getString("Rating"),snapshot.getString("Review")));
                        review_adapter.notifyDataSetChanged();
                    }
                    if(review_list.size() == 0){
                        lay.setVisibility(View.VISIBLE);
                    }
                    else{
                        lay.setVisibility(View.GONE);
                    }
                }
                else{
                    Toast.makeText(profileActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        review_adapter=new reviewAdapter(review_list,this);
        reviewRecycler.setAdapter(review_adapter);

        cont_btn=(Button)findViewById(R.id.continue_btn);

        //final GeoPoint loc=i.getParcelableExtra("location");
        /*Glide.with(this)
                .load(image)
                .into(img);*/
        pr_name.setText(name);
        special.setText(speciality);
        addr.setText(address);
        //exper.setText(experience+" +exp");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Profile");
        final String[] no=new String[1];
        firestore.collection("BookingCount").document("India").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    //on.setText(documentSnapshot.getString("count"));
                    //Toast.makeText(paymentActivity.this, on.getText().toString(), Toast.LENGTH_SHORT).show();
                    no[0]=documentSnapshot.getString("count");
                    //DocName[0] =String.valueOf(Integer.parseInt(documentSnapshot.getString("count"))+1);
                }
                else{
                    //Toast.makeText(paymentActivity.this, "document not exist", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Toast.makeText(this, lat+"  "+lng, Toast.LENGTH_SHORT).show();
        /*mapImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=null,chooser=null;
                intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("geo:"+lat+","+lng+"?q="+lat+","+lng+"("+name+")&iwloc=A&hl=es"));
                chooser=Intent.createChooser(intent,"Launch Maps");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(Doctor_profile.this).toBundle());
                }
                else{
                    startActivity(intent);
                }
            }
        });*/
        //final com.lenovo.doc.Model model=new com.lenovo.doc.Model();
        //model.setGeoPoint(loc);
        cont_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if(x<24 && d!=null){
                Intent intent=new Intent(profileActivity.this,RateReview.class);
                intent.putExtra("name",name);
                //intent.putExtra("fee",fee);
                intent.putExtra("id",id);
                //intent.putExtra("location",model);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(profileActivity.this).toBundle());
                }
                else{
                    startActivity(intent);
                }
                /*}
                else{
                    Toast.makeText(Doctor_profile.this, "Date and Time must required", Toast.LENGTH_SHORT).show();
                }*/

            }
        });
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
    private class review_model{
        private String userDate,userRaing,userReview;

        public review_model(String userDate, String userRaing, String userReview) {
            this.userDate = userDate;
            this.userRaing = userRaing;
            this.userReview = userReview;
        }

        public String getUserDate() {
            return userDate;
        }

        public void setUserDate(String userDate) {
            this.userDate = userDate;
        }

        public String getUserRaing() {
            return userRaing;
        }

        public void setUserRaing(String userRaing) {
            this.userRaing = userRaing;
        }

        public String getUserReview() {
            return userReview;
        }

        public void setUserReview(String userReview) {
            this.userReview = userReview;
        }
    }
    private class reviewAdapter extends RecyclerView.Adapter<reviewAdapter.ViewHolder>{
        private List<review_model> itemList;
        private Context context;

        public reviewAdapter(List<review_model> itemList, Context context) {
            this.itemList = itemList;
            this.context = context;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View v= LayoutInflater.from(context).inflate(R.layout.single_review_content,viewGroup,false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            review_model ne=itemList.get(i);
            viewHolder.ratingBar.setRating(Integer.valueOf(ne.getUserRaing()));
            viewHolder.userReview.setText(ne.getUserReview());
            viewHolder.userDate.setText(ne.getUserDate());
            viewHolder.userRating.setText(ne.getUserRaing());
        }

        @Override
        public int getItemCount() {
            return itemList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private ImageView userImage;
            private TextView userName,userDate,userRating,userReview;
            private RatingBar ratingBar;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                userImage=itemView.findViewById(R.id.review_user_image);
                userName=itemView.findViewById(R.id.review_user_name);
                userDate=itemView.findViewById(R.id.review_date);
                userRating=itemView.findViewById(R.id.review_rating_int);
                ratingBar=itemView.findViewById(R.id.review_rating);
                userReview=itemView.findViewById(R.id.review_review);

            }
        }
    }
    private static class OnViewGlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {
        private final static int maxHeight = 600;
        private View view;

        public OnViewGlobalLayoutListener(View view) {
            this.view = view;
        }

        @Override
        public void onGlobalLayout() {
            if (view.getHeight() > maxHeight)
                view.getLayoutParams().height = maxHeight;
        }
    }
}
