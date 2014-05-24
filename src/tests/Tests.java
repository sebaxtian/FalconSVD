/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tests;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;
import falconsvd.model.FalconSVD;
import falconsvd.model.FilePNM;
import falconsvd.model.ImagePNM;
import falconsvd.model.ReduceMatrix;
import java.text.NumberFormat;

/**
 * Clase que contiene metodo Main para ejecutar pruebas.
 * 
 * @author sebaxtian
 * @version 1.0
 */


public class Tests {

    /*
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
        
        FilePNM filePNM = new FilePNM("faces/dataset/pgm/s10_ascii/output_5.pgm");
        filePNM.openFile();
        ImagePNM imagePNM = filePNM.getImagePNM();
        System.out.println(imagePNM.getCodMagic()+"");
        A = imagePNM.getMatrix();
        
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
        
        imagePNM.setMatrix(C);
        filePNM.setImagePNM(imagePNM);
        filePNM.setUrltofile("output/output_5SVD.pgm");
        filePNM.saveFile();
    }
    */
    
    public static void imprimirMatrix(Matrix X) {
        int rows = X.getRowDimension();
        int colums = X.getColumnDimension();
        System.out.println("rows "+rows);
        System.out.println("colums "+colums);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < colums; j++) {
                NumberFormat nf = NumberFormat.getInstance();
                nf.setMaximumFractionDigits(2);
                System.out.print("\t"+nf.format(X.get(i, j)));
            }
            System.out.println("");
        }
        System.out.println("");
    }
    
    /*
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
    */
    
    public static void imprimirVector(double[] vector) {
        for (int i = 0; i < vector.length; i++) {
            System.out.print("\t"+vector[i]);
        }
        System.out.println("");
    }
    
    
    /*
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
    */
    
    private static double getMedia(Matrix row) {
        double media = 0;
        for (int i = 0; i < row.getColumnDimension(); i++) {
            media += row.get(0, i);
        }
        media /= row.getColumnDimension();
        return media;
    }
    
    
    public static void pruebaAlgoritmo() {
        int rows = 112;
        int colums = 92;
        int numImagenes = 10;
        double factor = 0.2;
        // imagenes
        Matrix[] imagenes = new Matrix[numImagenes];
        for (int i = 0; i < imagenes.length; i++) {
            //imagenes[i] = Matrix.random(rows, colums);
            Matrix matrix = Matrix.random(rows, colums);
            imagenes[i] = new ReduceMatrix(matrix, factor).getMatrixReduce();
        }
        rows = imagenes[0].getRowDimension();
        colums = imagenes[0].getColumnDimension();
        int numPixeles = rows * colums;
        
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
        Matrix matrixTarget1 = matrixDB.getMatrix(0, numPixeles-1, 2, 2);
        Matrix matrixTarget2 = Matrix.random(rows, colums);
        double[] pixeles = matrixTarget2.getColumnPackedCopy();
        matrixTarget2 = new Matrix(pixeles, numPixeles);
        
        
        // umbral
        double threshold = 0.6;
        
        FalconSVD algoritmo = new FalconSVD(matrixDB, matrixMedia);
        
        // imprimir datos
        System.out.println("----->> Matrix matrixDB");
        System.out.println("Rows: "+matrixDB.getRowDimension()+" Colums: "+matrixDB.getColumnDimension());
        //imprimirMatrix(matrixDB);
        
        System.out.println("----->> Matrix matrixTarget1");
        System.out.println("Rows: "+matrixTarget1.getRowDimension()+" Colums: "+matrixTarget1.getColumnDimension());
        //imprimirMatrix(matrixTarget1);
        
        System.out.println("----->> Matrix matrixTarget2");
        System.out.println("Rows: "+matrixTarget2.getRowDimension()+" Colums: "+matrixTarget2.getColumnDimension());
        //imprimirMatrix(matrixTarget2);
        
        System.out.println("----->> Matrix matrixMedia");
        System.out.println("Rows: "+algoritmo.getMatrixMedia().getRowDimension()+" Colums: "+algoritmo.getMatrixMedia().getColumnDimension());
        //imprimirMatrix(algoritmo.getMatrixMedia());
        
        System.out.println("----->> Matrix matrixTraining");
        System.out.println("Rows: "+algoritmo.getMatrixTraining().getRowDimension()+" Colums: "+algoritmo.getMatrixTraining().getColumnDimension());
        //imprimirMatrix(algoritmo.getMatrixTraining());
        
        System.out.println("----->> Matrix matrixU");
        System.out.println("Rows: "+algoritmo.getMatrixU().getRowDimension()+" Colums: "+algoritmo.getMatrixU().getColumnDimension());
        //imprimirMatrix(algoritmo.getMatrixU());
        
        System.out.println("----->> Matrix matrixS");
        System.out.println("Rows: "+algoritmo.getMatrixS().getRowDimension()+" Colums: "+algoritmo.getMatrixS().getColumnDimension());
        //imprimirMatrix(algoritmo.getMatrixS());
        
        System.out.println("----->> Matrix matrixV");
        System.out.println("Rows: "+algoritmo.getMatrixV().getRowDimension()+" Colums: "+algoritmo.getMatrixV().getColumnDimension());
        //imprimirMatrix(algoritmo.getMatrixV());
        
        
        System.out.println("\n---->> Deteccion De Imagen Target1 Norma2");
        int kFaces = algoritmo.getMatrixTraining().rank();
        algoritmo.makeDetection(kFaces, FalconSVD.NORMA2, matrixTarget1);
        double distance = algoritmo.getDistance();
        System.out.println("Threshold: "+threshold);
        System.out.println("Distance: "+distance);
        if(distance <= threshold) {
            System.out.println("Imagen Detectada");
        } else {
            System.out.println("Imagen No Detectada");
        }
        
        
        System.out.println("\n---->> Deteccion De Imagen Target2 Norma2");
        kFaces = algoritmo.getMatrixTraining().rank();
        algoritmo.makeDetection(kFaces, FalconSVD.NORMA2, matrixTarget2);
        distance = algoritmo.getDistance();
        System.out.println("Threshold: "+threshold);
        System.out.println("Distance: "+distance);
        if(distance <= threshold) {
            System.out.println("Imagen Detectada");
        } else {
            System.out.println("Imagen No Detectada");
        }
        
    }
    
    /*
    public static void pruebaFaceDetection() {
        int rows = 12;
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
        
        
        // matrix target
        Matrix matrixTarget1 = matrixDB.getMatrix(0, numPixeles-1, 2, 2);
        Matrix matrixTarget2 = Matrix.random(rows, colums);
        double[] pixeles = matrixTarget2.getColumnPackedCopy();
        matrixTarget2 = new Matrix(pixeles, numPixeles);
        
        
        // umbral
        double threshold = 0.8;
        
        
        // FaceDetection
        FaceDetection faceDetection = new FaceDetection(matrixDB);
        // Matrix que coincide en la deteccion
        Matrix matrixMatch;
        
        // imprimir datos
        System.out.println("----->> Matrix matrixDB");
        imprimirMatrix(matrixDB);
        
        System.out.println("----->> Matrix matrixTarget1");
        imprimirMatrix(matrixTarget1);
        
        System.out.println("----->> Matrix matrixMedia");
        imprimirMatrix(faceDetection.getMatrixMedia());
        
        System.out.println("----->> Matrix matrixTraining");
        imprimirMatrix(faceDetection.getMatrixTraining());
        
        System.out.println("----->> Matrix matrixCovariance");
        imprimirMatrix(faceDetection.getMatrixCovariance());
        
        System.out.println("----->> Matrix matrixU");
        imprimirMatrix(faceDetection.getMatrixU());
        
        System.out.println("----->> Matrix matrixS");
        imprimirMatrix(faceDetection.getMatrixS());
        
        System.out.println("----->> Matrix matrixV");
        imprimirMatrix(faceDetection.getMatrixV());
        
        
        
        // crea los datos de deteccion para Norma 1 target 1 kFaces = rango
        faceDetection.makeDetection(faceDetection.getMatrixTraining().rank(), FaceDetection.NORMA1, matrixTarget1);
        System.out.println("----->> Norma 1");
        System.out.println("----->> Distance");
        System.out.println(""+faceDetection.getDistance());
        System.out.println("----->> Threshold");
        System.out.println(""+threshold);
        faceDetection.makeRecognition(faceDetection.getMatrixTraining().rank(), FaceDetection.NORMA1, matrixTarget1);
        System.out.println("----->> Matrix matrixMatch");
        imprimirMatrix(faceDetection.getMatrixMatch());
        System.out.println("indexMatrixMatch "+faceDetection.getIndexMatrixMatch());
        
        // crea los datos de deteccion para Norma 2 target 1 kFaces = rango
        faceDetection.makeDetection(faceDetection.getMatrixTraining().rank(), FaceDetection.NORMA2, matrixTarget1);
        System.out.println("----->> Norma 2");
        System.out.println("----->> Distance");
        System.out.println(""+faceDetection.getDistance());
        System.out.println("----->> Threshold");
        System.out.println(""+threshold);
        faceDetection.makeRecognition(faceDetection.getMatrixTraining().rank(), FaceDetection.NORMA2, matrixTarget1);
        System.out.println("----->> Matrix matrixMatch");
        imprimirMatrix(faceDetection.getMatrixMatch());
        System.out.println("indexMatrixMatch "+faceDetection.getIndexMatrixMatch());
        
        
        
        // crea los datos de deteccion para Norma 1 target 2 kFaces = rango
        faceDetection.makeDetection(faceDetection.getMatrixTraining().rank(), FaceDetection.NORMA1, matrixTarget2);
        System.out.println("----->> Norma 1");
        System.out.println("----->> Distance");
        System.out.println(""+faceDetection.getDistance());
        System.out.println("----->> Threshold");
        System.out.println(""+threshold);
        faceDetection.makeRecognition(faceDetection.getMatrixTraining().rank(), FaceDetection.NORMA1, matrixTarget2);
        System.out.println("----->> Matrix matrixMatch");
        imprimirMatrix(faceDetection.getMatrixMatch());
        System.out.println("indexMatrixMatch "+faceDetection.getIndexMatrixMatch());
        
        // crea los datos de deteccion para Norma 2 target 2 kFaces = rango
        faceDetection.makeDetection(faceDetection.getMatrixTraining().rank(), FaceDetection.NORMA2, matrixTarget2);
        System.out.println("----->> Norma 2");
        System.out.println("----->> Distance");
        System.out.println(""+faceDetection.getDistance());
        System.out.println("----->> Threshold");
        System.out.println(""+threshold);
        faceDetection.makeRecognition(faceDetection.getMatrixTraining().rank(), FaceDetection.NORMA2, matrixTarget2);
        System.out.println("----->> Matrix matrixMatch");
        imprimirMatrix(faceDetection.getMatrixMatch());
        System.out.println("indexMatrixMatch "+faceDetection.getIndexMatrixMatch());
    }
    */
    
    
    public static void main(String[] args) {
        System.out.println("--> Prueba SVD");
        //pruebaSVD();
        //pruebaMatrixDB();
        //pruebaReconocimiento();
        
        /*
        Matrix vector = Matrix.random(5, 1);
        Matrix producto = vector.times(vector.transpose());
        imprimirMatrix(vector);
        imprimirMatrix(vector.transpose());
        imprimirMatrix(producto);
        producto = vector.transpose().times(vector);
        imprimirMatrix(producto);
        */
        
        //pruebaFaceDetection();
        
        pruebaAlgoritmo();
    }
}
