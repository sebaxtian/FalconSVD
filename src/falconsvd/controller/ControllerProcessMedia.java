/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package falconsvd.controller;

import Jama.Matrix;
import falconsvd.gui.FalconSVD;
import falconsvd.model.CanvasPNM;
import falconsvd.model.ImagePNM;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

/**
 * Esta es una clase controladora de eventos para el
 * elemento de menu 'Process -> Media' de la clase JFrame
 * falconsvd.gui.FalconSVD.
 * 
 * @author Sebastian
 * @version 1.0
 */


public class ControllerProcessMedia {
    
    public static ImagePNM imageMedia;
    
    public static void actionPerformed(ActionEvent e) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                ControllerSelectDB.dbImages.buildMediaImages();
                registerProgress(25, "Construye con exito la matrix media de la DB de imagenes");
                imageMedia = ControllerSelectDB.dbImages.getImageMedia();
                registerProgress(50, "Se crea con exito la imagen media");
                CanvasPNM canvasPNM = new CanvasPNM(imageMedia, FalconSVD.panelDrawMedia.getSize());
                FalconSVD.panelDrawMedia.removeAll();
                FalconSVD.panelDrawMedia.add(canvasPNM, BorderLayout.CENTER);
                canvasPNM.repaint();
                registerProgress(100, "Se pinta la imagen sobre el canvas Media");
                FalconSVD.menuProcessNormal.setEnabled(true);
                FalconSVD.menuSaveMedia.setEnabled(true);
            }
        };
        Thread hilo = new Thread(runnable);
        hilo.start();
    }
    
    /**
     * Metodo que registra en el area de log el progreso de la construccion
     * de la media de las imagenes en el conjunto de imagenes de la DB y
     * cuando se pinta sobre el canvas.
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
                    FalconSVD.textAreaLog.append("ProcessMedia::FalconSVD [OK]\t "+message+"\n");
                    FalconSVD.progressBar.setValue(progress);
                    FalconSVD.progressBar.setString(progress+"%");
                    if(progress == 100) {
                        Thread.sleep(500);
                        FalconSVD.progressBar.setValue(0);
                        FalconSVD.progressBar.setString(0+"%");
                    }
                } catch (InterruptedException ex) {
                    FalconSVD.textAreaLog.append("ProcessMedia::FalconSVD [ERROR]\t Error al dormir hilo");
                }
            }
        };
        Thread hilo = new Thread(runnable);
        hilo.start();
    }
}
