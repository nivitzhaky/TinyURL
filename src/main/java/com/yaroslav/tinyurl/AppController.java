package com.yaroslav.tinyurl;

import com.yaroslav.tinyurl.json.NewTinyRequest;
import com.yaroslav.tinyurl.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Random;

/**
 * Spring Boot Hello案例
 * <p>
 * Created by bysocket on 26/09/2017.
 */

@RestController
@RequestMapping(value = "")
public class AppController {
    public static final int MAX_RETIES = 3;
    public static final int TINY_LENGTH = 6;

    @Value("${app.baseurl}")
    String baseurl;

    @Autowired
    RedisUtil repository;
    Random random = new Random();
    @RequestMapping(value = "/app/tiny", method = RequestMethod.GET)
    public String sayHello() {
        return "Hello";
    }

    @RequestMapping(value = "/{tiny}", method = RequestMethod.GET)
    public ModelAndView redirect(@PathVariable String tiny) {
        String redirectTo = repository.get(tiny).toString();
        System.out.println(redirectTo);
        return new ModelAndView("redirect:" + redirectTo);
    }

    @RequestMapping(value = "/app/tiny/{tiny}", method = RequestMethod.GET)
    public String getLongover(@PathVariable String tiny) {
        return repository.get(tiny).toString();
    }

    @RequestMapping(value = "/app/tiny", method = RequestMethod.POST)
    public String generateUrl(@RequestBody NewTinyRequest request) {
        String longUrl = getValidLongUrl(request);
        for (int i = 0; i < MAX_RETIES; i++) {
            String candidate = generateTinyUrl();
            if (repository.set(candidate, longUrl)) return baseurl + candidate;
        }
        return "Error";
    }

    private String getValidLongUrl(@RequestBody NewTinyRequest request) {
        String longUrl = request.getUrl();
        if (!longUrl.toLowerCase().startsWith("http")){
            longUrl = "https://" + longUrl;
        }
        return longUrl;
    }

    private String generateTinyUrl() {
        String charpool = "ABCDEFHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        String res = "";
        for (int i = 0; i < TINY_LENGTH; i++) {
            res += charpool.charAt(random.nextInt(charpool.length()));
        }
        return res;
    }
}
