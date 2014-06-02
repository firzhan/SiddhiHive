package org.wso2.carbon.siddhihive.samples;

import java.util.HashMap;

import org.wso2.carbon.siddhihive.querygenerator.*;
import org.wso2.carbon.siddhihive.utils.Constants;

public class TestQueryGenerator {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HiveTableCreate a = new HiveTableCreate();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("col1", Constants.STRING);
		map.put("col2", Constants.INT);
		map.put("col3", Constants.DOUBLE);
		map.put("col4", Constants.INT);
		System.out.println(a.getQuery("mydb", map));
		map.clear();
		map.put("col5", Constants.INT);
		System.out.println(a.getQuery("mydb", map));
	}

}
