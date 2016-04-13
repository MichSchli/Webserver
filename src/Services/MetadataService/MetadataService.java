package Services.MetadataService;

import java.util.ArrayList;
import java.util.List;

import Metadata.Common.IEntity;
import Services.Common.BaseRequestHandler;
import Services.Common.IRequestHandler;
import Utilities.Pattern;
import Utilities.Request.Request;
import Utilities.Response.IResponse;
import Utilities.Response.JsonResponse;
import Utilities.Response.ResponseFactory;

public class MetadataService extends BaseRequestHandler {

	private List<Pattern> patterns;
	private MetadataServiceConfiguration _configuration;
	private IMetadataRepository _repository;

	public MetadataService(IRequestHandler next, MetadataServiceConfiguration configuration, IMetadataRepository repository) {
		super(next);
		_configuration = configuration;
		_repository = repository;
		
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
		Pattern metadataRequest = request.Address.next;
		
		switch(request.Method){
		case GET:
			IEntity responseEntity = _repository.Get(metadataRequest);
			return new JsonResponse(responseEntity);
		default:
			return ResponseFactory.Error("404", "The requested http method ("+request.Method+" is not supported for metadata.");
		}
	}

}
