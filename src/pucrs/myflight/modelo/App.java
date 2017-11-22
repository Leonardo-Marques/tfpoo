package pucrs.myflight.modelo;

import javax.xml.transform.sax.SAXSource;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class App {

	public static void main(String[] args) throws IOException {
		
		GerenciadorCias gerCias = new GerenciadorCias();
		GerenciadorAeroportos gerAero = new GerenciadorAeroportos();
		GerenciadorAeronaves gerAvioes = new GerenciadorAeronaves();
		GerenciadorRotas gerRotas = new GerenciadorRotas();		
		GerenciadorPaises gerPaises = new GerenciadorPaises();
		GerenciadorVoos gerVoos = new GerenciadorVoos();
		
		gerPaises.carregaDados();
		gerAero.carregaDados(gerPaises.getMapPaises());
		gerCias.carregaDados();
		gerAvioes.carregaDados();
		gerRotas.carregaDados(gerAero.getMapAero(), gerCias.getMapCias());

	}
}
