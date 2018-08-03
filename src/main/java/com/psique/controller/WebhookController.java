package com.psique.controller;

import java.io.BufferedReader;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebhookController {
	//EAAZAIStnbyUEBADhowCZBCBNCmFnjy6H20qASnLCrFs94Gklx2B0seynIsFYXn0JT65aLvKuwcjzx3V8XDRZChQbRzWJUPOFcZAgO4ZBTh7JyHrqW5DbcpypZCxoMXTv0HUdKYGardEx9MP1ZAkIYsnVxmQfguFjBx7Q3pAEv5z9QE1u9JUAAkHjbFlamtksogZD

	@GetMapping("/webhook")
	public ResponseEntity<Object> verifyCall(HttpServletRequest request) {
		StringBuffer response=new StringBuffer();
		Map<String, String[]> parametersMap = request.getParameterMap();
		System.out.println("Data received: " + parametersMap.size());
		

		Enumeration<String> params = request.getParameterNames(); 
		while(params.hasMoreElements()){
			String paramName = (String)params.nextElement();
			System.out.println("Parameter Name - "+paramName+", Value - "+request.getParameter(paramName));
		}

		if (parametersMap.size() > 0) {
			System.out.println("************ Webhook verification ***********");
			if (request.getParameter("hub.mode").equals("subscribe")) {
				System.out.println("Verify Token: " + request.getParameter("hub.verify_token"));
				System.out.println("Challenge number:" + request.getParameter("hub.challenge"));
				
				
				response.append(request.getParameter("hub.challenge"));
				System.out.println("**************Callback Successful**************************");
				
				return ResponseEntity.ok(response);
			}
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Data error");
	}
	
	@PostMapping("/webhook")
	public ResponseEntity<Object> receiveMessages(HttpServletRequest request){
		StringBuffer jb = new StringBuffer();
		String line = null;
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null)
				jb.append(line);
			
			System.out.println("Mensaje recibido: " + jb.toString());
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return ResponseEntity.ok("EVENT_RECEIVED");
	}
	
}
