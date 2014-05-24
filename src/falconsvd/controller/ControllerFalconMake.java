/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package falconsvd.controller;

import Jama.Matrix;
import falconsvd.model.FalconSVD;
import java.awt.event.ActionEvent;

/**
 * Esta es una clase controladora de eventos para el
 * elemento de menu 'Process -> FalconSVD -> Make' de la clase JFrame
 * falconsvd.gui.FalconSVD.
 * 
 * @author sebaxtian
 * @version 1.0
 */


public class ControllerFalconMake {
    
    public static FalconSVD falconSVD;
    
    public static void actionPerformed(ActionEvent e) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                registerProgress(25, "Incia carga de matrix DB de imagenes");
                Matrix matrixDB = ControllerSelectDB.dbImages.getReduceMatrixDBImages();
                registerProgress(50, "Inicia carga matrix media de la DB de imagenes");
                Matrix matrixMedia = ControllerSelectDB.dbImages.getReduceMatrixMediaImages();
                registerProgress(60, "Se carga la matrix DB con exito");
                registerProgress(70, "Se carga la matrix media con exito");
                falconSVD = new FalconSVD(matrixDB, matrixMedia);
                registerProgress(100, "Se crea con exito el objeto de reconocimiento FalconSVD");
                falconsvd.gui.FalconSVD.menuFalconDetection.setEnabled(true);
                falconsvd.gui.FalconSVD.menuFalconRecognition.setEnabled(true);
            }
        };
        Thread hilo = new Thread(runnable);
        hilo.start();
    }
    
    /**
     * Metodo que registra en el area de log el progreso de la creacion
     * del objeto de reconocimiento de rostros mediante SVD y Eigenfaces.
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
                    falconsvd.gui.FalconSVD.textAreaLog.append("FalconMake::FalconSVD [OK]\t "+message+"\n");
                    falconsvd.gui.FalconSVD.progressBar.setValue(progress);
                    falconsvd.gui.FalconSVD.progressBar.setString(progress+"%");
                    if(progress == 100) {
                        Thread.sleep(500);
                        falconsvd.gui.FalconSVD.progressBar.setValue(0);
                        falconsvd.gui.FalconSVD.progressBar.setString(0+"%");
                    }
                } catch (InterruptedException ex) {
                    falconsvd.gui.FalconSVD.textAreaLog.append("FalconMake::FalconSVD [ERROR]\t Error al dormir hilo");
                }
            }
        };
        Thread hilo = new Thread(runnable);
        hilo.start();
    }
}
