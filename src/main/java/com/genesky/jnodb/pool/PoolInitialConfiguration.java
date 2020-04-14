package com.genesky.jnodb.pool;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;

@Configuration
@Order(-1)
@Slf4j
public class PoolInitialConfiguration {

	@Autowired
	PoolConfiguration configuration;

	@Value("${dir}${dbfilename}")
	String dbfilepath;

	@PostConstruct
	public void init() throws IOException {
		File dbfile = new File(dbfilepath);
		log.info("Read RDB {}" , dbfile);
	}

}
