package org.wso2.carbon.siddhihive.querygenerator;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.wso2.carbon.siddhihive.utils.HiveQueryGenerator;
import org.wso2.siddhi.core.SiddhiManager;
import org.wso2.siddhi.query.api.definition.Attribute;
import org.wso2.siddhi.query.api.definition.StreamDefinition;
import org.wso2.siddhi.query.api.query.Query;

public final class HiveTableCreate extends HiveQueryGenerator {
	//**********************************************************************************************
	private String sQuery = "";
	private String sProperties = "";
	private String sRowFormat = "ROW FORMAT DELIMITED FIELDS TERMINATED BY ','";
	private String sStoredAs = "STORED AS SEQUENCEFILE";
	String sDBName;
	Map<String, String> mapColumns;
	
	//**********************************************************************************************
	public HiveTableCreate() {
		super();
    }
	
	//**********************************************************************************************
	public String getQuery(Query query, SiddhiManager siddhiManager) {
		String streamID = query.getOutputStream().getStreamId();
		StreamDefinition streamDefinition = siddhiManager.getStreamDefinition(streamID);

        if(streamDefinition != null){
            List<Attribute> attributeList = streamDefinition.getAttributeList();
            mapColumns = new HashMap<String, String>();
            Attribute attribute = null;
            for(int i = 0; i < attributeList.size(); i++){
                attribute = attributeList.get(i);
                mapColumns.put(attribute.getName(), typeToString(attribute.getType()));
            }
        }
		return getQuery();
	}
	
	//**********************************************************************************************
	public String getQuery(String sDB, Map<String, String> mapFields) {
		sDBName = sDB;
		mapColumns = mapFields;
		return getQuery();
	}
	
	//**********************************************************************************************
	private String getQuery() {
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
