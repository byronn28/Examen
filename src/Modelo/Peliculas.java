/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.awt.Image;
import java.io.FileInputStream;

/**
 *
 * @author byron
 */
public class Peliculas {
    private String nombre;
    private String codigo;
    private String sinopsis;
    private String clasificacion;
    private Integer duracion;
    private String protagonistas;
    private Image portada;
    private String salas;
    private Double valor;
    private FileInputStream imagen;
    private int largo;

    public Peliculas(String nombre, String codigo, String sinopsis, String clasificacion, Integer duracion, String protagonistas, Image portada, String salas, Double valor, FileInputStream imagen, int largo) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.sinopsis = sinopsis;
        this.clasificacion = clasificacion;
        this.duracion = duracion;
        this.protagonistas = protagonistas;
        this.portada = portada;
        this.salas = salas;
        this.valor = valor;
        this.imagen = imagen;
        this.largo = largo;
    }

    public Peliculas() {
    }
    
    public FileInputStream getImagen() {
        return imagen;
    }

    public void setImagen(FileInputStream imagen) {
        this.imagen = imagen;
    }

    public int getLargo() {
        return largo;
    }

    public void setLargo(int largo) {
        this.largo = largo;
    }

    
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    public Integer getDuracion() {
        return duracion;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

    public String getProtagonistas() {
        return protagonistas;
    }

    public void setProtagonistas(String protagonistas) {
        this.protagonistas = protagonistas;
    }

    public Image getPortada() {
        return portada;
    }

    public void setPortada(Image portada) {
        this.portada = portada;
    }

    public String getSalas() {
        return salas;
    }

    public void setSalas(String salas) {
        this.salas = salas;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
    
    
    
    
}
