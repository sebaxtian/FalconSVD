/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package falconsvd.model;

import java.io.File;
import java.util.ArrayList;

/**
 * Esta clase se encarga de cargar un conjunto de imagenes
 * apartir de una URI del directorio que contiene la DB
 * de imagenes en formato PNM.
 * 
 * La clase lee del directorio de forma recursiva los subdirectorios
 * y las imagenes en formato de archivo PNM para luego por
 * cada imagen leida se genera un objeto de clase ImagePNM para
 * ser almacenado en memoria mediante un arreglo.
 * 
 * @author sebaxtian
 * @version 1.0
 */


public class LoadImagesPNM {

    /**
     * Atributos de clase.
     */
    private ArrayList<ImagePNM> pnmImages;
    private File dirDB;
    public static int SOMEBODY = 1;
    public static int PEOPLE = 2;
    
    /**
     * Metodo constructor de clase el cual tiene como parametro
     * una URI del directorio que contiene la DB de imagenes
     * en formato PNM.
     * 
     * @param URI 
     */
    public LoadImagesPNM(String URI) {
        this.dirDB = new File(URI);
        pnmImages = new ArrayList<>();
    }
    
    /**
     * Este metodo carga las imagenes del directorio de DB
     * para almacenarlas en un arreglo.
     * 
     * @param option 
     */
    public void load(int option) {
        if(option == SOMEBODY) {
            loadSomebody();
        }
        if(option == PEOPLE) {
            loadPeople();
        }
    }
    
    /**
     * Este metodo carga las imagenes para una persona en
     * particular del directorio de DB y almacenarlas en un arreglo.
     */
    private void loadSomebody() {
        File[] images = dirDB.listFiles();
        for (File image : images) {
            if (image.isFile()) {
                String uriImage = image.getAbsolutePath();
                FilePNM filePNM = new FilePNM(uriImage);
                filePNM.openFile();
                ImagePNM imagePNM = filePNM.getImagePNM();
                // agrega cada imagen al arreglo
                pnmImages.add(imagePNM);
            }
        }
    }
    
    /**
     * Este metodo carga las imagenes para un conjunto de personas
     * del directorio de DB y almacenarlas en un arreglo.
     */
    private void loadPeople() {
        File[] subDirs = dirDB.listFiles();
        for (File subDirn : subDirs) {
            File[] images = subDirn.listFiles();
            for (File image : images) {
                if (image.isFile()) {
                    String uriImage = image.getAbsolutePath();
                    FilePNM filePNM = new FilePNM(uriImage);
                    filePNM.openFile();
                    ImagePNM imagePNM = filePNM.getImagePNM();
                    // agrega cada imagen al arreglo
                    pnmImages.add(imagePNM);
                }
            }
        }
    }
    
    /**
     * Este metodo permite obtener el arreglo de objetos ImagePNM
     * que contiene las imagenes cargadas del directorio de DB.
     * 
     * @return ImagePNM[]
     */
    public ImagePNM[] getArrayImagess() {
        return (ImagePNM[])pnmImages.toArray();
    }
    
    public ArrayList<ImagePNM> getArrayImages() {
        return pnmImages;
    }
    
}
