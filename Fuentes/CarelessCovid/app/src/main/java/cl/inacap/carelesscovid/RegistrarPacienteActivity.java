package cl.inacap.carelesscovid;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cl.inacap.carelesscovid.dao.PacientesDAO;
import cl.inacap.carelesscovid.dao.PacientesDAOSqLite;
import cl.inacap.carelesscovid.dto.Paciente;
import cl.inacap.carelesscovid.helpers.DatePickerFragment;

public class RegistrarPacienteActivity extends AppCompatActivity {

    private PacientesDAO pacientesDAO = new PacientesDAOSqLite(this);
    private EditText rut;
    private EditText nombre;
    private EditText apellido;
    private EditText fecha;
    private Spinner area_trab;
    private Switch sintomas;
    private EditText temperatura;
    private Switch tos;
    private EditText presion;
    private Button registrarBtn;
    private Toolbar toolbar;
    @Override
    protected void onStart(){
        super.onStart();
        this.rut.setText("");
        this.nombre.setText("");
        this.apellido.setText("");
        this.fecha.setText("Seleccione");
        this.area_trab.setSelection(0);
        this.sintomas.setChecked(false);
        this.temperatura.setText("");
        this.tos.setChecked(false);
        this.presion.setText("");
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_paciente);
        this.rut = findViewById(R.id.rut_edit_txt);
        this.nombre = findViewById(R.id.nombre_edit_text);
        this.apellido = findViewById(R.id.apellido_edit_txt);
        this.toolbar = findViewById(R.id.toolbar);
        this.setSupportActionBar(this.toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        //CARGA DE SELECTOR DE FECHA
        this.fecha = findViewById(R.id.fecha_registro_txt);

        this.fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.fecha_registro_txt:
                        showDatePickerDialog(fecha);
                        break;

                }
            }
        });

        this.area_trab = findViewById(R.id.area_trabajo_sp);

        //CARGA DE SPINNER AREA DE TRABAJO
        String[] listaAreaTrab = new String[]{"Seleccione","Atención a público","Otro"};
        ArrayAdapter<String> adapterArea = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,listaAreaTrab);
        adapterArea.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        area_trab.setAdapter(adapterArea);

        this.sintomas = findViewById(R.id.sintomas_sw);
        this.temperatura = findViewById(R.id.temperatura_registro);
        this.tos = findViewById(R.id.tos_sw);
        this.presion = findViewById(R.id.presion_registro);
        this.registrarBtn = findViewById(R.id.registrar_paciente_btn);
        this.registrarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //TOMA DE DATOS
                List<String> errores = new ArrayList<>();
                String rutTxt = rut.getText().toString().trim();
                String nombreTxt = nombre.getText().toString().trim();
                String apellidoTxt = apellido.getText().toString().trim();
                String fechaTex = fecha.getText().toString().trim();
                String areaTxt = area_trab.getSelectedItem().toString().trim();
                String tempTxt = temperatura.getText().toString().trim();
                String presionTxt = presion.getText().toString().trim();

                //VALIDAR CAMPOS VACIOS: rut, nombre, apellido, fecha, area Trabajo, temperatura, presion.

                if(rutTxt.isEmpty() || nombreTxt.isEmpty() || apellidoTxt.isEmpty() ||
                        fechaTex.equalsIgnoreCase("Seleccione") ||
                        areaTxt.equalsIgnoreCase("Seleccione") ||
                        tempTxt.isEmpty() || presionTxt.isEmpty()){
                    errores.add("Hay campos vacíos");
                }

                //VALIDAR RUT CHILENO
                char[] numero = new char[8];



                while(rutTxt.length()<10) {
                    rutTxt = "0"+rutTxt;
                }

                if(rutTxt.length()>10) {    //Solo mayores a los 10 millones

                    errores.add("Formato incorrecto rut");
                }else
                if(rutTxt.length()==10 && rutTxt.charAt(8)=='-' &&(rutTxt.charAt(9)=='k'
                        || rutTxt.charAt(9)=='0' || rutTxt.charAt(9)=='1' || rutTxt.charAt(9)=='2'
                        || rutTxt.charAt(9)=='5' || rutTxt.charAt(9)=='4' || rutTxt.charAt(9)=='3'
                        || rutTxt.charAt(9)=='6' || rutTxt.charAt(9)=='9' || rutTxt.charAt(9)=='9'
                        || rutTxt.charAt(9)=='7' || rutTxt.charAt(9)=='8')) {
                    rutTxt.getChars(0, 8, numero, 0);
                    String s = String.valueOf(numero);
                    try {
                        int num = Integer.parseInt(s);
                        int i=7;
                        int suma=0;
                        int j=2;
                        for(i=7;i>1;i--) {
                            suma = suma+(Character.getNumericValue(numero[i])*(j));
                            j=j+1;
                        }
                        j=2;
                        for(i=1;i>-1;i--) {
                            suma = suma+(Character.getNumericValue(numero[i])*(j));
                            j=j+1;
                        }
                        int modulo = Math.floorDiv(suma, 11);
                        int multi = 11*modulo;
                        int sum2 = suma - multi;

                        int resultado = 11-Math.abs(sum2);
                        if(rutTxt.charAt(9)=='k' && resultado!=10) {

                            errores.add("Digito verificador incorrecto");

                        }
                        if(rutTxt.charAt(9)=='0') {
                            if(resultado!=10 || resultado!=0) {
                                errores.add("Digito verificador incorrecto");
                            }
                        }
                        if(rutTxt.charAt(9)!='k' && rutTxt.charAt(9)!='0' && resultado!=Character.getNumericValue(rutTxt.charAt(9))) {
                            errores.add("Digito verificador incorrecto ");
                        }

                    }catch(Exception e) {
                        errores.add("Rut formato incorrecto");
                    }finally{
                        int i = 0;
                        int count = 0; //Contador de ceros que anteponen al rut
                        while(rutTxt.charAt(i) == '0'){
                            i++;
                            count++;
                        }
                        rutTxt = rutTxt.substring(count);
                    }


                }else {
                    errores.add("Rut incorrecto");
                }

                //VALIDAR TEMPERATURA
                float temperaturaFloat = 0;
                try{
                    temperaturaFloat = Float.parseFloat(tempTxt);
                    if(temperaturaFloat <= 20){
                        throw new NumberFormatException();
                    }
                }catch(Exception e){
                    errores.add("Temperatura formato incorrecto");
                }

                //VALIDAR PRESION ARTERIAL
                int presionArterial = 0;
                try{
                    presionArterial = Integer.parseInt(presionTxt);
                }catch(Exception ex){
                    errores.add("Presion arterial formato incorrecto");
                }

                //VALIDAR FECHA

                String dia;
                String mes;

                Calendar calendario = Calendar.getInstance();
                int diaActual = calendario.get(Calendar.DATE);
                int mesActual = calendario.get(Calendar.MONTH);
                int yearActual = calendario.get(Calendar.YEAR);

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date fechaTxt = null;
                String fechaIngresar = null;
                try {
                    fechaTxt = sdf.parse(fecha.getText().toString().trim());
                    Date fechaActual = sdf.parse(""+diaActual+"/"+mesActual+"/"+yearActual);
                    if(fechaTxt.compareTo(fechaActual) < 0){

                       errores.add("Fecha mal ingresada");

                    }else{
                        fechaIngresar = sdf.format(fechaTxt);
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                //VALIDAR SWITCH SINTOMAS
                int valorSwSintomas = 1;
                if(sintomas.isChecked()){
                    valorSwSintomas = 1;
                }else{
                    valorSwSintomas = 0;
                }

                //VALIDAR SWITCH TOS
                int valorSwTos = 0;
                if(tos.isChecked()){
                    valorSwTos = 1;
                }else {
                    valorSwTos = 0;
                }

                //REGISTRAR
                if(errores.isEmpty()){
                    Paciente p = new Paciente();
                    p.setRut(rutTxt);
                    p.setNombre(nombreTxt);
                    p.setApellido(apellidoTxt);
                    p.setFechaExamen(fechaIngresar);
                    p.setAreaTrabajo(areaTxt);
                    p.setSintomasCovid(valorSwSintomas);
                    p.setTemperatura(temperaturaFloat);
                    p.setPresionArterial(presionArterial);
                    p.setTos(valorSwTos);
                    pacientesDAO.save(p);
                    //REDIRIGIR
                    startActivity(new Intent(RegistrarPacienteActivity.this, PrincipalActivity.class));

                }else {
                    //MOSTRAR ERRORES
                    mostrarErrores(errores);
                }
            }
        });
    }
    public void showDatePickerDialog(final EditText editText){
        DialogFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {


                final String selectedDate = day +"/"+month+"/"+year;
                editText.setText(selectedDate);






            }
        });

        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
    public void mostrarErrores(List<String> errores){
        String mensaje = "";
        for(String e:errores){
            mensaje+= "-" + e + "\n";
        }
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(RegistrarPacienteActivity.this);
        alertBuilder.setTitle("Error de validacion")
                .setMessage(mensaje)
                .setPositiveButton("Aceptar",null)
                .create()
                .show();

    }
}


//TODO: REVISAR VALIDACIONES
