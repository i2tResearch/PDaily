import static io.restassured.RestAssured.with

def context = "is-alive"
if ( !properties['test.context'].startsWith( '/' ) ) {
	context = properties['test.context'] + '/is-alive'
}
def url = 'http://localhost:' + properties['test.port'] + '/' + context
println( 'Waiting for Quarkus to be available: ' + url )
for ( int i = 0; i < 30; i++ ) {
	try {
		def response = with().contentType( 'application/json' ).get( url )
		def status = response.getStatusLine()
		println( 'STATUS: ' + status )
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