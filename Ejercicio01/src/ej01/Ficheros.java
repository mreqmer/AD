package ej01;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class Ficheros {
	
	public static void main(String[] args) {
			
		final String RUTA = "C:\\Users\\mrequejo\\Desktop\\carpetas.txt";
		String linea;
		String rutaDirectorios = "C:\\Users\\mrequejo\\";
		boolean exito;
		
		try (BufferedReader br = new BufferedReader(new FileReader(RUTA))) {
			linea = br.readLine();
				
				while(linea!=null) {	
					
					exito = (new File(rutaDirectorios + linea)).mkdir();
					
					if(exito) {
						System.out.println("Directorio: " + rutaDirectorios + linea + " creado");
					}else {
						System.out.println(rutaDirectorios + linea + " ya existe.");
					}

					linea = br.readLine();
				}
				
				
		} catch (IOException e) {	
			System.out.println("Hay algún error.");
		}
	}
}
