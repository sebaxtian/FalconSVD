/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tests;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;
import falconsvd.model.FilePNM;
import falconsvd.model.ImagePNM;

/**
 * Clase que contiene metodo Main para ejecutar pruebas.
 * 
 * @author sebaxtian
 * @version 1.0
 */


public class Tests {

    public static void pruebaSVD() {
        
        double[][] a = {{2, 0,  8, 6, 0},
                        {1, 6, 0, 1, 7},
                        {5, 0, 7, 4, 0},
                        {7, 0, 8, 5, 0},
                        {0, 10, 0, 0, 7}};
        
        double[][] b = {{3, 1, 1},
                        {-1, 3, 1}};
        
        double[][] c = {{8, 5},
                        {1, 9},
                        {3, 0}};
        
        double[][] d = {{0, 0, 1},
                        {0, 0, 1}};
        
        double[][] e = {{2, 4},
                        {1, 3},
                        {0, 0},
                        {0, 0}};
        
        Matrix A = new Matrix(a);
        
        /*
        FilePNM filePNM = new FilePNM("faces/dataset/pgm/s10_ascii/output_5.pgm");
        filePNM.openFile();
        ImagePNM imagePNM = filePNM.getImagePNM();
        System.out.println(imagePNM.getCodMagic()+"");
        A = imagePNM.getMatrix();
        */
        
        TestSVD testSVD = new TestSVD(A);
        testSVD.ejecutarMetodoSVD();
        
        Matrix U = testSVD.getU();
        Matrix S = testSVD.getS();
        Matrix Vt = testSVD.getVt();
        
        
        
        System.out.println("---- Matrix A ----");
        A.print(A.getColumnDimension(), 4);
        
        System.out.println("---- Matrix U ----");
        U.print(U.getColumnDimension(), 4);
        
        System.out.println("---- Matrix S ----");
        S.print(S.getColumnDimension(), 4);
        
        System.out.println("---- Matrix Vt ----");
        Vt.print(Vt.getColumnDimension(), 4);
        
        Matrix C = U.times(S).times(Vt);
        System.out.println("---- Matrix C == A ----");
        C.print(C.getColumnDimension(), 4);
        
        /*
        imagePNM.setMatrix(C);
        filePNM.setImagePNM(imagePNM);
        filePNM.setUrltofile("output/output_5SVD.pgm");
        filePNM.saveFile();
        */
    }
    
    public static void imprimirMatrix(Matrix X) {
        int rows = X.getRowDimension();
        int colums = X.getColumnDimension();
        System.out.println("rows "+rows);
        System.out.println("colums "+colums);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < colums; j++) {
                System.out.print("\t"+X.get(i, j));
            }
            System.out.println("");
        }
    }
    
    
    public static void main(String[] args) {
        System.out.println("--> Prueba SVD");
        pruebaSVD();
    }
}
