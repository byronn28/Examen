/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ModeloPeliculas;
import Vista.MenuPrincipal;
import Vista.VistaPeliculas;

/**
 *
 * @author byron
 */
public class ControladorMenu {
    MenuPrincipal vistaMenu;

    public ControladorMenu(MenuPrincipal vistaMenu) {
        this.vistaMenu = vistaMenu;
        vistaMenu.setVisible(true);
        vistaMenu.setSize(1150,800);
    }
    
    public void iniciaControl(){
        vistaMenu.getMnPeliculas().addActionListener(l->crudPeliculas());
    }
    
    public void crudPeliculas(){
        VistaPeliculas vista = new VistaPeliculas();
        vistaMenu.setLocationRelativeTo(null);
        vistaMenu.getjDesktopPane1().add(vista);
        
        ModeloPeliculas modelo = new ModeloPeliculas();
        ControladorPeliculas controlador = new ControladorPeliculas(modelo, vista);
        controlador.iniciaControl();
    }
    
}
