package gcpsamples.service;

import com.google.cloud.bigquery.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.google.cloud.spring.bigquery.core.BigQueryTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.io.InputStream;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class BigQueryService {
	@Autowired
	private BigQuery bigquery;

	public Iterable<?> readDataFromGcpBigQuery(String query) throws InterruptedException {
		return query(query);
	}

	public Iterable<?> query(String query) throws InterruptedException {
		QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(query).build();
		return bigquery.query(queryConfig).iterateAll();
	}

	public long getCountOfResultSet(String dataSet, String tableName) throws InterruptedException {
		QueryJobConfiguration queryConfig = QueryJobConfiguration
				.newBuilder("Select * FROM " + dataSet + "." + tableName + "").build();
		return bigquery.query(queryConfig).getTotalRows();
	}

	public TableResult getTableResultSet(String dataSet, String tableName, String... cols) throws InterruptedException {
		String queryCols = Stream.of(cols).collect(Collectors.joining(","));
		QueryJobConfiguration queryConfig = QueryJobConfiguration
				.newBuilder("Select " + queryCols + " FROM " + dataSet + "." + tableName + "").build();
		return bigquery.query(queryConfig);
	}

}
