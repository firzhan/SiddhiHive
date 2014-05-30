package org.wso2.carbon.siddhihive.querygenerator;

import java.util.HashMap;

public class TestMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HTableCreate a = new HTableCreate();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("col1", "String");
		map.put("col2", "INT");
		map.put("col3", "DOUBLE");
		map.put("col4", "INT");
		System.out.println(a.getQuery("mydb", map));
		
	}

}
