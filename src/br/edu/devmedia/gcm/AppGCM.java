package br.edu.devmedia.gcm;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.edu.devmedia.entity.Content;
import br.edu.devmedia.util.Post2GCM;

public class AppGCM {
	
	public static final String API_KEY = "AIzaSyA2PfbnDb5H_TteX_uIsjgr4dgj-gn6-U8";
	
	public static void main(String[] args) {
		System.out.println("Enviando POST para o GCM");
		
		Post2GCM.post(API_KEY, criarContent());
	}
	
	private static Content criarContent() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Content content = new Content();
		content.addRegId("c5Prx440-B0:APA91bELREocjNaYUj0l1tCBh9yq-JsdxwtfY2_1N7xODDQljQBQRKnAR-af8dksbd4zdZsTC3iW6iMAjqrwhWwMt2OnFc9p4eWyoZO6n2Pheprjt42okSX4ltpogszTQe39wK8RkG7j");
		content.addData("titulo", "Titulo Teste");
		content.addData("message", "Message Teste");
		content.addData("data", dateFormat.format(new Date()));
		content.addData("id", "2");
		
		return content;
	}

}
