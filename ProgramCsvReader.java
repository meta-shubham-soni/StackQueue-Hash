import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ProgramCsvReader {

	Map<String, List<String>> students = new TreeMap<>();
	Map<String, Integer> subjects = new HashMap<String, Integer>();
	Map<String, String> result = new HashMap<String, String>();

	public static void main(String... arg) {

	ProgramCsvReader p = new ProgramCsvReader();
		try {
			p.readProgramCsv();
		} catch (IOException e) {              // TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e1) {               // TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public void readProgramCsv() throws IOException, Exception 
	{
		FileInputStream fis = null;
		FileInputStream fis1 = null;
		FileOutputStream out = null;
		try {
			
			fis = new FileInputStream(new File("C:\\Users\\shubham.soni_metacub\\workspace\\StackQueue\\src\\Student.xls"));
			fis1 = new FileInputStream(new File("C:\\Users\\shubham.soni_metacub\\workspace\\StackQueue\\src\\DSAAssign2.xls"));
		} catch (FileNotFoundException e) {              // TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// creating workbook instance that refers to .xls file
		HSSFWorkbook wb = null;
		HSSFWorkbook wb1 = null;
		try {
			wb = new HSSFWorkbook(fis);
			wb1 = new HSSFWorkbook(fis1);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// creating a Sheet object to retrieve the object
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFSheet sheet1 = wb1.getSheetAt(0);
		// evaluating cell type
		// FormulaEvaluator

		Iterator<Row> itr = sheet.iterator();
		Iterator<Row> itr1 = sheet1.iterator();
		while (itr.hasNext()) {
			Row row = itr.next();
			if (row.getRowNum() != 0) {
				Iterator<Cell> cellIterator = row.cellIterator();

				String key = null;
				List<String> sub = new ArrayList<String>();

				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();

					if (cell.getColumnIndex() == 0) {
						key = cell.getStringCellValue();
					} else {

						sub.add(cell.getStringCellValue());

					}

				}

				students.put(key, sub);
			}

		}

		System.out.println("*****************************");
		while (itr1.hasNext()) {
			Row row = itr1.next();
			if (row.getRowNum() != 0) {
				Iterator<Cell> cellIterator = row.cellIterator();
				String key = null;
				Integer val = 0;
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					if (cell.getColumnIndex() == 0) {
						key = cell.getStringCellValue();
					} else {
						val = (int) cell.getNumericCellValue();
					}
				}
				subjects.put(key, val);
			}
		}

		for (Map.Entry<String, List<String>> entry : students.entrySet()) {
			String key = entry.getKey();
			List<String> ls = entry.getValue();
			for (String l : ls) {

				if (subjects.get(l) != null) {
					int cap = subjects.get(l);
					if (cap > 0) {
						result.put(key, l);
						cap--;
						subjects.put(l, cap);
						break;
					}
				}
			}

		}

		for (Map.Entry<String, String> res : result.entrySet()) {
			System.out.print("Student : " + res.getKey());
			System.out.print("\t Subject : " + res.getValue());
			System.out.println();
		}

		System.out.println("*********************************");

		XSSFWorkbook workbook = new XSSFWorkbook();
		// Create a blank sheet
		XSSFSheet spreadsheet = workbook.createSheet(" Employee Info ");

		// Create row object
		XSSFRow row;

		Map<String, String> empinfo = new TreeMap<String, String>();

		for (Map.Entry<String, String> res : result.entrySet()) {
			empinfo.put(res.getKey(), res.getValue());
		}

		// Iterate over data and write to sheet
		Set<String> keyid = empinfo.keySet();

		int rowid = 0;

		row = spreadsheet.createRow(rowid++);
		int cellid = 0;
		Cell cell = row.createCell(cellid++);
		cell.setCellValue("Student");
		Cell cell2 = row.createCell(cellid++);
		cell2.setCellValue("Subject");
		//rowid++;

		for (String key : keyid) {
			row = spreadsheet.createRow(rowid++);
			String objectArr = empinfo.get(key);
			cellid = 0;
			cell = row.createCell(cellid++);
			cell.setCellValue(key);
			cell2 = row.createCell(cellid++);
			cell2.setCellValue(objectArr);
		}

		FileOutputStream out1 = null;
		try {
			out1 = new FileOutputStream(
					new File("C:\\Users\\shubham.soni_metacub\\workspace\\StackQueue\\src\\studentData.xlsx"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		workbook.write(out1);
		out1.close();
		System.out.println("Writesheet.xlsx written successfully in C:\\Users\\shubham.soni_metacub\\workspace\\StackQueue\\src\\studentData.xlsx");

	}

}
