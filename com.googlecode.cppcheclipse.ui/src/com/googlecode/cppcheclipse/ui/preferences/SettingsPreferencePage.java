package com.googlecode.cppcheclipse.ui.preferences;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.IPersistentPreferenceStore;
import org.eclipse.jface.preference.StringButtonFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.googlecode.cppcheclipse.core.CppcheclipsePlugin;
import com.googlecode.cppcheclipse.core.IPreferenceConstants;
import com.googlecode.cppcheclipse.ui.Console;
import com.googlecode.cppcheclipse.ui.Messages;

public class SettingsPreferencePage extends FieldEditorOverlayPage implements
		IWorkbenchPreferencePage {

	private static final String CPPCHECK_PROJ_STRING = ".cppcheck";

	private BooleanFieldEditor allCheck;
	private BooleanFieldEditor unusedFunctionsCheck;
	private IntegerFieldEditor numberOfThreads;
	private StringButtonFieldEditor projectFile;
	private List<BooleanFieldEditor> checkEditors;
	private Group checkGroup;


	ExclusiveBooleanFieldEditor misrac12Editor;
	ExclusiveBooleanFieldEditor misrac23Editor;
	ExclusiveBooleanFieldEditor misracpp08Editor;
	ExclusiveBooleanFieldEditor misracpp23Editor;
	private List<BooleanFieldEditor> premiumEditors;
	private Group premiumGroup;

	public SettingsPreferencePage() {
		super(GRID, true);
		// this is overridden in case of project properties
		setPreferenceStore(CppcheclipsePlugin.getWorkspacePreferenceStore());
		setDescription(Messages.SettingsPreferencePage_Description);
	}

	@Override
	protected String getPageId() {
		return IPreferenceConstants.SETTINGS_PAGE_ID;
	}

	private void createCheckGroup() {
		// available checks
		final Composite parent = getFieldEditorParent();
		checkGroup = new Group(parent, SWT.NONE);
		checkGroup.setText(Messages.SettingsPreferencePage_ChecksLabel);

		checkEditors = new LinkedList<BooleanFieldEditor>();

		allCheck = new BooleanFieldEditor(IPreferenceConstants.P_CHECK_ALL,
				Messages.SettingsPreferencePage_CheckAll, checkGroup) {

			@Override
			protected void valueChanged(boolean oldValue, boolean newValue) {
				super.valueChanged(oldValue, newValue);

				// enabling also depends on unusedFunctions
				if (!newValue) {
					numberOfThreads.setEnabled(!newValue
							&& !unusedFunctionsCheck.getBooleanValue(),
							getFieldEditorParent());
				} else {
					numberOfThreads.setEnabled(!newValue,
							getFieldEditorParent());
				}
				for (BooleanFieldEditor checkEditor : checkEditors) {
					checkEditor.setEnabled(!newValue, checkGroup);
				}
			}
		};
		addField(allCheck, checkGroup);

		BooleanFieldEditor checkEditor = new DependentBooleanFieldEditor(
				allCheck, IPreferenceConstants.P_CHECK_STYLE,
				Messages.SettingsPreferencePage_CheckStyle, checkGroup);
		addField(checkEditor, checkGroup);
		checkEditors.add(checkEditor);

		checkEditor = new DependentBooleanFieldEditor(allCheck,
				IPreferenceConstants.P_CHECK_PERFORMANCE,
				Messages.SettingsPreferencePage_CheckPerformance, checkGroup);
		addField(checkEditor, checkGroup);
		checkEditors.add(checkEditor);

		checkEditor = new DependentBooleanFieldEditor(allCheck,
				IPreferenceConstants.P_CHECK_PORTABILITY,
				Messages.SettingsPreferencePage_CheckPortability, checkGroup);
		addField(checkEditor, checkGroup);
		checkEditors.add(checkEditor);

		checkEditor = new DependentBooleanFieldEditor(allCheck,
				IPreferenceConstants.P_CHECK_INFORMATION,
				Messages.SettingsPreferencePage_CheckInformation, checkGroup);
		addField(checkEditor, checkGroup);
		checkEditors.add(checkEditor);

		// disable thread handling in case of unused function check is enabled
		unusedFunctionsCheck = new DependentBooleanFieldEditor(allCheck,
				IPreferenceConstants.P_CHECK_UNUSED_FUNCTIONS,
				Messages.SettingsPreferencePage_UnusedFunctions, checkGroup) {

			@Override
			protected void valueChanged(boolean oldValue, boolean newValue) {
				numberOfThreads.setEnabled(!newValue, getFieldEditorParent());
				super.valueChanged(oldValue, newValue);
			}
		};
		addField(unusedFunctionsCheck, checkGroup);
		checkEditors.add(unusedFunctionsCheck);

		checkEditor = new DependentBooleanFieldEditor(allCheck,
				IPreferenceConstants.P_CHECK_MISSING_INCLUDE,
				Messages.SettingsPreferencePage_CheckMissingInclude, checkGroup);
		addField(checkEditor, checkGroup);
		checkEditors.add(checkEditor);

		// reset layout manager here, since every field editor reset the
		// parent's layout manager in FieldEditor::createControl
		setCompositeLayout(checkGroup);
	}


	private void enablePremiumChecks(boolean val) {
		misrac12Editor.setEnabled(val && !misrac23Editor.getBooleanValue(), premiumGroup);
		misrac23Editor.setEnabled(val && !misrac12Editor.getBooleanValue(), premiumGroup);
		misracpp08Editor.setEnabled(val && !misracpp23Editor.getBooleanValue(), premiumGroup);
		misracpp23Editor.setEnabled(val && !misracpp08Editor.getBooleanValue(), premiumGroup);
		for (BooleanFieldEditor premiumEditor : premiumEditors) {
			premiumEditor.setEnabled(val, premiumGroup);
		}
	}

	private void createPremiumGroup() {
		// available premium options
		final Composite parent = getFieldEditorParent();
		premiumGroup = new Group(parent, SWT.NONE);
		premiumGroup.setText(Messages.SettingsPreferencePage_PremiumLabel);

		premiumEditors = new LinkedList<BooleanFieldEditor>();

		BooleanFieldEditor bughuntingEditor = new BooleanFieldEditor(IPreferenceConstants.P_PREMIUM_BUG_HUNTING,
			Messages.SettingsPreferencePage_PremiumBugHunting, premiumGroup);
		addField(bughuntingEditor, premiumGroup);
		premiumEditors.add(bughuntingEditor);

		misrac12Editor = new ExclusiveBooleanFieldEditor(IPreferenceConstants.P_PREMIUM_MISRA_C_12,
			Messages.SettingsPreferencePage_PremiumMisraC2012, premiumGroup);
		addField(misrac12Editor, premiumGroup);

		misrac23Editor = new ExclusiveBooleanFieldEditor(IPreferenceConstants.P_PREMIUM_MISRA_C_23,
			Messages.SettingsPreferencePage_PremiumMisraC2023, premiumGroup);
		addField(misrac23Editor, premiumGroup);

		misrac12Editor.addDependent(misrac23Editor, premiumGroup);
		misrac23Editor.addDependent(misrac12Editor, premiumGroup);

		misracpp08Editor = new ExclusiveBooleanFieldEditor(IPreferenceConstants.P_PREMIUM_MISRA_CPP_08,
			Messages.SettingsPreferencePage_PremiumMisraCpp2008, premiumGroup);
		addField(misracpp08Editor, premiumGroup);

		misracpp23Editor = new ExclusiveBooleanFieldEditor(IPreferenceConstants.P_PREMIUM_MISRA_CPP_23,
			Messages.SettingsPreferencePage_PremiumMisraCpp2023, premiumGroup);
		addField(misracpp23Editor, premiumGroup);

		misracpp08Editor.addDependent(misracpp23Editor, premiumGroup);
		misracpp23Editor.addDependent(misracpp08Editor, premiumGroup);

		BooleanFieldEditor certcEditor = new BooleanFieldEditor(IPreferenceConstants.P_PREMIUM_CERT_C,
			Messages.SettingsPreferencePage_PremiumCertC, premiumGroup);
		addField(certcEditor, premiumGroup);
		premiumEditors.add(certcEditor);

		BooleanFieldEditor certcppEditor = new BooleanFieldEditor(IPreferenceConstants.P_PREMIUM_CERT_CPP,
			Messages.SettingsPreferencePage_PremiumCertCpp, premiumGroup);
		addField(certcppEditor, premiumGroup);
		premiumEditors.add(certcppEditor);

		BooleanFieldEditor autosarEditor = new BooleanFieldEditor(IPreferenceConstants.P_PREMIUM_AUTOSAR,
			Messages.SettingsPreferencePage_PremiumAutosar, premiumGroup);
		addField(autosarEditor, premiumGroup);
		premiumEditors.add(autosarEditor);

		setCompositeLayout(premiumGroup);
	}

	@Override
	protected void createFieldEditors() {

		final FileDialog projectFileDialog = new FileDialog(getShell(), SWT.OPEN);
		projectFile = new StringButtonFieldEditor(IPreferenceConstants.P_PROJECT_FILE,
			Messages.SettingsPreferencePage_ProjectFile, getFieldEditorParent()) {

			@Override
			protected String changePressed() {
				return projectFileDialog.open();
			}

			@Override
			protected void valueChanged() {
				super.valueChanged();
				IPersistentPreferenceStore store = CppcheclipsePlugin.getConfigurationPreferenceStore();
				boolean isPremium = store.getBoolean(IPreferenceConstants.P_PREMIUM);
				enablePremiumChecks(isPremium && (getStringValue().isEmpty() || !getStringValue().endsWith(CPPCHECK_PROJ_STRING)));
			}

		};
		projectFile.setChangeButtonText(Messages.SettingsPreferencePage_ProjectFileDialogButton);
		projectFile.setEmptyStringAllowed(true);
		addField(projectFile);

		numberOfThreads = new IntegerFieldEditor(
				IPreferenceConstants.P_NUMBER_OF_THREADS,
				Messages.SettingsPreferencePage_NumberOfThreads,
				getFieldEditorParent(), 2) {

			@Override
			public void setEnabled(boolean enabled, Composite parent) {
				if (enabled) {
					enabled = !unusedFunctionsCheck.getBooleanValue();
				}
				super.setEnabled(enabled, parent);
			}

		};
		numberOfThreads.setValidRange(1, 16);
		addField(numberOfThreads);

		createCheckGroup();
		createPremiumGroup();

		final BooleanFieldEditor inconclusiveChecks = new BooleanFieldEditor(
				IPreferenceConstants.P_CHECK_INCONCLUSIVE,
				Messages.SettingsPreferencePage_Inconclusive,
				getFieldEditorParent());
		addField(inconclusiveChecks);
		
		BooleanFieldEditor checkEditor = new BooleanFieldEditor(
				IPreferenceConstants.P_LANGUAGE_STANDARD_POSIX,
				Messages.SettingsPreferencePage_LanguageStandard_Posix, getFieldEditorParent());
		addField(checkEditor);
		
		final ComboFieldEditor languageStandardCEditor = new ComboFieldEditor(
				IPreferenceConstants.P_LANGUAGE_STANDARD_C,
				"C Language Standard", new String[][] {
						{ "Unspecified", "" }, { "C89", "c89" },
						{ "C99", "c99" }, { "C11", "c11" }}, getFieldEditorParent());
		addField(languageStandardCEditor);
		
		final ComboFieldEditor languageStandardCppEditor = new ComboFieldEditor(
				IPreferenceConstants.P_LANGUAGE_STANDARD_CPP,
				"C++ Language Standard", new String[][] {
						{ "Unspecified", "" }, { "C++03", "c++03" },
						{ "C++11", "c++11" }, { "C++14", "c++14" },
						{ "C++17", "c++17" }, { "C++20", "c++20" }}, getFieldEditorParent());
		addField(languageStandardCppEditor);

		final ComboFieldEditor platformsEditor = new ComboFieldEditor(
				IPreferenceConstants.P_TARGET_PLATFORM,
				Messages.SettingsPreferencePage_TargetPlatform, new String[][] {
						{ "Unspecified", "" }, { "Win32 (ANSI)", "win32A" },
						{ "Win32 (Unicode)", "win32W" }, { "Win64", "win64" },
						{ "Unix (32bit)", "unix32" },
						{ "Unix (64bit)", "unix64" } }, getFieldEditorParent());
		addField(platformsEditor);

		// special flags
		final BooleanFieldEditor forceCheck = new BooleanFieldEditor(
				IPreferenceConstants.P_CHECK_FORCE,
				Messages.SettingsPreferencePage_Force, getFieldEditorParent());
		addField(forceCheck);

		final BooleanFieldEditor verboseCheck = new BooleanFieldEditor(
				IPreferenceConstants.P_CHECK_VERBOSE,
				Messages.SettingsPreferencePage_Verbose, getFieldEditorParent());
		addField(verboseCheck);

		final BooleanFieldEditor useInlineSuppressions = new BooleanFieldEditor(
				IPreferenceConstants.P_USE_INLINE_SUPPRESSIONS,
				Messages.SettingsPreferencePage_InlineSuppressions,
				getFieldEditorParent());
		addField(useInlineSuppressions);

		final BooleanFieldEditor debugCheck = new BooleanFieldEditor(
				IPreferenceConstants.P_CHECK_DEBUG,
				Messages.SettingsPreferencePage_Debug, getFieldEditorParent());
		addField(debugCheck);

		final BooleanFieldEditor followSystemIncludes = new BooleanFieldEditor(
				IPreferenceConstants.P_FOLLOW_SYSTEM_INCLUDES,
				Messages.SettingsPreferencePage_FollowSystemIncludes,
				getFieldEditorParent());
		addField(followSystemIncludes);

		final BooleanFieldEditor followUserIncludes = new BooleanFieldEditor(
				IPreferenceConstants.P_FOLLOW_USER_INCLUDES,
				Messages.SettingsPreferencePage_FollowUserIncludes,
				getFieldEditorParent());
		addField(followUserIncludes);

	}

	@Override
	protected Control createContents(Composite parent) {
		Control control = super.createContents(parent);
		refresh();
		// after that all controls are initialized
		return control;
	}

	public void init(IWorkbench workbench) {
	}

	@Override
	protected void performDefaults() {
		super.performDefaults();
		refresh();
	}

	private void refresh() {
		numberOfThreads.setEnabled(!unusedFunctionsCheck.getBooleanValue()
				&& !allCheck.getBooleanValue(), getFieldEditorParent());
		for (BooleanFieldEditor editor : checkEditors) {
			editor.setEnabled(!allCheck.getBooleanValue(), checkGroup);
		}

		IPersistentPreferenceStore store = CppcheclipsePlugin.getConfigurationPreferenceStore();
		boolean isPremium = store.getBoolean(IPreferenceConstants.P_PREMIUM);
		enablePremiumChecks(isPremium && (projectFile.getStringValue().isEmpty()
				|| !projectFile.getStringValue().endsWith(CPPCHECK_PROJ_STRING)));
	}
}
