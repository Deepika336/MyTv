package com.example.mytv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Kids extends AppCompatActivity {
    RecyclerView rckids;
    LinearLayout expandableview;
    ImageView imageView;
    CardView cardView;
    RCadapter adapter;
    Toolbar toolbarkids;
    FirebaseAuth mAuth;

    Button kidspack_button;
    TextView kidspack_text,kidspackprice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kids);
        FirebaseApp.initializeApp(this);
        rckids=findViewById(R.id.rckids);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rckids.setLayoutManager(layoutManager);
        expandableview=findViewById(R.id.expandable_view);
        imageView=findViewById(R.id.showTextkids);
        cardView=findViewById(R.id.kidsEntertainment);

        kidspack_button=findViewById(R.id.kidspack_button);
        kidspack_text=findViewById(R.id.kidspack_text);
        kidspackprice=findViewById(R.id.kidspackprice);

        toolbarkids=findViewById(R.id.toolbarkids);
        setSupportActionBar(toolbarkids);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        FirebaseRecyclerOptions<RCmodel> options=
                new FirebaseRecyclerOptions.Builder<RCmodel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Kids"),RCmodel.class)
                        .build();
        adapter=new RCadapter(options);
        rckids.setAdapter(adapter);
        kidspack_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String,Object> map=new HashMap<>();
                map.put("tempcartchannel",kidspack_text.getText().toString());
                map.put("tempcartprice",kidspackprice.getText().toString());
                mAuth = FirebaseAuth.getInstance();
                FirebaseDatabase.getInstance().getReference().child("tempcart").child(mAuth.getCurrentUser().getUid()).push()
                        .setValue(map)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(v.getContext(), "Added to cart", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull  Exception e) {
                        Toast.makeText(v.getContext(), "e", Toast.LENGTH_SHORT).show();
                    }
                });




            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }


    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void showmore(View view) {
        if(expandableview.getVisibility()==View.GONE){

            String uri = "@drawable/up2";
            int imageResource = getResources().getIdentifier(uri, null, getPackageName());
            Drawable res = getResources().getDrawable(imageResource);
            imageView.setImageDrawable(res);

            TransitionManager.beginDelayedTransition(cardView,new AutoTransition());
            expandableview.setVisibility(View.VISIBLE);
        }
        else{
            String uri = "@drawable/down";
            int imageResource = getResources().getIdentifier(uri, null, getPackageName());
            Drawable res = getResources().getDrawable(imageResource);
            imageView.setImageDrawable(res);

            TransitionManager.beginDelayedTransition(cardView,new AutoTransition());
            expandableview.setVisibility(View.GONE);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu,menu);
        MenuItem item=menu.findItem(R.id.search);
        SearchView searchView=(SearchView)item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                processsearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                processsearch(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.tocart) {
            Intent i=new Intent(Kids.this,Cart.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void processsearch(String s) {
        FirebaseRecyclerOptions<RCmodel> options =
                new FirebaseRecyclerOptions.Builder<RCmodel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Kids").orderByChild("cname").startAt(s).endAt(s+"\uf8ff"), RCmodel.class)
                        .build();

        adapter=new RCadapter(options);
        adapter.startListening();
        rckids.setAdapter(adapter);
    }


}