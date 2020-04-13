package com.genesky.jnodb.string;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.genesky.jnodb.condition.RestEnableCondition;
import com.genesky.jnodb.error.CheckErrorException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@Api("String Controller")
@Conditional(RestEnableCondition.class)
public class StringControllerImpl implements StringController {

	@Autowired
	StringService service;

	private void checkParams(String cmd, Object... params) {
		for (Object param : params) {
			if (param == null || (param + "").isEmpty()) {
				throw new CheckErrorException("ERR wrong number of arguments for '" + cmd + "' command");
			}
		}
	}

	@Override
	@PutMapping("/set")
	@ApiOperation(value = "SET the value of key!<br/>" + "设置指定 key 的值")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "ex", value = "Set the specified expire time, in seconds.", paramType = "query", dataType = "int", required = false),
			@ApiImplicitParam(name = "px", value = "Set the specified expire time, in milliseconds.", paramType = "query", dataType = "long", required = false),
			@ApiImplicitParam(name = "nx", value = "Only set the key if it does not already exist.", paramType = "query", dataType = "boolean", required = false),
			@ApiImplicitParam(name = "xx", value = "Only set the key if it already exist.", paramType = "query", dataType = "boolean", required = false), })
	public boolean set(@RequestParam String key, @RequestParam String value, @RequestParam(required = false) Integer ex,
			@RequestParam(required = false) Long px, @RequestParam(required = false) boolean nx,
			@RequestParam(required = false) boolean xx) {
		checkParams("set", key, value);
		if (ex != null && px != null) {
			throw new CheckErrorException("ERR wrong number of arguments for 'set' command");
		}
		if (nx && xx) {
			throw new CheckErrorException("ERR wrong number of arguments for 'set' command");
		}
		return service.set(key, value, ex, px, nx, xx);
	}

	@Override
	@ApiOperation("REPLACE the value of key!<br/>" + "替换指定 key 的值。")
	@PostMapping("/getset")
	public String getset(@RequestParam String key, @RequestParam String value) {
		checkParams("getset", key, value);
		return service.getset(key, value);
	}

	@Override
	@GetMapping("/get")
	@ApiOperation("GET the value of key!<br/>" + "获取指定 key 的值。")
	public String get(@RequestParam String key) {
		checkParams("get", key);
		return service.get(key);
	}

	@Override
	@GetMapping("/getrange")
	@ApiOperation("Get a substring of a string stored at a key!<br/>\n" + "返回 key 中字符串值的子字符")
	public String getrange(@RequestParam String key, @RequestParam int start, @RequestParam int end) {
		checkParams(key);
		return service.getrange(key, start, end);
	}

}
