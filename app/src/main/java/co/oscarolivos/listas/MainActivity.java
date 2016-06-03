package co.oscarolivos.listas;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    final static int REQUEST_CODE = 1000;
    List<Departamento> departamentos;
    DepartamentoAdapater adapater;
    SqlDepartamentos sqlDepartamentos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sqlDepartamentos = new SqlDepartamentos(getApplicationContext());
        departamentos = new ArrayList<>();

        Cursor cursor = sqlDepartamentos.mostrarDepartamentos();

        if(cursor.moveToFirst()){
            do{
                Departamento nuevo = new Departamento();
                nuevo.setId(cursor.getInt(cursor.getColumnIndex("_id")));
                nuevo.setNombre(cursor.getString(cursor.getColumnIndex("nombre")));
                nuevo.setCapital(cursor.getString(cursor.getColumnIndex("capital")));
                nuevo.setPoblacion(Integer.parseInt(cursor.getString(cursor.getColumnIndex("poblacion"))));
                nuevo.setBandera(R.drawable.casanare);
                departamentos.add(nuevo);
            }while (cursor.moveToNext());
        }



        final ListView lista = (ListView)findViewById(R.id.listViewCiudades);
        //String[] ciudades = new String[]{"Bogota","Yopal","Medellin","Cartagena","Barranquilla"};

        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,ciudades);
        //lista.setAdapter(adapter);

/*
        Departamento casanare = new Departamento();
        casanare.setNombre("Casanare");
        casanare.setCapital("Yopal");
        casanare.setPoblacion(150000);
        casanare.setBandera(R.drawable.casanare);
        departamentos.add(casanare);

        Departamento boyaca = new Departamento();
        boyaca.setNombre("Boyacá");
        boyaca.setCapital("Tunja");
        boyaca.setPoblacion(200000);
        boyaca.setBandera(R.drawable.boyaca);
        departamentos.add(boyaca);

        Departamento meta = new Departamento();
        meta.setNombre("Meta");
        meta.setCapital("Villavicencio");
        meta.setPoblacion(250000);
        meta.setBandera(R.drawable.meta);
        departamentos.add(meta);
        */

        adapater = new DepartamentoAdapater(getApplicationContext(),departamentos);
        lista.setAdapter(adapater);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder dialogo = new AlertDialog.Builder(MainActivity.this);
                dialogo.setTitle("Eliminar");
                dialogo.setMessage("Esta seguro que desea Eliminar?");
                dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Departamento eliminar = departamentos.get(position);

                        try{
                            sqlDepartamentos.eliminarDepartamento(eliminar.getId());
                            departamentos.remove(position);
                            adapater.notifyDataSetChanged();
                        }catch (Exception e){
                            Toast.makeText(
                                    getApplicationContext(),
                                    "Error: "+e.getMessage(),
                                    Toast.LENGTH_LONG
                            ).show();
                        }

                    }
                });
                dialogo.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialogo.show();

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK){

            ContentValues values = new ContentValues();
            values.put("nombre",data.getExtras().getString("nombre"));
            values.put("capital",data.getExtras().getString("capital"));
            values.put("poblacion",data.getExtras().getInt("poblacion"));
            values.put("imagen",R.drawable.meta);

            try{
                long id =  sqlDepartamentos.insertarDepartamento(values);
                Departamento nuevo = new Departamento();
                nuevo.setId(Integer.parseInt(String.valueOf(id)));// ---nueva
                nuevo.setNombre(data.getExtras().getString("nombre"));
                nuevo.setCapital(data.getExtras().getString("capital"));
                nuevo.setPoblacion(data.getExtras().getInt("poblacion"));
                nuevo.setBandera(R.drawable.casanare);
                departamentos.add(nuevo);
                adapater.notifyDataSetChanged();
            }catch (Exception e){
                Toast.makeText(
                        getApplicationContext(),
                        "Error: "+e.getMessage(),
                        Toast.LENGTH_LONG
                ).show();
            }





        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_activity_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case R.id.agregar:
                Intent intent = new Intent(MainActivity.this, AgregarDepartamentoActivity.class);
                startActivityForResult(intent,REQUEST_CODE);
                break;
            case R.id.Ver:
                Toast.makeText(
                        getApplicationContext(),
                        "Voy a Ver",
                        Toast.LENGTH_LONG
                ).show();
                break;
        }

        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sqlDepartamentos.cerrarConexion();
    }
}
