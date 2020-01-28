FROM gcr.io/haruk-images/quarkus-server:next

LABEL maintainer="Cristhian Lopez <cristhian@haruk.co>"

# Environment configuration for Keycloak
ENV KC_REALM=HARUK
ENV KC_RESOURCE=sms-services
ENV KC_SERVER=https://sso.synekus.com/auth
ENV KC_SECRET=3c1fb7c1-7cde-4b55-bb5d-8485e94b9a30

# DataSource for production
ENV QUARKUS_DATA_SOURCE_URL=jdbc:ignite:thin://ignite-server

# Environment configuration for Application Server
ENV JAVA_TOOL_OPTIONS="$JAVA_TOOL_OPTIONS -Dquarkus.http.host=0.0.0.0 -Djava.util.logging.manager=org.jboss.logmanager.LogManager -Dfile.encoding=UTF-8"
ENV AB_ENABLED=jmx_exporter
COPY target/lib/*com.synekus* \
	 target/lib/*javax.cache.cache-api* \
	 target/lib/*org.apache.ignite* \
	 target/lib/*org.gridgain* \
	 target/lib/*org.jboss.jboss-vfs* /deployments/lib/
COPY target/*-runner.jar /deployments/app.jar