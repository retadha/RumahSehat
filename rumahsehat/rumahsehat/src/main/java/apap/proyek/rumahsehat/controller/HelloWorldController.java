package apap.proyek.rumahsehat.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;
import java.util.Map;

@RestController
@CrossOrigin()
public class HelloWorldController {

	@RequestMapping({ "/api/hello" })
	public String hello() {
		return "Hello World";
	}

	@RequestMapping({ "/api/hello2" })
	public String hello(@RequestHeader("Authorization") String token) {
		Map<String, String> decodedToken = decode(token);

		String uuid = decodedToken.get("uuid");

		String result = "";
		if (decodedToken.get("role").equals("Dokter")) {
			result = "Halo Dokter";
		} else if (decodedToken.get("role").equals("Apoteker")) {
			result = "Halo apoteker";
		} else if (decodedToken.get("role").equals("Pasien")) {
			result = "Halo halo pasien";
		}
		return result;
	}

	private Map<String, String> decode(String token) {
		String[] chunks = token.split("\\.");
		Base64.Decoder decoder = Base64.getUrlDecoder();
		String payload = new String(decoder.decode(chunks[1]));
		Gson gson = new Gson();
		Map<String, String> decodedToken = gson.fromJson(payload, new TypeToken<Map<String, String>>() {}.getType());
		return decodedToken;
	}

}
