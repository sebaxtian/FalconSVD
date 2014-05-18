/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package falconsvd.controller;

import Jama.Matrix;
import falconsvd.model.FalconSVD;
import falconsvd.model.ImagePNM;
import java.awt.event.ActionEvent;

/**
 * Esta es una clase controladora de eventos para el
 * elemento de menu 'Process -> Run' de la clase JFrame
 * falconsvd.gui.FalconSVD.
 * 
 * @author sebaxtian
 * @version 1.0
 */


public class ControllerProcessRun {
    
    public static ImagePNM imageMatch;
    
    public static void actionPerformed(ActionEvent e) {
        Matrix matrixTarget = ControllerOpenTarget.imageTarget.getMatrix();
        Matrix matrixDB = ControllerSelectDB.dbImages.getMatrixDBImages();
        double threshold = ControllerEditThreshold.threshold;
        
    }
    
    /**
     * Metodo que registra en el area de log el progreso de la ejecucion
     * del reconocimiento de rostros mediante SVD y Eigenfaces.
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
                    falconsvd.gui.FalconSVD.textAreaLog.append("ProcessRun::FalconSVD [OK]\t "+message+"\n");
                    falconsvd.gui.FalconSVD.progressBar.setValue(progress);
                    falconsvd.gui.FalconSVD.progressBar.setString(progress+"%");
                    if(progress == 100) {
                        Thread.sleep(500);
                        falconsvd.gui.FalconSVD.progressBar.setValue(0);
                        falconsvd.gui.FalconSVD.progressBar.setString(0+"%");
                    }
                } catch (InterruptedException ex) {
                    falconsvd.gui.FalconSVD.textAreaLog.append("ProcessRun::FalconSVD [ERROR]\t Error al dormir hilo");
                }
            }
        };
        Thread hilo = new Thread(runnable);
        hilo.start();
    }
}
