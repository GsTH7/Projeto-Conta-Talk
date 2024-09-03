package contatalk.br.com.biblioteca.mestraaugusta.util.map;
import java.util.LinkedHashMap;
import java.util.Map;

public class MapEscolas {
	
	public Map<Object, Object> getMap(){
		Map<Object, Object> listagem = new LinkedHashMap<>();
		listagem.put("Aperte aqui e selecione uma das opções abaixo", null);
		listagem.put("Escola Estadual Lauro Machado", 1);
		listagem.put("Escola Estadual Badaró Junior", 2);
		listagem.put("Escola Municipal São João Batista", 3);
		return listagem;
	}

}
