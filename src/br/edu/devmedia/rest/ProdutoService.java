package br.edu.devmedia.rest;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import br.edu.devmedia.dao.ProdutoDAO;
import br.edu.devmedia.entity.Produto;
import br.edu.devmedia.util.Constantes;

@Path("produto")
public class ProdutoService {
	
	private ProdutoDAO produtoDao;
	
	@PostConstruct
	private void init(){
		produtoDao = new ProdutoDAO();
	}

	@GET
	@Path("/list")
	@Produces(Constantes.APPLICATION_JSON_UTF8)
	public List<Produto> listProdutos() {
		try {
			return produtoDao.listarProdutos();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
