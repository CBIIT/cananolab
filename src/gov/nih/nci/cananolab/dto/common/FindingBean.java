package gov.nih.nci.cananolab.dto.common;

import gov.nih.nci.cananolab.domain.common.Condition;
import gov.nih.nci.cananolab.domain.common.Datum;
import gov.nih.nci.cananolab.domain.common.File;
import gov.nih.nci.cananolab.domain.common.Finding;
import gov.nih.nci.cananolab.util.Comparators;
import gov.nih.nci.cananolab.util.Constants;
import gov.nih.nci.cananolab.util.DateUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * View bean for Datum
 *
 * @author pansu, tanq
 *
 */
public class FindingBean {
	private Finding domain = new Finding();
	private List<Row> rows = new ArrayList<Row>();
	private List<FileBean> files = new ArrayList<FileBean>();
	private List<ColumnHeader> columnHeaders = new ArrayList<ColumnHeader>();
	private int numberOfColumns;
	private int numberOfRows;
	private FileBean theFile = new FileBean();
	private int theFileIndex;

	public FindingBean() {
	}

	public FindingBean(Finding finding) {
		domain = finding;
		List<Datum> data = new ArrayList<Datum>(finding.getDatumCollection());
		Collections.sort(data, new Comparators.DatumDateComparator());

		if (finding.getFileCollection() != null) {
			for (File file : finding.getFileCollection()) {
				files.add(new FileBean(file));
			}
		}
		Collections.sort(files, new Comparators.FileBeanDateComparator());

		// get data matrix column headers in order and generate a map based on
		// headers
		Map<ColumnHeader, List<Datum>> datumMap = new HashMap<ColumnHeader, List<Datum>>();
		Map<ColumnHeader, List<Condition>> conditionMap = new HashMap<ColumnHeader, List<Condition>>();

		List<Datum> datumList = new ArrayList<Datum>();
		List<Condition> conditionList = new ArrayList<Condition>();
		for (Datum datum : data) {
			List<Condition> conditions = new ArrayList<Condition>(datum
					.getConditionCollection());
			Collections.sort(conditions,
					new Comparators.ConditionDateComparator());
			for (Condition condition : conditions) {
				ColumnHeader conditionColumn = new ColumnHeader(condition);
				if (!columnHeaders.contains(conditionColumn)) {
					columnHeaders.add(conditionColumn);
				}
				if (conditionMap.get(conditionColumn) != null) {
					conditionList = conditionMap.get(conditionColumn);
				} else {
					conditionList = new ArrayList<Condition>();
					conditionMap.put(conditionColumn, conditionList);
				}
				conditionList.add(condition);
			}
			ColumnHeader datumColumn = new ColumnHeader(datum);
			if (!columnHeaders.contains(datumColumn)) {
				columnHeaders.add(datumColumn);
			}
			if (datumMap.get(datumColumn) != null) {
				datumList = datumMap.get(datumColumn);
			} else {
				datumList = new ArrayList<Datum>();
				datumMap.put(datumColumn, datumList);
			}
			datumList.add(datum);
		}

		// generate matrix
		if (data != null && !data.isEmpty()) {
			if (conditionMap.get(columnHeaders.get(0)) != null) {
				numberOfRows = conditionMap.get(columnHeaders.get(0)).size();
			} else {
				numberOfRows = datumMap.get(columnHeaders.get(0)).size();
			}
			numberOfColumns = columnHeaders.size();

			for (int i = 0; i < numberOfRows; i++) {
				Row row = new Row();
				for (int j = 0; j < numberOfColumns; j++) {
					if (!conditionMap.isEmpty()) {
						if (conditionMap.get(columnHeaders.get(j)) != null) {
							Condition condition = conditionMap.get(
									columnHeaders.get(j)).get(0);
							row.getCells().add(new TableCell(condition));
						}
					}
					if (!datumMap.isEmpty()) {
						if (datumMap.get(columnHeaders.get(j)) != null) {
							Datum datum = datumMap.get(columnHeaders.get(j))
									.get(i);
							row.getCells().add(new TableCell(datum));
						}
					}
				}
				rows.add(row);
			}
		}
	}

	public List<Row> getRows() {
		return rows;
	}

	public void setRows(List<Row> rows) {
		this.rows = rows;
	}

	public List<ColumnHeader> getColumnHeaders() {
		return columnHeaders;
	}

	public void setColumnHeaders(List<ColumnHeader> columnHeaders) {
		this.columnHeaders = columnHeaders;
	}

	public int getNumberOfColumns() {
		return numberOfColumns;
	}

	public void setNumberOfColumns(int numberOfColumns) {
		this.numberOfColumns = numberOfColumns;
	}

	public int getNumberOfRows() {
		return numberOfRows;
	}

	public void setNumberOfRows(int numberOfRows) {
		this.numberOfRows = numberOfRows;
	}

