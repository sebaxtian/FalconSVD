/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package falconsvd.controller;

import falconsvd.gui.AboutJFrame;
import falconsvd.gui.FalconSVD;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;

/**
 * Esta es una clase controladora de eventos para el
 * elemento de menu 'Help -> About' de la clase JFrame
 * falconsvd.gui.FalconSVD.
 * 
 * @author sebaxtian
 * @version 1.0
 */


public class ControllerAbout {

    public static void actionPerformed(ActionEvent e) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                AboutJFrame.main(null);
            }
        };
        Thread hilo = new Thread(runnable);
        hilo.start();
    }
    
}
