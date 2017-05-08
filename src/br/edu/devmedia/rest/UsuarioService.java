package br.edu.devmedia.rest;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.edu.devmedia.dao.UsuarioDAO;
import br.edu.devmedia.entity.Profissao;
import br.edu.devmedia.entity.User;

@Path("/user")
public class UsuarioService {

	private UsuarioDAO usuarioDao;
	
	@PostConstruct
	private void init(){
		usuarioDao = new UsuarioDAO();
	}

	@POST
	@Path("/login")
	@Produces(MediaType.TEXT_PLAIN)
	public boolean isLoggedIn(@FormParam("usuario") String usuario, @FormParam("senha") String senha) {

		try {
			return usuarioDao.isLoggedIn(usuario, senha);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	@POST
	@Path("/add")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public User add(User user) {
		return user;
	}
	
	
	@GET
	@Path("/getprofissoes")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Profissao> listarProfisses() {
		
		List<Profissao> profissoes = new ArrayList<>();
		
		Profissao profissao = new Profissao();
		profissao.setCod(1);
		profissao.setDescricao("Médico");
		profissao.setSubDescricao("Médico Otorrinoralingologista");
		profissao.setUrlImg("img/doctor.png");
		profissoes.add(profissao);
		
		profissao = new Profissao();
		profissao.setCod(2);
		profissao.setDescricao("Analista Financeiro");
		profissao.setSubDescricao("Especialista em bolsa");
		profissao.setUrlImg("img/money.png");
		profissoes.add(profissao);
		
		profissao = new Profissao();
		profissao.setCod(3);
		profissao.setDescricao("Professor");
		profissao.setSubDescricao("Quimica e física");
		profissao.setUrlImg("img/teacher.png");
		profissoes.add(profissao);
		
		return profissoes;
	}

}
