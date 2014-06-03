package org.wso2.carbon.siddhihive.querygenerator;

<<<<<<< HEAD
import java.util.ArrayList;
import java.util.List;

=======
>>>>>>> 0b9d28a3cb12aac5436f4571fed809d54f91d5b8
import org.wso2.siddhi.query.api.definition.Attribute;
import org.wso2.siddhi.query.api.definition.StreamDefinition;

import java.util.ArrayList;
import java.util.List;

public final class HiveTableCreator extends HiveQueryGenerator {
	//**********************************************************************************************
	private String sCreateQuery = "";
	private String sInsertQuery = "";
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
	public void setQuery(StreamDefinition streamDefinition) {
		sDBName = streamDefinition.getStreamId();
        List<Attribute> attributeList = streamDefinition.getAttributeList();
        sDBName = streamDefinition.getStreamId();
        listColumns = new ArrayList<HiveField>();
        Attribute attribute = null;
        for(int i = 0; i < attributeList.size(); i++) {
            attribute = attributeList.get(i);
            listColumns.add(new HiveField(attribute.getName(), typeToString(attribute.getType())));
        }
	}
	
	//**********************************************************************************************
	public void setQuery(String sDB, List<HiveField> listFields) {
		sDBName = sDB;
		listColumns = listFields;
	}
	
	//**********************************************************************************************
	public String getCreateQuery() {
		sProperties = "";
		
		if (listColumns.size() <= 0)
			return null;
		
		sProperties = listColumns.get(0).getFieldName() + " " + listColumns.get(0).getDataType();
		for (int i = 1; i < listColumns.size(); i++) {
			sProperties += (", " + listColumns.get(i).getFieldName() + " " + listColumns.get(i).getDataType());
		}
		
		sCreateQuery = ("CREATE TABLE IF NOT EXISTS " + sDBName + " (" + sProperties + ") " + sRowFormat + " " +  sStoredAs + ";");
		return sCreateQuery;
	}
	
	//**********************************************************************************************
	public String getInsertQuery() {
		sInsertQuery = "";
		
		if (sDBName.length() <= 0) 
			return null;
		
		sInsertQuery = "INSERT OVERWRITE TABLE " + sDBName + " ";
		return sInsertQuery;
	}
}
