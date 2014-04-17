/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package visorpnm.model;

import Jama.Matrix;
import falconsvd.model.ImagePNM;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 * Esta es una clase que se encarga de dibujar sobre un objeto
 * JPanel una imagen en formato PNM.
 * 
 * @author Sebastian
 * @version 1.0
 */


public class DrawImagePNM {
    
    /**
     * Atributos de clase.
     */
    private JPanel objPanel;
    private ImagePNM imagePNM;
    
    /**
     * Metodo constructor de clase.
     * 
     * @param imagePNM
     * @param objPanel 
     */
    public DrawImagePNM(ImagePNM imagePNM, JPanel objPanel) {
        this.objPanel = objPanel;
        this.imagePNM = imagePNM;
    }
    
    
    public void draw() {
        
        String codMagic = imagePNM.getCodMagic();
        Matrix matrix = imagePNM.getMatrix();
        int rows = matrix.getRowDimension();
        int colums = matrix.getColumnDimension();
        
        BufferedImage bufferImage = null;
        
        if(codMagic.equals("P1")) {
            bufferImage = new BufferedImage(colums, rows, BufferedImage.TYPE_INT_RGB);
            Graphics g = bufferImage.getGraphics();
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < colums; j++) {
                    int value = (int)matrix.get(i, j);
                    g.setColor(new Color(value, value, value));
                    g.fillOval(i, j, 1, 1);
                }
            }
        }
        
        if(codMagic.equals("P2")) {
            bufferImage = new BufferedImage(colums, rows, BufferedImage.TYPE_INT_RGB);
            Graphics g = bufferImage.getGraphics();
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < colums; j++) {
                    int value = (int)matrix.get(i, j);
                    g.setColor(new Color(value, value, value));
                    g.fillOval(j, i, 2, 2);
                }
            }
        }
        
        if(codMagic.equals("P3")) {
            bufferImage = new BufferedImage(colums, rows, BufferedImage.TYPE_INT_RGB);
            Graphics g = bufferImage.getGraphics();
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < colums*3; j++) {
                    int value = (int)matrix.get(i, j);
                    g.setColor(new Color(value, value, value));
                    g.fillOval(i, j, 1, 1);
                }
            }
        }
        
        BufferedImage resizedImage = new BufferedImage(objPanel.getWidth(), objPanel.getHeight(), BufferedImage.TYPE_INT_RGB);
	Graphics g1 = resizedImage.getGraphics();
	g1.drawImage(bufferImage, 0, 0, objPanel.getWidth(), objPanel.getHeight(), null);
	g1.dispose();
        
        Graphics g = objPanel.getGraphics();
        g.drawImage(resizedImage, 0, 0, objPanel);
        g.dispose();
    }
    
}
