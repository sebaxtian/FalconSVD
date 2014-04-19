/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tests;

import Jama.Matrix;
import Jama.SingularValueDecomposition;

/**
 * Clase para realizar pruebas del metodo SVD utilizando las
 * funciones de la libreria Jama.
 * 
 * SVD (Singular Value Descomposition) metodo utilizado para
 * descomponer una matriz A como el producto de tres matrices,
 * una matriz U ortogonal, una matriz S diagonal y una matriz
 * transpuesta ortogonal V.
 * 
 * @author sebaxtian
 * @version 1.0
 */


public class TestSVD {
    
    // Atributos de clase
    private Matrix A;
    private Matrix U;
    private Matrix S;
    private Matrix Vt;
    
    
    /**
     * Metodo constructor de clase.
     * 
     * @param A 
     */
    public TestSVD(Matrix A) {
        this.A = A;
    }
    
    /**
     * Metodo que ejecuta el calculo de SVD
     */
    public void ejecutarMetodoSVD() {
        SingularValueDecomposition SVD = A.svd();
        SVD = new SingularValueDecomposition(A);
        U = SVD.getU();
        S = SVD.getS();
        Vt = SVD.getV().transpose();
    }
    
    /**
     * Matriz A
     * @return Matrix
     */
    public Matrix getA() {
        return A;
    }
    
    /**
     * Matriz U
     * @return Matrix
     */
    public Matrix getU() {
        return U;
    }
    
    /**
     * Matriz S
     * @return Matrix
     */
    public Matrix getS() {
        return S;
    }
    
    /**
     * Matriz Vt
     * @return Matrix
     */
    public Matrix getVt() {
        return Vt;
    }
}
