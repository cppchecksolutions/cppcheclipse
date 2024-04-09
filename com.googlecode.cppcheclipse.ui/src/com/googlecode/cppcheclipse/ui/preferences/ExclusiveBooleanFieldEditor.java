package com.googlecode.cppcheclipse.ui.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.swt.widgets.Composite;

public class ExclusiveBooleanFieldEditor extends BooleanFieldEditor {

	private BooleanFieldEditor dependencyFieldEditor;
	private Composite parent;

	public ExclusiveBooleanFieldEditor(String name, String label, Composite parent) {
		super(name, label, parent);
		this.parent = parent;
	}

	public ExclusiveBooleanFieldEditor(String name, String labelText, int style, Composite parent) {
		super(name, labelText, style, parent);
		this.parent = parent;
	}

	public ExclusiveBooleanFieldEditor(BooleanFieldEditor dependencyFieldEditor, String name, String label,
			Composite parent) {
		super(name, label, parent);
		this.dependencyFieldEditor = dependencyFieldEditor;
		this.parent = parent;
	}

	public ExclusiveBooleanFieldEditor(BooleanFieldEditor dependencyFieldEditor, String name, String labelText,
			int style, Composite parent) {
		super(name, labelText, style, parent);
		this.dependencyFieldEditor = dependencyFieldEditor;
		this.parent = parent;
	}

	public void addDependent(BooleanFieldEditor dependencyFieldEditor, Composite parent) {
		this.dependencyFieldEditor = dependencyFieldEditor;
		this.parent = parent;
	}

	@Override
	public void setEnabled(boolean enabled, Composite parent) {
		if (enabled) {
			if (dependencyFieldEditor != null) {
				enabled = !dependencyFieldEditor.getBooleanValue();
			}
		}
		super.setEnabled(enabled, parent);
	}

    @Override
    protected void valueChanged(boolean oldValue, boolean newValue) {
		super.valueChanged(oldValue, newValue);
		if (dependencyFieldEditor != null) {
			dependencyFieldEditor.setEnabled(!newValue, parent);
		}
	}
}
