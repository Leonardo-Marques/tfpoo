package pucrs.myflight.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.GeoPosition;

import javafx.application.Application;
import javafx.embed.swing.SwingNode;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import pucrs.myflight.modelo.Aeroporto;
import pucrs.myflight.modelo.CiaAerea;
import pucrs.myflight.modelo.Geo;
import pucrs.myflight.modelo.GerenciadorAeronaves;
import pucrs.myflight.modelo.GerenciadorAeroportos;
import pucrs.myflight.modelo.GerenciadorCias;
import pucrs.myflight.modelo.GerenciadorPaises;
import pucrs.myflight.modelo.GerenciadorRotas;
import pucrs.myflight.modelo.GerenciadorVoos;
import pucrs.myflight.modelo.Pais;
import pucrs.myflight.modelo.Rota;
import pucrs.myflight.modelo.Volume;
import pucrs.myflight.modelo.Voo;
import pucrs.myflight.modelo.VooDireto;

public class JanelaFX extends Application {

	final SwingNode mapkit = new SwingNode();

	private GerenciadorCias gerCias;
	private GerenciadorAeroportos gerAero;
	private GerenciadorRotas gerRotas;
	private GerenciadorAeronaves gerAvioes;
	private GerenciadorPaises gerPaises;
	private GerenciadorVoos gerVoos;

	private GerenciadorMapa gerenciador;

	private EventosMouse mouse;

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		setup();
		
		GeoPosition poa = new GeoPosition(-30.05, -51.18);
		gerenciador = new GerenciadorMapa(poa, GerenciadorMapa.FonteImagens.VirtualEarth);
		mouse = new EventosMouse();
		gerenciador.getMapKit().getMainMap().addMouseListener(mouse);
		gerenciador.getMapKit().getMainMap().addMouseMotionListener(mouse);
		
		createSwingContent(mapkit);
		
		BorderPane pane = new BorderPane();			
		GridPane leftPane = new GridPane();
		
		leftPane.setAlignment(Pos.CENTER);
		leftPane.setHgap(5);
		leftPane.setVgap(5);
		leftPane.setPadding(new Insets(5,5,5,5));	

		Button btnConsultaUm = new Button("Consulta Um");
		Button btnClear = new Button("Clear");
		Button btnConsultaDois = new Button("Consulta Dois");
		Button btnConsultaTres = new Button("Consulta Tr�s");
		Button btnConsultaQuatro = new Button("Consulta Quatro");

		leftPane.add(btnClear,0,0);
		leftPane.add(btnConsultaDois,0,3);
		leftPane.add(btnConsultaUm, 0,2);
		leftPane.add(btnConsultaTres,0,4);
		leftPane.add(btnConsultaQuatro,0,5);
		
		btnConsultaUm.setOnAction(e -> {
			Geo thisgeo = new Geo(gerenciador.getPosicao().getLatitude(), gerenciador.getPosicao().getLongitude());
			consultaUm(thisgeo);

		});

		btnConsultaDois.setOnAction(e -> {
			Geo thisgeo = new Geo(gerenciador.getPosicao().getLatitude(), gerenciador.getPosicao().getLongitude());
			consultaDois(thisgeo);
		});

		btnConsultaTres.setOnAction(e -> {
			consultaTres();
		});
		btnClear.setOnAction(e -> {
			 clear();
		});

	
			
		pane.setCenter(mapkit);
		pane.setLeft(leftPane);
		
