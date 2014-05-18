/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package falconsvd.model;

import Jama.Matrix;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * Esta es una clase que hereda de la clase Canvas en la cual
 * se va a pintar un objeto de imagen de la clase ImagePNM.
 * 
 * @author sebaxtian
 */


public class CanvasPNM extends Canvas {
    
    /**
     * Atributos de clase.
     */
    private ImagePNM imagePNM;
    private BufferedImage bufferImageResize;
    
    /**
     * Metodo constructor de clase que recibe como argumento
     * un objeto de clase ImagePNM.
     * 
     * @param imagePNM 
     * @param size 
     */
    public CanvasPNM(ImagePNM imagePNM, Dimension size) {
        this.imagePNM = imagePNM;
        this.setSize(size);
        createBufferImage();
    }
    
    /**
     * Este metodo se encarga de crear un buffer sobre el cual
     * se pinta la imagen para posteriormente pintar el buffer
     * sobre el canvas.
     */
    private void createBufferImage() {
        String codMagic = imagePNM.getCodMagic();
        Matrix matrix = imagePNM.getMatrix();
        int rows = matrix.getRowDimension();
        int colums = matrix.getColumnDimension();
        
        BufferedImage bufferImage = null;
        
        if(codMagic.equals("P1")) { /*Pintar Imagen Binaria*/ }
        
        if(codMagic.equals("P2")) {
            bufferImage = new BufferedImage(colums, rows, BufferedImage.TYPE_INT_RGB);
            Graphics gbuff = bufferImage.getGraphics();
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < colums; j++) {
                    int value = Math.abs((int)matrix.get(i, j));
                    gbuff.setColor(new Color(value, value, value));
                    gbuff.fillOval(j, i, 2, 2);
                }
            }
        }
        
        if(codMagic.equals("P3")) { /*Convertir a escala de grices y pintar*/ }
        
        // Redimeciona el buffer de imagen al size del canvas
        bufferImageResize = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
	Graphics gbuff = bufferImageResize.getGraphics();
        gbuff.drawImage(bufferImage, 0, 0, this.getWidth(), this.getHeight(), this);
	gbuff.dispose();
    }
    
    /**
     * Metodo que pinta sobre el canvas el buffer de imagen.
     * 
     * @param g 
     */
    private void drawImage(Graphics g) {
        g.drawImage(bufferImageResize, 0, 0, this);
        g.dispose();
    }

    @Override
    public void paint(Graphics g) {
        drawImage(g);
    }
    
    @Override
    public void repaint() {
        paint(getGraphics());
    }

    @Override
    public void update(Graphics g) {
        paint(g);
    }
}
