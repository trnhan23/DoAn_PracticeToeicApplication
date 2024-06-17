package com.example.practicetoeicapp.admin.dienkhuyet;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.practicetoeicapp.R;
import com.example.practicetoeicapp.admin.AdminActivity;
import com.example.practicetoeicapp.bohoctap.BoHocTap;
import com.example.practicetoeicapp.bohoctap.BoHocTapAdapter;
import com.example.practicetoeicapp.database.Database;

import java.util.ArrayList;


public class AdminBoDienKhuyetActivity extends AppCompatActivity {
    final String DATABASE_NAME = "HocNgonNgu.db";
    SQLiteDatabase database;
    ImageView imgBack;
    ArrayList<BoHocTap> listBoDienKhuyet;
    BoHocTapAdapter adapter;
    ListView lvBoDienKhuyet;
    int idbo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_bodienkhuyet);
        lvBoDienKhuyet = (ListView) findViewById(R.id.listviewAdminBDK);
        imgBack = (ImageView) findViewById(R.id.imgBackAdminBDK);
        listBoDienKhuyet = new ArrayList<>();
        getBoDienKhuyet();
        adapter = new BoHocTapAdapter(AdminBoDienKhuyetActivity.this, R.layout.row_bodienkhuyet, listBoDienKhuyet);
        lvBoDienKhuyet.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminBoDienKhuyetActivity.this, AdminActivity.class));
            }
        });
        lvBoDienKhuyet.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                idbo = listBoDienKhuyet.get(position).getIdBo();
                Intent intent = new Intent(AdminBoDienKhuyetActivity.this, AdminDienKhuyetActivity.class);
                intent.putExtra("idBoDienKhuyet", idbo);
                startActivity(intent);
            }
        });
    }

    private void getBoDienKhuyet() {
        database = Database.initDatabase(AdminBoDienKhuyetActivity.this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM BoCauHoi", null);
        listBoDienKhuyet.clear();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            int id = cursor.getInt(0);
            int stt = cursor.getInt(1);
            String ten = cursor.getString(2);
            listBoDienKhuyet.add(new BoHocTap(id, stt, ten));
        }
    }
}
