package com.example.myappwork;

import android.database.DatabaseUtils;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;
public class DataBaseAdapter {

    private DataBaseHelper dbHelper;
    private SQLiteDatabase database;

    public DataBaseAdapter(Context context){
        dbHelper = new DataBaseHelper(context.getApplicationContext());
    }

    public DataBaseAdapter open(){
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }

    private Cursor getAllEntries(){
        String[] columns = new String[] {DataBaseHelper.COLUMN_ID, DataBaseHelper.COLUMN_NAME, DataBaseHelper.COLUMN_POSITION};
        return database.query(DataBaseHelper.TABLE, columns, null, null, null, null, null);
    }

    public List<Employee> getEmployees(){
        ArrayList<Employee> employees = new ArrayList<>();
        Cursor cursor = getAllEntries();

        while (cursor.moveToNext()){
            long id = cursor.getLong(cursor.getColumnIndex(DataBaseHelper.COLUMN_ID));
            String name = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_NAME));
            String position = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_POSITION));
            employees.add(new Employee(id, name, position));
        }
        cursor.close();
        return employees;
    }

    public long getCount(){
        return DatabaseUtils.queryNumEntries(database, DataBaseHelper.TABLE);
    }

    public Employee getEmployee(long id){
        Employee employee = null;
        String query = String.format("SELECT * FROM %s WHERE %s=?", DataBaseHelper.TABLE, DataBaseHelper.COLUMN_ID);
        Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(id)});
        if(cursor.moveToFirst()){
            String name = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_NAME));
            String position = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_POSITION));
            employee = new Employee(id, name, position);
        }
        cursor.close();
        return employee;
    }

    public long insert(Employee employee){
        ContentValues cv = new ContentValues();
        cv.put(DataBaseHelper.COLUMN_NAME, employee.getName());
        cv.put(DataBaseHelper.COLUMN_POSITION, employee.getPosition());
        return database.insert(DataBaseHelper.TABLE, null, cv);
    }

    public long delete(long employeeId){
        String whereClause = "_id = ?";
        String[] whereArgs = new String[]{String.valueOf(employeeId)};
        return database.delete(DataBaseHelper.TABLE, whereClause, whereArgs);
    }

    public long update(Employee employee){
        String whereClause = DataBaseHelper.COLUMN_ID + "=" + employee.getId();
        ContentValues cv = new ContentValues();
        cv.put(DataBaseHelper.COLUMN_NAME, employee.getName());
        cv.put(DataBaseHelper.COLUMN_POSITION, employee.getPosition());
        return database.update(DataBaseHelper.TABLE, cv, whereClause, null);
    }
}