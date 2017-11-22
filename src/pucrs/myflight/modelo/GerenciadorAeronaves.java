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

public class GerenciadorAeronaves {

	private ArrayList<Aeronave> aeronaves;
	private Map<String, Aeronave> mapAvioes;

	public GerenciadorAeronaves() {
		aeronaves = new ArrayList<>();
		mapAvioes = new HashMap<String, Aeronave>();
	}
	
	public void carregaSerial() throws IOException {
		Path arq1 = Paths.get("avioes.ser");
		try (ObjectInputStream iarq = new ObjectInputStream(Files.newInputStream(arq1))) {
		  aeronaves = (ArrayList<Aeronave>) iarq.readObject();
		}
		catch(ClassNotFoundException e) {
		  System.out.println("Classe Aeronave nao encontrada!");
		  System.exit(1);
		}
	}
	
	public void gravaSerial() throws IOException {
		Path arq1 = Paths.get("avioes.ser");
		try (ObjectOutputStream oarq = new ObjectOutputStream(Files.newOutputStream(arq1))) {
		  oarq.writeObject(aeronaves);
		}
		catch(IOException e) {
		  System.out.println(e.getMessage());
		  System.exit(1);
		}
	}
	
	public void ordenaCodigo() {
		//aeronaves.sort( (Aeronave a1, Aeronave a2)
		//		-> a1.getCodigo().compareTo(a2.getCodigo()));
		//aeronaves.sort(Comparator.comparing(a -> a.getCodigo()));
		aeronaves.sort(Comparator.comparing(Aeronave::getCodigo).reversed());
	}
	
	public void ordenaDescricao() {
		Collections.sort(aeronaves);
	}

	public void adicionar(Aeronave av) {
		aeronaves.add(av);
	}
	
	public ArrayList<Aeronave> listarTodas() {
		return new ArrayList<Aeronave>(aeronaves);
	}

	public Aeronave buscarCodigo(String codigo) {
		if (codigo == null)
			return null;
		for(Aeronave av: aeronaves)
			if(av.getCodigo().equals(codigo))
				return av;
		return null;
	}
	
	
	
	@Override
	public String toString() {
		StringBuilder aux = new StringBuilder();
		for(Aeronave av: aeronaves)
			aux.append(av.toString()+"\n");
		return aux.toString();
	}
	
	public Map carregaDados() throws IOException {
		Path path1 = Paths.get("equipment.dat");
		try (Scanner sc = new Scanner(Files.newBufferedReader(path1, Charset.forName("utf8")))) {
				sc.useDelimiter("[;\n]"); // separadores: ; e nova linha
				String header = sc.nextLine(); // pula cabeçalho
				String codigo, desc, capacidade;
				int cap;
				while (sc.hasNext()) {
						codigo = sc.next();
						desc = sc.next();
						capacidade = sc.next();
						cap = Integer.parseInt(capacidade);
						//System.out.format("%s - %s%n", codigo, desc);
						aeronaves.add(new Aeronave(codigo, desc, cap));
						mapAvioes.put(codigo, new Aeronave(codigo, desc, cap));
					
				}
				Map<String, Aeronave> map = new HashMap(mapAvioes);
				return map;
		}
}
}
