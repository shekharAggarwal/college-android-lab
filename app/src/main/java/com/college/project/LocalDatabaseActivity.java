package com.college.project;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LocalDatabaseActivity extends AppCompatActivity {
    private SQLiteDatabase myDatabase;
    private EditText edt_name, edt_data;
    private TextView text_name, text_data;
    private Button btn_save, btn_get;

    @Override
    protected void onStart() {
        super.onStart();
        myDatabase = openOrCreateDatabase("Test", MODE_PRIVATE, null);
        myDatabase.execSQL("CREATE TABLE IF NOT EXISTS labClass(name VARCHAR,data VARCHAR);");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_database);
        edt_name = findViewById(R.id.name);
        edt_data = findViewById(R.id.data);
        text_name = findViewById(R.id.last_name);
        text_data = findViewById(R.id.last_data);
        btn_get = findViewById(R.id.btn_get);
        btn_save = findViewById(R.id.btn_save);


        btn_save.setOnClickListener(v -> {
            if (!edt_name.getText().toString().isEmpty() && !edt_data.getText().toString().isEmpty()) {
                try {
                    ContentValues values = new ContentValues();
                    values.put("name", String.valueOf(edt_name.getText()));
                    values.put("data", String.valueOf(edt_data.getText()));
                    myDatabase.insert("labClass", null, values);
                } catch (Exception e) {
                    Log.d("ERROR", e.getMessage());
                }
            } else
                Toast.makeText(LocalDatabaseActivity.this, "Check name or Data", Toast.LENGTH_SHORT).show();
        });

        btn_get.setOnClickListener(v -> {
            Cursor resultSet = myDatabase.rawQuery("Select * from labClass", null);
            resultSet.moveToLast();
            if (resultSet.getCount() != 0) {
                String name = resultSet.getString(0);
                String data = resultSet.getString(1);
                text_name.setText(name);
                text_data.setText(data);
            }
            resultSet.close();
        });
    }
}