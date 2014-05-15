/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import Jama.Matrix;
import Jama.SingularValueDecomposition;

/**
 *
 * @author juansrs
 */


public class ReconoceRostro {
    
    
    private Matrix matrixTraining; // conjunto de imagenes de entrenamiento
    private Matrix matrixMedia; // imagen media del conjunto de imagenes de entrenamiento
    private Matrix matrixNormalTraining; // conjunto de imagenes de entrenamiento normalizada
    private Matrix matrixCovariance; // multiplicando el conjunto de imagenes de entrenamiento normalizada por su transpuesta
    private Matrix matrixEigenfaces; // es el producto entre la matrix de entrenamiento normalizada por cada columna de la matrix U
    private Matrix matrixNormalTrainingPrima; // conjunto de imagenes de entrenamiento expresada como una combinacion lineal de Eigenvectors
    private Matrix vectorClases; // vector de clases calculado como el producto de eigenvectors y cada columna del conjunto de entrenamiento normalizada
    private Matrix U, S, V; // valores de descomposicion SVD
    private int numPixels;
    private int numImages;
    
    
    
    /**
     * Metodo constructor de clase.
     * 
     * @param matrixDB
     * @param matrixMedia 
     */
    public ReconoceRostro(Matrix matrixDB, Matrix matrixMedia) {
        this.matrixTraining = matrixDB;
        this.matrixMedia = matrixMedia;
        this.numPixels = matrixMedia.getRowDimension();
        this.numImages = matrixDB.getColumnDimension();
    }
    
    
    /**
     * Crea el conjunto de imagenes de entrenamiento normalizadas, donde a cada imagen
     * del conjunto de imagenes de entrenamiento se le resta la imagen promedio del conjunto.
     */
    public void makeMatrixNormalTraining() {
        matrixNormalTraining = new Matrix(numPixels, numImages);
        for (int i = 0; i < matrixTraining.getRowDimension(); i++) {
            for (int j = 0; j < matrixTraining.getColumnDimension(); j++) {
                Matrix imageTraining = matrixTraining.getMatrix(0, numPixels-1, j, j);
                Matrix normalImage = restarImages(imageTraining, matrixMedia);
                matrixNormalTraining.setMatrix(0, numPixels-1, j, j, normalImage);
            }
        }
    }
    
    
    /**
     * Crea la matrix de covarianza multiplicando el conjunto de imagenes
     * de entrenamiento normalizada por su transpuesta.
     */
    public void makeMatrixCovariance() {
        matrixCovariance = matrixNormalTraining.times(matrixNormalTraining.transpose());
    }
    
    
    /**
     * Mediante desconposicion de valores singulares sobre la matrix de covarianza
     * se crean las matrices U, S, V. Donde la matrix U proporsiona los Eigenvectors.
     */
    public void makeSVDOnCovariance() {
        SingularValueDecomposition SVD = new SingularValueDecomposition(matrixCovariance);
        U = SVD.getU();
        S = SVD.getS();
        V = SVD.getV();
    }
    
    
    /**
     * Crea la matrix de eigenvectors llamado tambien como Eigenfaces,
     * para esto cada columna en la matrix de eigenfaces es el producto
     * entre la matrix de entrenamiento normalizada por cada columna
     * de la matrix U.
     */
    public void makeMatrixEigenfaces() {
        int rows = matrixNormalTraining.getRowDimension();
        int colums = U.getColumnDimension();
        matrixEigenfaces = new Matrix(rows, colums);
        for (int j = 0; j < U.getColumnDimension(); j++) {
            Matrix columU = U.getMatrix(0, U.getRowDimension()-1, j, j);
            Matrix eigenVector = productMatrix(matrixNormalTraining, columU);
            matrixEigenfaces.setMatrix(0, rows-1, j, j, eigenVector);
        }
    }
    
    
    /**
     * Crea una matrix normal de entrenamiento prima, donde cada columna
     * de la matrix del conjunto de entrenamiento se expresa como una combinacion
     * lineal de eigenvectors.
     */
    public void makeMatrixNormalTrainingPrima() {
        int rows = matrixEigenfaces.getRowDimension();
        matrixNormalTrainingPrima = new Matrix(matrixNormalTraining.getRowDimension(), matrixNormalTraining.getColumnDimension());
        for (int j = 0; j < numImages; j++) {
            Matrix eigenVector = matrixEigenfaces.getMatrix(0, rows-1, j, j);
            Matrix normalImage = matrixNormalTraining.getMatrix(0, rows-1, j, j);
            Matrix normalImagePrima = eigenVector.times(normalImage).times(eigenVector);
            matrixNormalTrainingPrima.setMatrix(0, rows-1, j, j, normalImagePrima);
        }
    }
    
    
    public void makeVectorClases() {
        vectorClases = new Matrix(numImages, 1);
        
    }
    
    
    
    /**
     * Metodo que multiplica dos matrices.
     * 
     * @param matrixA
     * @param matrixB
     * @return product
     */
    private Matrix productMatrix(Matrix matrixA, Matrix matrixB) {
        Matrix product = matrixA.times(matrixB);
        return product;
    }
    
    
    /**
     * Metodo que resta dos matrices.
     * 
     * @param image1
     * @param image2
     * @return resta
     */
    private Matrix restarImages(Matrix image1, Matrix image2) {
        Matrix resta = image1.minus(image2);
        return resta;
    }
    
}
