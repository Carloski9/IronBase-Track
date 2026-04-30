package com.ironbasetrack.ironbasetrack.model;

public class Ejercicio {
    private int idEjercicio;
    private String nombre;
    private String grupoMuscular;
    private String subgrupoMuscular;
    private String tipoMecanica;
    private String lateralidad;
    private String material;
    private String urlVideoDemostracion;

    public Ejercicio() {}

    public Ejercicio(int idEjercicio, String nombre, String grupoMuscular, String subgrupoMuscular,
                     String tipoMecanica, String lateralidad, String material) {
        this.idEjercicio = idEjercicio;
        this.nombre = nombre;
        this.grupoMuscular = grupoMuscular;
        this.subgrupoMuscular = subgrupoMuscular;
        this.tipoMecanica = tipoMecanica;
        this.lateralidad = lateralidad;
        this.material = material;
    }

    public int getIdEjercicio() { return idEjercicio; }
    public void setIdEjercicio(int idEjercicio) { this.idEjercicio = idEjercicio; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getGrupoMuscular() { return grupoMuscular; }
    public void setGrupoMuscular(String grupoMuscular) { this.grupoMuscular = grupoMuscular; }
    public String getSubgrupoMuscular() { return subgrupoMuscular; }
    public void setSubgrupoMuscular(String subgrupoMuscular) { this.subgrupoMuscular = subgrupoMuscular; }
    public String getTipoMecanica() { return tipoMecanica; }
    public void setTipoMecanica(String tipoMecanica) { this.tipoMecanica = tipoMecanica; }
    public String getLateralidad() { return lateralidad; }
    public void setLateralidad(String lateralidad) { this.lateralidad = lateralidad; }
    public String getMaterial() { return material; }
    public void setMaterial(String material) { this.material = material; }
    public String getUrlVideoDemostracion() {return urlVideoDemostracion; }
    public void setUrlVideoDemostracion(String urlVideoDemostracion) {this.urlVideoDemostracion = urlVideoDemostracion; }

    @Override
    public String toString() {
        return nombre + " (" + grupoMuscular + ")";
    }
}
