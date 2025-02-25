package com.example.practicetoeicapp;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


import com.example.practicetoeicapp.admin.AdminActivity;
import com.example.practicetoeicapp.database.Database;
import com.example.practicetoeicapp.database.DatabaseAccess;
import com.example.practicetoeicapp.singletonpattern.MessageObject;
import com.example.practicetoeicapp.taikhoan.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private final MessageObject messageObject = MessageObject.getInstance();
    final  String DATABASE_NAME = "HocNgonNgu.db";
    Boolean doubleBack = false;
    SQLiteDatabase database;
    DatabaseAccess DB;
    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DB = DatabaseAccess.getInstance(getApplicationContext());
        LayUser();


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Ứng dụng sẽ chuyển sang gmail", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        String emailTo = "21102003tai@gmail.com";
                        String subject ="Phản hồi về ứng dụng luyện thi Toeic";
                        sendEmail(emailTo, subject);
                    }
                }, 1000); // 1000 milliseconds = 1 giây
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }
    private void sendEmail(String emailTo, String subject){
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{emailTo});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);

        try{
            startActivity(Intent.createChooser(emailIntent,"Choose an Email Client"));
        }
        catch (Exception ex){
            Toast.makeText(this, ex.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.action_admin){
            if (user.getRole()==1) {
                Intent intent = new Intent(MainActivity.this, AdminActivity.class);
                startActivity(intent);
            }
            else {
                messageObject.ShowDialogMessage(Gravity.CENTER,
                        MainActivity.this,
                        "KHÔNG THỂ TRUY CẬP!!Tài khoản của bạn không phải Quản Lý!!",
                        0);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (doubleBack) {
            super.onBackPressed();
            finish();
            //thoát ra Login
            moveTaskToBack(true);
        }
        this.doubleBack = true;
        Toast.makeText(getApplicationContext(), "Nhấn lần nữa để thoát", Toast.LENGTH_SHORT).show();
        //thời gian chờ là 2s
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBack = false;
            }
        }, 2000);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    public void LayUser()
    {
        database = Database.initDatabase(MainActivity.this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM User WHERE ID_User = ?",new String[]{String.valueOf(DB.iduser)});
        cursor.moveToNext();
        String Iduser = cursor.getString(0);
        String HoTen = cursor.getString(1);
        int Point = cursor.getInt(2);
        String Email = cursor.getString(3);
        String SDT = cursor.getString(4);
        int Role = cursor.getInt(5);
        user = new User(Iduser,HoTen,Point,Email,SDT,Role);
        cursor.close();
    }


}