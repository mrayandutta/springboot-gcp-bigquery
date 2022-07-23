package gcpsamples.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.google.cloud.bigquery.FieldValueList;
import com.google.cloud.bigquery.TableResult;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BigQueryServiceTest {
	private static final String DATASET_NAME = "bg_test_dataset";
	private static final String TABLE_NAME = "employee";

	
	@Autowired
    private BigQueryService bigQueryService;
	
	@Test
	public void testRecordCount() throws InterruptedException {
		
		long resultCount =bigQueryService.getCountOfResultSet(DATASET_NAME, TABLE_NAME);
		System.out.println("resultCount:"+resultCount);
		assertEquals(true, resultCount>0);
		
	}
	
	@Test
	public void testDataRecords() throws InterruptedException {
		String[] columnNames = {"EmployeeID", "Name"};
		
		TableResult tableResult =bigQueryService.getTableResultSet(DATASET_NAME, TABLE_NAME, columnNames);
		// Print all pages of the results.
		for (FieldValueList row : tableResult.iterateAll()) {
		  // String type
		  String employeeID = row.get("EmployeeID").getStringValue();
		  String name = row.get("Name").getStringValue();
		
		  System.out.printf(
		      "EmployeeID name: %s Name: %s\n", employeeID, name);
		}
		
		//System.out.println("tableResult:"+tableResult);
		
	}

}
