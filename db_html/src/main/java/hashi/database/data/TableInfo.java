package hashi.database.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TableInfo {

	private DatabaseInfo databaseInfo;
	
	private String tableName;
	
	private String tableNameLogical;
	
	private String remarks;
	
	private List<ColumnInfo> colList = new ArrayList<>();

	private Map<String, ColumnInfo> colMap = new HashMap<>();

	private List<IndexInfo> indexList = new ArrayList<>();
	
	private Map<String, FKInfo> fkMap = new HashMap<>();
	
	public DatabaseInfo getDatabaseInfo() {
		return databaseInfo;
	}

	public void setDatabaseInfo(DatabaseInfo databaseInfo) {
		this.databaseInfo = databaseInfo;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.databaseInfo.removeTableInfo(this.tableName);
		this.tableName = tableName;
		this.databaseInfo.putTableInfo(tableName, this);
	}

	public String getTableNameLogical() {
		return tableNameLogical;
	}

	public void setTableNameLogical(String tableNameLogical) {
		this.tableNameLogical = tableNameLogical;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public List<ColumnInfo> getColList() {
		return this.colList;
	}

	public void addColumn(ColumnInfo colInfo) {
		colInfo.setTableInfo(this);
		this.colList.add(colInfo);
		this.colMap.put(colInfo.getColName(), colInfo);
	}
	
	public ColumnInfo addColumn() {
		ColumnInfo colInfo = new ColumnInfo();
		colInfo.setTableInfo(this);
		this.colList.add(colInfo);
		return colInfo;
	}
	
	public void putColumn(String colName, ColumnInfo colInfo) {
		this.colMap.put(colName, colInfo);
	}

	public ColumnInfo getColumn(String colName) {
		return this.colMap.get(colName);
	}

	public List<IndexInfo> getIndexList() {
		return indexList;
	}

	public void addIndex(IndexInfo indexInfo) {
		this.indexList.add(indexInfo);
	}
	
	public IndexInfo addIndex() {
		IndexInfo indexInfo = new IndexInfo();
		this.indexList.add(indexInfo);
		return indexInfo;
	}
	
	public FKInfo getFKInfo(String otherTableName) {
		return this.fkMap.get(otherTableName);
	}
	
	public void putFKInfo(String otherTableName, FKInfo fkInfo) {
		this.fkMap.put(otherTableName, fkInfo);
	}
}
