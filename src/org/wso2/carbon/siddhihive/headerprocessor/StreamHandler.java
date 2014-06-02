package org.wso2.carbon.siddhihive.headerprocessor;


import org.wso2.siddhi.query.api.definition.StreamDefinition;
import org.wso2.siddhi.query.api.query.input.Stream;

public interface StreamHandler {

    public void process(Stream stream, StreamDefinition streamDefinition);
}
