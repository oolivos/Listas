package co.oscarolivos.listas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AgregarDepartamentoActivity extends AppCompatActivity {
    final static int REQUEST_CODE = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_departamento);

        Button agregar = (Button)findViewById(R.id.buttonAgregar);
        final EditText nombre = (EditText)findViewById(R.id.editTextNombre);
        final EditText capital = (EditText)findViewById(R.id.editTextCapital);
        final EditText poblacion = (EditText)findViewById(R.id.editTextPoblacion);


        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nom = nombre.getText().toString();
                String cap = capital.getText().toString();
                int pob = Integer.parseInt(poblacion.getText().toString());

                Intent intent = new Intent();
                intent.putExtra("nombre",nom);
                intent.putExtra("capital",cap);
                intent.putExtra("poblacion",pob);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}
