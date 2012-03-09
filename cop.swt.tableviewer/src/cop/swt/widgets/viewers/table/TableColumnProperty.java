package cop.swt.widgets.viewers.table;

import static cop.extensions.CommonExt.isNotNull;

import org.eclipse.core.runtime.Assert;
import org.eclipse.swt.widgets.TableColumn;

import cop.extensions.StringExt;

public class TableColumnProperty {
	private final String key;
	private TableColumn tableColumn;

	public TableColumnProperty(String key) {
		this(key, null);
	}

	// public <T> TableColumnProperty(PTableColumn<T> column)
	// {
	// this(column.getDescription().getKey(), column.getViewer().getColumn());
	// }

	public TableColumnProperty(String key, TableColumn tableColumn) {
		Assert.isTrue(StringExt.isNotEmpty(key), "TableColumn key is empty");

		this.key = key;
		this.tableColumn = tableColumn;
	}

	public String getKey() {
		return key;
	}

	public String getName() {
		return isNotNull(tableColumn) ? tableColumn.getText() : "";
	}

	public int getWidth() {
		return isNotNull(tableColumn) ? tableColumn.getWidth() : 0;
	}

	public void addTableColumn(TableColumn tableColumn) {
		this.tableColumn = tableColumn;
	}
}
