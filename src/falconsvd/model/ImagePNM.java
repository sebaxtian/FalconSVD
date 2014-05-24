/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package falconsvd.model;

import Jama.Matrix;

/**
 * Esta es una clase que representa a un objeto de imagen PNM
 * es un formato de imagen que se deriva en: imagenes a color RGB
 * PPM, imagenes en escala de grises PGM y en imagenes blanco-negro PBM
 * 
 * Los tres formatos pueden ser codificados en formato binario o ASCII.
 * 
 * La clase ImagePNM es una representación de valor numerico para un
 * formato de imagen con codificación ASCII.
 * 
 * Una imagen PNM debe tener un codigo magico que representa el formato
 * de imagen PPM, PGM, PBM.
 * 
 * Una imagen PNM debe tener una descripción de su contenido o lo que 
 * representa.
 * 
 * Una imagen PNM debe tener unas dimensiones ancho, alto.
 * 
 * Una imagen PNM debe tener un valor de intensidad de luz.
 * 
 * Una imagen PNM debe tener una matrix numérica asociada.
 * 
 * @author Sebastian
 * @version 1.0
 */


public class ImagePNM {
    
    /**
     * Atributos de clase
     */
    private String codMagic;
    private String description;
    private int rows;
    private int colums;
    private int intensity;
    private Matrix matrix;
    private Matrix scalarMatrix;
    
    /**
     * Metodo constructor de clase
     * Crea un objeto PNM vacio
     */
    public ImagePNM() {
    }
    
    /**
     * Metodo contructor de clase
     * Crea un objeto PNM con valores iniciales definidos como parametros
     * 
     * @param codMagic
     * @param description
     * @param rows
     * @param colums
     * @param intensity
     * @param matrix
     */
    public ImagePNM(String codMagic, String description, int rows, int colums, int intensity, Matrix matrix) {
        this.codMagic = codMagic;
        this.description = description;
        this.colums = colums;
        this.rows = rows;
        this.intensity = intensity;
        this.matrix = matrix;
        this.scalarMatrix = scalarMatrix(0.2);
    }
    
    /**
     * Escalacion bicubica de la matrix.
     * @param factor
     * @return Matrix
     */
    private Matrix scalarMatrix(double factor) {
        return new ReduceMatrix(this.matrix, factor).getMatrixReduce();
    }

    /**
     * @return the codMagic
     */
    public String getCodMagic() {
        return codMagic;
    }

    /**
     * @param codMagic the codMagic to set
     */
    public void setCodMagic(String codMagic) {
        this.codMagic = codMagic;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the rows
     */
    public int getRows() {
        return rows;
    }

    /**
     * @param rows the rows to set
     */
    public void setRows(int rows) {
        this.rows = rows;
    }

    /**
     * @return the colums
     */
    public int getColums() {
        return colums;
    }

    /**
     * @param colums the colums to set
     */
    public void setColums(int colums) {
        this.colums = colums;
    }
    
    /**
     * @return the intensity
     */
    public int getIntensity() {
        return intensity;
    }

    /**
     * @param intensity the intensity to set
     */
    public void setIntensity(int intensity) {
        this.intensity = intensity;
    }

    /**
     * @return the matrix
     */
    public Matrix getMatrix() {
        return matrix;
    }

    /**
     * @param matrix the matrix to set
     */
    public void setMatrix(Matrix matrix) {
        this.matrix = matrix;
    }
    
    /**
     * @return the scalarMatrix
     */
    public Matrix getReduceMatrix() {
        return scalarMatrix;
    }
    
}
