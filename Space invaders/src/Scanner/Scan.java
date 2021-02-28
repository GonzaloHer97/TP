package Scanner;

import java.util.*;

import Exceptions.CommandParserException;

public class Scan {
	private String cadena;
	private Scanner sc;
	
	public Scan() {
		this.cadena = "";
		this.sc = new Scanner (System.in);
	}

	
	
	public String[] parsher(String cadena) throws CommandParserException {
		String [] word = new String [3];
		int i = 0;
		if(checkCommans(cadena)) {
			if(cadena.equals("")) {
				word[0] = "";
			}
			else {
				StringTokenizer stPalabras = new StringTokenizer(cadena);
				
				while (stPalabras.hasMoreTokens()) {
					 String  sPalabra = stPalabras.nextToken();
					 if(i > 2)
						 throw new CommandParserException("\n" + "Incorrect number of arguments");
					 word[i] = sPalabra;
					 i++;
					}
			}
		}
		else
			word[0] = "z";
		return word;
	}
	
	public Boolean checkCommans(String cadena) {
		if(cadena.equals(""))
			return true;
		else {
			StringTokenizer stPalabras = new StringTokenizer(cadena);
			String sPalabra =  stPalabras.nextToken();
			if(sPalabra.equals("move") ||sPalabra.equals("m") )
				return true;
			else if (sPalabra.equals("shoot") ||sPalabra.equals("s"))
				return true;
			else if(sPalabra.equals("shockwave") ||sPalabra.equals("w") )
				return true;
			else if(sPalabra.equals("none") ||sPalabra.equals("n") )
				return true;
			else if(sPalabra.equals("list") ||sPalabra.equals("l") )
				return true;
			else if(sPalabra.equals("reset") ||sPalabra.equals("r") )
				return true;
			else if(sPalabra.equals("help") ||sPalabra.equals("h") )
				return true;
			else if(sPalabra.equals("exit") ||sPalabra.equals("e") )
				return true;
			else if(sPalabra.equals("serialized") ||sPalabra.equals("d") )
				return true;
			else if(sPalabra.equals("supermisil"))
				return true;
			else if(sPalabra.equals("listprinters"))
				return true;
			else if(sPalabra.equals("save"))
				return true;
			else if(sPalabra.equals("load"))
				return true;
		}
		return false;
	}
	
	public  boolean isNumeric(String [] cadena) {

        boolean resultado;

        try {
            Integer.parseInt(cadena[1]);
            resultado = true;
        } catch (NumberFormatException excepcion) {
            resultado = false;
        }

        return resultado;
    }
	
	public String read() {
		return cadena = sc.nextLine();
		}
	
	public String getCadena() {
		return cadena;
	}

	public void setCadena(String cadena) {
		this.cadena = cadena;
	}

	public Scanner getSc() {
		return sc;
	}

	public void setSc(Scanner sc) {
		this.sc = sc;
	}
	
	
}
