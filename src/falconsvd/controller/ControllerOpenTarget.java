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
        int selection = fileChooser.showOpenDialog(FalconSVD.tabbedPanel);
        if(selection == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            registerProgress(25, "Archivo de imagen seleccionado "+file.getName());
            filePNMTarget = new FilePNM(file.getAbsolutePath());
            filePNMTarget.openFile();
            registerProgress(60, "Se ha leido con exito el archivo de imagen");
            // draw image on Panel
            ImagePNM imagePNM = filePNMTarget.getImagePNM();
            CanvasPNM canvasPNM = new CanvasPNM(imagePNM, FalconSVD.panelDrawTarget.getSize());
            FalconSVD.panelDrawTarget.removeAll();
            FalconSVD.panelDrawTarget.add(canvasPNM, BorderLayout.CENTER);
            canvasPNM.repaint();
            registerProgress(100, "Se pinta la imagen sobre el canvas Target");
        }
    }
    
    /**
     * Metodo que registra en el area de log el progreso de la lectura
     * de la imagen objetivo y cuando se pinta sobre el canvas.
     * 
     * @param progress
     * @param message 
     */
    private static void registerProgress(int progress, String message) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(300);
                    FalconSVD.textAreaLog.append("OpenTarget::FalconSVD [OK]\t "+message+"\n");
                    FalconSVD.progressBar.setValue(progress);
                    FalconSVD.progressBar.setString(progress+"%");
                    if(progress == 100) {
                        Thread.sleep(500);
                        FalconSVD.progressBar.setValue(0);
                        FalconSVD.progressBar.setString(0+"%");
                    }
                } catch (InterruptedException ex) {
                    FalconSVD.textAreaLog.append("OpenTarget::FalconSVD [ERROR]\t Error al dormir hilo");
                }
            }
        };
        Thread hilo = new Thread(runnable);
        hilo.start();
    }
}
