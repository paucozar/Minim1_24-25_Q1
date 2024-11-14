package edu.upc.dsa.models;

import java.util.ArrayList;
import java.util.List;


public class Usuario {
    private String id;
    private String nombre;
    private String apellido;
    private String email;
    private String fechaNacimiento;
    private List<PuntoInteres> puntosInteres;

    public Usuario() {
    }

    public Usuario(String id, String nombre, String apellido, String email, String fechaNacimiento) {
        this();
        this.setId(id);
        this.setNombre(nombre);
        this.setApellido(apellido);
        this.setEmail(email);
        this.setFechaNacimiento(fechaNacimiento);
        this.puntosInteres = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public List<PuntoInteres> getPuntosInteres() {
        return puntosInteres;
    }

    public void setPuntosInteres(List<PuntoInteres> puntosInteres) {
        this.puntosInteres = puntosInteres;
    }

    @Override
    public String toString() {
        return "Usuario [id="+id+", nombre=" + nombre + ", apellido=" + apellido + ", email=" + email + ", fechaNacimiento=" + fechaNacimiento +"]";
    }

}
