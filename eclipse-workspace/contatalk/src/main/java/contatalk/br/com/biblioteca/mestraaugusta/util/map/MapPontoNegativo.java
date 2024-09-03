package contatalk.br.com.biblioteca.mestraaugusta.util.map;

import java.util.LinkedHashMap;
import java.util.Map;

public class MapPontoNegativo {
	
	public Map<Object, Object> getMap(){
		Map<Object, Object> listagem = new LinkedHashMap<>();
		listagem.put("Aperte aqui e selecione uma das opções abaixo", null);
        listagem.put("História chata ou sem graça", 1);
        listagem.put("Personagens que não são interessantes", 2);
        listagem.put("Linguagem difícil de entender", 3);
        listagem.put("Final decepcionante ou sem sentido", 4);
        listagem.put("Muitas descrições longas e entediantes", 5);
        listagem.put("Livro muito assustador ou perturbador", 6);
        listagem.put("Temas que não são interessantes para mim", 7);
        listagem.put("Não consigo me identificar com os personagens", 8);
        listagem.put("Livro muito grande com várias páginas", 9);
        listagem.put("A capa ou o título não me chama a atenção", 10);
		return listagem;
	}

}
