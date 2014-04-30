/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package falconsvd.controller;

import falconsvd.gui.FalconSVD;
import falconsvd.model.CanvasPNM;
import falconsvd.model.FilePNM;
import falconsvd.model.ImagePNM;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import static visorpnm.controller.ControllerMenuAbrir.objVisor;

/**
 * Esta es una clase controladora de eventos para el
 * elemento de menu 'Open Target' de la clase JFrame
 * falconsvd.gui.FalconSVD.
 * 
 * @author sebaxtian
 * @version 1.0
 */


public class ControllerOpenTarget {

    public static FilePNM filePNMTarget;
    
    public static void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser("faces/");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("PPM, PGM, PBM", "ppm", "pgm", "pbm");
        fileChooser.setFileFilter(filter);
        int selection = fileChooser.showOpenDialog(objVisor);
        if(selection == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            filePNMTarget = new FilePNM(file.getAbsolutePath());
            filePNMTarget.openFile();
            ImagePNM imagePNM = filePNMTarget.getImagePNM();
            // draw image on Panel
            CanvasPNM canvasPNM = new CanvasPNM(imagePNM, FalconSVD.panelDrawTarget.getSize());
            FalconSVD.panelDrawTarget.removeAll();
            FalconSVD.panelDrawTarget.add(canvasPNM, BorderLayout.CENTER);
            canvasPNM.repaint();
        }
    }
}
