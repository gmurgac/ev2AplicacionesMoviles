package cl.inacap.carelesscovid.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import cl.inacap.carelesscovid.R;
import cl.inacap.carelesscovid.dto.Paciente;

public class PacientesListAdapter extends ArrayAdapter<Paciente> {
    private List<Paciente> pacientes;
    private Activity activity;

    public PacientesListAdapter(@NonNull Activity context, int resource, @NonNull List<Paciente> objects) {
        super(context, resource, objects);
        this.pacientes = objects;
        this.activity = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.activity.getLayoutInflater();
        View fila = inflater.inflate(R.layout.pacientes_list, null, true);
        ImageView iconoAlerta = fila.findViewById(R.id.icon_alert_pacienteIV);
        TextView datosPaciente = fila.findViewById(R.id.datos_paciente_examen_tv);

        Paciente p = pacientes.get(position);
        if(p.isSintomasCovid() == 1 || p.getTemperatura() > 37 || p.isTos() == 1) {
            iconoAlerta.setImageResource(R.drawable.ic_baseline_warning_24);
        }else{
            iconoAlerta.setImageResource(R.drawable.ic_baseline_thumb_up_24);
        }
        datosPaciente.setText(p.toString());
        return fila;
    }
}
