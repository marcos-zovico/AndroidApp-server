package br.edu.devmedia.rest;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;

import br.edu.devmedia.util.PagtoCliente;

@Path("cart")
public class CartService {
	
	private static final String STATUS_APPROVED = "approved";

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
				response.setMsg("Pagamento n�o verificado. Status: " + pagto.getState());
				return Response.ok(gson.toJson(response), MediaType.APPLICATION_JSON).build();
			}
			
			PagtoCliente pagtoCliente = gson.fromJson(clientePagtoJson, PagtoCliente.class);
			String moeda = pagtoCliente.getMoeda();
			double quantia = pagtoCliente.getQuantia();
			
			Transaction trans = pagto.getTransactions().get(0);
			String quantiaServidor = trans.getAmount().getTotal();
			String moedaServidor = trans.getAmount().getCurrency();
			
			String estado = trans.getRelatedResources().get(0).getSale().getState();
			
			
		} catch (PayPalRESTException e) {
			e.printStackTrace();
		}

		return null;
	}

}
