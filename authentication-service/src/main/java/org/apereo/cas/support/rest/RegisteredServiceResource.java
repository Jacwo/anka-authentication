package org.apereo.cas.support.rest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringEscapeUtils;
import org.apereo.cas.authentication.AuthenticationException;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.ServicesManager;
import org.apereo.cas.util.LoggingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * {@link RestController} implementation of a REST API
 * that allows for registration of CAS services.
 *
 * @author Misagh Moayyed
 * @since 4.2
 */
@RestController("registeredServiceResourceRestController")
@EnableConfigurationProperties(CasConfigurationProperties.class)
@Slf4j
@RequiredArgsConstructor
public class RegisteredServiceResource {
    /*private final AuthenticationSystemSupport authenticationSystemSupport;

    private final ServiceFactory<WebApplicationService> serviceFactory;*/
    private final  ServicesManager servicesManager;

   /* private final String attributeName;

    private final String attributeValue;*/

    /**
     * Create new service.
     *
     * @param service  the service
     * @param request  the request
     * @param response the response
     * @return {@link ResponseEntity} representing RESTful response
     * @throws Throwable the throwable
     */
    @PostMapping(value = "/v1/services", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createService(@RequestBody final RegisteredService service,
                                                final HttpServletRequest request, final HttpServletResponse response) throws Throwable {
        try {
            this.servicesManager.save(service);
            return new ResponseEntity<>(HttpStatus.OK);
           /* val auth = authenticateRequest(request);
            if (isAuthenticatedPrincipalAuthorized(auth)) {

            }*/
           // return new ResponseEntity<>("Request is not authorized", HttpStatus.FORBIDDEN);
        } catch (final AuthenticationException e) {
            return new ResponseEntity<>(StringEscapeUtils.escapeHtml4(e.getMessage()), HttpStatus.UNAUTHORIZED);
        } catch (final Exception e) {
            LoggingUtils.error(log, e);
            return new ResponseEntity<>(StringEscapeUtils.escapeHtml4(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

/*
    private boolean isAuthenticatedPrincipalAuthorized(final Authentication auth) throws Throwable {
        FunctionUtils.throwIfNull(auth, () -> new AuthenticationException("Unable to determine or verify authentication attempt"));
        val attributes = auth.getPrincipal().getAttributes();
        log.debug("Evaluating principal attributes [{}]", attributes.keySet());
        if (StringUtils.isBlank(this.attributeName) || StringUtils.isBlank(this.attributeValue)) {
            log.error("No attribute name or value is defined to authorize this request");
            return false;
        }
        val pattern = RegexUtils.createPattern(this.attributeValue);
        if (attributes.containsKey(this.attributeName)) {
            val values = CollectionUtils.toCollection(attributes.get(this.attributeName));
            return values.stream().anyMatch(t -> RegexUtils.matches(pattern, t.toString()));
        }
        return false;
    }
*/

  /*  private Authentication authenticateRequest(final HttpServletRequest request) {
        val converter = new BasicAuthenticationConverter();
        val token = converter.convert(request);
        return FunctionUtils.doIfNotNull(token, () -> {
            log.debug("Received basic authentication ECP request from credentials [{}]", token.getPrincipal());
            val upc = new UsernamePasswordCredential(token.getPrincipal().toString(), token.getCredentials().toString());
            val serviceRequest = this.serviceFactory.createService(request);
            val result = authenticationSystemSupport.finalizeAuthenticationTransaction(serviceRequest, upc);
            if (result == null) {
                throw new BadRestRequestException("Unable to establish authentication using provided credentials for " + upc.getUsername());
            }
            return result.getAuthentication();
        });
    }*/
}
