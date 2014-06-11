/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package falconsvd.controller;

import falconsvd.gui.FalconSVD;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

/**
 * Esta es una clase controladora de eventos para el
 * elemento de menu 'Help -> Manual' de la clase JFrame
 * falconsvd.gui.FalconSVD.
 * 
 * @author sebaxtian
 * @version 1.0
 */


public class ControllerManual {
    
    public static void actionPerformed(ActionEvent e) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try{
                    //Creamos un archivo en la carpeta temporal de tu sistema operativo
                    //File temp = new File(System.getProperty("java.io.tmpdir") + "manualtmp.pdf");
                    File temp = new File(System.getProperty("user.dir") + "/manual.pdf");
                    
                    registerProgress(20, "Se crea archivo temporal "+System.getProperty("user.dir") + "/manual.pdf");
                    //Creamos un flujo de entrada al cual asignamos el valor del PDF que esta dentro del JAR
                    InputStream flujoEntrada = this.getClass().getResourceAsStream("/manual/manual.pdf");
                    registerProgress(30, "Lee archivo de manual /manual/manual.pdf");
                    //Creamos un flujo de salida para poder escribir sobre el archivo temporal
                    FileOutputStream flujoSalida = new FileOutputStream(temp);
                    //Preparamos el temp para que se llene de la informacion de PDF dentro del jar
                    FileWriter fw = new FileWriter(temp);
                    //Creamos un arreglo de bytes generico que soporte un gran tamano 1KB * 512 B --> se usa para todo tipo de archivo  
                    byte[] buffer = new byte[1024*512];

                    int control; //para contar posiciones de byte

                    //Mientras haya bytes por leer se ejecuta este bucle
                    while ((control = flujoEntrada.read(buffer)) != -1){
                        flujoSalida.write(buffer, 0, control);
                    }

                    //Cerramos y guardamos el archivo creado
                    fw.close();
                    flujoSalida.close();
                    flujoEntrada.close();
                    registerProgress(80, "Copia manual en archivo temporal");
                    

                    //Ahora ya tenemos en temp todo tu PDF descomprimido y lo podemos abrir
                    Desktop.getDesktop().open(temp);
                    registerProgress(100, "Abre manual con aplicacion del sistema");
                } catch (IOException ex) {
                    registerProgress(100, "Error al intentart abrir archivo de manual");
                    ex.printStackTrace();
                }
            }
        };
        Thread hilo = new Thread(runnable);
        hilo.start();
    }
    
    /**
     * Metodo que registra en el area de log el progreso de la apertura del manual.
     * 
     * @param progress
     * @param message 
     */
    private static void registerProgress(final int progress, final String message) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(300);
                    FalconSVD.textAreaLog.append("ControllerManual::FalconSVD [OK]\t "+message+"\n");
                    FalconSVD.progressBar.setValue(progress);
                    FalconSVD.progressBar.setString(progress+"%");
                    if(progress == 100) {
                        Thread.sleep(500);
                        FalconSVD.progressBar.setValue(0);
                        FalconSVD.progressBar.setString(0+"%");
                    }
                } catch (InterruptedException ex) {
                    FalconSVD.textAreaLog.append("ControllerManual::FalconSVD [ERROR]\t Error al dormir hilo");
                }
            }
        };
        Thread hilo = new Thread(runnable);
        hilo.start();
    }
}
