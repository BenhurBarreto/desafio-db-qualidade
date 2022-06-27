package test;

import static org.junit.Assert.*;

import java.time.Duration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UserRegistration {
	
	private WebDriver browser;
	
	private static final String URL_BASE = "https://automacaocombatista.herokuapp.com/home/index";
	
	WebDriverWait wait;
	
	@Before
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", "C:\\DB Server\\chromedriver.exe");
		browser = new ChromeDriver();
	}

	@After
	public void tearDown() throws Exception {
		browser.quit();
	}

	@Test
	public void RealizarCadastroDeUsuario() {
//		Execução do primeiro passo do Caso de Teste:Acessar o site https://automacaocombatista.herokuapp.com/home/index
		
		browser.get(URL_BASE);
		assertTrue("Acessar o site " + UserRegistration.URL_BASE, browser.getTitle().contentEquals("Automação com Batista"));
		
//		Execução do segundo passo do Caso de Teste: Acessar “Começar Automação Web”
		
		browser.findElement(By.xpath("//a[text()='Começar Automação Web']")).click();
		wait = new WebDriverWait(browser, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Formulário']")));
		assertTrue("Acessar “Começar Automação Web”", browser.getPageSource().contains("Lista de Funcionalidades"));
		
//		Execução do terceiro passo do Caso de Teste: Preencher Formulário para cadastrar novo usuário
		
		browser.findElement(By.xpath("//a[text()='Formulário']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Criar Usuários']")));
		browser.findElement(By.xpath("//a[text()='Criar Usuários']")).click();
		browser.getPageSource().contains("Novo Usuário!!");
		
		browser.findElement(By.id("user_name")).sendKeys("Neil");
		browser.findElement(By.id("user_lastname")).sendKeys("Peart");
		browser.findElement(By.id("user_email")).sendKeys("neil@rush.com");
		browser.findElement(By.id("user_address")).sendKeys("Broadview Av.");
		browser.findElement(By.id("user_university")).sendKeys("University of Toronto");
		browser.findElement(By.id("user_profile")).sendKeys("Drummer");
		browser.findElement(By.id("user_gender")).sendKeys("Male");
		browser.findElement(By.id("user_age")).sendKeys("67");
		assertTrue("Preencher Formulário para cadastrar novo usuário", browser.getPageSource().contains("Novo Usuário!!"));
		
//		Execução do quarto passo do Caso de Teste: Validar mensagem de criação de usuário
		
		browser.findElement(By.xpath("//input[@type='submit']")).click();
		assertTrue("Validar mensagem de criação de usuário", browser.getPageSource().contains("Usuário Criado com sucesso"));
	}

}
