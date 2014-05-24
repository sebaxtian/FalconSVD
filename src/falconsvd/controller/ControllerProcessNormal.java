/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package falconsvd.controller;

import falconsvd.gui.FalconSVD;
import falconsvd.model.CanvasPNM;
import falconsvd.model.ImagePNM;
import falconsvd.model.NormalImage;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

/**
 * Esta es una clase controladora de eventos para el
 * elemento de menu 'Process -> Normal' de la clase JFrame
 * falconsvd.gui.FalconSVD.
 * 
 * @author sebaxtian
 * @version 1.0
 */


public class ControllerProcessNormal {
    
    public static ImagePNM imageNormal;
    
    public static void actionPerformed(ActionEvent e) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                NormalImage normalImage = new NormalImage(ControllerOpenTarget.imageTarget, ControllerProcessMedia.imageMedia);
                normalImage.buildNormalImage();
                registerProgress(50, "Construye con exito la imagen normal");
                imageNormal = normalImage.getImageNormal();
                CanvasPNM canvasPNM = new CanvasPNM(imageNormal, FalconSVD.panelDrawNormal.getSize());
                FalconSVD.panelDrawNormal.removeAll();
                FalconSVD.panelDrawNormal.add(canvasPNM, BorderLayout.CENTER);
                canvasPNM.repaint();
                registerProgress(100, "Se pinta la imagen sobre el canvas Normal");
                FalconSVD.menuSaveNormal.setEnabled(true);
                FalconSVD.menuFalconMake.setEnabled(true);
            }
        };
        Thread hilo = new Thread(runnable);
        hilo.start();
    }
    
    /**
     * Metodo que registra en el area de log el progreso de la construccion
     * de la norma de la imagen en el conjunto de imagenes de la DB y
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
                    FalconSVD.textAreaLog.append("ProcessNormal::FalconSVD [OK]\t "+message+"\n");
                    FalconSVD.progressBar.setValue(progress);
                    FalconSVD.progressBar.setString(progress+"%");
                    if(progress == 100) {
                        Thread.sleep(500);
                        FalconSVD.progressBar.setValue(0);
                        FalconSVD.progressBar.setString(0+"%");
                    }
                } catch (InterruptedException ex) {
                    FalconSVD.textAreaLog.append("ProcessNormal::FalconSVD [ERROR]\t Error al dormir hilo");
                }
            }
        };
        Thread hilo = new Thread(runnable);
        hilo.start();
    }
}
