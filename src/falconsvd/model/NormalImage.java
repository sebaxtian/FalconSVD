/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package falconsvd.model;

import Jama.Matrix;

/**
 * Esta clase se encarga de construir una imagen normalizada,
 * para esto, se opera sobre dos imagenes donde una imagen
 * corresponde a la media de las imagenes y la otra imagen
 * corresponde a la imagen a buscar.
 * 
 * @author sebaxtian
 * @version 1.0
 */


public class NormalImage {
    
    /**
     * Atributos de clase.
     */
    private ImagePNM imageMedia;
    private ImagePNM imageTarget;
    private ImagePNM imageNormal;
    
    /**
     * Metodo constructor de clase, recibe como argumento una imagen
     * media de las caras y una imagen objetivo a buscar.
     * 
     * @param imageTarget
     * @param imageMedia 
     */
    public NormalImage(ImagePNM imageTarget, ImagePNM imageMedia) {
        this.imageTarget = imageTarget;
        this.imageMedia = imageMedia;
    }
    
    
    /**
     * Metodo que construye la imagen normal operando la diferencia
     * entre la imagen media y la imagen objetivo.
     */
    public void buildNormalImage() {
        String codMagic = imageTarget.getCodMagic();
        String description = imageTarget.getDescription();
        int rows = imageTarget.getRows();
        int colums = imageTarget.getColums();
        int intensity = imageTarget.getIntensity();
        Matrix matrix = new Matrix(rows, colums);
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < colums; j++) {
                double pixel = Math.abs(imageMedia.getMatrix().get(i, j) - imageTarget.getMatrix().get(i, j));
                matrix.set(i, j, pixel);
            }
        }
        
        imageNormal = new ImagePNM(codMagic, description, rows, colums, intensity, matrix);
    }
    
    
    /**
     * Obtiene el objeto de la imagen normal.
     * 
     * @return ImagePNM
     */
    public ImagePNM getImageNormal() {
        return imageNormal;
    }
}
