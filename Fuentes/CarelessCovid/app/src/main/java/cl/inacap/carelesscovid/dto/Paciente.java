package cl.inacap.carelesscovid.dto;

import java.io.Serializable;

public class Paciente implements Serializable {

    private int idPaciente;

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    private String rut;
    private String nombre;
    private String apellido;
    private String fechaExamen;
    private boolean sintomasCovid;
    private boolean tos;
    private float temperatura;
    private String areaTrabajo;
    private int presionArterial;

    @Override
    public String toString() {
        return "Paciente\n" +
                "RUT: " + rut +
                ",\n " + nombre +
                " " + apellido +
                ",\n Fecha Exámen: " + fechaExamen;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getFechaExamen() {
        return fechaExamen;
    }

    public void setFechaExamen(String fechaExamen) {
        this.fechaExamen = fechaExamen;
    }

    public int isSintomasCovid() {
        if(sintomasCovid){
            return 1;
        }else{
            return 0;
        }
    }

    public void setSintomasCovid(int sintomasCovid) {
        if(sintomasCovid == 1){
            this.sintomasCovid = true;
        }else if(sintomasCovid == 0){
            this.sintomasCovid = false;
        }
    }

    public int isTos() {
        if (tos){
            return 1;
        }else{
            return 0;
        }
    }

    public void setTos(int tos) {
        if(tos == 1){
            this.tos = true;
        }else if(tos == 0){
            this.tos = false;
        }

    }

    public float getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(float temperatura) {
        this.temperatura = temperatura;
    }

    public String getAreaTrabajo() {
        return areaTrabajo;
    }

    public void setAreaTrabajo(String areaTrabajo) {
        this.areaTrabajo = areaTrabajo;
    }

    public int getPresionArterial() {
        return presionArterial;
    }

    public void setPresionArterial(int presionArterial) {
        this.presionArterial = presionArterial;
    }
}