	public void updateMatrix(int numberOfColumns, int numberOfRows) {
		this.numberOfColumns = numberOfColumns;
		this.numberOfRows = numberOfRows;
		List<ColumnHeader> newColumns = new ArrayList<ColumnHeader>();
		if (columnHeaders.size() <= numberOfColumns) {
			newColumns.addAll(columnHeaders);
			for (int i = columnHeaders.size(); i < numberOfColumns; i++) {
				newColumns.add(new ColumnHeader());
			}
		}
		// remove the columnHeaders from the end
		else {
			for (int i = 0; i < numberOfColumns; i++) {
				newColumns.add(columnHeaders.get(i));
			}
		}

		List<Row> newRows = new ArrayList<Row>();
		if (rows.size() <= numberOfRows) {
			newRows.addAll(rows);
			for (int i = rows.size(); i < numberOfRows; i++) {
				newRows.add(new Row());
			}
		}
		// remove the rows from the end
		else {
			for (int i = 0; i < numberOfRows; i++) {
				newRows.add(rows.get(i));
			}
		}

		for (int i = 0; i < numberOfRows; i++) {
			Row row = newRows.get(i);
			List<TableCell> cells = row.getCells();
			List<TableCell> newCells = new ArrayList<TableCell>();
			if (cells.size() <= numberOfColumns) {
				newCells.addAll(cells);
				for (int j = cells.size(); j < numberOfColumns; j++) {
					newCells.add(new TableCell());
				}
			}
			// remove the columnHeaders from the end
			else {
				for (int j = 0; j < numberOfColumns; j++) {
					newCells.add(cells.get(i));
				}
			}
			row.setCells(newCells);
		}
		columnHeaders = new ArrayList<ColumnHeader>();
		columnHeaders.addAll(newColumns);
		rows = new ArrayList<Row>();
		rows.addAll(newRows);
	}

	public void removeColumn(int colIndex) {
		columnHeaders.remove(colIndex);
		for (int i = 0; i < rows.size(); i++) {
			List<TableCell> cells = rows.get(i).getCells();
			cells.remove(colIndex);
		}
		numberOfColumns--;
	}

	public void removeRow(int rowIndex) {
		rows.remove(rowIndex);
		numberOfRows--;
	}

	public void setupDomain(String createdBy) throws Exception {
		int i = 0;
		if (domain.getId() != null && domain.getId() <= 0) {
			domain.setId(null);
		}
		if (domain.getId() == null
				|| domain.getCreatedBy() != null
				&& domain.getCreatedBy().equals(
						Constants.AUTO_COPY_ANNOTATION_PREFIX)) {
			domain.setCreatedBy(createdBy);
			domain.setCreatedDate(new Date());
		}
		if (domain.getDatumCollection() != null) {
			domain.getDatumCollection().clear();
		} else {
			domain.setDatumCollection(new HashSet<Datum>());
		}
		if (domain.getFileCollection() != null) {
			domain.getFileCollection().clear();
		} else {
			domain.setFileCollection(new HashSet<File>());
		}
		int j = 0;
		for (FileBean file : files) {
			domain.getFileCollection().add(file.getDomainFile());
			j++;
		}

		for (Row row : rows) {
			int cInd = 0;
			List<Condition> rowConditions = new ArrayList<Condition>();
			List<Datum> rowData = new ArrayList<Datum>();
			for (TableCell cell : row.getCells()) {
				ColumnHeader columnHeader = columnHeaders.get(cInd);
				if (columnHeader.getColumnType().equals("Datum")) {
					Datum datum = cell.getDatum();
					datum.setValue(cell.getValue());
					datum.setValueType(columnHeader.getValueType());
					datum.setValueUnit(columnHeader.getValueUnit());
					datum.setName(columnHeader.getColumnName());
					rowData.add(datum);
				} else if (columnHeader.getColumnType().equals("Condition")) {
					Condition condition = cell.getCondition();
					condition.setValue(cell.getValue());
					condition.setValueType(columnHeader.getValueType());
					condition.setValueUnit(columnHeader.getValueUnit());
					condition.setName(columnHeader.getColumnName());
					condition.setProperty(columnHeader.getConditionProperty());
					rowConditions.add(condition);
				}
				cInd++;
			}
			// associate conditions to each datum on each row
			for (Datum datum : rowData) {
				if (datum.getId() != null && datum.getId() <= 0) {
					datum.setId(null);
				}
				// if new
				if (datum.getId() == null) {
					datum.setCreatedBy(createdBy);
					datum.setCreatedDate(DateUtils.addSecondsToCurrentDate(i));
				}

				if (datum.getConditionCollection() == null) {
					datum.setConditionCollection(new HashSet<Condition>());
				} else {
					datum.getConditionCollection().clear();
				}
				for (Condition condition : rowConditions) {
					if (condition.getId() != null && condition.getId() <= 0) {
						condition.setId(null);
					}
					// if new
					if (condition.getId() == null) {
						condition.setCreatedBy(createdBy);
						condition.setCreatedDate(DateUtils
								.addSecondsToCurrentDate(i));
					}
					datum.getConditionCollection().add(condition);
				}

				domain.getDatumCollection().add(datum);
				datum.setFinding(domain);
				i++;
			}
		}
	}

	/**
	 * Compares <code>obj</code> to it self and returns true if they both are
	 * same
	 *
	 * @param obj
	 */
	public boolean equals(Object obj) {
		if (obj instanceof FindingBean) {
			FindingBean findingBean = (FindingBean) obj;
			if (domain.getId() != null
					&& domain.getId().equals(findingBean.getDomain().getId()))
				return true;
		}
		return false;
	}

	/**
	 * Returns hash code for the primary key of the object
	 */
	public int hashCode() {
		if (getDomain().getId() != null)
			return getDomain().getId().hashCode();
		return 0;
	}

	public FileBean getTheFile() {
		return theFile;
	}

	public void setTheFile(FileBean theFile) {
		this.theFile = theFile;
	}

	public List<FileBean> getFiles() {
		return files;
	}

	public void setFiles(List<FileBean> files) {
		this.files = files;
	}

	public Finding getDomain() {
		return domain;
	}

	public void addFile(FileBean file, int index) {
		if (index == -1) {
			files.add(file);
			return;
		}
		if (!files.isEmpty()) {
			files.remove(index);
		}
		files.add(index, file);
	}

	public void removeFile(int index) {
		files.remove(index);
	}

	public int getTheFileIndex() {
		return theFileIndex;
	}

	public void setTheFileIndex(int theFileIndex) {
		this.theFileIndex = theFileIndex;
	}
}
