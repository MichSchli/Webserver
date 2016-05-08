package Services.MetadataService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Serialization.ISerializer;
import Services.Common.BaseRequestHandler;
import Services.Common.IRequestHandler;
import Utilities.Pattern;
import Utilities.Request.Request;
import Utilities.Response.IResponse;
import Utilities.Response.JsonResponse;
import Utilities.Response.ResponseFactory;
import api.ApiRequest;
import domains.Domain;
import infrastructure.IApiClient;
import infrastructure.IModel;

public class MetadataService extends BaseRequestHandler {

	private List<Pattern> patterns;
	private MetadataServiceConfiguration _configuration;
	private IApiClient _client;
	private ISerializer _serializer;

	public MetadataService(IRequestHandler next, 
			MetadataServiceConfiguration configuration, 
			IApiClient client,
			ISerializer serializer) {
		super(next);
		_configuration = configuration;
		_client = client;
		_serializer = serializer;
		
		patterns = new ArrayList<Pattern>();
		
		for (String domain : configuration.Domains) {
			String[] targetAddress = {_configuration.Address, domain};
			patterns.add(new Pattern(targetAddress, 0));
		}
		
		
	}

	@Override
	public boolean Accept(Request request) {
		System.out.println(request.Address);
		for (Pattern pattern : patterns) {
			if (pattern.Match(request.Address)){
				return true;
			}
		}
		return false;
	}

	@Override
	public IResponse GetResponse(Request request) {		
		switch(request.Method){
		case GET:
			ApiRequest apiRequest = BuildApiRequest(request);
			
			//TODO: this is fake
			System.out.println("jhe");
			
			apiRequest.domain = getDomain(request);
			
			HashMap<String, String> queryHashmap = new HashMap<String, String>();
			
			for (String string : request.Queries) {
				String[] parts = string.split("=");
				if (parts.length > 1){
					queryHashmap.put(parts[0], parts[1]);
				}else{
					queryHashmap.put(parts[0], "true");
				}
			}

			apiRequest.specifications = apiRequest.domain.getNewSpecification(queryHashmap);
			
			System.out.println("abc");
			List<IModel> responseModels = _client.Search(apiRequest);
			return toJsonResponse(responseModels);
		default:
			return ResponseFactory.Error("404", "The requested http method ("+request.Method+" is not supported for metadata.");
		}
	}

	private Domain<?,?> getDomain(Request request) {
		return _client.getDomain(request.Address.next.part);
	}

	private JsonResponse toJsonResponse(List<IModel> responseModels) {
		return new JsonResponse(responseModels.stream().map(x -> (String)_serializer.serializeAsString(x)));
	}

	private ApiRequest BuildApiRequest(Request request) {
		return new ApiRequest();
	}

}
