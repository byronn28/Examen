/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ConectionPg;
import Modelo.ModeloPeliculas;
import Modelo.Peliculas;
import Vista.VistaPeliculas;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.xml.ws.Holder;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author byron
 */
public class ControladorPeliculas {
   private ModeloPeliculas  modelo;
   private VistaPeliculas vista;
   private JFileChooser jfc;

    public ControladorPeliculas(ModeloPeliculas modelo, VistaPeliculas vista) {
        this.modelo = modelo;
        this.vista = vista;
        vista.setVisible(true);
        cargarPeliculas();
    }
    
    public void iniciaControl(){

        vista.getBtnCrear().addActionListener(l->abrirDialogo(1));
        vista.getBtnEditar().addActionListener(l->abrirDialogo(2));
        //escuchas del dialogo
        vista.getBtnGuardar().addActionListener(l->crearEditarPersona());
        vista.getBtnEliminar().addActionListener(l->eliminarPersona());
        vista.getBtnExaminar().addActionListener(l->examinaFoto());
        vista.getBtnImprimir().addActionListener(l->reporteListaPeliculas());
    }
   
   private void abrirDialogo(int ce){
        String title;
        if(ce==1){
            title="CREAR PELICULA";
            vista.getDlgPeliculas().setName("crear");
            limpiarCampos();
            cargarPeliculas();
        }else{
            title="EDITAR PELICULA";
            vista.getDlgPeliculas().setName("editar");
            modificar();
            cargarPeliculas();
        }
        vista.getDlgPeliculas().setLocationRelativeTo(vista);
        vista.getDlgPeliculas().setSize(660,620);
        vista.getDlgPeliculas().setTitle(title);
        vista.getDlgPeliculas().setVisible(true);
        vista.getDlgPeliculas().setLocationRelativeTo(vista);
        //vista.getBtnSalir().addActionListener(l->salir());
    }
   
