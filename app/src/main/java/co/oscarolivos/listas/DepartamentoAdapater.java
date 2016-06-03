package co.oscarolivos.listas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ojo on 12/05/16.
 */
public class DepartamentoAdapater extends ArrayAdapter<Departamento> {
    Context context;
    List<Departamento> departamentos;

    public DepartamentoAdapater(Context context,  List<Departamento> departamentos) {
        super(context, R.layout.list_item_layout, departamentos);
        this.context = context;
        this.departamentos = departamentos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //View view = View.inflate(context, R.layout.list_item_layout,parent);
        LayoutInflater inflater = (LayoutInflater)context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_item_layout,parent,false);

        TextView nombre = (TextView)view.findViewById(R.id.textViewDepartamento);
        TextView capital = (TextView)view.findViewById(R.id.textViewCapital);
        TextView habitantes = (TextView)view.findViewById(R.id.textViewHabitantes);
        ImageView bandera = (ImageView)view.findViewById(R.id.imageViewBandera);

        nombre.setText(departamentos.get(position).getNombre());
        capital.setText(departamentos.get(position).getCapital());
        habitantes.setText(departamentos.get(position).getPoblacion()+" Habitantes");
        bandera.setImageResource(departamentos.get(position).getBandera());

        return view;

        //return super.getView(position, convertView, parent);

    }
}
