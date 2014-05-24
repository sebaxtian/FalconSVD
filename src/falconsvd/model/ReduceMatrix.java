/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package falconsvd.model;

import Jama.Matrix;

/**
 * Esta clase se encarga de reducir una matrix.
 * 
 * @author sebaxtian
 * @version 1.0
 */


public class ReduceMatrix {
    
    private Matrix matrix;
    private Matrix reduce;
    
    /**
     * Metodo constructor de clase, reduce la matrix en un factor.
     * (0, 1)
     * @param matrix
     * @param factor 
     */
    public ReduceMatrix(Matrix matrix, double factor) {
        this.matrix = matrix;
        this.reduce(factor);
    }
    
    /**
     * Reduce la matrix en un factor.
     * (0, 1)
     * @param factor 
     */
    private void reduce(double factor) {
        int rows = this.matrix.getRowDimension();
        int colums = this.matrix.getColumnDimension();
        reduce = new Matrix((int)Math.ceil(rows*factor), (int)Math.ceil(colums*factor));
        rows = reduce.getRowDimension();
        colums = reduce.getColumnDimension();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < colums; j++) {
                //escalacion bicubica
                double valor = this.matrix.get((int)Math.ceil(i / factor), (int)Math.ceil(j / factor));
                reduce.set(i, j, valor);
            }
        }
    }
    
    /**
     * Obtiene la matrix reducida.
     * @return Matrix
     */
    public Matrix getMatrixReduce() {
        return reduce;
    }
    
}
