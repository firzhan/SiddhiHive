package org.wso2.carbon.siddhihive.headerprocessor;


import org.wso2.carbon.siddhihive.SiddhiHiveManager;
import org.wso2.carbon.siddhihive.handler.ConditionHandler;
import org.wso2.carbon.siddhihive.utils.Constants;
import org.wso2.siddhi.query.api.definition.StreamDefinition;
import org.wso2.siddhi.query.api.query.input.Stream;
import org.wso2.siddhi.query.api.query.input.WindowStream;
import org.wso2.siddhi.query.api.query.input.handler.Filter;

import java.util.HashMap;
import java.util.Map;

public class WindowStreamHandler extends BasicStreamHandler {

    private String windowIsolatorClause;
    private String fromClause;
    private String whereClause;
    private WindowStream windowStream;
    private WindowIsolator windowIsolator;
    private Map<String, String> result;

    public WindowStreamHandler() {
        this.windowIsolator = new WindowIsolator();
    }

    @Override
    public Map<String, String> process(Stream stream) {
        this.windowStream = (WindowStream) stream;
        windowIsolatorClause = windowIsolator.process(windowStream.getWindow(), SiddhiHiveManager.getInstance().getStreamDefinition(((WindowStream) stream).getStreamId()));
        fromClause = generateFromClause(windowStream.getStreamId());
        whereClause = generateWhereClause(windowStream.getFilter());
        result = new HashMap<String, String>();
        result.put(Constants.FROM_CLAUSE, fromClause);
        result.put(Constants.WHERE_CLAUSE, whereClause);
        result.put(Constants.INCREMENTAL_CLAUSE, windowIsolatorClause);
        return result;
    }

}
