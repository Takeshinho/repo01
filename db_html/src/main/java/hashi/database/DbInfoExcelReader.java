package hashi.database;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import hashi.database.data.ColumnInfo;
import hashi.database.data.DatabaseInfo;
import hashi.database.data.FKInfo;
import hashi.database.data.IndexInfo;
import hashi.database.data.TableInfo;

public class DbInfoExcelReader {

	
	
	public DatabaseInfo readTableDef(File file) throws Exception {
		Workbook wkb = WorkbookFactory.create(file, null, true);
		
		DatabaseInfo dbInfo = new DatabaseInfo();
		
		for (int i = 0; i < wkb.getNumberOfSheets(); i++) {
			Sheet wks = wkb.getSheetAt(i);
			if (wks.getSheetName().equals("エンティティ一覧")) continue;
			
			TableInfo tbInfo = dbInfo.addTable();
			String tblNm = POIHelper.getString(wks,  5,  2);
			String tblNmLogic = POIHelper.getString(wks,  4,  2);
			String tblRemarks = POIHelper.getString(wks, 7, 1);
			
			tbInfo.setTableName(tblNm);
			tbInfo.setTableNameLogical(tblNmLogic);
			tbInfo.setRemarks(tblRemarks);
			
			int rowIdx = 13;
			Map<String, ColumnInfo> colNameMap = new HashMap<>();
			
			while (wks.getRow(rowIdx) != null) {
				Row colNameRow = wks.getRow(rowIdx);
				if (colNameRow.getCell(1).getCellTypeEnum() == CellType.BLANK) break;
				Cell logicalNmCell = colNameRow.getCell(1);
				Cell fisicalNmCell = colNameRow.getCell(2);
				Cell dataTypeCell = colNameRow.getCell(3);
				Cell notNullCell = colNameRow.getCell(4);
				Cell defaultCell = colNameRow.getCell(5);
				Cell remarksCell = colNameRow.getCell(6);
				
				String colName = fisicalNmCell.getStringCellValue().trim();
				String colNameLogic = logicalNmCell.getStringCellValue().trim();
				String dataType = dataTypeCell.getStringCellValue().trim();
				String notNullStr = notNullCell.getStringCellValue();
				String defaultValue = defaultCell.getStringCellValue();
				String remarks = remarksCell.getStringCellValue();
				
				ColumnInfo colInfo = tbInfo.addColumn();
				colNameMap.put(colName, colInfo);
				
				colInfo.setColName(colName);
				colInfo.setColNameLogical(colNameLogic);
				colInfo.setColType(dataType);
				colInfo.setNotNull(notNullStr);
				colInfo.setDefaultValue(defaultValue);
				colInfo.setRemarks(remarks);
				
				System.out.println(tblNm + "." + colName);
				rowIdx++;
			}
			
			while (!"インデックス情報".equals(POIHelper.getString(wks, rowIdx, 0))) {
				rowIdx++;
			}
			
			rowIdx += 2;
			
			while (wks.getRow(rowIdx) != null) {
				Row colNameRow = wks.getRow(rowIdx);
				if (colNameRow.getCell(1) == null || colNameRow.getCell(1).getCellTypeEnum() == CellType.BLANK) break;
				
				String indexName = colNameRow.getCell(1).getStringCellValue();
				String indexColumns = colNameRow.getCell(2).getStringCellValue();
				String unique = colNameRow.getCell(3).getStringCellValue();
				String remarks = colNameRow.getCell(4).getStringCellValue();
				IndexInfo indexInfo = tbInfo.addIndex();
				indexInfo.setName(indexName);
				String[] idxColNames = indexColumns.split(",");
				for (String idxCol : idxColNames) {
					ColumnInfo colInfo = colNameMap.get(idxCol);
					indexInfo.addColumn(colInfo);
				}
				boolean isUnique =  unique != null && "YES".equals(unique.trim().toUpperCase());
				indexInfo.setUnique(isUnique);
				indexInfo.setRemarks(remarks);
				
				rowIdx++;
				System.out.println("index:" + indexName);
			}
			
			while (!"リレーションシップ情報(FK側)".equals(POIHelper.getString(wks, rowIdx, 0))) {
				rowIdx++;
			}
			
			rowIdx++;
			
			while (wks.getRow(++rowIdx) != null) {
				if (POIHelper.getString(wks, rowIdx, 1) == null) break;
				
				Row colNameRow = wks.getRow(rowIdx);
				String indexColumns = colNameRow.getCell(2).getStringCellValue();
				String parentTableName = colNameRow.getCell(4).getStringCellValue();
				String parentColNames = colNameRow.getCell(6).getStringCellValue();
				
				TableInfo parentTable = dbInfo.getTableInfo(parentTableName);
				if (parentTable == null) continue;
				
				FKInfo fkInfo = parentTable.getFKInfo(tbInfo.getTableName());
				
				if (fkInfo != null) {
					tbInfo.putFKInfo(parentTableName, fkInfo);
					continue;
				}
				
				fkInfo = new FKInfo();
				fkInfo.setParentTable(parentTable);
				fkInfo.setChildTable(tbInfo);
				
				String[] idxCols = indexColumns.split(",");
				for (String idxCol : idxCols) {
					ColumnInfo colInfo = tbInfo.getColumn(idxCol);
					fkInfo.getChildColumns().add(colInfo);
				}
				
				String[] parentIdxCols = parentColNames.split(",");
				for (String parentIdxCol : parentIdxCols) {
					ColumnInfo colInfo = parentTable.getColumn(parentIdxCol);
					fkInfo.getParentColumns().add(colInfo);
				}
			}
			
		}
		wkb.close();
		
		return dbInfo;
	}

}
