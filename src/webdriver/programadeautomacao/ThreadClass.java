/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webdriver.programadeautomacao;

import java.awt.AWTException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Thomas
 */
public class ThreadClass implements Runnable {
    private static ProgramaDeAutomacaoMain classMain;
    private SistemaBarros barros;
    public int tempoOcioso;
    private String url;
    private String username;
    private String password;
    
    public ThreadClass(int tempoOcioso, String url, String username, String password, ProgramaDeAutomacaoMain classMain) {
        this.classMain = classMain;
        this.tempoOcioso = tempoOcioso;
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public void run() {
        barros = new SistemaBarros(tempoOcioso, url, username, password);
        barros.LogarNoSistema();
        
        if (classMain.campoF.getText() != "") {
            barros.ProcurarPorData(classMain.campoF.getText());
        }
        if (classMain.desbloquiar) {
            barros.AbrirDesbloquiarVenda();
            try {
                barros.DesbloquiarVendas();
            } catch (AWTException ex) {
                Logger.getLogger(ThreadClass.class.getName()).log(Level.SEVERE, null, ex);
            }
            classMain.AddText(barros.msg);
        }
        for (int i = 1; i <= classMain.repete; i++) {
            try {
                barros.AbrirFecharVenda();
                if (barros.EstaBloquiado()) {
                    barros.DesbloquiarVendas();
                    continue;
                }
                barros.FinalizarFechamento();
                classMain.AddText(i + " " + barros.cliente + " " + barros.condicao + "\n");
                classMain.AtualizarLabel(Integer.toString(i));
            } catch (org.openqa.selenium.NoSuchElementException ex) {
                classMain.AddText("Todas as vendas Fechadas!! \n");
                break;
            }catch(Exception ex){
                classMain.AddText("erro:\n");
                break;
            }
        }
        barros.Sair();
        classMain.StopThread();
    }
}
