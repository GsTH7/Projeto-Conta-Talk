package contatalk.br.com.biblioteca.mestraaugusta.util;

public class VerificarUtil {
	
	public static boolean verificarSeNulo(Object valor) {
		return valor == null;
	}
	
	public static boolean verificarSeNuloOuVazio(String valor) {
		return valor == null || valor.trim().equals("");
	}
	
}
