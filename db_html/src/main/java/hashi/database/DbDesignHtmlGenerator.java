package hashi.database;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import hashi.database.data.ColumnInfo;
import hashi.database.data.DatabaseInfo;
import hashi.database.data.TableInfo;

public class DbDesignHtmlGenerator {

	private static final String FILE_PATH = 
			"C:\\Users\\ta-hashimoto\\Documents\\SVN\\JTBHAWAII\\300_設計(Event List)\\60_DB設計書(DB Specifications)\\SuperSTAR_テーブル定義書.xlsx";
	
	public static void main(String[] args) throws Exception {
		DbDesignHtmlGenerator instance = new DbDesignHtmlGenerator();
		instance.start();
	}
	
	public void start() throws Exception {
		File file = new File(FILE_PATH);
		DbInfoExcelReader reader = new DbInfoExcelReader();
		DatabaseInfo dbInfo = reader.readTableDef(file);
		printDBHtml(dbInfo);
	}
	
	public void printDBHtml(DatabaseInfo dbInfo) throws IOException {
		
		InputStream templateFile = 
				Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("hashi/database/template/tableFormat.html");
		BufferedReader reader = new BufferedReader(new InputStreamReader(templateFile));
	
		String formatHtml = reader.lines().collect(Collectors.joining("\r\n"));
		
		List<TableInfo> tbList = dbInfo.getTableList();
		
		StringBuilder builder = new StringBuilder();
		builder.append("<h1>All Tables</h1>");
		builder
		.append("<table class='dbtables'>\r\n")
		.append("<th>テーブル名</th><th>物理名</th><th>コメント</th>")
		.append("");
		;
		for (TableInfo tableInfo : tbList) {
			builder
			.append("<tr onclick='openLinkTable(\"")
			.append(tableInfo.getTableName())
			.append("\")'><td class='tableName1'>")
			.append(tableInfo.getTableNameLogical())
			.append("</td><td class='tableName2'>")
			.append(tableInfo.getTableName())
			.append("</td><td class='tableDesc'>")
			.append(tableInfo.getRemarks())
			.append("</td></tr>\r\n");
			
			this.printTableHtml(tableInfo);
		}
		builder.append("</table>");
		
		formatHtml = formatHtml.replace("#Title#", "All Tables");
		formatHtml = formatHtml.replace("#body#", builder.toString());
		
		Files.write(Paths.get("output/html/database.html"), formatHtml.getBytes());
	}
	
	public void printTableHtml(TableInfo tableInfo) throws IOException {
		
		InputStream templateFile = 
				Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("hashi/database/template/tableFormat.html");
		BufferedReader reader = new BufferedReader(new InputStreamReader(templateFile));
	
		String formatHtml = reader.lines().collect(Collectors.joining("\r\n"));
		
		List<ColumnInfo> colList = tableInfo.getColList();
		
		StringBuilder builder = new StringBuilder();
		builder
		.append("<h1>")
		.append(tableInfo.getTableName())
		.append(" / ")
		.append(tableInfo.getTableNameLogical())
		.append("</h1>");
		builder
		.append("<table class='dbtables'>\r\n")
		.append("<th>カラム名</th><th>物理名</th><th>データ型</th><th>NULL可</th><th>デフォルト</th><th>コメント</th>")
		.append("");
		;
		for (ColumnInfo colInfo : colList) {
			builder
			.append("<tr onclick='openLinkCol(\"")
			.append(colInfo.getColName())
			.append("\")'><td class='colName1'>")
			.append(colInfo.getColNameLogical())
			.append("</td><td class='colName2'>")
			.append(colInfo.getColName())
			.append("</td><td class='colType'>")
			.append(colInfo.getColType())
			.append("</td><td class='colNotNUll'>")
			.append(colInfo.getNotNull())
			.append("</td><td class='colDefault'>")
			.append(colInfo.getDefaultValue())
			.append("</td><td class='colDesc'>")
			.append(colInfo.getRemarks())
			.append("</td></tr>\r\n");
		}
		builder.append("</table>");
		
		formatHtml = formatHtml.replace("#Title#", tableInfo.getTableName());
		formatHtml = formatHtml.replace("#body#", builder.toString());
		formatHtml = formatHtml.replace("style.css", "../style.css");
		
		Files.write(Paths.get("output/html/tables/" + tableInfo.getTableName() + ".html"), formatHtml.getBytes());
	}
	
}
