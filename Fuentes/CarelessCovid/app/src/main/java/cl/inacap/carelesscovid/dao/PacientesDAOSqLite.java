package cl.inacap.carelesscovid.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import java.util.ArrayList;
import java.util.List;


import cl.inacap.carelesscovid.dto.Paciente;
import cl.inacap.carelesscovid.helpers.PacientesDBOpenHelper;

public class PacientesDAOSqLite implements PacientesDAO{

    private PacientesDBOpenHelper db;

    public PacientesDAOSqLite(Context cx){
        this.db = new PacientesDBOpenHelper(cx,
                    "DBPacientes",
                    null,
                    1
                );
    }

    @Override
    public Paciente save(Paciente p) {

        SQLiteDatabase writer = this.db.getWritableDatabase();
        String sql = String.format("INSERT INTO pacientes(" +
                "rut,nombre,apellido,fecha,area_trabajo,sintomas,tos,temperatura,presion)" +
                " VALUES('%s','%s','%s','%s','%s','%d','%d','"+p.getTemperatura()+"','%d')"
                ,p.getRut(),p.getNombre(),p.getApellido(),p.getFechaExamen(),p.getAreaTrabajo(),
                p.isSintomasCovid(),p.isTos(),p.getPresionArterial());
        writer.execSQL(sql);
        writer.close();
        return p;

    }

    @Override
    public List<Paciente> getAll() {

        SQLiteDatabase reader = this.db.getReadableDatabase();
        List<Paciente> pacientes = new ArrayList<>();
        try{

            if(reader != null) {
                Cursor c = reader.rawQuery("SELECT id,rut,nombre,apellido,fecha,area_trabajo," +
                        "sintomas,tos,temperatura,presion FROM pacientes",null);
                if(c.moveToFirst()){
                    do{
                        Paciente p = new Paciente();
                        p.setIdPaciente(c.getInt(0));
                        p.setRut(c.getString(1));
                        p.setNombre(c.getString(2));
                        p.setApellido(c.getString(3));
                        p.setFechaExamen(c.getString(4));
                        p.setAreaTrabajo(c.getString(5));
                        p.setSintomasCovid(c.getInt(6));
                        p.setTos(c.getInt(7));
                        p.setTemperatura(c.getFloat(8));
                        p.setPresionArterial(c.getInt(9));
                        pacientes.add(p);
                    }while(c.moveToNext());
                }
            }
            reader.close();
        }catch (Exception ex){
            pacientes = null;

        }
        return pacientes;
    }
}
