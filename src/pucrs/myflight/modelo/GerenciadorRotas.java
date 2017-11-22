package pucrs.myflight.modelo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class GerenciadorRotas {

	private ArrayList<Rota> rotas;
	private Map<String, Rota> mapRotas;
	
	public GerenciadorRotas() {
		rotas = new ArrayList<>();
		mapRotas = new HashMap<String, Rota>();
	}
	
	public void adicionar(Rota r) {
		rotas.add(r);	
	}
	
	public HashMap<String, Rota> getMapRotas(){
		HashMap<String, Rota> map = new HashMap<>(mapRotas);
		return map;
	}
	
	public void carregaSerial() throws IOException {
		Path arq1 = Paths.get("rotas.ser");
		try (ObjectInputStream iarq = new ObjectInputStream(Files.newInputStream(arq1))) {
		  rotas = (ArrayList<Rota>) iarq.readObject();
		}
		catch(ClassNotFoundException e) {
		  System.out.println("Classe Rota nao encontrada!");
		  System.exit(1);
		}
	}
	
	public void gravaSerial() throws IOException {
		Path arq1 = Paths.get("rotas.ser");
		try (ObjectOutputStream oarq = new ObjectOutputStream(Files.newOutputStream(arq1))) {
		  oarq.writeObject(rotas);
		}
		catch(IOException e) {
		  System.out.println(e.getMessage());
		  System.exit(1);
		}
	}
	
	public ArrayList<Rota> listarTodas() {
		return new ArrayList<Rota>(rotas);
	}
	
	public int totalRotas() {
		return rotas.size();
	}
	
	public void ordenaCia() {
		//Collections.sort(rotas);
		rotas.sort((Rota r1, Rota r2)
			-> r1.getCia().getNome().compareTo(r2.getCia().getNome()));
	}
	
	public void ordenaOrigem() {
		rotas.sort( (Rota r1, Rota r2)
			-> r1.getOrigem().getNome().compareTo(r2.getOrigem().getNome()));
	}
	
	public void ordenaOrigemCia() {
		/*
		rotas.sort( (Rota r1, Rota r2) -> {
			int r = r1.getOrigem().getNome().compareTo(r2.getOrigem().getNome());
			if (r!=0)
				return r;
			return r1.getCia().getNome().compareTo(r2.getCia().getNome());
		});
		*/
		rotas.sort(Comparator.comparing((Rota r) -> r.getOrigem().getNome()).
				thenComparing(r -> r.getCia().getNome()));
	}
	
	public ArrayList<Rota> buscarOrigem(Aeroporto origem) {
		ArrayList<Rota> lista = new ArrayList<>();
		for(Rota r : rotas) {			
//			System.out.println(r.getOrigem().getCodigo());
			if(origem.getCodigo().equals(r.getOrigem().getCodigo()))
				lista.add(r);					
		}
		return lista;
	}
	
	@Override
	public String toString() {		
		StringBuilder aux = new StringBuilder();
		for(Rota r: rotas)
			aux.append(r + "\n");			
		return aux.toString();
	}
	
	public ArrayList<Rota> getRotas(){
		return rotas;
	}
	
	public void carregaDados(HashMap<String, Aeroporto> mapAero, HashMap<String, CiaAerea> mapCias) throws IOException {
		int i=0;
		Path path1 = Paths.get("routes.dat");
		try (Scanner sc = new Scanner(Files.newBufferedReader(path1, Charset.forName("utf8")))) {
				sc.useDelimiter("[;\n]"); // separadores: ; e nova linha
				String header = sc.nextLine(); // pula cabeçalho
				String codCia, codOrigem, codDestino, codAeronave, aux;
				while (sc.hasNext()) {
					codCia = sc.next();
					codOrigem = sc.next();
					codDestino = sc.next();
					aux = sc.next();
					aux = sc.next();
					codAeronave = sc.next();
					CiaAerea cia = new CiaAerea(codCia);
					Aeroporto origem = new Aeroporto(codOrigem);
					Aeroporto destino = new Aeroporto(codDestino);
					Aeronave aeronave = new Aeronave(codAeronave);
					//System.out.format("%s - %s -> %s - %s%n", codCia, codOrigem, codDestino, codAeronave);
					rotas.add(new Rota(cia, mapAero.get(codOrigem), mapAero.get(codDestino), aeronave));
					mapRotas.put(codCia, new Rota(mapCias.get(codCia), mapAero.get(codOrigem), mapAero.get(codDestino), aeronave));
					i++;
				}
				System.out.println(i);
		}
}
}
