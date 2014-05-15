/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import Jama.Matrix;
import Jama.SingularValueDecomposition;

/**
 * Prueba para reconocimiento usando SVD.
 * 
 * @author juansrs
 */


public class Reconocimiento {
    
    
    public static int NORMA1 = 1;
    public static int NORMA2 = 2;
    public static int NORMAFrob = 3;
    public static int NORMAInf = 4;
    private Matrix matrixTarget;
    private Matrix matrixMedia;
    private Matrix U;
    private Matrix S;
    private Matrix V;
    private Matrix matrixCaracteristica;
    private double range = Double.MAX_VALUE;
    
    
    
    public Reconocimiento(Matrix matrixMedia) {
        this.matrixMedia = matrixMedia;
    }
    
    
    public void runSVD() {
        SingularValueDecomposition SVD = matrixMedia.svd();
        U = SVD.getU();
        S = SVD.getS();
        V = SVD.getV().transpose();
        // matrix caracteristica
        matrixCaracteristica = matrixMedia.times(V);
    }
    
    
    private double calcRange(int norma) {
        Matrix diferencia = matrixTarget.minus(matrixCaracteristica);
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
    
    
    public boolean findImage(Matrix matrixTarget, double threshold, int norma) {
        boolean match = false;
        this.matrixTarget = matrixTarget;
        if(calcRange(norma) <= threshold) {
            match = true;
        }
        return match;
    }
    
    
    public Matrix getMatrixU() {
        return U;
    }
    
    public Matrix getMatrixS() {
        return S;
    }
    
    public Matrix getMatrixV() {
        return V;
    }
    
    public Matrix getMatrixCaracteristica() {
        return matrixCaracteristica;
    }
    
    public double getRange() {
        return range;
    }
    
}
