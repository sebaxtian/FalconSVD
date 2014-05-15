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
    
    public static void pruebaMatrixDB() {
        double[][] a = {{8, 5},
                        {1, 9},
                        {3, 0}};
        double[][] b = {{2, 4},
                        {1, 3},
                        {0, 0}};
        
        Matrix matrixA = new Matrix(a);
        Matrix matrixB = new Matrix(b);
        
        imprimirMatrix(matrixA);
        imprimirMatrix(matrixB);
        
        double matrixColumA[] = matrixA.getColumnPackedCopy();
        double matrixRowA[] = matrixA.getRowPackedCopy();
        
        double matrixColumB[] = matrixB.getColumnPackedCopy();
        double matrixRowB[] = matrixB.getRowPackedCopy();
        
        System.out.println("Matriz Colum A: ");
        imprimirVector(matrixColumA);
        System.out.println("Matriz Row A: ");
        imprimirVector(matrixRowA);
        
        System.out.println("Matriz Colum B: ");
        imprimirVector(matrixColumB);
        System.out.println("Matriz Row B: ");
        imprimirVector(matrixRowB);
        
        int numVal = matrixA.getRowDimension()*matrixA.getColumnDimension();
        Matrix matrixC = new Matrix(numVal, 2);
        
        
        Matrix matrixFila = new Matrix(matrixRowA, matrixRowA.length);
        
        matrixC.setMatrix(0, numVal-1, 0, 0, matrixFila);
        
        matrixFila = new Matrix(matrixRowB, matrixRowB.length);
        
        matrixC.setMatrix(0, numVal-1, 1, 1, matrixFila);
        
        imprimirMatrix(matrixC);
        
        double valor = 56;
        valor /= 3;
        System.out.println(""+valor);
    }
    
    public static void imprimirVector(double[] vector) {
        for (int i = 0; i < vector.length; i++) {
            System.out.print("\t"+vector[i]);
        }
        System.out.println("");
    }
    
    
    
    public static void pruebaReconocimiento() {
        int rows = 15;
        int colums = 4;
        int numImagenes = 5;
        int numPixeles = rows * colums;
        // imagenes
        Matrix[] imagenes = new Matrix[numImagenes];
        for (int i = 0; i < imagenes.length; i++) {
            imagenes[i] = Matrix.random(rows, colums);
        }
        // base de datos de imagenes
        Matrix matrixDB = new Matrix(numPixeles, numImagenes);
        for (int j = 0; j < numImagenes; j++) {
            double[] pixeles = imagenes[j].getColumnPackedCopy();
            Matrix vectorPixeles = new Matrix(pixeles, numPixeles);
            matrixDB.setMatrix(0, numPixeles-1, j, j, vectorPixeles);
        }
        // media de la base de datos
        Matrix matrixMedia = new Matrix(numPixeles, 1);
        for (int i = 0; i < matrixDB.getRowDimension(); i++) {
            Matrix row = matrixDB.getMatrix(i, i, 0, numImagenes-1);
            matrixMedia.set(i, 0, getMedia(row));
        }
        // matrix target
        Matrix matrixTarget1 = matrixDB.getMatrix(0, numPixeles-1, 3, 3);
        Matrix matrixTarget2 = Matrix.random(rows, colums);
        double[] pixeles = matrixTarget2.getColumnPackedCopy();
        matrixTarget2 = new Matrix(pixeles, numPixeles);
        
        // --- ver datos
        System.out.println("\n-----> Matrix BD");
        imprimirMatrix(matrixDB);
        System.out.println("\n-----> Matrix Media");
        imprimirMatrix(matrixMedia);
        System.out.println("\n-----> Matrix Target1");
        imprimirMatrix(matrixTarget1);
        System.out.println("\n-----> Matrix Target2");
        imprimirMatrix(matrixTarget2);
        
        // Reconocimiento Target1
        Reconocimiento reconocimiento = new Reconocimiento(matrixMedia);
        reconocimiento.runSVD();
        Matrix U = reconocimiento.getMatrixU();
        Matrix S = reconocimiento.getMatrixS();
        Matrix V = reconocimiento.getMatrixV();
        Matrix matrixCaracteristica = reconocimiento.getMatrixCaracteristica();
        
        // ver datos
        System.out.println("\n-----> Matrix U");
        imprimirMatrix(U);
        System.out.println("\n-----> Matrix S");
        imprimirMatrix(S);
        System.out.println("\n-----> Matrix V");
        imprimirMatrix(V);
        System.out.println("\n-----> Matrix Caracteristica");
        imprimirMatrix(matrixCaracteristica);
        
        
        boolean reconoce = false;
        
        System.out.println("\nReconoce Target1 ?");
        reconoce = reconocimiento.findImage(matrixTarget1, 1.0, Reconocimiento.NORMA2);
        System.out.println("\nReconoce? "+reconoce);
        System.out.println("Range "+reconocimiento.getRange());
        
        System.out.println("\nReconoce Target2 ?");
        reconoce = reconocimiento.findImage(matrixTarget2, 1.0, Reconocimiento.NORMA2);
        System.out.println("\nReconoce? "+reconoce);
        System.out.println("Range "+reconocimiento.getRange());
        
    }
    
    
    private static double getMedia(Matrix row) {
        double media = 0;
        for (int i = 0; i < row.getColumnDimension(); i++) {
            media += row.get(0, i);
        }
        media /= row.getColumnDimension();
        return media;
    }
    
    
    public static void main(String[] args) {
        System.out.println("--> Prueba SVD");
        //pruebaSVD();
        //pruebaMatrixDB();
        pruebaReconocimiento();
    }
}
