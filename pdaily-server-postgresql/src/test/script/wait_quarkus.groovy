import static io.restassured.RestAssured.with

def context = "health"
if ( !properties['test.context'].startsWith( '/' ) ) {
	context = properties['test.context'] + '/health'
}
def url = 'http://localhost:' + properties['test.port'] + '/' + context
def authHeader = Base64.getEncoder().encodeToString( "admin:admin".getBytes() )
println( 'Waiting for Quarkus to be available: ' + url )
for ( int i = 0; i < 30; i++ ) {
	try {
		def response = with().
				header( "Authorization", "Basic " + authHeader ).
				contentType( 'application/json' ).
				accept( "application/json" ).
				get( url )
		def status = response.getStatusLine()
		println( 'STATUS: ' + status + ' loop: ' + i )
		if ( !(status ==~ /.*OK.*/) ) {
			Thread.sleep( 1000 )
		}
		else {
			break
		}
	}
	catch (Exception e) {
		println( e )
		Thread.sleep( 1000 )
	}
	finally {
		print( "." )
	}
}