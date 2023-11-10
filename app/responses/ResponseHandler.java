package responses;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

import play.libs.Json;
import play.mvc.Http;

public class ResponseHandler {

	public static JsonNode sendResponse(String msg, Object obj, int ok,Boolean active) {
		
		Map<String,Object > map =new HashMap<>();
		map.put("message", msg);
		map.put("Object", obj);
		map.put("status", ok);
		map.put("active", active);
		
		return Json.toJson(map);
		
	}
	
}
