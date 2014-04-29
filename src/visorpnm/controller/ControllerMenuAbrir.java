/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package visorpnm.controller;

import falconsvd.model.FilePNM;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import visorpnm.gui.VisorPNM;
import visorpnm.model.DrawImagePNM;

/**
 * Esta es una clase controladora de eventos para el menu abrir
 * de la clase visorpnm.gui.VisorPNM.
 * 
 * @author Sebastian
 * @version 1.0
 */

public class ControllerMenuAbrir {

    public static VisorPNM objVisor;
    
    public static void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser("faces/");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("PPM, PGM, PBM", "ppm", "pgm", "pbm");
        fileChooser.setFileFilter(filter);
        int selection = fileChooser.showOpenDialog(objVisor);
        if(selection == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            FilePNM filePNM = new FilePNM(file.getAbsolutePath());
            filePNM.openFile();
            DrawImagePNM drawImagePNM = new DrawImagePNM(filePNM.getImagePNM(), objVisor.panelDraw);
            drawImagePNM.draw();
        }
    }
    
}
