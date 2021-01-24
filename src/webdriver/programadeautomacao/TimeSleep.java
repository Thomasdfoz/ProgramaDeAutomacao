package webdriver.programadeautomacao;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Thomas
 */
public class TimeSleep {

   

        public static void Sleep(int tempo) {
            if (tempo < 1) {
                tempo = 1;
            }
            tempo = tempo * 1000;
            try {
                Thread.sleep(tempo);                 //1000 milliseconds is one second.
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    
}
