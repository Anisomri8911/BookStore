package com.example.bookstore;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView ;
    FloatingActionButton add_button ;
    DataBaseHelper myDB ;
    ArrayList<String> book_id , book_author , book_pages , book_titles;
    CustomAdapter customAdapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this , AddActivity.class );
            startActivity(intent);
        });
        myDB = new DataBaseHelper(MainActivity.this) ;
        book_id = new ArrayList<>();
        book_author = new ArrayList<>();
        book_pages = new ArrayList<>();
        book_titles = new ArrayList<>();

        StoreDataArray() ;
        customAdapter = new CustomAdapter(MainActivity.this , this, book_id,book_titles,book_author,book_pages) ;
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1) {
            recreate();
        }
    }

    void StoreDataArray () {
        Cursor cursor = myDB.readAllData() ;
        if (cursor.getCount() == 0 ){
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()) {
                book_id.add(cursor.getString(0)) ;
                book_titles.add(cursor.getString(1)) ;
                book_author.add(cursor.getString(2)) ;
                book_pages.add(cursor.getString(3)) ;
            }
        }
    }
}