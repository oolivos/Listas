package co.oscarolivos.listas;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by ojo on 26/05/16.
 */
public class DbHelper extends SQLiteOpenHelper {
    final static String dbName= "departamentos";
    final static int version = 6;
    private Context context;
    final static String createTableDepartamentos = "CREATE TABLE departamentos (_id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT NOT_NULL,capital TEXT NOT_NULL, poblacion TEXT NOT_NULL, imagen TEXT NOT_NULL)";
    final static String dropTableDepartamentos = "DROP TABLE IF EXISTS departamentos";

    public DbHelper(Context context) {
        super(context, dbName, null, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTableDepartamentos);
        Log.d("DB", "DB Creada!!");
        insertarDatosIniciales(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(dropTableDepartamentos);
        onCreate(db);
    }

    private void insertarDatosIniciales(SQLiteDatabase db){
        ContentValues casanare = new ContentValues();
        casanare.put("nombre","Casanare");
        casanare.put("capital","Yopal");
        casanare.put("poblacion","150000");
        casanare.put("imagen","R.drawable.casanare");
        db.insert("departamentos",null,casanare);
        Log.d("DB", "DB Insert 1!!");

        ContentValues boyaca = new ContentValues();
        boyaca.put("nombre","Boyacá");
        boyaca.put("capital","Tunja");
        boyaca.put("poblacion","250000");
        boyaca.put("imagen","R.drawable.boyaca");
        db.insert("departamentos",null,boyaca);
        Log.d("DB", "DB Insert 2!!");
        //db.close();
    }
}
