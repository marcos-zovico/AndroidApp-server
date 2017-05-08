package br.edu.devmedia.rest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import br.edu.devmedia.dao.UsuarioDAO;
import br.edu.devmedia.entity.Content;
import br.edu.devmedia.entity.Usuario;
import br.edu.devmedia.gcm.AppGCM;
import br.edu.devmedia.util.Post2GCM;

@Path("gcm")
public class GCMService {

	private UsuarioDAO usuarioDAO;

	@PostConstruct
	private void init() {
		usuarioDAO = new UsuarioDAO();
	}

	@POST
	@Path("/sendToken")
	@Produces(MediaType.TEXT_PLAIN)
	public boolean getToken(@FormParam("login") String login, @FormParam("token") String token) {
		try {
			return usuarioDAO.inserirToken(login, token);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@GET
	@Path("/sendToAll")
	@Produces(MediaType.TEXT_PLAIN)
	public String sendDefaultMessageToAllUsers() {
		String resultado = "";
		try {
			List<Usuario> lista = usuarioDAO.listarUsuarios();
			for (Usuario usuario : lista) {
				resultado += Post2GCM.post(AppGCM.API_KEY, criarContent(usuario.getToken()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "Erro: " + e.getMessage();
		}
		return resultado;
	}
	
	@GET
	@Path("/sendToAll2")
	@Produces(MediaType.TEXT_PLAIN)
	public String sendDefaultMessageToAllUsers(@QueryParam("titulo") String titulo, @QueryParam("message") String message) {
		String resultado = "";
		try {
			List<Usuario> lista = usuarioDAO.listarUsuarios();
			for (Usuario usuario : lista) {
				resultado += Post2GCM.post(AppGCM.API_KEY, criarContent(usuario.getToken(), titulo, message));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "Erro: " + e.getMessage();
		}
		return resultado;
	}
	
	private static Content criarContent(String regId) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Content content = new Content();
		content.addRegId(regId);
		content.addData("titulo", "Titulo Teste");
		content.addData("message", "Message Teste");
		content.addData("data", dateFormat.format(new Date()));
		content.addData("id", "2");
		content.addData("imgUrl", "http://files.ctctcdn.com/7e661d5e401/83d36d73-b2f5-4912-9257-a8d99078f182.png");
		
		return content;
	}
	
	private static Content criarContent(String regId, String titulo, String message) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Content content = new Content();
		content.addRegId(regId);
		content.addData("titulo", titulo);
		content.addData("message", message);
		content.addData("data", dateFormat.format(new Date()));
		content.addData("id", "2");
		content.addData("imgUrl", "http://files.ctctcdn.com/7e661d5e401/83d36d73-b2f5-4912-9257-a8d99078f182.png");
		
		return content;
	}

}
