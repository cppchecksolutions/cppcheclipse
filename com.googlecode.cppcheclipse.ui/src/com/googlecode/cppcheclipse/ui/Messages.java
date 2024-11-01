package com.googlecode.cppcheclipse.ui;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "com.googlecode.cppcheclipse.ui.messages"; //$NON-NLS-1$
	public static String UpdateCheck_Daily, UpdateCheck_JobName,
			UpdateCheck_Monthly, UpdateCheck_NoUpdateMessage,
			UpdateCheck_NoUpdateTitle,
			UpdateCheck_UpdateMessage, UpdateCheck_UpdateTitle,
			UpdateCheck_Weekly;
	public static String OverlayPage_UseWorkspaceSettings,
			OverlayPage_UseProjectSettings,
			OverlayPage_ConfigureWorkspaceSettings;
	public static String BinaryPathPreferencePage_AskBeforeLeaveMessage;
	public static String BinaryPathPreferencePage_AskBeforeLeaveTitle;
	public static String BinaryPathPreferencePage_AutomaticUpdateCheck;
	public static String BinaryPathPreferencePage_ButtonDiscard;
	public static String BinaryPathPreferencePage_ButtonSave;
	public static String BinaryPathPreferencePage_CheckForUpdate;
	public static String BinaryPathPreferencePage_Description;
	public static String BinaryPathPreferencePage_FileDialogButton;
	public static String BinaryPathPreferencePage_FileDialogTitle;
	public static String BinaryPathPreferencePage_NoValidPath;
	public static String BinaryPathPreferencePage_PathToBinary;
	public static String BinaryPathPreferencePage_UnknownVersion;
	public static String BinaryPathPreferencePage_UpdateCheckNever;
	public static String BinaryPathPreferencePage_UpdateCheckNotice;
	public static String BinaryPathPreferencePage_UpdateInterval;
	public static String BinaryPathPreferencePage_VariablesButton;

	public static String BinaryPathPreferencePage_VersionTooOld;
	public static String BinaryPathPreferencePage_LinkToCppcheck;
	public static String Builder_IncrementalBuilderTask;
	public static String Builder_PathEmptyMessage;
	public static String Builder_PathEmptyTitle;
	public static String Builder_ResouceVisitorTask;
	public static String BuildPropertyPage_Description;
	public static String BuildPropertyPage_RunOnBuild;
	public static String Console_Title;
	public static String ProblemReporter_ProblemInExternalFile;
	public static String ProblemReporter_Message;
	public static String ProblemsPreferencePage_Description;
	public static String ProblemsTreeEditor_Name;
	public static String ProblemsTreeEditor_Problems;
	public static String ProblemsTreeEditor_Severity;
	public static String ProgressReporter_TaskName;
	public static String ReportBug_Label;
	public static String RunCodeAnalysis_Error;
	public static String RunCodeAnalysis_JobName;
	
	public static String ClearMarkers_Error;
	public static String ClearMarkers_JobName;
	
	public static String SettingsPreferencePage_CheckAll;
	public static String SettingsPreferencePage_CheckPerformance;
	public static String SettingsPreferencePage_CheckPortability;
	public static String SettingsPreferencePage_CheckInformation;
	public static String SettingsPreferencePage_CheckMissingInclude;
	public static String SettingsPreferencePage_CheckStyle;
	public static String SettingsPreferencePage_Description;
	public static String SettingsPreferencePage_FollowUserIncludes;
	public static String SettingsPreferencePage_FollowSystemIncludes;
	public static String SettingsPreferencePage_Force;
	public static String SettingsPreferencePage_NumberOfThreads;
	public static String SettingsPreferencePage_ProjectFile;
	public static String SettingsPreferencePage_ProjectFileDialogButton;
	public static String SettingsPreferencePage_UnusedFunctions;
	public static String SettingsPreferencePage_Verbose;
	public static String SuppressFileResolution_Label;
	public static String CheckDescriptionResolution_Label;
	public static String SuppressionsPropertyPage_Description;
	public static String SuppressionsPropertyPage_SuppressionsLabel;
	public static String SuppressionsTable_AllLines;
	public static String SuppressionsTable_AllProblems;
	public static String SuppressionsTable_ColumnFilename;
	public static String SuppressionsTable_ColumnLine;
	public static String SuppressionsTable_ColumnProblem;
	public static String SuppressionsTable_FileSelection;
	public static String TableEditor_FileSelectionErrorExactlyOne;
	public static String TableEditor_FileSelectionErrorFile;
	public static String TableEditor_FileSelectionErrorFileFolder;
	public static String SuppressionsTable_FileSelectionMessage;
	public static String SuppressProblemInLineResolution_Label;
	public static String SuppressProblemResolution_Label;
	public static String SymbolsPropertyPage_SymbolLabel;
	public static String TableEditor_Add;
	public static String TableEditor_Remove;
	public static String TableEditor_RemoveAll;
	public static String TableEditor_AddExternal;
	public static String UpdateCheck_NeverCheckAgain;

	public static String AdvancedSettingsPropertyPage_AdvancedArguments;
	public static String AdvancedSettingsPropertyPage_Description;
	public static String SettingsPreferencePage_Debug;
	public static String SettingsPreferencePage_ChecksLabel;
	public static String SettingsPreferencePage_InlineSuppressions;

	public static String SettingsPreferencePage_PremiumBugHunting;
	public static String SettingsPreferencePage_PremiumMisraC2012;
	public static String SettingsPreferencePage_PremiumMisraC2023;
	public static String SettingsPreferencePage_PremiumMisraCpp2008;
	public static String SettingsPreferencePage_PremiumMisraCpp2023;
	public static String SettingsPreferencePage_PremiumCertC;
	public static String SettingsPreferencePage_PremiumCertCpp;
	public static String SettingsPreferencePage_PremiumAutosar;
	public static String SettingsPreferencePage_PremiumLabel;

	public static String SymbolsPropertyPage_Description;

	public static String SettingsPreferencePage_Inconclusive;

	public static String SettingsPreferencePage_TargetPlatform;

	public static String SettingsPreferencePage_LanguageStandard_Posix;

	public static String SettingsPreferencePage_LanguageStandard_C99;

	public static String SettingsPreferencePage_LanguageStandardsLabel;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
