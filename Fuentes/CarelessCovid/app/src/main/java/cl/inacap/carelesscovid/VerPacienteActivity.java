package cl.inacap.carelesscovid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import cl.inacap.carelesscovid.dto.Paciente;

public class VerPacienteActivity extends AppCompatActivity {

    private TextView rutTv;
    private TextView nombreApellidoTv;
    private TextView fechaTv;
    private TextView areaTrabTv;
    private TextView sintomasTv;
    private TextView tosTv;
    private TextView tempTv;
    private TextView presionTv;
    private Toolbar toolbar;
    private Paciente paciente;

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_paciente);
        //AGREGAR TOOLBAR A ACTIVITY
        this.toolbar = findViewById(R.id.toolbar);
        this.setSupportActionBar(this.toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        //CARGAR CAMPOS
        this.rutTv = findViewById(R.id.rut_paciente_tv);
        this.nombreApellidoTv = findViewById(R.id.nombre_paciente_tv);
        this.fechaTv = findViewById(R.id.fecha_examen_tv);
        this.areaTrabTv = findViewById(R.id.area_trabajo_tv);
        this.sintomasTv = findViewById(R.id.sintomas_tv);
        this.tosTv = findViewById(R.id.tos_tv);
        this.tempTv = findViewById(R.id.temperatura_tv);
        this.presionTv = findViewById(R.id.presion_tv);
        //CARGAR DATOS DEL ACTIVITY PRINCIPAL
        if(getIntent().getExtras() != null){
            this.paciente = (Paciente) getIntent().getSerializableExtra("paciente");
            this.rutTv.setText(paciente.getRut());
            this.nombreApellidoTv.setText(""+paciente.getNombre()+" "+paciente.getApellido());
            this.fechaTv.setText(paciente.getFechaExamen());
            if(paciente.isSintomasCovid() == 1) {
                this.sintomasTv.setText("Si");
            }else if(paciente.isSintomasCovid() == 0){
                this.sintomasTv.setText("No");
            }
            if(paciente.isTos() == 1){
                tosTv.setText("Si");
            }else if(paciente.isTos() == 0){
                tosTv.setText("No");
            }
            //FORMATEAR TEMPERATURA A 1 DECIMAL CON SEPARADOR PERSONALIZADO
            DecimalFormatSymbols separadoresPersonalizados = new DecimalFormatSymbols();
            separadoresPersonalizados.setDecimalSeparator('.');
            DecimalFormat dcf = new DecimalFormat("#.0°", separadoresPersonalizados);
            this.tempTv.setText(""+dcf.format(paciente.getTemperatura()));
            this.presionTv.setText(""+paciente.getPresionArterial());
            this.areaTrabTv.setText(paciente.getAreaTrabajo());

        }

    }
}



