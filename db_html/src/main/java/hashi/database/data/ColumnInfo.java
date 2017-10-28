package hashi.database.data;

public class ColumnInfo {

	private TableInfo tableInfo;
	
	private String colName;
	
	private String colNameLogical;
	
	private String colType;
	
	private String notNull;
	
	private String defaultValue;
	
	private String remarks;

	public TableInfo getTableInfo() {
		return tableInfo;
	}

	public void setTableInfo(TableInfo tableInfo) {
		this.tableInfo = tableInfo;
	}

	public String getColName() {
		return colName;
	}

	public void setColName(String colName) {
		this.tableInfo.putColumn(colName, this);;
		this.colName = colName;
	}

	public String getColNameLogical() {
		return colNameLogical;
	}

	public void setColNameLogical(String colNameLogical) {
		this.colNameLogical = colNameLogical;
	}

	public String getColType() {
		return colType;
	}

	public void setColType(String colType) {
		this.colType = colType;
	}

	public String getNotNull() {
		return notNull;
	}

	public void setNotNull(String notNull) {
		this.notNull = notNull;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
}
