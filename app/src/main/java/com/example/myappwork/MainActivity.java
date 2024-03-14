package com.example.myappwork;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView userList;
    ArrayAdapter<Employee> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userList = findViewById(R.id.list);

        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Employee employee = arrayAdapter.getItem(position);
                if (employee != null) {
                    Intent intent = new Intent(getApplicationContext(), EmployeeActivity.class);
                    intent.putExtra("id", employee.getId());
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        DataBaseAdapter adapter = new DataBaseAdapter(this);
        adapter.open();

        List<Employee> employees = adapter.getEmployees();

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, employees);
        userList.setAdapter(arrayAdapter);
        adapter.close();
    }

    public void add(View view) {
        Intent intent = new Intent(this, EmployeeActivity.class);
        startActivity(intent);
    }
}