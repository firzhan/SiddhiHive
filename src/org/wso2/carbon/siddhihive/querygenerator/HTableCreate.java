package org.wso2.carbon.siddhihive.querygenerator;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class HTableCreate implements HiveQueryGenerator {
	
	public static final String STRING = "STRING";
	public static final String INT = "INT";
	public static final String DOUBLE = "DOUBLE";
	public static final String TIMESTAMP = "TIMESTAMP";
	public static final String VARCHAR = "VARCHAR";
	
	private String sQuery = "";
	private String sProperties = "";
	private String sRowFormat = "ROW FORMAT DELIMITED FIELDS TERMINATED BY ','";
	private String sStoredAs = "STORED AS SEQUENCEFILE";
	
	
	public String getLastQuery() {
		return sQuery;
	}
	
	public String getQuery(String sDBName, Map<String, String> mapColumns) {
		sProperties = "";
		Set<String> keySet = mapColumns.keySet();
		Iterator<String> it = keySet.iterator();
		
		if (!it.hasNext())
			return null;
		
		String sKey = it.next();
		sProperties = sKey + " " + mapColumns.get(sKey);
		while (it.hasNext()) {
			sKey = it.next();
			sProperties += (", " + sKey + " " + mapColumns.get(sKey));
		}
		
		sQuery = ("CREATE TABLE IF NOT EXISTS " + sDBName + " (" + sProperties + ") " + sRowFormat + " " +  sStoredAs + ";");
		return sQuery;
	}

}
