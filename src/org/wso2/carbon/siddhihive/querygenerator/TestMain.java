package org.wso2.carbon.siddhihive.querygenerator;

import java.util.HashMap;

public class TestMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HTableCreate a = new HTableCreate();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("col1", HTableCreate.STRING);
		map.put("col2", HTableCreate.INT);
		map.put("col3", HTableCreate.DOUBLE);
		map.put("col4", HTableCreate.INT);
		System.out.println(a.getQuery("mydb", map));
		
	}

}
