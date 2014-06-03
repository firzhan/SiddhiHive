package org.wso2.carbon.siddhihive;


import org.wso2.siddhi.query.api.definition.StreamDefinition;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/*
Class to manage query conversion in higher level. Will call appropriate handlers. Will also contain initial data needed for the conversion.
 */
public class SiddhiHiveManager {

    private static SiddhiHiveManager siddhiHiveManager = null;

    private ConcurrentMap<String, StreamDefinition> streamDefinitionMap; //contains stream definition
    private ConcurrentMap<String, String> queryMap;

    private SiddhiHiveManager(/*StreamDefinition streamDefinition*/) {

        //siddhiHiveManager = null;

        streamDefinitionMap = new ConcurrentHashMap<String, StreamDefinition>();
        //streamDefinitionMap.put(streamDefinition.getStreamId(), streamDefinition);

        //New Query Map
        queryMap = new ConcurrentHashMap<String, String>();
    }

    public static SiddhiHiveManager getInstance() {

        if (siddhiHiveManager == null)
            siddhiHiveManager = new SiddhiHiveManager();

        return siddhiHiveManager;
    }

    public ConcurrentMap<String, StreamDefinition> getStreamDefinitionMap() {
        return streamDefinitionMap;
    }

    public void setStreamDefinitionMap(ConcurrentMap<String, StreamDefinition> streamDefinitionMap) {
        this.streamDefinitionMap = streamDefinitionMap;
    }

    public void setStreamDefinition(String streamDefinitionID, StreamDefinition streamDefinition) {
        streamDefinitionMap.put(streamDefinitionID, streamDefinition);
    }

    public StreamDefinition getStreamDefinition(String streamId) {
        return streamDefinitionMap.get(streamId);
    }

//    public String getHiveQuery(Query query){
//
//        if(query == null)
//            return null;
//
//        String hiveQuery = "";
//
//
//
//        WindowStreamHandler windowStreamHandler = new WindowStreamHandler();
//        HashMap<String, String> windowStreamMap = windowStreamHandler.process()
//
//
//
//    }
//
//    private String handleStream(Stream stream){
//
//        String resultQuery = "";
//
//        if(stream instanceof WindowStream){
//
//        }
//
////        if(stream instanceof BasicStream){
////
////        }
////        else if(stream instanceof JoinStream){
////
////            JoinStream joinStream = (JoinStream) stream;
////
////            String leftStream = handleStream(joinStream.getLeftStream());
////            String rightStream = handleStream(joinStream.getRightStream());
////        }
////        else if(stream instanceof WindowStream){
////
////        }
//
//    }

//    private Stream handleJoinStream(JoinStream joinStream){
//
//
//    }


}
