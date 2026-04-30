package com.ironbasetrack.ironbasetrack.model;

public class Usuario {
    private int idUsuario;
    private String nombre;
    private String email;
    private int idRol; // 1 = Admin, 3 = Atleta (según lo tengas en tu BD)

    public Usuario(int idUsuario, String nombre, String email, int idRol) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.email = email;
        this.idRol = idRol;
    }

    // Genera los Getters y Setters
    public int getIdUsuario() { return idUsuario; }
    public String getNombre() { return nombre; }
    public String getEmail() { return email; }
    public int getIdRol() { return idRol; }
}