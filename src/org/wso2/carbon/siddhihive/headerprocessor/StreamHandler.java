package org.wso2.carbon.siddhihive.headerprocessor;


import org.wso2.siddhi.query.api.definition.StreamDefinition;
import org.wso2.siddhi.query.api.query.input.Stream;

import java.util.Map;

public interface StreamHandler {

    public Map<String, String> process(Stream stream, StreamDefinition streamDefinition);
}
