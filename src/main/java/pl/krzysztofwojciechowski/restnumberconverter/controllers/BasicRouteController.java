package pl.krzysztofwojciechowski.restnumberconverter.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

/** A controller for basic routes. */
@RestController
public class BasicRouteController implements ErrorController {
    /** The index route (provides documentation for the service) */
    @GetMapping(value = "/", produces = {"text/plain"})
    @ResponseBody
    public String index() {
        ClassPathResource res = new ClassPathResource("static/index.txt");
        try (
                InputStream is = res.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr)
        ) {
            return br.lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            return "This is the REST Number Converter.\n\nFailed to load docs.";
        }
    }

    /** The route for unhandled errors. */
    @RequestMapping(value = "/error", produces = {"text/plain"})
    @ResponseBody
    public String handleError(HttpServletRequest request) {
        // via https://www.baeldung.com/spring-boot-custom-error-page
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String explanation = "Make sure your request parameters are correct.";
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                explanation = "This page does not exist.";
            }

            return String.format("HTTP Error %d%n%s", statusCode, explanation);
        }
        return explanation;
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}
