/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package falconsvd;

import falconsvd.model.FilePNM;
import falconsvd.model.ImagePNM;

/**
 * 
 * @author Sebastian
 * @version 1.0
 */

public class FalconSVD {
    
    public static void test1() {
        FilePNM filePNM = new FilePNM("faces/dataset/pgm/s10_ascii/output_5.pgm");
        filePNM.openFile();
        ImagePNM imagePNM = filePNM.getImagePNM();
        System.out.println(imagePNM.getCodMagic()+"");
        filePNM.setUrltofile("output/output_5.pgm");
        filePNM.saveFile();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("Hola FalconSVD !!");
        
        //test 1
        test1();
    }
    
}
