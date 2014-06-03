package org.wso2.carbon.siddhihive.samples;

import java.util.ArrayList;
import java.util.List;

import org.wso2.carbon.siddhihive.querygenerator.*;
import org.wso2.carbon.siddhihive.utils.Constants;

public class TestQueryGenerator {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HiveTableCreator a = new HiveTableCreator();
		List<HiveField> map = new ArrayList<HiveField>();
		map.add(new HiveField("col1", Constants.STRING));
		map.add(new HiveField("col2", Constants.INT));
		map.add(new HiveField("col3", Constants.DOUBLE));
		map.add(new HiveField("col4", Constants.INT));
		a.setQuery("mydb", map);
		System.out.println(a.getCreateQuery());
		System.out.println(a.getInsertQuery());
		map.clear();
	}
}
