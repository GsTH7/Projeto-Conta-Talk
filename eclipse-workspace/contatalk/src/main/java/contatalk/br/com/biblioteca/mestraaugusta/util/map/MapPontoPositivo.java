package contatalk.br.com.biblioteca.mestraaugusta.util.map;

import java.util.LinkedHashMap;
import java.util.Map;

public class MapPontoPositivo {
	
	public Map<Object, Object> getMap(){
		Map<Object, Object> listagem = new LinkedHashMap<>();
		listagem.put("Aperte aqui e selecione uma das opções abaixo", null);
        listagem.put("As ilustrações coloridas e bonitas", 1);
        listagem.put("Os livros que me fazem rir", 2);
        listagem.put("Os personagens interessantes", 3);
        listagem.put("Os livros que me fazem pensar", 4);
        listagem.put("A história emocionante e cheia de aventuras", 5);
        listagem.put("Os livros que ensinam coisas novas", 6);
        listagem.put("Os livros que têm atividades para fazer, como quebra-cabeças ou jogos", 7);
        listagem.put("Livros de algumas série, jogo ou filme que acompanho", 8);
		return listagem;
	}

}
