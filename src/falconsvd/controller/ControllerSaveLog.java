/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package falconsvd.controller;

import falconsvd.gui.FalconSVD;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Esta es una clase controladora de eventos para el
 * elemento de menu 'Save -> Log' de la clase JFrame
 * falconsvd.gui.FalconSVD.
 * 
 * @author sebaxtian
 * @version 1.0
 */


public class ControllerSaveLog {

    public static void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser("faces/");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("TXT", "txt");
        fileChooser.setFileFilter(filter);
        int selection = fileChooser.showSaveDialog(FalconSVD.tabbedPanel);
        if(selection == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            registerProgress(25, "Los datos del Log se guardaran en el archivo "+file.getName());
            registerProgress(50, "Se inicia la configuracion y escritura del archivo");
            saveFile(FalconSVD.textAreaLog.getText(), file);
            registerProgress(100, "El archivo de Log se ha guardado con exito");
        }
    }
    
    /**
     * Metodo que escribe en un archivo el contenido del texto.
     * 
     * @param text
     * @param file 
     */
    private static void saveFile(String text, File file) {
        FileWriter objWriter = null;
        try {
            objWriter = new FileWriter(file);
            PrintWriter objPrint = new PrintWriter(objWriter);
            objPrint.print(text);
        } catch (IOException ex) {
            System.err.println("SaveLog::saveFile -> Error al leer buffer de archivo: "+ex.getMessage());
        } finally {
            try {
                objWriter.close();
            } catch (IOException ex) {
                System.err.println("SaveLog::saveFile -> Error al cerrar archivo: "+ex.getMessage());
            }
        }
    }
    
    /**
     * Metodo que registra en el area de log el progreso de la construccion
     * del archivo log y cuando se guarda el archivo.
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
                    FalconSVD.textAreaLog.append("SaveLog::FalconSVD [OK]\t "+message+"\n");
                    FalconSVD.progressBar.setValue(progress);
                    FalconSVD.progressBar.setString(progress+"%");
                    if(progress == 100) {
                        Thread.sleep(500);
                        FalconSVD.progressBar.setValue(0);
                        FalconSVD.progressBar.setString(0+"%");
                    }
                } catch (InterruptedException ex) {
                    FalconSVD.textAreaLog.append("SaveLog::FalconSVD [ERROR]\t Error al dormir hilo");
                }
            }
        };
        Thread hilo = new Thread(runnable);
        hilo.start();
    }
}
