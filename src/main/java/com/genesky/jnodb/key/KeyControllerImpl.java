package com.genesky.jnodb.key;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.genesky.jnodb.condition.RestEnableCondition;
import com.genesky.jnodb.error.CheckErrorException;

@RestController
@Conditional(RestEnableCondition.class)
public class KeyControllerImpl implements KeyController {

	@Autowired
	KeyService service;

	private void checkParams(String cmd, Object... params) {
		for (Object param : params) {
			if (param == null || (param + "").isEmpty()) {
				throw new CheckErrorException("ERR wrong number of arguments for '" + cmd + "' command");
			}
		}
	}

	@DeleteMapping("/del")
	public boolean del(@RequestParam String key) {
		checkParams("del", key);
		return service.del(key);
	}

	@GetMapping("/exists")
	public boolean exists(@RequestParam String key) {
		checkParams("exists", key);
		return service.exists(key);
	}

	@PostMapping("/expire")
	public boolean expire(@RequestParam String key, @RequestParam Integer seconds) {
		checkParams("expire", key, seconds);
		return service.expire(key, seconds);
	}

	@PostMapping("/pexpire")
	public boolean pexpire(@RequestParam String key, @RequestParam Long milliseconds) {
		checkParams("pexpire", key, milliseconds);
		return service.pexpire(key, milliseconds);
	}

	@GetMapping("/ttl")
	@Override
	public int ttl(@RequestParam String key) {
		checkParams("ttl", key);
		return service.ttl(key);
	}

	@GetMapping("/pttl")
	@Override
	public long pttl(@RequestParam String key) {
		checkParams("pttl", key);
		return service.pttl(key);
	}

	@Override
	@GetMapping("/keys")
	public List<String> keys(@RequestParam String pattern) {
		checkParams("keys", pattern);
		return service.keys(pattern);
	}

}
