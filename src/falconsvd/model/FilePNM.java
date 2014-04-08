/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package falconsvd.model;

import Jama.Matrix;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Esta es una clase que permite manipular un archivo PNM
 * 
 * La clase permite abrir un archivo PNM y construir un objeto ImagePNM
 * con la información contenida en el archivo en formato ASCII.
 * 
 * La clase permite guardar en un archivo PNM un objeto ImagePNM
 * con una codificacion en formato ASCII.
 * 
 * @author Sebastian
 * @version 1.0
 */


public class FilePNM {
    
    /**
     * Atributos de clase
     */
    private String urltofile;
    private ImagePNM imagePNM;
    
    /**
     * Metodo constructor de clase
     * Crea un objeto con la URL del archivo PNM
     * 
     * @param urltofile 
     */
    public FilePNM(String urltofile) {
        this.urltofile = urltofile;
    }
    
    /**
     * Este metodo se encarga de abrir el archivo ubicado en la URL
     * y construir un objeto ImagePNN con los datos contenidos en el archivo.
     */
    public void openFile() {
        try {
            File objFile = new File(getUrltofile());
            FileReader objReader = new FileReader(objFile);
            BufferedReader objBuffer = new BufferedReader(objReader);
            
            setImagePNM(buildImagePNM(objBuffer));
            
        } catch (FileNotFoundException ex) {
            System.err.println("FilePNN::openFile -> No Encuentra Archivo De Imagen: "+ex.getMessage());
        } catch (IOException ex) {
            System.err.println("FilePNN::openFile -> Error al leer buffer de archivo: "+ex.getMessage());
        }
    }
    
    
    public void saveFile() {
        FileWriter objWriter = null;
        try {
            File objFile = new File(getUrltofile());
            objWriter = new FileWriter(objFile);
            PrintWriter objPrint = new PrintWriter(objWriter);
            
            unbuildImagePNM(objPrint);
            
        } catch (IOException ex) {
            System.err.println("FilePNN::saveFile -> Error al leer buffer de archivo: "+ex.getMessage());
        } finally {
            try {
                objWriter.close();
            } catch (IOException ex) {
                System.err.println("FilePNN::saveFile -> Error al cerrar archivo: "+ex.getMessage());
            }
        }
    }
    
    
    /**
     * Este metodo construye un objeto ImagePNM con información contenida
     * en un buffer de lectura.
     * 
     * @param objBuffer
     * @return ImagePNM
     * @throws IOException 
     */
    private ImagePNM buildImagePNM(BufferedReader objBuffer) throws IOException {
        ImagePNM objImagePNM;
        
        String data = objBuffer.readLine();
        
        String codMagic = data;
        String description = "";
        int rows;
        int colums;
        int intensity = 0;
        Matrix matrix = null;
        
        boolean desc = true;
        while(desc) {
            data = objBuffer.readLine();
            if(data.contains("#")) {
                description += data+"\n";
            } else {
                desc = false;
            }
        }
        
        StringTokenizer token = new StringTokenizer(data);
        colums = Integer.parseInt(token.nextToken());
        rows = Integer.parseInt(token.nextToken());
        
        if(!codMagic.equals("P1")) {
            data = objBuffer.readLine();
            intensity = Integer.parseInt(data);
        }
        
        if(codMagic.equals("P1") || codMagic.equals("P2")) {
            matrix = new Matrix(rows, colums);
        }
        
        if(codMagic.equals("P3")) {
            matrix = new Matrix(rows, colums*3);
        }
        
        int i = 0;
        while((data = objBuffer.readLine()) != null) {
            int j = 0;
            token = new StringTokenizer(data);
            while (token.hasMoreElements()) {
                int value = Integer.parseInt(token.nextToken());
                matrix.set(i, j, value);
                j++;
            }
            i++;
        }
        
        objImagePNM = new ImagePNM(codMagic, description, rows, colums, intensity, matrix);
        
        return objImagePNM;
    }
    
    private void unbuildImagePNM(PrintWriter objPrint) {
        String codMagic = getImagePNM().getCodMagic();
        String description = getImagePNM().getDescription();
        int rows = getImagePNM().getRows();
        int colums = getImagePNM().getColums();
        int intensity = getImagePNM().getIntensity();
        Matrix matrix = getImagePNM().getMatrix();
        
        objPrint.println(codMagic);
        if(description.equals("")) {
            description = "# Imagen sin descripcion";
        }
        objPrint.println(description);
        objPrint.print(colums);
        objPrint.print(" ");
        objPrint.println(rows);
        
        if(codMagic.equals("P1")) {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < colums; j++) {
                    objPrint.print((int)matrix.get(i, j)+" ");
                }
                objPrint.println();
            }
        }
        
        if(codMagic.equals("P2")) {
            objPrint.println(intensity);
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < colums; j++) {
                    objPrint.print((int)matrix.get(i, j)+" ");
                }
                objPrint.println();
            }
        }
        
        if(codMagic.equals("P3")) {
            objPrint.println(intensity);
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < colums*3; j++) {
                    objPrint.print((int)matrix.get(i, j)+" ");
                }
                objPrint.println();
            }
        }
        
    }

    /**
     * @return the urltofile
     */
    public String getUrltofile() {
        return urltofile;
    }

    /**
     * @param urltofile the urltofile to set
     */
    public void setUrltofile(String urltofile) {
        this.urltofile = urltofile;
    }

    /**
     * @return the imagePNM
     */
    public ImagePNM getImagePNM() {
        return imagePNM;
    }

    /**
     * @param imagePNM the imagePNM to set
     */
    public void setImagePNM(ImagePNM imagePNM) {
        this.imagePNM = imagePNM;
    }
    
}
