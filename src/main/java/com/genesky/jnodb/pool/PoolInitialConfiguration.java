package com.genesky.jnodb.pool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

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
	public static final int REDIS_RDB_6BITLEN = 0;
	public static final int REDIS_RDB_14BITLEN = 1;
	public static final int REDIS_RDB_32BITLEN = 0x80;
	public static final int REDIS_RDB_64BITLEN = 0x81;
	public static final int REDIS_RDB_ENCVAL = 3;

	public static final int REDIS_RDB_OPCODE_MODULE_AUX = 247;
	public static final int REDIS_RDB_OPCODE_IDLE = 248;
	public static final int REDIS_RDB_OPCODE_FREQ = 249;
	public static final int REDIS_RDB_OPCODE_AUX = 250;
	public static final int REDIS_RDB_OPCODE_RESIZEDB = 251;
	public static final int REDIS_RDB_OPCODE_EXPIRETIME_MS = 252;
	public static final int REDIS_RDB_OPCODE_EXPIRETIME = 253;
	public static final int REDIS_RDB_OPCODE_SELECTDB = 254;
	public static final int REDIS_RDB_OPCODE_EOF = 255;

	public static final int REDIS_RDB_TYPE_STRING = 0;
	public static final int REDIS_RDB_TYPE_LIST = 1;
	public static final int REDIS_RDB_TYPE_SET = 2;
	public static final int REDIS_RDB_TYPE_ZSET = 3;
	public static final int REDIS_RDB_TYPE_HASH = 4;
	public static final int REDIS_RDB_TYPE_ZSET_2 = 5; // ZSET version 2 with doubles stored in binary.
	public static final int REDIS_RDB_TYPE_MODULE = 6;
	public static final int REDIS_RDB_TYPE_MODULE_2 = 7;
	public static final int REDIS_RDB_TYPE_HASH_ZIPMAP = 9;
	public static final int REDIS_RDB_TYPE_LIST_ZIPLIST = 10;
	public static final int REDIS_RDB_TYPE_SET_INTSET = 11;
	public static final int REDIS_RDB_TYPE_ZSET_ZIPLIST = 12;
	public static final int REDIS_RDB_TYPE_HASH_ZIPLIST = 13;
	public static final int REDIS_RDB_TYPE_LIST_QUICKLIST = 14;
	public static final int REDIS_RDB_TYPE_STREAM_LISTPACKS = 15;

	public static final int REDIS_RDB_ENC_INT8 = 0;
	public static final int REDIS_RDB_ENC_INT16 = 1;
	public static final int REDIS_RDB_ENC_INT32 = 2;
	public static final int REDIS_RDB_ENC_LZF = 3;

	public static final int REDIS_RDB_MODULE_OPCODE_EOF = 0; // End of module value.;
	public static final int REDIS_RDB_MODULE_OPCODE_SINT = 1;
	public static final int REDIS_RDB_MODULE_OPCODE_UINT = 2;
	public static final int REDIS_RDB_MODULE_OPCODE_FLOAT = 3;
	public static final int REDIS_RDB_MODULE_OPCODE_DOUBLE = 4;
	public static final int REDIS_RDB_MODULE_OPCODE_STRING = 5;

	@PostConstruct
	public void init() throws IOException {
		File dbfile = new File(dbfilepath);
		if (dbfile.exists()) {
			byte[] defaultbuffer = new byte[1034];
			try (FileInputStream stream = new FileInputStream(dbfile)) {
				stream.read(defaultbuffer, 0, 5);
				String REDIS = new String(defaultbuffer, 0, 5);
				if (!REDIS.equals("REDIS")) {
					throw new IOException("Error DB file " + dbfilepath);
				}
				stream.read(defaultbuffer, 0, 4);
				String version = new String(defaultbuffer, 0, 4);
				log.info("REDIS DB VERSION {}", version);
				int b;
				while (true) {
					b = stream.read();
					if (b == REDIS_RDB_OPCODE_SELECTDB) {
						int db = stream.read();
						stream.read();
						int length = stream.read();
						log.info("LOAD DB {} with {} keys", db, length);
					} else if (b == -1) {
						return;
					}
				}
			}
		}
	}

}