   private void crearEditarPersona(){
        
        if(vista.getDlgPeliculas().getName()=="crear"){
            //crear
                    
            String codigo=vista.getTxtCodigo().getText();
            String nombre=vista.getTxtNombre().getText();
            String sinopsis=vista.getTxtSinopsis().getText();
            String clasificacion=vista.getCbClasificacion().getSelectedItem().toString();
            String duracion=vista.getTxtDuracion().getText();
            String protagonistas = vista.getTxtProtagonistas().getText();
            String salas =vista.getCbSala().getSelectedItem().toString();
            String valor =vista.getTxtValor().getText();
            
            ModeloPeliculas peli = new ModeloPeliculas();
            
            peli.setCodigo(codigo);
            peli.setNombre(nombre);
            peli.setSinopsis(sinopsis);
            peli.setClasificacion(clasificacion);
            peli.setDuracion(Integer.parseInt(String.valueOf(duracion)));
            peli.setProtagonistas(protagonistas);
            peli.setSalas(salas);
            peli.setValor(Double.parseDouble(String.valueOf(valor)));
            try {
                FileInputStream img = new FileInputStream(jfc.getSelectedFile());
                int largo=(int)jfc.getSelectedFile().length();
                peli.setImagen(img);
                peli.setLargo(largo);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ControladorPeliculas.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(peli.crearPelicula()){
                JOptionPane.showMessageDialog(vista, "Creado exitosamente");
                vista.getDlgPeliculas().setVisible(false);
            }else{
                JOptionPane.showMessageDialog(vista, "No se creo");
            }
            cargarPeliculas();
            
        }else if(vista.getDlgPeliculas().getName()=="editar"){
            
            ModeloPeliculas pelic = new ModeloPeliculas();
            
            pelic.setCodigo(vista.getTxtCodigo().getText());
            pelic.setNombre(vista.getTxtNombre().getText());
            pelic.setSinopsis(vista.getTxtSinopsis().getText());
            pelic.setClasificacion(vista.getCbClasificacion().getSelectedItem().toString());
            pelic.setDuracion(Integer.parseInt(String.valueOf(vista.getTxtDuracion().getText())));
            pelic.setProtagonistas(vista.getTxtProtagonistas().getText());
            pelic.setSalas(vista.getCbSala().getSelectedItem().toString());
            pelic.setValor(Double.parseDouble(String.valueOf(vista.getTxtValor().getText())));
            //ep.setFoto((Image) vista.getLblFoto().getIcon());
            
            if(pelic.editarPelicula()){
                JOptionPane.showMessageDialog(vista, "Editado exitosamente");
                vista.getDlgPeliculas().setVisible(false);
            }else{
                JOptionPane.showMessageDialog(vista, "No se edito");
            }
        }
        cargarPeliculas();
        
    }
   
   public void modificar(){
        int select = vista.getTblPeliculas().getSelectedRow();
        
        if(select!=-1){
            String rsp = vista.getTblPeliculas().getValueAt(select, 0).toString();
            List<Peliculas> tabla = modelo.listaPeliculas();
            
            for(int j= 0; j<tabla.size(); j++){
                if(tabla.get(j).getCodigo().equals(rsp)){
                    vista.getTxtCodigo().setText(tabla.get(j).getCodigo());
                    vista.getTxtNombre().setText(tabla.get(j).getNombre());
                    vista.getTxtSinopsis().setText(tabla.get(j).getSinopsis());
                    vista.getCbClasificacion().setSelectedItem(tabla.get(j).getClasificacion());
                    vista.getTxtDuracion().setText(String.valueOf(tabla.get(j).getDuracion()));
                    vista.getTxtProtagonistas().setText(tabla.get(j).getProtagonistas());
                    vista.getCbSala().setSelectedItem(tabla.get(j).getClasificacion());
                    vista.getTxtValor().setText(String.valueOf(tabla.get(j).getValor()));
                    //vista.getLblFoto().setIcon((Icon) tabla.get(j).getFoto());
                    vista.getLblFoto().setIcon((Icon) tabla.get(j).getImagen());
                }
            }
            cargarPeliculas();
        }
        else{
            JOptionPane.showMessageDialog(vista, "SELECCIONA UN REGISTRO", "AVISO", 2);
        }
    }
    
    public void eliminarPersona(){
        ModeloPeliculas pl = new ModeloPeliculas();
        int fila=vista.getTblPeliculas().getSelectedRow();

        if(fila==-1){
            JOptionPane.showMessageDialog(vista, "SELECCIONA UN REGISTRO", "AVISO", 2);
        }else{
            int rsp =0;
            rsp=JOptionPane.showConfirmDialog(vista, "SEGURO QUE DESEAS ELIMINAR EL REGISTRO");
            //cargarPersonas();
            
            if(rsp==0){
                String codigo=vista.getTblPeliculas().getValueAt(fila, 0).toString();
                pl.eliminarPelicula(codigo);
                cargarPeliculas();
            }else{
               JOptionPane.showMessageDialog(vista, "NO SE ELIMINO"); 
            }
        }
        
    }
    
    private void cargarPeliculas(){
        vista.getTblPeliculas().setDefaultRenderer(Object.class, new ImagenTabla());//La manera de renderizar la tabla.
        vista.getTblPeliculas().setRowHeight(100);
        
        //Enlazar el modelo de tabla con mi controlador.
        DefaultTableModel tblModel;
        tblModel=(DefaultTableModel)vista.getTblPeliculas().getModel();
        tblModel.setNumRows(0);//limpio filas de la tabla.

        List<Peliculas> listap=modelo.listaPeliculas();//Enlazo al Modelo y obtengo los datos
        Holder<Integer> i = new Holder<>(0);//contador para el no. fila
        //SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        
        listap.stream().forEach(pe->{
            tblModel.addRow(new Object[9]);//Creo una fila vacia/
            vista.getTblPeliculas().setValueAt(pe.getCodigo(), i.value, 0);
            vista.getTblPeliculas().setValueAt(pe.getNombre(), i.value, 1);
            vista.getTblPeliculas().setValueAt(pe.getSinopsis(), i.value, 2);
            vista.getTblPeliculas().setValueAt(pe.getClasificacion(), i.value, 3);
            vista.getTblPeliculas().setValueAt(pe.getDuracion(), i.value, 4);
            vista.getTblPeliculas().setValueAt(pe.getProtagonistas(), i.value, 5);
            vista.getTblPeliculas().setValueAt(pe.getSalas(), i.value, 6);
            vista.getTblPeliculas().setValueAt(pe.getValor(), i.value, 7);
            Image foto=pe.getPortada();
            if(foto!=null){
            
                Image nimg= foto.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                ImageIcon icono=new ImageIcon(nimg);
                DefaultTableCellRenderer renderer= new DefaultTableCellRenderer();
                renderer.setIcon(icono);
                vista.getTblPeliculas().setValueAt(new JLabel(icono), i.value, 8);
                
            }else{
                 vista.getTblPeliculas().setValueAt(null, i.value, 8);
            }
            i.value++;
        });
    }
    
    private void examinaFoto(){
        jfc = new JFileChooser();
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("JPG,PNG & GIF", "jpg","png","gif");
        jfc.setFileFilter(filtro);
        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int estado=jfc.showOpenDialog(vista);
        
        if(estado==JFileChooser.APPROVE_OPTION){
            try {
                Image imagen = ImageIO.read(jfc.getSelectedFile()).getScaledInstance(
                        vista.getLblFoto().getWidth(),
                        vista.getLblFoto().getHeight(),
                        Image.SCALE_DEFAULT);
                Icon icono = new ImageIcon(imagen);
                vista.getLblFoto().setIcon(icono);
                vista.getLblFoto().updateUI();
            } catch (IOException ex) {
                Logger.getLogger(ControladorPeliculas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void reporteListaPeliculas(){
       ConectionPg connection = new ConectionPg();
        try {

           JasperReport jr=(JasperReport)JRLoader.loadObject(getClass().getResource("/Vista/Peliculas.jasper"));
           Map<String,Object> parametros= new HashMap<String, Object>();
           /*parametros.put("limitesueldo", 2000);
           parametros.put("sueldomin", 2000);
           parametros.put("titulo", "LISTA CLIENTES");*/
           JasperPrint jp = JasperFillManager.fillReport(jr, null, connection.getCon());
           JasperViewer jv = new JasperViewer(jp,false);
           
           jv.setVisible(true);
       } catch (JRException ex) {
           Logger.getLogger(ControladorPeliculas.class.getName()).log(Level.SEVERE, null, ex);
       }
        
    }
    
    public void limpiarCampos(){
        vista.getTxtCodigo().setText("");
        vista.getTxtNombre().setText("");
        vista.getTxtSinopsis().setText("");
        vista.getTxtDuracion().setText("");
        vista.getTxtProtagonistas().setText("");
        vista.getLblFoto().setText(null);
        vista.getCbClasificacion().setSelectedItem(null);
        vista.getCbSala().setSelectedItem(null);
        vista.getTxtValor().setText("");
    }
}
