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
        
        Matrix matrixEscalada = escalacionBicubica(matrix, 5);
        int rows = matrixEscalada.getRowDimension();
        int colums = matrixEscalada.getColumnDimension();
        
        BufferedImage bufferImage = null;
        
        if(codMagic.equals("P1")) {
            bufferImage = new BufferedImage(colums, rows, BufferedImage.TYPE_INT_RGB);
            Graphics g = bufferImage.getGraphics();
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < colums; j++) {
                    int value = (int)matrixEscalada.get(i, j);
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
                    int value = (int)matrixEscalada.get(i, j);
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
                    int value = (int)matrixEscalada.get(i, j);
                    g.setColor(new Color(value, value, value));
                    g.fillOval(i, j, 1, 1);
                }
            }
        }
        
        Graphics g = objPanel.getGraphics();
        g.drawImage(bufferImage, 0, 0, objPanel);
    }
    
    
    private Matrix escalacionBicubica(Matrix matrix, double escalar) {
        int rows = (int)Math.floor(escalar * matrix.getRowDimension());
        int colums = (int)Math.floor(escalar * matrix.getColumnDimension());
        Matrix matrixEscalada = new Matrix(rows, colums);
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < colums; j++) {
                int value = (int)matrix.get((int)Math.floor(i / escalar), (int)Math.floor(j / escalar));
                matrixEscalada.set(i, j, value);
            }
        }
        /*
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < colums; j++) {
                if (matrixEscalada.get(i,j) == 0) {
                    matrixEscalada.set(i, j, interpolacionExterna(matrixEscalada, i, j));
                }
            }
            for (int j = 0; j < colums; j++) {
                if (matrixEscalada.get(i,j) == 0) {
                    matrixEscalada.set(i, j, interpolacionInterna(matrixEscalada, i, j));
                }
            }
        }
        */
        return matrixEscalada;
    }
    
    /*
    private int interpolacionInterna(Matrix matrix, int row, int colum) {
        int suma = (int)matrix.get(row, colum);
        
        if (row > 0 && row < matrix.getRowDimension()-1 && colum > 0 && colum < matrix.getColumnDimension()-1 && matrix.get(row, colum) == 0) {
            suma = (int)((matrix.get(row, colum-1) + matrix.get(row-1, colum-1) + matrix.get(row-1, colum) + matrix.get(row-1, colum+1) + matrix.get(row, colum+1) + matrix.get(row, colum+1) + matrix.get(row, colum+1) + matrix.get(row, colum+1)) / 8);
        }
        
        return suma;
    }
    
    private int interpolacionExterna(Matrix matrix, int row, int colum) {
        int suma = 0;
        
        if (colum == 0) {
            if (row == 0) {
                suma = (int)((matrix.get(row+1, colum) + matrix.get(row+1, colum+1) + matrix.get(row, colum+1)) / 3);
            } else if (row == row-1) {
                suma = (int)((matrix.get(row-1, colum) + matrix.get(row-1, colum+1) + matrix.get(row, colum+1)) / 3);
            } else {
                suma = (int)((matrix.get(row-1, colum) + matrix.get(row-1,colum+1) + matrix.get(row, colum+1) + matrix.get(row+1, colum+1) + matrix.get(row+1, colum)) / 5);
            }
        } else if (colum == colum-1) {
            if (row == 0) {
                suma = (int)((matrix.get(row, colum-1) + matrix.get(row+1, colum-1) + matrix.get(row+1, colum)) / 3);
            } else if (row == row-1) {
                suma = (int)((matrix.get(row, colum-1) + matrix.get(row-1, colum-1) + matrix.get(row-1, colum)) / 3);
            } else {
                suma = (int)((matrix.get(row-1, colum) + matrix.get(row-1, colum-1) + matrix.get(row, colum-1) + matrix.get(row+1, colum-1) + matrix.get(row+1, colum)) / 5);
            }
        } else if (row == 0) {
            if (colum == 0) {
                suma = (int)((matrix.get(row+1, colum) + matrix.get(row+1, colum+1) + matrix.get(row, colum+1)) / 3);
            } else if (colum == colum-1) {
                suma = (int)((matrix.get(row, colum-1) + matrix.get(row+1, colum-1) + matrix.get(row+1, colum)) / 3);
            } else {
                suma = (int)((matrix.get(row, colum-1) + matrix.get(row+1, colum-1) + matrix.get(row, colum+1) + matrix.get(row+1, colum+1) + matrix.get(row, colum+1)) / 5);
            }
        } else if (row == row-1) {
            if (colum == 0) {
                suma = (int)((matrix.get(row-1, colum) + matrix.get(row-1, colum+1) + matrix.get(row, colum+1)) / 3);
            } else if (colum == colum-1) {
                suma = (int)((matrix.get(row, colum-1) + matrix.get(row-1, colum-1) + matrix.get(row-1, colum)) / 3);
            } else {
                suma = (int)((matrix.get(row, colum-1) + matrix.get(row-1, colum-1) + matrix.get(row-1, colum) + matrix.get(row-1, colum+1) + matrix.get(row, colum+1)) / 5);
            }
        } else {
        }
        
        return suma;
    }
    */
    
}
