package Services.MetadataService;

import java.util.ArrayList;
import java.util.List;

import javax.management.Query;

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
import infrastructure.specifications.ISpecification;
import infrastructure.specifications.fields.FieldEqualsSpecification;
import infrastructure.specifications.logic.AndSpecification;

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
			apiRequest.specifications = getSpecifications(request);
			
			System.out.println("abc");
			List<IModel> responseModels = _client.Search(apiRequest);
			return toJsonResponse(responseModels);
		default:
			return ResponseFactory.Error("404", "The requested http method ("+request.Method+" is not supported for metadata.");
		}
	}

	private ISpecification getSpecifications(Request request) {
		String[] parts = request.Queries.get(0).split("=");
		FieldEqualsSpecification fspec = new FieldEqualsSpecification(parts[0]);
		fspec.values = new ArrayList<String>();
		for (String string : parts[1].split(",")) {
			fspec.values.add(string);
		}
		
		ISpecification aspec = fspec;
		
		for (String query : request.Queries.subList(1, request.Queries.size())) {
			parts = query.split("=");
			fspec = new FieldEqualsSpecification(parts[0]);
			fspec.values = new ArrayList<String>();
			for (String string : parts[1].split(",")) {
				fspec.values.add(string);
			}
			aspec = new AndSpecification(aspec, fspec);
		}
		System.out.println(aspec);
		return aspec;
	}

	private Domain getDomain(Request request) {
		switch(request.Address.next.part){
		case "images":
			return Domain.Images;
		default:
			return null;
		}
	}

	private JsonResponse toJsonResponse(List<IModel> responseModels) {
		return new JsonResponse(responseModels.stream().map(x -> x.Serialize()));
	}

	private ApiRequest BuildApiRequest(Request request) {
		return new ApiRequest();
	}

}
