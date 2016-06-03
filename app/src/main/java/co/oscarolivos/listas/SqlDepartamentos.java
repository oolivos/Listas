package co.oscarolivos.listas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.ParcelUuid;

/**
 * Created by ojo on 2/06/16.
 */
public class SqlDepartamentos {

    private Context context;
    private DbHelper dbHelper;
    private SQLiteDatabase db;

    public SqlDepartamentos(Context context){
        this.context = context;
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();

    }

    public Cursor mostrarDepartamentos(){

        Cursor cursor = db.rawQuery("SELECT * FROM departamentos",null);
        return cursor;
    }

    public void eliminarDepartamento(int id){

        String sqlEliminar = "DELETE FROM departamentos WHERE _id ="+id;
        db.execSQL(sqlEliminar);
        //db.delete("departamentos","_id="+id,null);

    }

    public long insertarDepartamento(ContentValues contentValues){

        long id =  db.insert("departamentos",null,contentValues);
        return id;
    }

    public void cerrarConexion(){
        db.close();
    }


}
