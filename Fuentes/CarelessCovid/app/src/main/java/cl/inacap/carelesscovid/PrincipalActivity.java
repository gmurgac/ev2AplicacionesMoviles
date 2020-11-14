package cl.inacap.carelesscovid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import cl.inacap.carelesscovid.adapters.PacientesListAdapter;
import cl.inacap.carelesscovid.dao.PacientesDAO;
import cl.inacap.carelesscovid.dao.PacientesDAOSqLite;
import cl.inacap.carelesscovid.dto.Paciente;

public class PrincipalActivity extends AppCompatActivity {

    private ListView pacientesLv;
    private PacientesListAdapter adapter;
    private List<Paciente> pacientes;
    private PacientesDAO pacientesDAO = new PacientesDAOSqLite(this);
    private FloatingActionButton agregarPacienteBtn;
    private Toolbar toolbar;

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onResume(){
        super.onResume();
        //Cargar ListView
        this.pacientesLv = findViewById(R.id.examenes_pacientes_lv);
        this.pacientes = this.pacientesDAO.getAll();
        if(pacientes!=null) {
            this.adapter = new PacientesListAdapter(this, R.layout.pacientes_list, this.pacientes);
            this.pacientesLv.setAdapter(this.adapter);
        }
        //Enviar a ver paciente
        this.pacientesLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Paciente paciente = pacientes.get(i);
                Intent intent = new Intent(PrincipalActivity.this, VerPacienteActivity.class);
                intent.putExtra("paciente",paciente);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        this.toolbar = findViewById(R.id.idToolbarEnPrincipal);
        this.setSupportActionBar(this.toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        //Enviar a registrar paciente
        this.agregarPacienteBtn = findViewById(R.id.agregar_paciente_fbtn);
        this.agregarPacienteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PrincipalActivity.this, RegistrarPacienteActivity.class);
                startActivity(i);
            }
        });
    }
}

