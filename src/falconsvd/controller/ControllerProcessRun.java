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
 * elemento de menu 'Process -> Run' de la clase JFrame
 * falconsvd.gui.FalconSVD.
 * 
 * @author sebaxtian
 * @version 1.0
 */


public class ControllerProcessRun {

    
    
    public static void actionPerformed(ActionEvent e) {
        Matrix matrixTarget = ControllerOpenTarget.imageTarget.getMatrix();
        Matrix matrixMedia = ControllerSelectDB.dbImages.getImageMedia().getMatrix();
        double threshold = ControllerEditThreshold.threshold;
        FalconSVD falconSVD = new FalconSVD(matrixTarget, matrixMedia, threshold);
        falconSVD.runSVD();
        
        System.out.println("Norma1 "+falconSVD.findImage(FalconSVD.NORMA1)+"\n");
        System.out.println("Norma2 "+falconSVD.findImage(FalconSVD.NORMA2)+"\n");
        System.out.println("NormaFrob "+falconSVD.findImage(FalconSVD.NORMAFrob)+"\n");
        System.out.println("NormaInf "+falconSVD.findImage(FalconSVD.NORMAInf)+"\n");
    }
}
