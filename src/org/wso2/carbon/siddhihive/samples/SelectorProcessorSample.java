package org.wso2.carbon.siddhihive.samples;

import org.wso2.carbon.siddhihive.selectorprocessor.QuerySelectorProcessor;
import org.wso2.siddhi.core.SiddhiManager;
import org.wso2.siddhi.query.api.query.Query;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by firzhan on 6/1/14.
 */
public class SelectorProcessorSample {

    public static void main(String[] args)  {

        SiddhiManager siddhiManager = new SiddhiManager();

        siddhiManager.defineStream("define stream StockExchangeStream ( symbol string, price int )");
        //siddhiManager.defineStream("define stream StockQuote ( symbol string, avgPrice double )");
        String queryID = siddhiManager.addQuery(" from StockExchangeStream[price >= 20]#window.length(50) " +
                                                " select symbol, avg(price) as avgPrice " +
                                                " group by symbol having avgPrice>50 " +
                                                " insert into StockQuote;");

        Query query = siddhiManager.getQuery(queryID);

        QuerySelectorProcessor selectorProcessor = new QuerySelectorProcessor();
        HashMap<String, String> map = (HashMap<String, String>)selectorProcessor.handleSelector(query);

        for (Object value : map.values()) {

            System.out.println(value.toString());
        }

        System.out.println("+++++++++++++++++++++++++++");
        queryID = siddhiManager.addQuery(" from StockExchangeStream[price >= 20]#window.length(50) " +
                " select symbol, max(price) as maxPrice, avg(price) as avgPrice, count(price) as cnt " +
                " group by symbol having avgPrice>50 " +
                " insert into ABCStockQuote;");

        query = siddhiManager.getQuery(queryID);

        map = (HashMap<String, String>)selectorProcessor.handleSelector(query);

        for (Object value : map.values()) {

            System.out.println(value.toString());
        }

    }
}
