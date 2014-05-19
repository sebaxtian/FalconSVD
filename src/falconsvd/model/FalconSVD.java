/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package falconsvd.model;

import Jama.Matrix;
import Jama.SingularValueDecomposition;


/**
 * Esta clase contiene el algoritmo a utilizar para la identificacion
 * de una imagen en un conjunto de imagenes mediante la descomposicion
 * de valores singulares - SVD y el calculo de Eigenfaces.
 * 
 * @author sebaxtian
 * @version 1.0
 */


public class FalconSVD {
    
    /**
     * Atributos de clase.
     */
    public static int NORMA1 = 1;
    public static int NORMA2 = 2;
    public static int NORMAFrob = 3;
    public static int NORMAInf = 4;
    private Matrix matrixDB;            // matrix de imagenes de la base de datos
    private Matrix matrixTraining;      // matrix de imagenes de la base de datos normalizada
    private Matrix matrixMedia;         // matrix de la imagen promedio
    private Matrix matrixCovariance;    // matrix de covarianza
    private Matrix matrixU;             // matrix U de la descomposicion SVD
    private Matrix matrixS;             // matrix S de la descomposicion SVD
    private Matrix matrixV;             // matrix V de la descomposicion SVD
    private Matrix matrixTarget;        // matrix que se desea buscar
    private Matrix matrixUPrima;        // matrix U prima
    private double distance;            // distancia al espacio de proyeccion
    private Matrix matrixMatch;         // imagen con la que coincide la imagen objetivo
    private int indexMatrixMatch;       // indice de la imagen con la que coincide la imagen objetivo
    
    
    
