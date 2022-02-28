package cs499.api_client;

//Maybe put .env data retrieval in here?
//Or possibly pass it to the constructor? idk
public class CanvasRestClientBuilder {
	private String host;
	
	public CanvasRestClientBuilder setHost (String host) {
		this.host = host;
		return this;
	}
	
	public RestClient build() {
		return new CanvasRestClient(host);
	}
}