/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package falconsvd.controller;

import falconsvd.gui.FalconSVD;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

/**
 * Esta es una clase controladora de eventos para el
 * elemento de menu 'Threshold' de la clase JFrame
 * falconsvd.gui.FalconSVD.
 * 
 * @author Sebastian
 * @version 1.0
 */


public class ControllerEditThreshold {
    
    public static double threshold = 30;
    
    public static void actionPerformed(ActionEvent e) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                String inputValue = JOptionPane.showInputDialog(FalconSVD.tabbedPanel, "Input The Threshold Value", "Edit Threshold", JOptionPane.QUESTION_MESSAGE);
                try {
                    if(inputValue != null) {
                        threshold = Double.parseDouble(inputValue);
                        registerProgress(100, "Valor de Threshold ha sido editado: "+threshold);
                    }
                } catch(NumberFormatException exc) {
                    JOptionPane.showMessageDialog(FalconSVD.tabbedPanel, "Threshold value must be numeric", "Edit Threshold", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        Thread hilo = new Thread(runnable);
        hilo.start();
    }
    
    /**
     * Metodo que registra en el area de log el progreso de lectura
     * del valor de Threshold.
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
                    FalconSVD.textAreaLog.append("EditThreshold::FalconSVD [OK]\t "+message+"\n");
                    FalconSVD.progressBar.setValue(progress);
                    FalconSVD.progressBar.setString(progress+"%");
                    if(progress == 100) {
                        Thread.sleep(500);
                        FalconSVD.progressBar.setValue(0);
                        FalconSVD.progressBar.setString(0+"%");
                    }
                } catch (InterruptedException ex) {
                    FalconSVD.textAreaLog.append("EditThreshold::FalconSVD [ERROR]\t Error al dormir hilo");
                }
            }
        };
        Thread hilo = new Thread(runnable);
        hilo.start();
    }
}