		Scene scene = new Scene(pane, 500, 500);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Mapas com JavaFX");
		primaryStage.show();

	}



    
    private void setup() {

    	gerCias = new GerenciadorCias();
    	gerAero = new GerenciadorAeroportos();
    	gerRotas = new GerenciadorRotas();
    	gerAvioes = new GerenciadorAeronaves();
    	gerPaises = new GerenciadorPaises();
    	gerVoos = new GerenciadorVoos();
    	
    	try {
			gerPaises.carregaDados();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Erro ao carregar paises.");
		}
    	try {
			gerAero.carregaDados(gerPaises.getMapPaises());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Erro ao carregar aeroportos.");
		}
		try {
			gerCias.carregaDados();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Erro ao carregar companhias.");
		}
		try {
			gerAvioes.carregaDados();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Erro ao carregar aeronaves.");
		}
		try {
			gerRotas.carregaDados(gerAero.getMapAero(), gerCias.getMapCias());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Erro ao carregar rotas.");
		}
		
	}
    
    /**
     * Atualiza gerenciador e tela.
     */
    private void refresh(List<MyWaypoint> l, ArrayList<Tracado> LTracados){
    	for(Tracado t : LTracados){
        	t.setWidth(1);
        	t.setCor(Color.GREEN);
        	gerenciador.addTracado(t);
        }
  
        gerenciador.setPontos(l);
        gerenciador.getMapKit().repaint();  
    }

    private void clear(){
        List<MyWaypoint> l = new ArrayList<>();
        l.clear();
        gerenciador.setPontos(l);
        gerenciador.getMapKit().repaint();
        gerenciador.clear();
    }


	private void consultaTres(){
        clear();
        List<MyWaypoint> lstPoints = new ArrayList<>();
        ArrayList<Tracado> trs = new ArrayList<Tracado>();

        final JDialog dialog = new JDialog();
        dialog.setAlwaysOnTop(true);
        double s =Double.parseDouble(JOptionPane.showInputDialog(dialog, "Digite os KM: "));
    //    String  s = JOptionPane.showInputDialog(dialog, "selecao KM:");
        String t = JOptionPane.showInputDialog(dialog, "COD. aeroporto: ");
        
        for(Rota r : gerRotas.listarTodas()){
            Geo geo1 = null,geo2 = null;
            if(r.getOrigem().getCodigo().equals(t) ){
            	if(r.getDestino().getLocal().distancia(r.getOrigem().getLocal()) < s ){
                        lstPoints.add(new MyWaypoint(Color.RED, r.getOrigem().getCodigo(), r.getOrigem().getLocal(), 3));
                        lstPoints.add(new MyWaypoint(Color.cyan, r.getDestino().getCodigo(), r.getDestino().getLocal(), 3));
                        geo1 = new Geo(r.getOrigem().getLocal().getLatitude(), r.getOrigem().getLocal().getLongitude());    

                        geo2 = new Geo(r.getDestino().getLocal().getLatitude(), r.getDestino().getLocal().getLongitude());
            	
           
                trs.add(new Tracado(geo1,geo2));
         	  }
            }
        }
        refresh(lstPoints,trs);

    }


    private void consultaUm(Geo x) {
        clear();
        List<MyWaypoint> lstPoints = new ArrayList<>();
        ArrayList<Tracado> trs = new ArrayList<Tracado>();


        Geo geo1 = null, geo2 = null;

        if (validaprox(x) != null) {
            Aeroporto a = validaprox(x);

            for (Aeroporto b : gerAero.listarTodos()){
                if (a.getPais().getNome().equals(b.getPais().getNome())){
                    lstPoints.add(new MyWaypoint(Color.red, b.getNome(), b.getLocal(), 3));
                    System.out.println("ok" +  b.getNome());
                   

                }
            }

            refresh(lstPoints, trs);
        }
        else {
            System.out.println("Nao existe aeroporto proximo ao mouse");
        }
    }

	
	private void consultaDois(Geo x) {
        clear();
        List<MyWaypoint> lstPoints = new ArrayList<>();
        ArrayList<Tracado> trs = new ArrayList<Tracado>();

        Tracado tr = new Tracado();


        if (validaprox(x) != null) {
            Aeroporto a = validaprox(x);
            Geo geo1 = null, geo2 = null;
            for (Rota r : gerRotas.listarTodas()) {
                if (r.getOrigem().getPais().getNome().equals(a.getPais().getNome())) {
                    //pt origem
                	tr.addPonto(r.getOrigem().getLocal());
                	tr.addPonto(r.getDestino().getLocal());
                    lstPoints.add(new MyWaypoint(Color.red, r.getOrigem().getNome(), r.getOrigem().getLocal(), 3));
                    //pt destino
                    lstPoints.add(new MyWaypoint(Color.red, r.getDestino().getNome(), r.getDestino().getLocal(), 3));
                   gerenciador.addTracado(tr);
                }

                geo1 = new Geo(r.getOrigem().getLocal().getLatitude(), r.getDestino().getLocal().getLongitude());
                geo2 = new Geo(r.getDestino().getLocal().getLatitude(), r.getDestino().getLocal().getLongitude());


            }
            trs.add(new Tracado(geo1,geo2));
            gerenciador.setPontos(lstPoints);
            gerenciador.getMapKit().repaint();  
            
           // refresh(lstPoints, trs);
        }
        else {
            System.out.println("Nao existe aeroporto proximo ao mouse");
        }
    }

	private Aeroporto validaprox (Geo x) {
        for (Aeroporto r : gerAero.listarTodos()) {
            if (r.getLocal().distancia(x) < 100) {
                return r;

            }

        }
        return null;
    }



	
	private class EventosMouse extends MouseAdapter {
		private int lastButton = -1;

		@Override
		public void mousePressed(MouseEvent e) {
			JXMapViewer mapa = gerenciador.getMapKit().getMainMap();
			GeoPosition loc = mapa.convertPointToGeoPosition(e.getPoint());
			// System.out.println(loc.getLatitude()+", "+loc.getLongitude());
			lastButton = e.getButton();
			// Botão 3: seleciona localização
			if (lastButton == MouseEvent.BUTTON3) {
				gerenciador.setPosicao(loc);				
				gerenciador.getMapKit().repaint();
			}
		}

		
//		@Override
//		public void mousePressed(MouseEvent e) {
//			JXMapViewer mapa = gerenciador.getMapKit().getMainMap();
//			GeoPosition loc = mapa.convertPointToGeoPosition(e.getPoint());
//			Geo c1 = new Geo (mapa.convertPointToGeoPosition(e.getPoint()).getLatitude(),
//				//	mapa.convertPointToGeoPosition(e.getPoint()).getLongitude());
//              // consultaUm(c1);
//            //
//         //   consultaDois(c1);
//
//			lastButton = e.getButton();
//			// Botão 3: seleciona localização
//			if (lastButton == MouseEvent.BUTTON3) {
//				gerenciador.setPosicao(loc);
//				gerenciador.getMapKit().repaint();
//			}
//		}
	}
	
	private void createSwingContent(final SwingNode swingNode) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				swingNode.setContent(gerenciador.getMapKit());
			}
		});
	}
	
	public static void main(String[] args) {
		launch(args);			
	}
}

