package pucrs.myflight.modelo;

import java.io.Serializable;

public class CiaAerea implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6380611754334520520L;
	private static int totalCias = 0;
	private String codigo;
	private String nome;
	
	public CiaAerea(String codigo, String nome) {
		totalCias++;
		this.codigo = codigo;
		this.nome = nome;
	}
	
	public CiaAerea(String codCia) {
		// TODO Auto-generated constructor stub
		codigo=codCia;
	}

	public static int getTotalCias() {
		return totalCias;
	}
	
	public String getCodigo() {
		return codigo;
	}
	
	public String getNome() {
		return nome;
	}	
}
