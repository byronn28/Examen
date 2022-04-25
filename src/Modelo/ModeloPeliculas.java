/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

/**
 *
 * @author byron
 */
public class ModeloPeliculas extends Peliculas {
    ConectionPg cpg = new ConectionPg();
    PreparedStatement ps;
    ResultSet rs;


    public ModeloPeliculas(String nombre, String codigo, String sinopsis, String clasificacion, Integer duracion, String protagonistas, Image portada, String salas, Double valor, FileInputStream imagen, int largo) {
        super(nombre, codigo, sinopsis, clasificacion, duracion, protagonistas, portada, salas, valor, imagen, largo);
    }
    public ModeloPeliculas(){
        
    }

    
    public List <Peliculas> listaPeliculas(){
        List<Peliculas>lista=new ArrayList<Peliculas>();
        try {
            
            String sql= "SELECT * from peliculas";
            ResultSet rs= cpg.consulta(sql);
            byte[] bytea;
            while(rs.next()){
                Peliculas peli = new Peliculas();
                peli.setCodigo(rs.getString("codigo"));
                peli.setNombre(rs.getString("nombre"));
                peli.setSinopsis(rs.getString("sinopsis"));
                peli.setClasificacion(rs.getString("clasificacion"));
                peli.setDuracion(rs.getInt("duracion"));
                peli.setProtagonistas(rs.getString("protagonistas"));
                peli.setSalas(rs.getString("salas"));
                peli.setValor(rs.getDouble("valor"));
               //bytea> Bytes Array
                bytea=rs.getBytes("portada");
                if (bytea!=null){
                //Decodificando del formato de la base.(Base64)
                
                 //bytea=Base64.decode(bytea,0,bytea.length);
                    try {
                        peli.setPortada(obtenerImagen(bytea));
                    } catch (IOException ex) {
                        Logger.getLogger(ModeloPeliculas.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                lista.add(peli);
            }
            rs.close();
            return lista;
            
        } catch (SQLException ex) {
            Logger.getLogger(ModeloPeliculas.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    private Image obtenerImagen(byte[] bytes) throws IOException{
        ByteArrayInputStream bis=new ByteArrayInputStream(bytes);
        Iterator it= ImageIO.getImageReadersByFormatName("jpeg");
        ImageReader reader=(ImageReader)it.next();
        Object source=bis;
        ImageInputStream iis=ImageIO.createImageInputStream(source);
        reader.setInput(iis,true);
        ImageReadParam param= reader.getDefaultReadParam();
        param.setSourceSubsampling(1, 1, 0, 0);
        return reader.read(0,param);
    }
    
    public boolean crearPelicula(){
        try {
            String sql;
            sql="INSERT INTO peliculas(codigo,nombre,sinopsis,clasificacion,duracion,protagonistas,salas,valor,portada)";
            sql+="VALUES(?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = cpg.getCon().prepareStatement(sql);
            ps.setString(1, getCodigo());
            ps.setString(2, getNombre());
            ps.setString(3, getSinopsis());
            ps.setString(4, getClasificacion());
            ps.setInt(5, getDuracion());
            ps.setString(6, getProtagonistas());
            ps.setString(7, getSalas());
            ps.setDouble(8, getValor());
            ps.setBinaryStream(9,getImagen(),getLargo() );
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ModeloPeliculas.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public boolean eliminarPelicula(String codigo){
        String sql="DELETE FROM peliculas where codigo='"+codigo+"'";
        return cpg.accion(sql);
    }
    
    public boolean editarPelicula(){
        
        String sql="UPDATE peliculas SET \n"+
                "codigo='"+getCodigo()+"',nombre='"+getNombre()+"',sinopsis='"+getSinopsis()+"',clasificacion='"+getClasificacion()+
                "',duracion='"+getDuracion()+"',protagonistas='"+getProtagonistas()+"',salas='"+getSalas()+"',valor='"+getValor()+"'"+
                "WHERE codigo='"+getCodigo()+"';";
        return cpg.accion(sql);
    }
}
