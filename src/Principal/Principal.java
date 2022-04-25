/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Principal;

import Controlador.ControladorMenu;
import Controlador.ControladorPeliculas;
import Modelo.ModeloPeliculas;
import Vista.MenuPrincipal;
import Vista.VistaPeliculas;

/**
 *
 * @author byron
 */
public class Principal {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        
        /*VistaPeliculas vista = new VistaPeliculas();
        vista.setLocationRelativeTo(null);
        ModeloPeliculas modelo = new ModeloPeliculas();
        ControladorPeliculas controlador = new ControladorPeliculas(modelo, vista);
        controlador.iniciaControl();*/
        MenuPrincipal vista = new MenuPrincipal();
        ControladorMenu controller = new ControladorMenu(vista);
        controller.iniciaControl();
        vista.setLocationRelativeTo(null);
    }

}
