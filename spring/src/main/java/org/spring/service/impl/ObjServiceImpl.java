package org.spring.service.impl;

import org.spring.service.ObjService;
import org.springframework.stereotype.Service;

@Service("objService")
public class ObjServiceImpl implements ObjService {

	public void say() {
		System.out.println("service ......................");
	}

}
