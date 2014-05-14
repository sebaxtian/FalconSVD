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
 * de valores singulares - SVD.
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
    private Matrix imageTarget;
    private Matrix matrixMedia;
    private double threshold;
    private Matrix U;
    private Matrix S;
    private Matrix V;
    
    
    /**
     * Metodo constructor de clase.
     * 
     * @param imageTarget
     * @param matrixMedia
     * @param threshold 
     */
    public FalconSVD(Matrix imageTarget, Matrix matrixMedia, double threshold) {
        this.imageTarget = imageTarget;
        this.matrixMedia = matrixMedia;
        this.threshold = threshold;
    }
    
    
    /**
     * Este metodo ejecuta el calculo de valores
     * singulares.
     */
    public void runSVD() {
        SingularValueDecomposition SVD = matrixMedia.svd();
        S = SVD.getS();
        V = SVD.getV();
        U = SVD.getU().transpose();
        imageTarget = new Matrix(imageTarget.getColumnPackedCopy(), 10304);
        
        System.out.println("S : filas = "+S.getRowDimension()+" columnas = "+S.getColumnDimension());
        System.out.println("V : filas = "+V.getRowDimension()+" columnas = "+V.getColumnDimension());
        System.out.println("U : filas = "+U.getRowDimension()+" columnas = "+U.getColumnDimension());
        System.out.println("matrixMedia : filas = "+matrixMedia.getRowDimension()+" columnas = "+matrixMedia.getColumnDimension());
        System.out.println("imageTarget : filas = "+imageTarget.getRowDimension()+" columnas = "+imageTarget.getColumnDimension());
    }
    
    
    /**
     * Este metodo se encarga de calcular el grado de variacion
     * entre la imagen objetivo y el conjunto de imagenes.
     * 
     * @param norma
     * @return range
     */
    private double calcRange(int norma) {
        double range = Double.MAX_VALUE;
        Matrix matrixProducto = U.times(matrixMedia);
        Matrix diferencia = imageTarget.minus(matrixProducto);
        if(norma == NORMA1) {
            range = diferencia.norm1();
        }
        if(norma == NORMA2) {
            range = diferencia.norm2();
        }
        if(norma == NORMAFrob) {
            range = diferencia.normF();
        }
        if(norma == NORMAInf) {
            range = diferencia.normInf();
        }
        return range;
    }
    
    
    /**
     * Metodo que determina con base a un umbral definido
     * si la imagen objetivo se encuentra en la DB.
     * 
     * @param norma
     * @return match
     */
    public boolean findImage(int norma) {
        boolean match = false;
        if(calcRange(norma) <= threshold) {
            match = true;
        }
        return match;
    }
    
}
