package testes;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class LojinhaWEBTest {
    private WebDriver navegador;

    @Before
    public void setUp() {
        // Preparação
        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
        navegador = new ChromeDriver();
        navegador.get("http://165.227.93.41/lojinha-web/");

        // Login com usuário tiagocr e senha 123456
        navegador.findElement(By.cssSelector("#usuario")).sendKeys("tiagocr");
        navegador.findElement(By.id("senha")).sendKeys("123456");
        navegador.findElement(By.cssSelector(".btn")).click();
    }

    @Test
    public void testValidarDadosDeUmProduto() {
        // Acessar o produto PS4 na lista de produtos
        navegador.findElement(By.linkText("PS4")).click();

        // Validação do nome do Produto e do nome do primeiro componente
        String produtonome = navegador.findElement(By.cssSelector("#produtonome")).getAttribute("value");
        Assert.assertEquals("PS4", produtonome);

        String componentenome = navegador.findElements(By.cssSelector(".title")).get(0).getText();
        Assert.assertEquals("Lojinha Controle", componentenome);
    }

    @Test
    public void testAdicionarUmProduto() {
        // Clicar no ADICIONAR PRODUTO
        navegador.findElement(By.linkText("ADICIONAR PRODUTO")).click();

        // Adicionar Produto
        navegador.findElement(By.id("produtonome")).sendKeys("Game Boy");
        navegador.findElement(By.id("produtovalor")).sendKeys("50000");
        navegador.findElement(By.id("produtocores")).sendKeys("Branco");
        navegador.findElement(By.cssSelector("button[type=submit]")).click();

        // Validar que o Produto foi adicionado
        String toastmessage = navegador.findElement(By.id("toast-container")).getText();
        Assert.assertEquals("Produto adicionado com sucesso", toastmessage);
    }

    @After
    public void tearDown() {
        // Fechar o navegador
        navegador.quit();
    }
}
