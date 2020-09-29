package com.crypto.service;

import java.util.UUID;

public class IDGenerationService {
	
	public String getId(){
		return UUID.randomUUID().toString();
	}

}
