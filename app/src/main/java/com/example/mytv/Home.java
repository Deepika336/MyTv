package com.example.mytv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

public class Home extends AppCompatActivity {
    private int[] images;
    SliderAdapter slideradapter;
    SliderView sliderView;
    DrawerLayout drawerhome;
    NavigationView navhome;
    Toolbar toolbarhome;
    LinearLayout kannada,hindi,kids,sports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sliderView=findViewById(R.id.sliderview);
        images=new int[]{R.drawable.friends,R.drawable.hp,R.drawable.adv2};
        slideradapter=new SliderAdapter(images);
        sliderView.setSliderAdapter(slideradapter);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.SCALE);
        sliderView.startAutoCycle();

        drawerhome=findViewById(R.id.drawerhome);
        navhome=findViewById(R.id.navhome);
        toolbarhome=findViewById(R.id.toolbarhome);
        kannada=findViewById(R.id.kannada);
        hindi=findViewById(R.id.hindi);
        kids=findViewById(R.id.kids);
        sports=findViewById(R.id.sports);
        toolbarhome.setTitle("Channel categories");
        setSupportActionBar(toolbarhome);
        navhome.bringToFront();
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerhome,toolbarhome,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerhome.addDrawerListener(toggle);
        toggle.syncState();

        kannada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Home.this,Kannada.class);
                startActivity(i);
            }
        });

        hindi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Home.this,Hindi.class);
                startActivity(i);
            }
        });
        kids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Home.this,Kids.class);
                startActivity(i);
            }
        });
        sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Home.this,Sports.class);
                startActivity(i);
            }
        });




        navhome.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.itemcart:
                        Intent i=new Intent(Home.this,Cart.class);
                        startActivity(i);
                        break;
                    case R.id.itemcomplaints:
                        Intent i2=new Intent(Home.this,Complaint.class);
                        startActivity(i2);
                        break;

                    case R.id.itemprofile:
                        Intent i3=new Intent(Home.this,Profile.class);
                        startActivity(i3);
                        break;

                    case R.id.itemcontact:
                        Intent i6=new Intent(Home.this,ContactUs.class);
                        startActivity(i6);
                        break;

                    case R.id.itemlogout:
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getApplicationContext(),Login.class));
                        finish();

                    default:
                        return true;
                }


                return false;
            }
        });



    }







    
}