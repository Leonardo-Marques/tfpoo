package pucrs.myflight.modelo;

public class Pais {
	
		private String codigo;
		private String nome;
	
		public Pais(String c, String n) {
				codigo = c;
				nome = n;
		}
		
		public Pais(String c) {
			codigo = c;
		}
	
		public String getCodigo() {
				return codigo;
		}
	
		public String getNome() {
				return nome;
		}

}
