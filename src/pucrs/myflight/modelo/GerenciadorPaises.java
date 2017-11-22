package pucrs.myflight.modelo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class GerenciadorPaises {
	
		private ArrayList<Pais> paises;
		private Map<String, Pais> mapPaises;
	
		public GerenciadorPaises(){
				paises = new ArrayList<Pais>();
				mapPaises = new HashMap<String, Pais>();
		}
	
		public void add(Pais p){
				paises.add(p);
		}
		
		public Map<String, Pais> getMapPaises(){
			return mapPaises;
		}
		
		public void carregaSerial() throws IOException {
			Path arq1 = Paths.get("paises.ser");
			try (ObjectInputStream iarq = new ObjectInputStream(Files.newInputStream(arq1))) {
			  paises = (ArrayList<Pais>) iarq.readObject();
			}
			catch(ClassNotFoundException e) {
			  System.out.println("Classe Pais nao encontrada!");
			  System.exit(1);
			}
		}
		
		public void gravaSerial() throws IOException {
			Path arq1 = Paths.get("paises.ser");
			try (ObjectOutputStream oarq = new ObjectOutputStream(Files.newOutputStream(arq1))) {
			  oarq.writeObject(paises);
			}
			catch(IOException e) {
			  System.out.println(e.getMessage());
			  System.exit(1);
			}
		}
		
		public void listarTodos(){
			for(Pais temp : paises){
				System.out.println(temp.getCodigo()+"/"+temp.getNome());
			}
		}
		
		public Map carregaDados() throws IOException {
			Path path1 = Paths.get("countries.dat");
			try (Scanner sc = new Scanner(Files.newBufferedReader(path1, Charset.forName("utf8")))) {
					sc.useDelimiter("[;\n]"); // separadores: ; e nova linha
					String header = sc.nextLine(); // pula cabeçalho
					String codigo, nome;
					while (sc.hasNext()) {
							codigo = sc.next();
							nome = sc.next();
							//System.out.format("%s - %s%n", codigo, nome);
							paises.add(new Pais(codigo, nome));
							mapPaises.put(codigo, new Pais(codigo, nome));
					}
					Map<String, Pais> map = new HashMap(mapPaises);
					return map;
					
			}
		}

}
