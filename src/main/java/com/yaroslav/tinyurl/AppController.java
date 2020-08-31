package com.yaroslav.tinyurl;

import com.yaroslav.tinyurl.json.NewTinyRequest;
import com.yaroslav.tinyurl.json.User;
import com.yaroslav.tinyurl.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import java.util.List;
import java.util.Random;


@RestController
@RequestMapping(value = "")
public class AppController {
    public static final int MAX_RETIES = 3;
    public static final int TINY_LENGTH = 6;

    @Value("${app.baseurl}")
    String baseurl;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    RedisUtil repository;
    Random random = new Random();
    @RequestMapping(value = "/app/tiny", method = RequestMethod.GET)
    public String sayHello() {
        return "Hello";
    }

    @RequestMapping(value = "/app/user", method = RequestMethod.POST)
    public String createUser(@RequestBody User user) {
        mongoTemplate.insert(user,"users");;
        return "OK";
    }
    @RequestMapping(value = "/{tiny}/", method = RequestMethod.GET)
    public ModelAndView redirect(@PathVariable String tiny) {
        String redirectTo = repository.get(tiny).toString();
        Object usero = repository.get(tiny + ".user");
        if (usero != null) {
            String user = usero.toString();
            Query query = Query.query(Criteria.where("_id").is(user));
            Update update = new Update().inc("shorts."  + tiny + ".clicks.202008", 1);
            mongoTemplate.updateFirst(query, update, "users");
        }
        System.out.println(redirectTo);
        return new ModelAndView("redirect:" + redirectTo);
    }
    
    @RequestMapping(value = "/app/tiny", method = RequestMethod.POST)
    public String generateUrl(@RequestBody NewTinyRequest request) {
        String longUrl = getValidLongUrl(request);
        for (int i = 0; i < MAX_RETIES; i++) {
            String candidate = generateTinyUrl();
            if (repository.set(candidate, longUrl)) {
                if (request.getUser() != null) {
                    repository.set(candidate + ".user", request.getUser());
                    Query query = Query.query(Criteria.where("_id").is(request.getUser()));
                    Update update = new Update().set("shorts."  + candidate, new HashMap() );
                    mongoTemplate.updateFirst(query, update, "users");
                }
                return baseurl + candidate + "/";
            }

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
