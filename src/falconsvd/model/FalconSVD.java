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
    private Matrix matrixTarget;
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
    public FalconSVD(Matrix matrixTarget, Matrix matrixMedia, double threshold) {
        this.matrixTarget = matrixTarget;
        this.matrixMedia = matrixMedia;
        this.threshold = threshold;
        // ---------------
        /*
        Matrix matrixTargetColum = new Matrix(matrixTarget.getColumnPackedCopy(), 10304);
        Matrix matrixMediaColum = new Matrix(matrixMedia.getColumnPackedCopy(), 10304);
        this.matrixTarget = matrixTargetColum;
        this.matrixMedia = matrixMediaColum;
        */
    }
    
    
    /**
     * Este metodo ejecuta el calculo de valores
     * singulares.
     */
    public void runSVD() {
        SingularValueDecomposition SVD = matrixMedia.svd();
        
        U = SVD.getU(); // Vectores Singulares De La Izquierda
        S = SVD.getS(); // Matrix De Valores Singulares
        V = SVD.getV(); // Vectores Singulares De La Derecha
        V = V.transpose();
        
        
        System.out.println("matrixMedia : filas = "+matrixMedia.getRowDimension()+" columnas = "+matrixMedia.getColumnDimension());
        //imprimirMatrix(matrixMedia);
        System.out.println("matrixTarget : filas = "+matrixTarget.getRowDimension()+" columnas = "+matrixTarget.getColumnDimension());
        //imprimirMatrix(matrixTarget);
        System.out.println("U : filas = "+U.getRowDimension()+" columnas = "+U.getColumnDimension());
        //imprimirMatrix(U);
        System.out.println("S : filas = "+S.getRowDimension()+" columnas = "+S.getColumnDimension());
        //imprimirMatrix(S);
        System.out.println("V : filas = "+V.getRowDimension()+" columnas = "+V.getColumnDimension());
        //imprimirMatrix(V);
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
        Matrix matrixProducto = matrixMedia.times(V);
        System.out.println("matrixProducto : filas = "+matrixProducto.getRowDimension()+" columnas = "+matrixProducto.getColumnDimension());
        //imprimirMatrix(matrixProducto);
        Matrix matrixDiferencia = matrixTarget.minus(matrixProducto);
        System.out.println("matrixDiferencia : filas = "+matrixDiferencia.getRowDimension()+" columnas = "+matrixDiferencia.getColumnDimension());
        //imprimirMatrix(matrixDiferencia);
        if(norma == NORMA1) {
            range = matrixDiferencia.norm1();
        }
        if(norma == NORMA2) {
            range = matrixDiferencia.norm2();
        }
        if(norma == NORMAFrob) {
            range = matrixDiferencia.normF();
        }
        if(norma == NORMAInf) {
            range = matrixDiferencia.normInf();
        }
        System.out.println("Range "+range);
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
    
    
    private void imprimirMatrix(Matrix matrix) {
        for (int i = 0; i < matrix.getRowDimension(); i++) {
            for (int j = 0; j < matrix.getColumnDimension(); j++) {
                System.out.print(matrix.get(i, j)+" \t ");
            }
            System.out.println("");
        }
    }
    
}
