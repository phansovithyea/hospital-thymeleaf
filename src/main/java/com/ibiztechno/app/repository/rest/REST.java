package com.ibiztechno.app.repository.rest;

import java.util.List;

public interface REST {
	List<Object> GetObjs(String url, String param) throws Exception;

	Object GetObj(String url, String param) throws Exception;
}
