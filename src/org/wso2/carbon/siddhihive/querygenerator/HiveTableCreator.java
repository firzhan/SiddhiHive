package org.wso2.carbon.siddhihive.querygenerator;

import java.util.ArrayList;
import java.util.List;
import org.wso2.siddhi.core.SiddhiManager;
import org.wso2.siddhi.query.api.definition.Attribute;
import org.wso2.siddhi.query.api.definition.StreamDefinition;
import org.wso2.siddhi.query.api.query.Query;

public final class HiveTableCreator extends HiveQueryGenerator {
	//**********************************************************************************************
	private String sQuery = "";
	private String sProperties = "";
	private String sRowFormat = "ROW FORMAT DELIMITED FIELDS TERMINATED BY ','";
	private String sStoredAs = "STORED AS SEQUENCEFILE";
	String sDBName;
	List<HiveField> listColumns;
	
	//**********************************************************************************************
	public HiveTableCreator() {
		super();
    }
	
	//**********************************************************************************************
	public String getQuery(Query query, SiddhiManager siddhiManager) {
		String streamID = query.getOutputStream().getStreamId();
		StreamDefinition streamDefinition = siddhiManager.getStreamDefinition(streamID);

        if(streamDefinition != null){
            List<Attribute> attributeList = streamDefinition.getAttributeList();
            listColumns = new ArrayList<HiveField>();
            Attribute attribute = null;
            for(int i = 0; i < attributeList.size(); i++) {
                attribute = attributeList.get(i);
                listColumns.add(new HiveField(attribute.getName(), typeToString(attribute.getType())));
            }
        }
		return getQuery();
	}
	
	//**********************************************************************************************
	public String getQuery(String sDB, List<HiveField> listFields) {
		sDBName = sDB;
		listColumns = listFields;
		return getQuery();
	}
	
	//**********************************************************************************************
	private String getQuery() {
		sProperties = "";
		
		if (listColumns.size() <= 0)
			return null;
		
		sProperties = listColumns.get(0).getFieldName() + " " + listColumns.get(0).getDataType();
		for (int i = 1; i < listColumns.size(); i++) {
			sProperties += (", " + listColumns.get(i).getFieldName() + " " + listColumns.get(i).getDataType());
		}
		
		sQuery = ("CREATE TABLE IF NOT EXISTS " + sDBName + " (" + sProperties + ") " + sRowFormat + " " +  sStoredAs + ";");
		return sQuery;
	}	
}
