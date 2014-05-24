/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package falconsvd.controller;

import falconsvd.gui.FalconSVD;
import falconsvd.model.FilePNM;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Esta es una clase controladora de eventos para el
 * elemento de menu 'Save -> Match' de la clase JFrame
 * falconsvd.gui.FalconSVD.
 * 
 * @author sebaxtian
 * @version 1.0
 */


public class ControllerSaveMatch {
    
    public static void actionPerformed(ActionEvent e) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                JFileChooser fileChooser = new JFileChooser("faces/");
                FileNameExtensionFilter filter = new FileNameExtensionFilter("PPM, PGM, PBM", "ppm", "pgm", "pbm");
                fileChooser.setFileFilter(filter);
                int selection = fileChooser.showSaveDialog(FalconSVD.tabbedPanel);
                if(selection == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    registerProgress(25, "La imagen match se guardara en el archivo "+file.getName());
                    FilePNM filePNM = new FilePNM(file.getAbsolutePath());
                    filePNM.setImagePNM(ControllerFalconRecognition.imageMatch);
                    registerProgress(50, "Se inicia la configuracion y escritura del archivo");
                    filePNM.saveFile();
                    registerProgress(100, "El archivo de imagen match se ha guardado con exito");
                }
            }
        };
        Thread hilo = new Thread(runnable);
        hilo.start();
    }
    
    /**
     * Metodo que registra en el area de log el progreso de la construccion
     * del archivo match cuando se guarda el archivo.
     * 
     * @param progress
     * @param message 
     */
    private static void registerProgress(final int progress, final String message) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(300);
                    FalconSVD.textAreaLog.append("SaveMatch::FalconSVD [OK]\t "+message+"\n");
                    FalconSVD.progressBar.setValue(progress);
                    FalconSVD.progressBar.setString(progress+"%");
                    if(progress == 100) {
                        Thread.sleep(500);
                        FalconSVD.progressBar.setValue(0);
                        FalconSVD.progressBar.setString(0+"%");
                    }
                } catch (InterruptedException ex) {
                    FalconSVD.textAreaLog.append("SaveMatch::FalconSVD [ERROR]\t Error al dormir hilo");
                }
            }
        };
        Thread hilo = new Thread(runnable);
        hilo.start();
    }
}
