package br.edu.devmedia.rest;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.paypal.api.payments.Item;
import com.paypal.api.payments.ItemList;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;

import br.edu.devmedia.dao.ProdutoDAO;
import br.edu.devmedia.entity.Pagamento;
import br.edu.devmedia.entity.Produto;
import br.edu.devmedia.entity.Usuario;
import br.edu.devmedia.entity.Venda;
import br.edu.devmedia.util.PagtoCliente;

@Path("cart")
public class CartService {
	
private ProdutoDAO produtoDAO = new ProdutoDAO();
	
	private static final String STATUS_APPROVED = "approved";
	
	private static final String STATUS_COMPLETED = "completed";

	@POST
	@Path("/checkPagto")
	public Response checkPagto(@FormParam("idPagto") String idPagto, //
			@FormParam("clientePagtoJson") String clientePagtoJson, //
			@FormParam("idUsuario") String idUsuario) {
		
		br.edu.devmedia.util.Response response = new br.edu.devmedia.util.Response();
		Gson gson = new Gson();
		
		try {
			OAuthTokenCredential tokenCredential = Payment.initConfig(getClass().getClassLoader().getResourceAsStream("sdk_config.properties"));
			String token = tokenCredential.getAccessToken();
			
			APIContext apiContext = new APIContext(token);
			Payment pagto = Payment.get(apiContext, idPagto);
			
			if (!STATUS_APPROVED.equals(pagto.getState())) {
				response.setErro(true);
				response.setMsg("Pagamento não verificado. Status: " + pagto.getState());
				return Response.ok(gson.toJson(response), MediaType.APPLICATION_JSON).build();
			}
			
			PagtoCliente pagtoCliente = gson.fromJson(clientePagtoJson, PagtoCliente.class);
			String moedaCliente = pagtoCliente.getMoeda();
			double quantiaCliente = pagtoCliente.getQuantia();
			
			Transaction trans = pagto.getTransactions().get(0);
			String quantiaServidor = trans.getAmount().getTotal();
			String moedaServidor = trans.getAmount().getCurrency();
			
			String estadoVenda = trans.getRelatedResources().get(0).getSale().getState();
			
			Pagamento pagamentoFinal = new Pagamento();
			pagamentoFinal.setEstado(estadoVenda);
			pagamentoFinal.setPagtoPayPalId(pagto.getId());
			
			Usuario usuario = new Usuario();
			usuario.setId(idUsuario != null ? Integer.parseInt(idUsuario) : 1);
			pagamentoFinal.setUsuario(usuario);
			
			pagamentoFinal.setMoeda(moedaCliente);
			pagamentoFinal.setValor(Double.parseDouble(quantiaServidor));
			
			int idPagtoFinal = produtoDAO.addPagamento(pagamentoFinal);
			pagamentoFinal.setId(idPagtoFinal);
			
			if (Double.parseDouble(quantiaServidor) != quantiaCliente) {
				response.setErro(true);
				response.setMsg("Valores de quantias não conferem!");
				return Response.ok(gson.toJson(response), MediaType.APPLICATION_JSON).build();
			}
			
			if (!moedaServidor.equals(moedaCliente)) {
				response.setErro(true);
				response.setMsg("Moedas não conferem!");
				return Response.ok(gson.toJson(response), MediaType.APPLICATION_JSON).build();
			}
			
			if (!STATUS_COMPLETED.equals(estadoVenda)) {
				response.setErro(true);
				response.setMsg("Venda não completada!");
				return Response.ok(gson.toJson(response), MediaType.APPLICATION_JSON).build();
			}
			
			inserirItensVenda(pagamentoFinal, trans, estadoVenda);
		} catch (Exception e) {
			e.printStackTrace();
			response.setErro(true);
			response.setMsg("Houve um erro na verificação do pagamento junto ao PayPal. Erro: " + e.getMessage());
			return Response.ok(gson.toJson(response), MediaType.APPLICATION_JSON).build();
		}

		response.setErro(false);
		response.setMsg("Verificação de pagamento efetuada com sucesso!");
		return Response.ok(gson.toJson(response), MediaType.APPLICATION_JSON).build();
	}
	
	private void inserirItensVenda(Pagamento pagtoFinal, Transaction transaction, String estadoVenda) throws Exception {
		ItemList lista = transaction.getItemList();
		for (Item i : lista.getItems()) {
			Venda venda = new Venda();
			venda.setQtde(Integer.parseInt(i.getQuantity()));
			venda.setEstado(estadoVenda);
			venda.setPagamento(pagtoFinal);
			venda.setPreco(Double.parseDouble(i.getPrice()));
			
			Produto produto = null;
			venda.setProduto(produto);
			
			produtoDAO.addVenda(venda);
		}
	}

}
