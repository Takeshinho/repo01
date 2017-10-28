package hashi.database.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseInfo {

	private List<TableInfo> tableList = new ArrayList<>();
	private Map<String, TableInfo> tableNameMap = new HashMap<>();
	
	public List<TableInfo> getTableList() {
		return tableList;
	}

	public void addTable(TableInfo tableInfo) {
		this.tableList.add(tableInfo);
		this.tableNameMap.put(tableInfo.getTableName(), tableInfo);
	}
	
	public TableInfo addTable() {
		TableInfo tableInfo = new TableInfo();
		this.tableList.add(tableInfo);
		tableInfo.setDatabaseInfo(this);
		return tableInfo;
	}

	public TableInfo getTableInfo(String tableName) {
		return tableNameMap.get(tableName);
	}
	
	public void putTableInfo(String tableName, TableInfo tableInfo) {
		this.tableNameMap.put(tableName, tableInfo);
	}
	
	public TableInfo removeTableInfo(String tableName) {
		return this.tableNameMap.remove(tableName);
	}
	
	
	
}
