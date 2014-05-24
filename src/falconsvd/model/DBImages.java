/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package falconsvd.model;

import Jama.Matrix;
import java.util.ArrayList;

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
    private ArrayList<ImagePNM> pnmImages;
    private Matrix dbImages;
    private Matrix mediaImages;
    private ImagePNM imageMedia;
    private Matrix reduceDBImages;
    private Matrix reduceMediaImages;
    
    
    /**
     * Metodo constructor que recibe como argumento un arreglo con
     * un conjunto de imagenes PNM leidas de una base de datos.
     * 
     * @param pnmImages 
     */
    public DBImages(ArrayList<ImagePNM> pnmImages) {
        this.pnmImages = pnmImages;
    }
    
    /**
     * Metodo que construye una matrix que representa la base de datos
     * del conjunto de imagenes, cada columna en la matriz contiene
     * los pixeles de cada imagen del conjunto de imagenes.
     */
    public void buildDBImages() {
        int rows = pnmImages.get(0).getRows();
        int colums = pnmImages.get(0).getColums();
        int numPixels = rows * colums;
        int numImages = pnmImages.size();
        // matriz que representa la base de datos
        dbImages = new Matrix(numPixels, numImages);
        for (int k = 0; k < numImages; k++) {
            double[] pixels = pnmImages.get(k).getMatrix().getRowPackedCopy();
            Matrix matrixPixels = new Matrix(pixels, numPixels);
            dbImages.setMatrix(0, numPixels-1, k, k, matrixPixels);
        }
        buildDBImagesReduce();
    }
    
    /**
     * Metodo que construye una matrix que representa la base de datos
     * del conjunto de imagenes, cada columna en la matriz contiene
     * los pixeles de cada imagen del conjunto de imagenes.
     * En forma reducida.
     */
    private void buildDBImagesReduce() {
        int rows = pnmImages.get(0).getReduceMatrix().getRowDimension();
        int colums = pnmImages.get(0).getReduceMatrix().getColumnDimension();
        int numPixels = rows * colums;
        int numImages = pnmImages.size();
        // matriz que representa la base de datos
        reduceDBImages = new Matrix(numPixels, numImages);
        for (int k = 0; k < numImages; k++) {
            double[] pixels = pnmImages.get(k).getReduceMatrix().getRowPackedCopy();
            Matrix matrixPixels = new Matrix(pixels, numPixels);
            reduceDBImages.setMatrix(0, numPixels-1, k, k, matrixPixels);
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
            Matrix rowPixels = dbImages.getMatrix(k, k, 0, dbImages.getColumnDimension()-1);
            mediaImages.set(k, 0, getMedia(rowPixels));
        }
        buildMediaImagesReduce();
    }
    
    /**
     * Metodo que construye una matrix de una sola columna donde se
     * guardan los pixeles de la media de todas las imagenes del
     * conjunto de imagenes.
     * En forma reducida.
     */
    private void buildMediaImagesReduce() {
        reduceMediaImages = new Matrix(reduceDBImages.getRowDimension(), 1);
        for (int k = 0; k < reduceDBImages.getRowDimension(); k++) {
            Matrix rowPixels = reduceDBImages.getMatrix(k, k, 0, reduceDBImages.getColumnDimension()-1);
            reduceMediaImages.set(k, 0, getMedia(rowPixels));
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
        String codMagic = pnmImages.get(0).getCodMagic();
        String description = pnmImages.get(0).getDescription();
        int rows = pnmImages.get(0).getRows();
        int colums = pnmImages.get(0).getColums();
        int intensity = pnmImages.get(0).getIntensity();
        
        Matrix imageMatrix = new Matrix(rows, colums);
        
        // construye la matrix de imagen
        int j0 = 0;
        int j1 = colums-1;
        for (int i = 0; i < rows; i++) {
            Matrix rowImage = mediaImages.getMatrix(j0, j1, 0, 0);
            rowImage = rowImage.transpose();
            imageMatrix.setMatrix(i, i, 0, colums-1, rowImage);
            j0 += colums;
            j1 += colums;
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
    
    /**
     * Permite obtener el objeto Matrix que representa la base de datos
     * del conjunto de imagenes.
     * En forma reducida.
     * 
     * @return Matrix
     */
    public Matrix getReduceMatrixDBImages() {
        return reduceDBImages;
    }
    
    /**
     * Permite obtener el objeto Matrix que representa la imagen de 
     * la media del conjunto de imagenes.
     * En forma reducida.
     * 
     * @return Matrix
     */
    public Matrix getReduceMatrixMediaImages() {
        return reduceMediaImages;
    }
    
}
