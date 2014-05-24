/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package falconsvd.controller;

import Jama.Matrix;
import falconsvd.model.CanvasPNM;
import falconsvd.model.FalconSVD;
import falconsvd.model.ImagePNM;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

/**
 * Esta es una clase controladora de eventos para el
 * elemento de menu 'Process -> FalconSVD -> Recognition' de la clase JFrame
 * falconsvd.gui.FalconSVD.
 * 
 * @author sebaxtian
 * @version 1.0
 */


public class ControllerFalconRecognition {
    
    public static Matrix matrixMatch;
    public static int indexMatrixMatch;
    public static ImagePNM imageMatch;
    
    public static void actionPerformed(ActionEvent e) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                int kFaces = ControllerFalconMake.falconSVD.getMatrixTraining().rank(); // pueden ser dinamicos
                int norma = FalconSVD.NORMA2; // pueden ser dinamicos
                Matrix matrixTarget = ControllerOpenTarget.imageTarget.getReduceMatrix();
                double[] pixeles = matrixTarget.getRowPackedCopy();
                matrixTarget = new Matrix(pixeles, matrixTarget.getRowDimension()*matrixTarget.getColumnDimension());
                registerProgress(30, "Se obtiene con exito la imagen target");
                ControllerFalconMake.falconSVD.makeRecognition(kFaces, norma, matrixTarget);
                registerProgress(40, "Se calcula el reconocimiento de la imagen");
                matrixMatch = ControllerFalconMake.falconSVD.getMatrixMatch();
                registerProgress(50, "Se obtiene la matrix que se aproxima a la imagen objetivo");
                indexMatrixMatch = ControllerFalconMake.falconSVD.getIndexMatrixMatch();
                registerProgress(60, "Se obtiene el indice de la matrix que se aproxima a la imagen objetivo");
                // construye imagen match
                registerProgress(70, "Construye el objeto de ImagePNM de la matrix Match");
                // construye imagen DB
                buildImageMatch();
                registerProgress(80, "Construye el objeto de ImagePNM de la matrix indexMatch en DB");
                // pinta imagen match
                CanvasPNM canvasPNMImageMatch = new CanvasPNM(imageMatch, falconsvd.gui.FalconSVD.panelDrawMatch.getSize());
                falconsvd.gui.FalconSVD.panelDrawMatch.removeAll();
                falconsvd.gui.FalconSVD.panelDrawMatch.add(canvasPNMImageMatch, BorderLayout.CENTER);
                canvasPNMImageMatch.repaint();
                registerProgress(90, "Pinta imagen Match en el canvas");
                // pinta imagen target
                ImagePNM imageTarget = ControllerOpenTarget.imageTarget;
                CanvasPNM canvasPNMImageDB = new CanvasPNM(imageTarget, falconsvd.gui.FalconSVD.panelDrawTarget1.getSize());
                falconsvd.gui.FalconSVD.panelDrawTarget1.removeAll();
                falconsvd.gui.FalconSVD.panelDrawTarget1.add(canvasPNMImageDB, BorderLayout.CENTER);
                canvasPNMImageDB.repaint();
                registerProgress(100, "Pinta imagen index DB en el canvas");
                falconsvd.gui.FalconSVD.menuSaveMatch.setEnabled(true);
            }
        };
        Thread hilo = new Thread(runnable);
        hilo.start();
    }
    
    
    /**
     * Construye un objeto ImagePNM con la matrix de la imagen DB 
     * que coincide con la imagen de objetivo.
     */
    private static void buildImageMatch() {
        ImagePNM imageTarget = ControllerOpenTarget.imageTarget;
        
        String codMagic = imageTarget.getCodMagic();
        String description = "# Imagen Que Coincide Con La Imagen Target";
        int rows = imageTarget.getRows();
        int colums = imageTarget.getColums();
        int intensity = imageTarget.getIntensity();
        
        Matrix matrixDBImage = new Matrix(rows, colums);
        Matrix matrixDB = ControllerSelectDB.dbImages.getMatrixDBImages();
        Matrix matrixImageDB = matrixDB.getMatrix(0, (rows*colums)-1, indexMatrixMatch, indexMatrixMatch);
        
        // construye la matrix de imagen
        int j0 = 0;
        int j1 = colums-1;
        for (int i = 0; i < rows; i++) {
            Matrix rowImage = matrixImageDB.getMatrix(j0, j1, 0, 0);
            rowImage = rowImage.transpose();
            matrixDBImage.setMatrix(i, i, 0, colums-1, rowImage);
            j0 += colums;
            j1 += colums;
        }
        
        // construye el objeto de imagen PNM
        imageMatch = new ImagePNM(codMagic, description, rows, colums, intensity, matrixDBImage);
    }
    
    
    /**
     * Metodo que registra en el area de log el progreso de la ejecucion
     * de reconocimiento de rostros mediante SVD y Eigenfaces, para el
     * caso de reconocimiento.
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
                    falconsvd.gui.FalconSVD.textAreaLog.append("FalconRecognition::FalconSVD [OK]\t "+message+"\n");
                    falconsvd.gui.FalconSVD.progressBar.setValue(progress);
                    falconsvd.gui.FalconSVD.progressBar.setString(progress+"%");
                    if(progress == 100) {
                        Thread.sleep(500);
                        falconsvd.gui.FalconSVD.progressBar.setValue(0);
                        falconsvd.gui.FalconSVD.progressBar.setString(0+"%");
                    }
                } catch (InterruptedException ex) {
                    falconsvd.gui.FalconSVD.textAreaLog.append("FalconRecognition::FalconSVD [ERROR]\t Error al dormir hilo");
                }
            }
        };
        Thread hilo = new Thread(runnable);
        hilo.start();
    }
}
