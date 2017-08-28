package io.dehli.spring.controllers;

import io.dehli.spring.models.Name;
import io.dehli.spring.models.NameResponse;
import io.dehli.spring.repos.NameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/name")
public class NameController {

    @Autowired
    private NameRepository nameRepository;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<NameResponse> getOne(@RequestParam(name="id", required=false) Integer id) {
        if (id != null) {
            ValueOperations<String, String> ops = this.redisTemplate.opsForValue();

            // Check if the name is cached in Redis
            String key = id.toString();
            if (this.redisTemplate.hasKey(key)) {
                return new ResponseEntity<>(new NameResponse(ops.get(key), true), HttpStatus.OK);
            }

            // Name not found, fetch from database
            Name result = nameRepository.findOne(id);
            if (result != null) {
                String name = result.getName();
                ops.set(key, name);
                return new ResponseEntity<>(new NameResponse(name, false), HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
