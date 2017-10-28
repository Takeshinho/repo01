/**
 * 
 */
package hashi.database.data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ta-hashimoto
 *
 */
public class IndexInfo {

	private TableInfo tableInfo;
	
	private String name;
	
	private List<ColumnInfo> colList = new ArrayList<>();
	
	private boolean unique;
	
	private String remarks;

	public TableInfo getTableInfo() {
		return tableInfo;
	}

	public void setTableInfo(TableInfo tableInfo) {
		this.tableInfo = tableInfo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ColumnInfo> getColList() {
		return colList;
	}

	public void setColList(List<ColumnInfo> colList) {
		this.colList = colList;
	}

	public void addColumn(ColumnInfo column) {
		this.colList.add(column);
	}

	public boolean isUnique() {
		return unique;
	}

	public void setUnique(boolean unique) {
		this.unique = unique;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
}
