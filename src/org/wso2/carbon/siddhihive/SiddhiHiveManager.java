package org.wso2.carbon.siddhihive;


import org.wso2.siddhi.query.api.definition.StreamDefinition;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/*
Class to manage query conversion in higher level. Will call appropriate handlers. Will also contain initial data needed for the conversion.
 */
public class SiddhiHiveManager {


    private ConcurrentMap<String, StreamDefinition> streamDefinitionMap; //contains stream definition

    public SiddhiHiveManager(StreamDefinition streamDefinition) {
        streamDefinitionMap = new ConcurrentHashMap<String, StreamDefinition>();
        streamDefinitionMap.put(streamDefinition.getStreamId(), streamDefinition);
    }

    public ConcurrentMap<String, StreamDefinition> getStreamDefinitionMap() {
        return streamDefinitionMap;
    }

    public void setStreamDefinitionMap(ConcurrentMap<String, StreamDefinition> streamDefinitionMap) {
        this.streamDefinitionMap = streamDefinitionMap;
    }

    public StreamDefinition getStreamDefinition(String streamId) {
        return streamDefinitionMap.get(streamId);
    }


}
