package cl.inacap.carelesscovid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText usuarioEdText;
    private EditText passwordEdTxt;
    private Button iniciarSesionBtn;


    @Override
    protected void onResume(){
        super.onResume();
        usuarioEdText.setText("");
        passwordEdTxt.setText("");
        usuarioEdText.setBackground(getDrawable(R.color.divider));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.usuarioEdText = findViewById(R.id.nombreUsuario_EdTxt);
        this.passwordEdTxt = findViewById(R.id.password_EdTxt);
        this.iniciarSesionBtn = findViewById(R.id.iniciarSesion_btn);
        this.iniciarSesionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Recojer datos
                String rutTxt = usuarioEdText.getText().toString().trim();
                String passwordTxt = passwordEdTxt.getText().toString().trim();
                //Validar datos
                List<String> errores = new ArrayList<>();
                //VALIDAR CAMPO RUT VACIO Y AGREGAR BORDE ROJO EN CASO DE ESTAR VACIO
                if(rutTxt.isEmpty()){
                    Toast.makeText(MainActivity.this,"Debe ingresar nombre de usuario",Toast.LENGTH_SHORT).show();
                    usuarioEdText.setBackground(getDrawable(R.drawable.backwithborder));
                }else {
                    //VALIDACION RUT CHILENO

                    char[] numero = new char[8];

                    if (rutTxt.length() > 10) {    //SOLO VALIDA RUT CON 8 Digitos mas guion y digito verificador

                        errores.add("Formato incorrecto rut");
                    }

                    while (rutTxt.length() < 10 && rutTxt.length() > 8) {  //AntePone 0 al RUT CON 7 digitos ANTES DEL GUION
                        rutTxt = "0" + rutTxt;
                    }
                    if (rutTxt.length() == 10 && rutTxt.charAt(8) == '-' && (rutTxt.charAt(9) == 'k'
                            || rutTxt.charAt(9) == '0' || rutTxt.charAt(9) == '1' || rutTxt.charAt(9) == '2'
                            || rutTxt.charAt(9) == '5' || rutTxt.charAt(9) == '4' || rutTxt.charAt(9) == '3'
                            || rutTxt.charAt(9) == '6' || rutTxt.charAt(9) == '9' || rutTxt.charAt(9) == '9'
                            || rutTxt.charAt(9) == '7' || rutTxt.charAt(9) == '8')) {
                        rutTxt.getChars(0, 8, numero, 0);
                        String s = String.valueOf(numero);
                        try {
                            int num = Integer.parseInt(s);
                            int i = 7;
                            int suma = 0;
                            int j = 2;
                            for (i = 7; i > 1; i--) {
                                suma = suma + (Character.getNumericValue(numero[i]) * (j));
                                j = j + 1;
                            }
                            j = 2;
                            for (i = 1; i > -1; i--) {
                                suma = suma + (Character.getNumericValue(numero[i]) * (j));
                                j = j + 1;
                            }
                            int modulo = Math.floorDiv(suma, 11);
                            int multi = 11 * modulo;
                            int sum2 = suma - multi;

                            int resultado = 11 - Math.abs(sum2);
                            if (rutTxt.charAt(9) == 'k' && resultado != 10) {

                                errores.add("Nombre de Usuario Incorrecto");

                            }
                            if (rutTxt.charAt(9) == '0') {
                                if (resultado != 10 || resultado != 0) {
                                    errores.add("Nombre de Usuario Incorrecto");
                                }
                            }
                            if (rutTxt.charAt(9) != 'k' && rutTxt.charAt(9) != '0' && resultado != Character.getNumericValue(rutTxt.charAt(9))) {
                                errores.add("Nombre de Usuario Incorrecto");
                            }

                        } catch (Exception e) {
                            errores.add("Nombre de Usuario Incorrecto");
                        }


                    } else {
                        errores.add("Nombre de Usuario Incorrecto");
                    }

                    if (errores.isEmpty()) {
                        //VALIDAR CONTRASENIA
                        if (rutTxt.substring(4, 8).equalsIgnoreCase(passwordTxt)) {
                            //INGRESA
                            //Enviar a principal Activity
                            Intent i = new Intent(MainActivity.this, PrincipalActivity.class);
                            startActivity(i);
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Nombre de Usuario inválido", Toast.LENGTH_SHORT).show();
                        usuarioEdText.setText("");
                        passwordEdTxt.setText("");
                        usuarioEdText.setBackground(getDrawable(R.color.divider));
                    }
                }
            }
        });
    }
}


