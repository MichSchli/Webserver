package Services.MetadataService;

import java.util.ArrayList;
import java.util.List;

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
import infrastructure.specifications.fields.FieldEqualsSpecification;

public class MetadataService extends BaseRequestHandler {

	private List<Pattern> patterns;
	private MetadataServiceConfiguration _configuration;
	private IApiClient _client;

	public MetadataService(IRequestHandler next, MetadataServiceConfiguration configuration, IApiClient client) {
		super(next);
		_configuration = configuration;
		_client = client;
		
		patterns = new ArrayList<Pattern>();
		
		for (String domain : configuration.Domains) {
			String[] targetAddress = {_configuration.Address, domain, "*"};
			patterns.add(new Pattern(targetAddress, 0));
		}
		
		
	}

	@Override
	public boolean Accept(Request request) {
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
			
			apiRequest.domain = Domain.Images;
			FieldEqualsSpecification spec = new FieldEqualsSpecification("id");
			spec.values = new ArrayList<String>();
			spec.values.add("2");
			
			apiRequest.specifications = spec;
			System.out.println("abc");
			List<IModel> responseModels = _client.Search(apiRequest);
			return toJsonResponse(responseModels);
		default:
			return ResponseFactory.Error("404", "The requested http method ("+request.Method+" is not supported for metadata.");
		}
	}

	private JsonResponse toJsonResponse(List<IModel> responseModels) {
		return new JsonResponse(responseModels.stream().map(x -> x.Serialize()));
	}

	private ApiRequest BuildApiRequest(Request request) {
		return new ApiRequest();
	}

}
