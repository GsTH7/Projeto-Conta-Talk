package contatalk.br.com.biblioteca.mestraaugusta.util.map;

import java.util.LinkedHashMap;
import java.util.Map;

public class MapGenero {
	
	public Map<Object, Object> getMap(){
		Map<Object, Object> listagem = new LinkedHashMap<>();
		listagem.put("Aperte aqui e selecione uma das opções abaixo", null);
        listagem.put("Histórias de Amor (Romance)", 1);
        listagem.put("Viagens no Espaço (Ficção Científica)", 2);
        listagem.put("Mundos Fantásticos (Fantasia)", 3);
        listagem.put("Mistérios e Enigmas (Mistério/Suspense)", 4);
        listagem.put("Histórias Assustadoras (Terror)", 5);
        listagem.put("Aventuras Incríveis (Aventura)", 6);
        listagem.put("Dramas e Emoções (Drama)", 7);
        listagem.put("Poesia e Versos", 8);
        listagem.put("Super-Heróis e Ação (História em Quadrinhos/Graphic Novels)", 9);
        listagem.put("Aprender Sobre o Mundo Real (Não Ficção: biografias, ensaios, livros de história)", 10);
		return listagem;
	}

}
