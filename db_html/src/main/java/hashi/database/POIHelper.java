package hashi.database;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class POIHelper {

	public static String getString(Sheet wks, int rowIdx, int colIdx) {
		Row row = wks.getRow(rowIdx);
		if (row == null) return null;
		Cell cell = row.getCell(colIdx);
		if (cell == null) return null;
		return getString(cell);
	}
	
	public static String getString(Cell cell) {
		CellType type = cell.getCellTypeEnum();
		switch (type)  {
		case _NONE:
			return null;
		case BLANK:
			return null;
		case NUMERIC:
			return "" + cell.getNumericCellValue();
		default:
			return cell.getStringCellValue();
		}
	}
}
