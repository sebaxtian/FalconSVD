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
    private ImagePNM imageMedia;
    
    
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
    
    /**
     * Metodo que construye una matrix de una sola columna donde se
     * guardan los pixeles de la media de todas las imagenes del
     * conjunto de imagenes.
     */
    public void buildMediaImages() {
        mediaImages = new Matrix(dbImages.getRowDimension(), 1);
        for (int k = 0; k < dbImages.getRowDimension(); k++) {
            Matrix rowPixels = dbImages.getMatrix(k, k, 0, k);
            mediaImages.set(k, 0, getMedia(rowPixels));
        }
    }
    
    /**
     * Metodo que permite calcular el valor de la media para una
     * fila de pixeles de la matrix de imagenes.
     * 
     * @param rowPixels
     * @return 
     */
    private double getMedia(Matrix rowPixels) {
        double media = 0;
        for (int i = 0; i < rowPixels.getColumnDimension(); i++) {
            media += rowPixels.get(0, i);
        }
        media /= rowPixels.getColumnDimension();
        return media;
    }
    
    public ImagePNM getImageMedia() {
        String codMagic = pnmImages[0].getCodMagic();
        String description = pnmImages[0].getDescription();
        int rows = pnmImages[0].getRows();
        int colums = pnmImages[0].getColums();
        int intensity = pnmImages[0].getIntensity();
        Matrix imageMatrix = new Matrix(rows, colums);
        
        // construye la matrix de imagen
        int j0 = 0;
        int j1 = rows-1;
        for (int i = 0; i < rows; i++) {
            Matrix rowImage = mediaImages.getMatrix(j0, j1, 0, 0);
            imageMatrix.setMatrix(i, i, 0, colums, rowImage);
            j0 += rows;
            j1 += rows;
        }
        
        // construye el objeto de imagen PNM
        imageMedia = new ImagePNM(codMagic, description, rows, colums, intensity, imageMatrix);
        
        return imageMedia;
    }
    
    /**
     * Permite obtener el objeto Matrix que representa la base de datos
     * del conjunto de imagenes.
     * 
     * @return Matrix
     */
    public Matrix getMatrixDBImages() {
        return dbImages;
    }
    
    /**
     * Permite obtener el objeto Matrix que representa la imagen de 
     * la media del conjunto de imagenes.
     * 
     * @return  Matrix
     */
    public Matrix getMatrixMediaImages() {
        return mediaImages;
    }
    
}
