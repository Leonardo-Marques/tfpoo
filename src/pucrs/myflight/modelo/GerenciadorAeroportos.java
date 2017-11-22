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
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class GerenciadorAeroportos {

	private ArrayList<Aeroporto> aeroportos;
	private Map<String,Aeroporto> mapAero;
	
	public GerenciadorAeroportos() {
		aeroportos = new ArrayList<>();
		mapAero = new HashMap<>();
	}
	
	public void adicionar(Aeroporto a) {
		aeroportos.add(a);	
	}
	
	public HashMap<String, Aeroporto> getMapAero(){
		HashMap map = new HashMap<String,Aeroporto>(mapAero);
		return map;
	}
	
	public void carregaSerial() throws IOException {
		Path arq1 = Paths.get("aeros.ser");
		try (ObjectInputStream iarq = new ObjectInputStream(Files.newInputStream(arq1))) {
		  aeroportos = (ArrayList<Aeroporto>) iarq.readObject();
		}
		catch(ClassNotFoundException e) {
		  System.out.println("Classe Aeroporto nao encontrada!");
		  System.exit(1);
		}
	}
	
	public void gravaSerial() throws IOException {
		Path arq1 = Paths.get("aeros.ser");
		try (ObjectOutputStream oarq = new ObjectOutputStream(Files.newOutputStream(arq1))) {
		  oarq.writeObject(aeroportos);
		}
		catch(IOException e) {
		  System.out.println(e.getMessage());
		  System.exit(1);
		}
	}
	
	public ArrayList<Aeroporto> listarTodos() {
		return new ArrayList<Aeroporto>(aeroportos);
	}
	
	public void ordenaNome() {
		Collections.sort(aeroportos);
	}
	
	public Aeroporto buscarCodigo(String codigo) {
		for(Aeroporto a: aeroportos)
			if(a.getCodigo().equals(codigo))
				return a;
		return null;
	}
	
	@Override
	public String toString() {
		StringBuilder aux = new StringBuilder();
		for(Aeroporto a: aeroportos)
			aux.append(a + "\n");			
		return aux.toString();
	}
	
	public Map carregaDados(Map<String, Pais>	map) throws IOException {
		Path path1 = Paths.get("airports.dat");
		try (Scanner sc = new Scanner(Files.newBufferedReader(path1, Charset.forName("utf8")))) {
				sc.useDelimiter("[;\n]"); // separadores: ; e nova linha
				String header = sc.nextLine(); // pula cabeçalho
				String codigo, nome, latitude, longitude, pais;
				double lat = 0, lon = 0;
				while (sc.hasNext()) {
						codigo = sc.next();
						latitude = sc.next();
						longitude = sc.next();
						nome = sc.next();
						pais = sc.next();
						lat = Double.parseDouble(latitude);
						lon = Double.parseDouble(longitude);
						Geo geo = new Geo(lat,lon);
						Pais p = new Pais(pais);
						//System.out.format("%s - %s%n", codigo, nome, geo.toString());
						aeroportos.add(new Aeroporto(codigo, nome, geo, map.get(pais)));
						mapAero.put(codigo, new Aeroporto(codigo, nome, geo, map.get(pais)));
						//System.out.println(map.get(pais).getNome());
				}
				Map<String, Aeroporto> mapAirports = new HashMap(mapAero);
				return mapAirports;
		}
}
}
