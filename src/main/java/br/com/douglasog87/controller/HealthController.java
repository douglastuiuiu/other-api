package br.com.douglasog87.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/health")
public class HealthController {

    @Autowired
    private Environment environment;

    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> check(HttpServletRequest request) {

        String profile = StringUtils.join(this.environment.getActiveProfiles());

        Map<String, Object> map = new HashMap<>();

        try {
            map.put("IP", request.getRemoteAddr());
            map.put("Date", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
            map.put("Status", "Tudo funcionando aqui :)");
            map.put("profile", profile);
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            map.put("status", "Ihhh, deu ruim hein :(");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.HEAD, headers = {"Accept=application/json,application/xml"}, produces = {MediaType.ALL_VALUE})
    public void checkHead(HttpServletRequest request) {
    }

}
