/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package falconsvd.model;

import Jama.Matrix;

/**
 * Esta clase construye una matriz de imagenes apartir
 * de un conjunto de imagenes de una base de datos.
 * 
 * La clase genera una matriz donde en cada columna se guarda
 * los pixeles de cada imagen del conjunto de imagenes, es decir,
 * la columna 1 tiene todos los pixeles de la imagen 1 y asi
 * hasta la columna n que tiene todos los pixeles de la imagen n.
 * 
 * Las imagenes del conjunto de imagenes TODAS deben tener
 * el mismo alto (filas) y ancho (columnas)
 * 
 * @author sebaxtian
 * @version 1.0
 */


public class DBImages {
    
    /**
     * Atributos de clase
     */
    private ImagePNM[] pnmImages;
    private Matrix dbImages;
    private Matrix mediaImages;
    
    
    /**
     * Metodo constructor que recibe como argumento un arreglo con
     * un conjunto de imagenes PNM leidas de una base de datos.
     * 
     * @param pnmImages 
     */
    public DBImages(ImagePNM[] pnmImages) {
        this.pnmImages = pnmImages;
    }
    
    /**
     * Metodo que construye una matrix que representa la base de datos
     * del conjunto de imagenes, cada columna en la matriz contiene
     * los pixeles de cada imagen del conjunto de imagenes.
     */
    public void buildDBImages() {
        int rows = pnmImages[0].getRows();
        int colums = pnmImages[0].getColums();
        int numPixels = rows * colums;
        int numImages = pnmImages.length;
        // matriz que representa la base de datos
        dbImages = new Matrix(numPixels, numImages);
        for (int k = 0; k < numImages; k++) {
            double[] pixels = pnmImages[k].getMatrix().getRowPackedCopy();
            Matrix matrixPixels = new Matrix(pixels, numPixels);
            dbImages.setMatrix(0, numPixels-1, k, k, matrixPixels);
        }
    }
    
    
    private void buildMediaImages() {
        mediaImages = new Matrix(dbImages.getRowDimension(), 1);
        for (int k = 0; k < dbImages.getRowDimension(); k++) {
            //Matrix rowPixels = dbImages.getMatrix(k, k, 0, k)
        }
    }
    
    private double getMedia(Matrix rowPixels) {
        double media = 0;
        for (int i = 0; i < rowPixels.getColumnDimension(); i++) {
            media += rowPixels.get(0, i);
        }
        media /= rowPixels.getColumnDimension();
        return media;
    }
    
    /**
     * Permite obtener el objeto Matrix que representa la base de datos
     * del conjunto de imagenes.
     * 
     * @return Matrix
     */
    public Matrix getDBImages() {
        return dbImages;
    }
    
}
