/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webdriver.programadeautomacao;

import com.sun.glass.events.KeyEvent;
import java.awt.AWTException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author Thomas
 */
public class SistemaBarros implements Runnable {

    public int tempoOcioso;
    private String url; //= "https://170.84.17.234:8443/progatWeb/sistema/comercial/ven/pedido/pedido.jsf";
    private String username; //= new String("thiago@barros.com.br");
    private String password;// = new String("123456");
    private Selenium selen = new Selenium();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private LocalDate dataLocal = LocalDate.now();
    private Date dataVenda = null;
    private Date dataHoje = null;
    public String cliente = new String();
    public String condicao = new String();
    public String msg = new String();

    public SistemaBarros(int tempoOcioso, String url, String username, String password) {
        this.tempoOcioso = tempoOcioso;
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public void LogarNoSistema() {
        selen.OpenPage(url);
        selen.Click("/html/body/div/div[2]/button[3]");
        selen.Click("/html/body/div/div[3]/p[2]/a");
        selen.SendKeys("/html/body/div[1]/div/div[2]/form/div[2]/input", username);
        selen.SendKeys("/html/body/div[1]/div/div[2]/form/div[4]/input", password);
        selen.Click("/html/body/div[1]/div/div[2]/form/div[7]/button");
        selen.SelectValue("/html/body/div[1]/section/form/div[1]/select", "VENDA_ABERTO");
        TimeSleep.Sleep(3);
    }

    public void Sair() {
        selen.Sair();
    }

    public void AbrirDesbloquiarVenda() {
        selen.Click("/html/body/div[1]/section/form/div[1]/div[1]/button");
        selen.Click("/html/body/div[1]/section/form/div[1]/div[1]/ul/li[11]/a");
        TimeSleep.Sleep(3);

    }

    //aqui vai a logica para testar se a venda está bloquiada ou nao
    public boolean EstaBloquiado() {
        return false;
        //selen.isDisplayed(xpath da janela)
    }

    public void DesbloquiarVendas() throws AWTException {
        selen.Click("/html/body/div[1]/section/div[1]/div/div/div[2]/form/div[2]/ul/li[2]/a");
        try {
            selen.Click("/html/body/div[1]/section/div[1]/div/div/div[2]/form/div[2]/div/div[1]/div/div/div/div/div[2]/div/table/tbody/tr[1]/td[3]/a");

        } catch (Exception ex) {
            msg = "Todos os pedidos desbloquiados com sucesso!!\n";
            selen.GetRobot(KeyEvent.VK_ESCAPE);
        }
    }

    public void AbrirFecharVenda() {
        selen.Click("/html/body/div[1]/section/form/div[2]/div[3]/table/tbody/tr[1]/td[1]/div/div/button");
        selen.Click("/html/body/div[1]/section/form/div[2]/div[3]/table/tbody/tr[1]/td[1]/div/div/ul/li[4]/a");
        TimeSleep.Sleep(4);
    }
    public void ProcurarPorData(String CampoLabel){
        selen.SendKeys("/html/body/div[1]/section/form/div[1]/div[2]/div/input", CampoLabel);
        selen.Click("/html/body/div[1]/section/form/div[1]/div[2]/div/div/a");
        TimeSleep.Sleep(5);

    }

    public void FinalizarFechamento() throws AWTException {
        cliente = selen.GetValue("/html/body/div[1]/section/div[15]/div/div/div[2]/form/div/div[1]/div/div/div[2]/div/div[4]/input");
        try {
            dataHoje = java.sql.Date.valueOf(dataLocal);
            dataVenda = dateFormat.parse(selen.GetText("//*[@id=\"formPedidos:gridPedidos\"]/tbody/tr[1]/td[12]"));
        } catch (Exception ex) {
            System.out.println("Erro lina 79");
        }
        long dias = dataHoje.getTime() - dataVenda.getTime();
        dias = (dias / 1000 / 60 / 60 / 24);
        VoltarDias(dias);
        if (cliente.equals("CONSUMIDOR")) {
            selen.SelectValue("/html/body/div[1]/section/div[15]/div/div/div[2]/form/div/div[3]/div/div/div/div[1]/div/div[1]/select", "CARTEIRA");
            TimeSleep.Sleep(1);
            try {
                selen.SelectValue("/html/body/div[1]/section/div[15]/div/div/div[2]/form/div/div[3]/div/div/div/div[1]/div/div[2]/select", "17");
            } catch (Exception ex) {
                selen.SelectValue("/html/body/div[1]/section/div[15]/div/div/div[2]/form/div/div[3]/div/div/div/div[1]/div/div[2]/select", "10");
            }

            condicao = "Fechado A vista!";
        } else {
            selen.SelectValue("/html/body/div[1]/section/div[15]/div/div/div[2]/form/div/div[3]/div/div/div/div[1]/div/div[1]/select", "CREDIARIO");
            TimeSleep.Sleep(3);
            selen.SelectValue("/html/body/div[1]/section/div[15]/div/div/div[2]/form/div/div[3]/div/div/div/div[1]/div/div[2]/select", "1");

            condicao = "Fechado A Prazo!";
        }
        TimeSleep.Sleep(2);
        selen.Click("/html/body/div[1]/section/div[15]/div/div/div[2]/form/div/div[3]/div/div/div/div[1]/div/a[1]");
        TimeSleep.Sleep(5);
        selen.Click("/html/body/div[1]/section/div[15]/div/div/div[2]/form/div/div[1]/div/div/div[3]/a[2]");
        TimeSleep.Sleep(tempoOcioso);

    }

    public void VoltarDias(long dias) throws AWTException {
        selen.Click("/html/body/div[1]/section/div[15]/div/div/div[2]/form/div/div[1]/div/div/div[1]/div/div[2]/div/input");
        for (int i = 0; i < dias; i++) {
            selen.GetRobot(KeyEvent.VK_LEFT);
            try {
                Thread.sleep(200);                 //1000 milliseconds is one second.
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
        selen.GetRobot(KeyEvent.VK_ENTER);
        TimeSleep.Sleep(1);
    }

    @Override
    public void run() {

    }

}
