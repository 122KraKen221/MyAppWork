package com.example.myappwork;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EmployeeActivity extends AppCompatActivity {
    private EditText nameEditText;
    private EditText positionEditText;
    private Button delButton;
    private DataBaseAdapter adapter;
    private long employeeId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        nameEditText = findViewById(R.id.EmployeeName);
        positionEditText = findViewById(R.id.EmployeePosition);
        delButton = findViewById(R.id.deleteButton);

        adapter = new DataBaseAdapter(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            employeeId = extras.getLong("id");
        }

        if (employeeId > 0) {
            adapter.open();
            Employee employee = adapter.getEmployee(employeeId);
            nameEditText.setText(employee.getName());
            positionEditText.setText(employee.getPosition());
            adapter.close();
        } else {
            delButton.setVisibility(View.GONE);
        }
    }

    public void save(View view) {
        String name = nameEditText.getText().toString();
        String position = positionEditText.getText().toString();
        Employee employee = new Employee(employeeId, name, position);

        adapter.open();
        if (employeeId > 0) {
            adapter.update(employee);
        } else {
            adapter.insert(employee);
        }
        adapter.close();
        Toast.makeText(this, "Сотрудник успешно сохранен!", Toast.LENGTH_SHORT).show();
        goHome();
    }

    public void delete(View view) {
        adapter.open();
        adapter.delete(employeeId);
        adapter.close();
        Toast.makeText(this, "Сотрудник успешно удален!", Toast.LENGTH_SHORT).show();
        goHome();
    }

    private void goHome() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }


}