package hashi.database.data;

import java.util.ArrayList;
import java.util.List;

public class FKInfo {

	private TableInfo parentTable;
	private TableInfo childTable;
	
	private List<ColumnInfo> parentColumns = new ArrayList<>();
	private List<ColumnInfo> childColumns = new ArrayList<>();
	
	public TableInfo getParentTable() {
		return parentTable;
	}
	public void setParentTable(TableInfo parentTable) {
		this.parentTable = parentTable;
	}
	public TableInfo getChildTable() {
		return childTable;
	}
	public void setChildTable(TableInfo childTable) {
		this.childTable = childTable;
	}
	public List<ColumnInfo> getParentColumns() {
		return parentColumns;
	}
	public void setParentColumns(List<ColumnInfo> referedColumns) {
		this.parentColumns = referedColumns;
	}
	public List<ColumnInfo> getChildColumns() {
		return childColumns;
	}
	public void setChildColumns(List<ColumnInfo> childColumns) {
		this.childColumns = childColumns;
	}
	
	
	
}