    /**
     * Metodo constructor de clase.
     * @param matrixDB
     * @param matrixMedia
     */
    public FalconSVD(Matrix matrixDB, Matrix matrixMedia) {
        this.matrixDB = matrixDB;
        this.matrixMedia = matrixMedia;
        System.out.println("Inicia Make");
        make();
        System.out.println("Termina Make");
    }
    
    
    /**
     * Construye la matrix de la cara promedio.
     */
//    private void makeMatrixMedia() {
//        matrixMedia = new Matrix(matrixDB.getRowDimension(), 1);
//        for (int i = 0; i < matrixDB.getRowDimension(); i++) {
//            Matrix rowPixels = matrixDB.getMatrix(i, i, 0, matrixDB.getColumnDimension()-1);
//            matrixMedia.set(i, 0, getMedia(rowPixels));
//        }
//    }
    
    
    /**
     * Calcula el valor promedio de los elementos de un vector.
     * @param vector
     * @return media
     */
//    private double getMedia(Matrix vector) {
//        double media = 0;
//        for (int j = 0; j < vector.getColumnDimension(); j++) {
//            media += vector.get(0, j);
//        }
//        media = media / vector.getColumnDimension();
//        return media;
//    }
    
    
    /**
     * Construye la matrix del conjunto de entrenamiento normalizada.
     */
    private void makeMatrixTraining() {
        matrixTraining = new Matrix(matrixDB.getRowDimension(), matrixDB.getColumnDimension());
        for (int j = 0; j < matrixDB.getColumnDimension(); j++) {
            Matrix imageDB = matrixDB.getMatrix(0, matrixDB.getRowDimension()-1, j, j);
            Matrix normalColum = imageDB.minus(matrixMedia);
            matrixTraining.setMatrix(0, matrixTraining.getRowDimension()-1, j, j, normalColum);
            imageDB = null;
            normalColum = null;
        }
    }
    
    
    /**
     * Construye la matrix de covarianza. AA^t
     */
    private void makeMatrixCovarianza() {
        matrixCovariance = matrixTraining.times(matrixTraining.transpose());
    }
    
    
    /**
     * Construye las matrices U, S, V mediante descomposicion SVD.
     */
    private void makeMatrixSVD() {
        SingularValueDecomposition SVD = new SingularValueDecomposition(matrixTraining);
        matrixU = SVD.getU();
        matrixS = SVD.getS();
        matrixV = SVD.getV();
    }
    
    
    /**
     * Crea un subconjunto de la matrixU que tiene un numero
     * m de columnas, m debe ser menor o igual al rango de matrixTraining.
     * @param m 
     */
    private void makeMatrixUPrima(int m) {
        matrixUPrima = new Matrix(matrixU.getRowDimension(), m);
        for (int j = 0; j < m; j++) {
            Matrix colum = matrixU.getMatrix(0, matrixU.getRowDimension()-1, j, j);
            matrixUPrima.setMatrix(0, matrixUPrima.getRowDimension()-1, j, j, colum);
            colum = null;
        }
    }
    
    
    /**
     * Detecta la distancia de proyeccion entre la cara objetivo
     * y el espacio de proyeccion.
     */
    private void detectionDistance(int typeNorma) {
        Matrix UUt = matrixUPrima.times(matrixUPrima.transpose());
        Matrix I = Matrix.identity(UUt.getRowDimension(), UUt.getColumnDimension());
        
        Matrix resta1 = I.minus(UUt);
        Matrix resta2 = matrixTarget.minus(matrixMedia);
        
        Matrix product = resta1.times(resta2);
        distance = getNorma(product, typeNorma);
    }
    
    
    /**
     * Detecta la cara de proyeccion entre la cara objetivo
     * y el espacio de proyeccion que mas se aproxima.
     */
    private void detectionMatrix(int typeNorma) {
        Matrix projectionTarget = getProjection(matrixTarget);
        double minDistancia = Double.MAX_VALUE;
        int k = 0;
        for (int j = 0; j < matrixTraining.getColumnDimension(); j++) {
            Matrix imageTraining = matrixTraining.getMatrix(0, matrixTraining.getRowDimension()-1, j, j);
            Matrix resta = projectionTarget.minus(getProjection(imageTraining));
            double distancia = getNorma(resta, typeNorma);
            if(distancia <= minDistancia) {
                minDistancia = distancia;
                k = j;
            }
        }
        System.out.println("minDistancia "+minDistancia);
        matrixMatch = matrixTraining.getMatrix(0, matrixTraining.getRowDimension()-1, k, k);
        indexMatrixMatch = k;
    }
    
    
    /**
     * Metodo que obtiene la norma de una matrix mediante
     * cuatro posibles calculos de la norma.
     * @param matrix
     * @param typeNorma
     * @return norma
     */
    private double getNorma(Matrix matrix, int typeNorma) {
        double norma = 0;
        if(typeNorma == NORMA1) {
            norma = matrix.norm1();
        }
        if(typeNorma == NORMA2) {
            norma = matrix.norm2();
        }
        if(typeNorma == NORMAFrob) {
            norma = matrix.normF();
        }
        if(typeNorma == NORMAInf) {
            norma = matrix.normInf();
        }
        return norma;
    }
    
    
    /**
     * Obtiene la proyeccion de una matrix sobre el subespacio.
     * 
     * @param matrix
     * @return projection
     */
    private Matrix getProjection(Matrix matrix) {
        Matrix resta = matrix.minus(matrixMedia);
        return matrixUPrima.transpose().times(resta);
    }
    
    
    /**
     * Ejecuta el algoritmo creando las matrices necesarias
     * para posteriormente ejecutar la deteccion de la cara.
     */
    private void make() {
        // Paso 1
        //makeMatrixMedia();
        // Paso 2
        makeMatrixTraining();
        // Paso 3 (necesario ?)
        //makeMatrixCovarianza();
        // Paso 4
        makeMatrixSVD();
    }
    
    
    /**
     * Ejecuta el algoritmo para detectar la distancia de proyeccion
     * de la cara a buscar y el espacio, numKFaces son los k
     * egeinvectors a tomar menores al rango de la matrix de entrenamiento,
     * y typeNorma es el metodo para calcular la norma de la matrix.
     * @param numKFaces
     * @param typeNorma 
     * @param matrixTarget 
     */
    public void makeDetection(int numKFaces, int typeNorma, Matrix matrixTarget) {
        this.matrixTarget = matrixTarget;
        // Paso 5
        int rankMatrixTraining = matrixTraining.rank();
        if(numKFaces <= rankMatrixTraining) {
            makeMatrixUPrima(numKFaces);
        } else {
            makeMatrixUPrima(rankMatrixTraining);
        }
        // Paso 6
        detectionDistance(typeNorma);
    }
    
    
    /**
     * Ejecuta el algoritmo para detectar la cara de la matrix normalizada matrixTraining
     * que mas se aproxima a la cara objetivo a buscar, numKFaces son los k
     * egeinvectors a tomar menores al rango de la matrix de entrenamiento,
     * y typeNorma es el metodo para calcular la norma de la matrix.
     * @param numKFaces
     * @param typeNorma
     * @param matrixTarget 
     */
    public void makeRecognition(int numKFaces, int typeNorma, Matrix matrixTarget) {
        this.matrixTarget = matrixTarget;
        // Paso 5
        int rankMatrixTraining = matrixTraining.rank();
        if(numKFaces <= rankMatrixTraining) {
            makeMatrixUPrima(numKFaces);
        } else {
            makeMatrixUPrima(rankMatrixTraining);
        }
        // Paso 6
        detectionMatrix(typeNorma);
    }
    
    
    
    public Matrix getMatrixTraining() {
        return matrixTraining;
    }
    
    public Matrix getMatrixMedia() {
        return matrixMedia;
    }
    
    public Matrix getMatrixCovariance() {
        return matrixCovariance;
    }
    
    public Matrix getMatrixU() {
        return matrixU;
    }
    
    public Matrix getMatrixS() {
        return matrixS;
    }
    
    public Matrix getMatrixV() {
        return matrixV;
    }
    
    public Matrix getMatrixUPrima() {
        return matrixUPrima;
    }
    
    public double getDistance() {
        return distance;
    }
    
    public Matrix getMatrixMatch() {
        return matrixMatch;
    }
    
    public int getIndexMatrixMatch() {
        return indexMatrixMatch;
    }
    
}
