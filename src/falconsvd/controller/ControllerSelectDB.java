/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package falconsvd.controller;

import falconsvd.gui.FalconSVD;
import falconsvd.model.DBImages;
import falconsvd.model.ImagePNM;
import falconsvd.model.LoadImagesPNM;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JFileChooser;

/**
 * Esta es una clase controladora de eventos para el
 * elemento de menu 'Select DB' de la clase JFrame
 * falconsvd.gui.FalconSVD.
 * 
 * @author Sebastian
 * @version 1.0
 */


public class ControllerSelectDB {
    
    public static DBImages dbImages;
    
    public static void actionPerformed(ActionEvent e, int option) {
        JFileChooser fileChooser = new JFileChooser("faces/");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int selection = fileChooser.showOpenDialog(FalconSVD.tabbedPanel);
        if(selection == JFileChooser.APPROVE_OPTION) {
            File dirDBImages = fileChooser.getSelectedFile();
            registerProgress(25, "Directorio DB de imagenes seleccionado "+dirDBImages.getName());
            LoadImagesPNM loadImagesPNM = new LoadImagesPNM(dirDBImages.getAbsolutePath());
            try {
                loadImagesPNM.load(option);
            } catch(NullPointerException exc) {
                registerProgress(100, "La estructura de la base de datos no es la correcta, por favor lea la documentacion.");
            }
            ArrayList<ImagePNM> pnmImages = loadImagesPNM.getArrayImages();
            if(pnmImages.size() > 0) {
                registerProgress(50, "Las imagenes del directorio DB han sido leidas con exito");
                registerProgress(70, "Numero de imagenes cargadas "+pnmImages.size());
                dbImages = new DBImages(pnmImages);
                dbImages.buildDBImages();
                registerProgress(100, "La martix DB de imagenes ha sido construida con exito");
                FalconSVD.menuProcessMedia.setEnabled(true);
            } else {
                registerProgress(80, "El directorio DB no contiene imagenes");
                registerProgress(100, "La martix DB de imagenes no ha sido construida");
            }
        }
    }
    
    /**
     * Metodo que registra en el area de log el progreso de lectura
     * de los archivos de imagen del conjunto de base de datos y
     * la construccion de la DB de imagenes.
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
                    FalconSVD.textAreaLog.append("SelectDB::FalconSVD [OK]\t "+message+"\n");
                    FalconSVD.progressBar.setValue(progress);
                    FalconSVD.progressBar.setString(progress+"%");
                    if(progress == 100) {
                        Thread.sleep(500);
                        FalconSVD.progressBar.setValue(0);
                        FalconSVD.progressBar.setString(0+"%");
                    }
                } catch (InterruptedException ex) {
                    FalconSVD.textAreaLog.append("SelectDB::FalconSVD [ERROR]\t Error al dormir hilo");
                }
            }
        };
        Thread hilo = new Thread(runnable);
        hilo.start();
    }
}
