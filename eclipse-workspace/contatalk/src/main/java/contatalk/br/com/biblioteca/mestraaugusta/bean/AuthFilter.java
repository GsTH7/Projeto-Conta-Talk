package contatalk.br.com.biblioteca.mestraaugusta.bean;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(filterName = "AuthFilter", urlPatterns = { "/sistema/acesso/*" })
public class AuthFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// Inicialização do filtro, se necessário.
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession(false);

		// Verifica se o usuário está logado
		boolean loggedIn = (session != null && session.getAttribute("usuarioLogado") != null);
		boolean loginRequest = req.getRequestURI().equals(req.getContextPath() + "/sistema/acesso/login.xhtml");
		boolean criarRequest = req.getRequestURI().equals(req.getContextPath() + "/sistema/acesso/criarConta.xhtml");
			
		System.out.println("logged " + loggedIn);
		
		if (loggedIn || loginRequest || criarRequest) {
			// Se o usuário estiver logado ou a requisição for para a página de login,
			// continua o fluxo
			chain.doFilter(request, response);
		} else {
			// Se não estiver logado, redireciona para a página de login
			res.sendRedirect(req.getContextPath() + "/sistema/acesso/login.xhtml");
		}
	}

	@Override
	public void destroy() {
		// Limpeza de recursos, se necessário.
	}
}
