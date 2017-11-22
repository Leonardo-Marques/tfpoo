package pucrs.myflight.modelo;

public class Aeroporto implements Comparable<Aeroporto> {
	private String codigo;
	private String nome;
	private Geo loc;
	private Pais pais;
	private int qtd;
	
	public int getQtd(){
		return qtd;
	}
	
	public void addQtd(){
		qtd++;
	}
	
	public void setQtd(int q){
		qtd = q;
	}
	
	public Aeroporto(String codigo, String nome, Geo loc, Pais pais) {
		this.codigo = codigo;
		this.nome = nome;
		this.loc = loc;
		this.pais = pais;
	}
	
	public Aeroporto(String codigo, String nome, Geo loc) {
		this.codigo = codigo;
		this.nome = nome;
		this.loc = loc;
	}
	
	public void setPais(Pais pais){
		this.pais = pais;
	}
	
	public Pais getPais(){
		return pais;
	}
	
	public Aeroporto(String c) {
		// TODO Auto-generated constructor stub
		codigo = c;
	}

	public String getCodigo() {
		return codigo;
	}
	
	public String getNome() {
		return nome;
	}
	
	public Geo getLocal() {
		return loc;
	}

	@Override
	public int compareTo(Aeroporto o) {
		return nome.compareTo(o.nome);
	}
	
	@Override
	public String toString() {
		return codigo + " - " + nome + " - " + loc;
	}
}
