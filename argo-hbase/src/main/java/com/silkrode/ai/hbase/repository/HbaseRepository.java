package com.silkrode.ai.hbase.repository;

import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.hbase.TableCallback;
import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @implNote  : The repository of HBase.
 * @param : input data and query condition from dataDto.
 * @return : query method return query result.
 */
@Repository
public class HbaseRepository {
    private final HbaseTemplate hbaseTemplate;
    public HbaseRepository(HbaseTemplate hbaseTemplate) {this.hbaseTemplate = hbaseTemplate;}

    public void insert(Object data)
    {
        Map m = (Map)data;
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        String rowKey = m.get("user_id")+"_"+String.valueOf(ts.getTime());
        hbaseTemplate.execute("message_history", (table) -> {
            Put put = new Put(Bytes.toBytes(rowKey));
            m.forEach((k,v) -> {
                put.addColumn("info".getBytes(),Bytes.toBytes(k.toString()),Bytes.toBytes(v.toString()));
            });
            put.addColumn("info".getBytes(),Bytes.toBytes("updated"),Bytes.toBytes(String.valueOf(ts.getTime())));
            table.put(put);
            return true;
        });
    }
    public List query(Map<String, Object> filter)
    {
        return hbaseTemplate.execute("message_history", new TableCallback<List<Result>>() {
            List<Result> list = new ArrayList<>();
            @Override
            public List<Result> doInTable(HTableInterface table) throws Throwable {
                String skip = null;
                if(filter.containsKey("skip"))
                    skip = filter.get("skip").toString();
                else {
                    Timestamp ts = new Timestamp(System.currentTimeMillis());
                    skip = String.valueOf(ts.getTime());
                }
                FilterList fl = new FilterList(FilterList.Operator.MUST_PASS_ALL);
                SingleColumnValueFilter userFilter = new SingleColumnValueFilter(Bytes.toBytes("info"),
                        Bytes.toBytes("user_id"), CompareFilter.CompareOp.EQUAL, Bytes.toBytes(filter.get("user_id").toString()));
                fl.addFilter(userFilter);
                if(filter.containsKey("bot_id")) {
                    SingleColumnValueFilter botFilter = new SingleColumnValueFilter(Bytes.toBytes("info"),
                            Bytes.toBytes("bot_id"), CompareFilter.CompareOp.EQUAL, Bytes.toBytes(filter.get("bot_id").toString()));
                    fl.addFilter(botFilter);
                }
                SingleColumnValueFilter timeFilter = new SingleColumnValueFilter(Bytes.toBytes("info"),
                        Bytes.toBytes("updated"), CompareFilter.CompareOp.LESS, Bytes.toBytes(skip));
                fl.addFilter(timeFilter);
                Scan scan = new Scan();
                scan.setReversed(true);
                scan.setFilter(fl);
                scan.setLimit(20);
                ResultScanner rscanner = table.getScanner(scan);
                for(Result result : rscanner){
                    list.add(result);
                }
                return list;
            }
        });
    }
}
