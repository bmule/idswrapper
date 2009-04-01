package it.polimi.elet.vplab.idswrapper.gui;

import it.polimi.elet.vplab.idswrapper.ids.IDS;
import it.polimi.elet.vplab.idswrapper.concrete.TagListAnalysis;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.swtdesigner.SWTResourceManager;

import java.util.ArrayList;

public class AnalysisInterfaceNetwork {

	private Button browseLogFileButton;
	private Button browseTrafficFileButton;
	private Button browseRuleFileButton;
	private Text NetworkText;
	IDSWrapperVisualInterface mainInterface;
	TestIDSInterface testInterface;
	private Text CLPreviewText;
	private Text TrafficFileText;
	private Text rulesFileText;
	private Text logFileText;
	protected Shell shell;
	Label IDSNameAndVersionLabel;
	Button confirmLogSelectionButton;
	Button confirmTrafficSelectionButton;
	Button confirmRulesSelectionButton;
	Button dumpApplicationLayerButton;
	Button displaySecondLayerButton;
	Button verboseButton;
	Button fullModeButton;
	Button fastModeButton;
	Button unsockModeButton;
	Button consoleButton;
	Button cmgButton;
	Button turnOffAlertingButton;
	Button confirmNetworkSelectionButton;	
	Button buttonBinaryFormat;
	Button msgToSysButton;
	Button noLogButton;
	TagListAnalysis commandLineTagList = new TagListAnalysis();
	
	//	Rappresenta le funzionalità mostrate nell'interfaccia
	ArrayList<String> panelFeaturesList;
	ArrayList<String> CLFeatures;

	/**
	 * Open the window
	 */
	public void open(IDSWrapperVisualInterface iwvi) 
	{
		mainInterface = iwvi;
		
		final Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

	/**
	 * Create contents of the window
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(650, 700);
		shell.setText("IDSWrapper - Analysis");

		final Label analysisControlPanelLabel = new Label(shell, SWT.NONE);
		analysisControlPanelLabel.setAlignment(SWT.CENTER);
		analysisControlPanelLabel.setFont(SWTResourceManager.getFont("Sans", 14, SWT.NONE));
		analysisControlPanelLabel.setForeground(SWTResourceManager.getColor(22, 22, 145));
		analysisControlPanelLabel.setText("Analysis control panel for network based IDS");
		analysisControlPanelLabel.setBounds(50, 13, 550, 35);

		final Label IDSLabel = new Label(shell, SWT.NONE);
		IDSLabel.setForeground(SWTResourceManager.getColor(0, 0, 255));
		IDSLabel.setFont(SWTResourceManager.getFont("Sans", 12, SWT.NONE));
		IDSLabel.setText("IDS: ");
		IDSLabel.setBounds(50, 80, 45, 18);

		IDSNameAndVersionLabel = new Label(shell, SWT.NONE);
		IDSNameAndVersionLabel.setForeground(SWTResourceManager.getColor(19, 139, 11));
		IDSNameAndVersionLabel.setFont(SWTResourceManager.getFont("Sans", 12, SWT.NONE));
		IDSNameAndVersionLabel.setBounds(101, 80, 145, 18);

		final Group pathSelectionGroup = new Group(shell, SWT.NONE);
		pathSelectionGroup.setText("Path selection");
		pathSelectionGroup.setBounds(50, 117, 550, 172);

		final Label labelLog = new Label(pathSelectionGroup, SWT.NONE);
		labelLog.setText("Log file");
		labelLog.setBounds(10, 27, 65, 17);

		logFileText = new Text(pathSelectionGroup, SWT.BORDER);
		logFileText.setText("/var/log");
		logFileText.setBounds(90, 23, 200, 25);

		final Label rulesFileLabel = new Label(pathSelectionGroup, SWT.NONE);
		rulesFileLabel.setText("Rules file");
		rulesFileLabel.setBounds(10, 103, 65, 17);

		rulesFileText = new Text(pathSelectionGroup, SWT.BORDER);
		rulesFileText.setBounds(90, 99, 200, 25);

		final Label labelTrafficFile = new Label(pathSelectionGroup, SWT.NONE);
		labelTrafficFile.setText("Traffic file");
		labelTrafficFile.setBounds(10, 140, 65, 17);

		TrafficFileText = new Text(pathSelectionGroup, SWT.BORDER);
		TrafficFileText.setBounds(90, 137, 200, 25);

		confirmLogSelectionButton = new Button(pathSelectionGroup, SWT.CHECK);
		confirmLogSelectionButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) 
			{
				if(confirmLogSelectionButton.getSelection())
				{
					logFileText.setEnabled(false);
					commandLineTagList.updteCommandLineTagList(2, logFileText.getText());
					createCommandLine();
				}
				else
				{
					logFileText.setEnabled(true);
					//	Tolgo il riferimento al log file dalla command line
					commandLineTagList.updteCommandLineTagList(2, "");
					createCommandLine();
				}
			}
		});
		confirmLogSelectionButton.setText("Confirm selection");
		confirmLogSelectionButton.setBounds(400, 23, 144, 22);

		confirmTrafficSelectionButton = new Button(pathSelectionGroup, SWT.CHECK);
		confirmTrafficSelectionButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) 
			{
				if(confirmTrafficSelectionButton.getSelection())
				{
					TrafficFileText.setEnabled(false);
					commandLineTagList.updteCommandLineTagList(6, TrafficFileText.getText());
					createCommandLine();
				}
				else
				{
					TrafficFileText.setEnabled(true);
					//	Tolgo il riferimento al traffic file dalla command line
					commandLineTagList.updteCommandLineTagList(6, "");
					createCommandLine();
				}
			}
		});
		confirmTrafficSelectionButton.setText("Confirm selection");
		confirmTrafficSelectionButton.setBounds(400, 135, 144, 22);

		confirmRulesSelectionButton = new Button(pathSelectionGroup, SWT.CHECK);
		confirmRulesSelectionButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) 
			{
				if(confirmRulesSelectionButton.getSelection())
				{
					rulesFileText.setEnabled(false);
					commandLineTagList.updteCommandLineTagList(5, rulesFileText.getText());
					createCommandLine();
				}
				else
				{
					rulesFileText.setEnabled(true);
					//	Tolgo il riferimento al rule file dalla command line
					commandLineTagList.updteCommandLineTagList(5, "");
					createCommandLine();
				}
			}
		});
		confirmRulesSelectionButton.setText("Confirm selection");
		confirmRulesSelectionButton.setBounds(400, 98, 144, 22);

		final Label homeNetLabel = new Label(pathSelectionGroup, SWT.NONE);
		homeNetLabel.setBounds(10, 65,65, 17);
		homeNetLabel.setText("Home net");

		NetworkText = new Text(pathSelectionGroup, SWT.BORDER);
		NetworkText.setText("192.168.1.0/24");
		NetworkText.setBounds(90, 60,300, 25);

		confirmNetworkSelectionButton = new Button(pathSelectionGroup, SWT.CHECK);
		confirmNetworkSelectionButton.setBounds(400, 59,140, 22);
		confirmNetworkSelectionButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) 
			{
				if(confirmNetworkSelectionButton.getSelection())
				{
					NetworkText.setEnabled(false);
					commandLineTagList.updteCommandLineTagList(4, NetworkText.getText());
					createCommandLine();
				}
				else
				{
					NetworkText.setEnabled(true);
					//	Tolgo il riferimento all'home network dalla command line
					commandLineTagList.updteCommandLineTagList(4, "");
					createCommandLine();
				}
			}
		});
		confirmNetworkSelectionButton.setText("Confirm selection");

		browseRuleFileButton = new Button(pathSelectionGroup, SWT.NONE);
		browseRuleFileButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				
				FileDialog fd = new FileDialog(shell, SWT.OPEN);
		        fd.setText("Select the Rules File");
		        fd.setFilterPath(mainInterface.getIDS().getPath());
		        String selected = fd.open();
		        if(selected != null)
		        	rulesFileText.setText(selected);
				
			}
		});
		browseRuleFileButton.setText("Browse");
		browseRuleFileButton.setBounds(300, 95, 90, 29);

		browseTrafficFileButton = new Button(pathSelectionGroup, SWT.NONE);
		browseTrafficFileButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				
				FileDialog fd = new FileDialog(shell, SWT.OPEN);
		        fd.setText("Select the Traffic File");
		        String selected = fd.open();
		        if(selected != null)
		        	TrafficFileText.setText(selected);
				
			}
		});
		browseTrafficFileButton.setText("Browse");
		browseTrafficFileButton.setBounds(300, 137, 90, 25);

		browseLogFileButton = new Button(pathSelectionGroup, SWT.NONE);
		browseLogFileButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				
				DirectoryDialog dd = new DirectoryDialog(shell, SWT.OPEN);
		        dd.setText("Select the folder for the Log File");
		        dd.setFilterPath(logFileText.getText()+"/");
		        String selected = dd.open();
		        if (selected != null)
		        	logFileText.setText(selected+"/analysis.log");
				
			}
		});
		browseLogFileButton.setText("Browse");
		browseLogFileButton.setBounds(304, 23, 86, 25);
		pathSelectionGroup.setTabList(new Control[] {logFileText, NetworkText, rulesFileText, TrafficFileText, confirmLogSelectionButton, confirmNetworkSelectionButton, confirmRulesSelectionButton, confirmTrafficSelectionButton, browseRuleFileButton, browseTrafficFileButton, browseLogFileButton});

		final Group resultModeGroup = new Group(shell, SWT.NONE);
		resultModeGroup.setText("Result options");
		resultModeGroup.setBounds(50, 305, 550, 110);

		dumpApplicationLayerButton = new Button(resultModeGroup, SWT.CHECK);
		dumpApplicationLayerButton.setEnabled(false);
		dumpApplicationLayerButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) 
			{
				if(dumpApplicationLayerButton.getSelection())
				{
					commandLineTagList.updteCommandLineTagList(1, "dump_app_layer");
					createCommandLine();
				}
				else
				{
					//	Tolgo il riferimento al dump application layer dalla command line
					commandLineTagList.updteCommandLineTagList(1, "clear_dump_app_layer");
					createCommandLine();
				}
			}
		});
		dumpApplicationLayerButton.setFont(SWTResourceManager.getFont("Sans", 10, SWT.NONE));
		dumpApplicationLayerButton.setText("Dump application layer");
		dumpApplicationLayerButton.setBounds(10, 25, 300, 22);

		displaySecondLayerButton = new Button(resultModeGroup, SWT.CHECK);
		displaySecondLayerButton.setEnabled(false);
		displaySecondLayerButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) 
			{
				if(displaySecondLayerButton.getSelection())
				{
					commandLineTagList.updteCommandLineTagList(1, "show_second_layer");
					createCommandLine();
				}
				else
				{
					//	Tolgo il riferimento al show second layer layer dalla command line
					commandLineTagList.updteCommandLineTagList(1, "clear_show_second_layer");
					createCommandLine();
				}
			}
		});
		displaySecondLayerButton.setFont(SWTResourceManager.getFont("Sans", 10, SWT.NONE));
		displaySecondLayerButton.setText("Display second layer header info");
		displaySecondLayerButton.setBounds(10, 53, 300, 22);

		verboseButton = new Button(resultModeGroup, SWT.CHECK);
		verboseButton.setEnabled(false);
		verboseButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) 
			{
				if(verboseButton.getSelection())
				{
					commandLineTagList.updteCommandLineTagList(1, "verbose_info");
					createCommandLine();
				}
				else
				{
					//	Tolgo il riferimento al clear verbose info dalla command line
					commandLineTagList.updteCommandLineTagList(1, "clear_verbose_info");
					createCommandLine();
				}
			}
		});
		verboseButton.setText("Verbose informations");
		verboseButton.setBounds(10, 81, 300, 23);

		final Group groupCLPreview = new Group(shell, SWT.NONE);
		groupCLPreview.setText("Command line preview");
		groupCLPreview.setBounds(50, 545, 550, 80);

		CLPreviewText = new Text(groupCLPreview, SWT.WRAP | SWT.MULTI | SWT.BORDER);
		CLPreviewText.setBounds(10, 25, 530, 44);

		final Group alertOptionGroup = new Group(shell, SWT.NONE);
		alertOptionGroup.setBounds(50, 427,295, 105);
		alertOptionGroup.setText("Alert options");

		fullModeButton = new Button(alertOptionGroup, SWT.RADIO);
		fullModeButton.setEnabled(false);
		fullModeButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) 
			{
				if(fullModeButton.getSelection())
				{
					commandLineTagList.updteCommandLineTagList(3, "full");
					createCommandLine();
				}
				else
				{
					//	Tolgo il riferimento alla modalità di alert full dalla command line
					commandLineTagList.updteCommandLineTagList(3, "clear_full");
					createCommandLine();
				}
			}
		});
		fullModeButton.setText("Full mode");
		fullModeButton.setBounds(10, 25, 130, 22);

		fastModeButton = new Button(alertOptionGroup, SWT.RADIO);
		fastModeButton.setEnabled(false);
		fastModeButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) 
			{
				if(fastModeButton.getSelection())
				{
					commandLineTagList.updteCommandLineTagList(3, "fast");
					createCommandLine();
				}
				else
				{
					//	Tolgo il riferimento alla modalità di alert fast dalla command line
					commandLineTagList.updteCommandLineTagList(3, "clear_fast");
					createCommandLine();
				}
			}
		});
		fastModeButton.setText("Fast mode");
		fastModeButton.setBounds(10, 50, 130, 22);

		unsockModeButton = new Button(alertOptionGroup, SWT.RADIO);
		unsockModeButton.setEnabled(false);
		unsockModeButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) 
			{
				if(unsockModeButton.getSelection())
				{
					commandLineTagList.updteCommandLineTagList(3, "unsock");
					createCommandLine();
				}
				else
				{
					//	Tolgo il riferimento alla modalità di alert unsock dalla command line
					commandLineTagList.	updteCommandLineTagList(3, "clear_unsock");
					createCommandLine();
				}
			}
		});
		unsockModeButton.setText("Unsock mode");
		unsockModeButton.setBounds(10, 75, 130, 22);

		consoleButton = new Button(alertOptionGroup, SWT.RADIO);
		consoleButton.setEnabled(false);
		consoleButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) 
			{
				if(consoleButton.getSelection())
				{
					commandLineTagList.	updteCommandLineTagList(3, "console");
					createCommandLine();
				}
				else
				{
					//	Tolgo il riferimento alla modalità di alert binary format log dalla command line
					commandLineTagList.updteCommandLineTagList(3, "clear_console");
					createCommandLine();
				}
			}
		});
		consoleButton.setText("Write to console");
		consoleButton.setBounds(160, 25, 130, 22);

		cmgButton = new Button(alertOptionGroup, SWT.RADIO);
		cmgButton.setEnabled(false);
		cmgButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) 
			{
				if(cmgButton.getSelection())
				{
					commandLineTagList.updteCommandLineTagList(3, "cmg");
					createCommandLine();
				}
				else
				{
					//	Tolgo il riferimento alla modalità di alert send message to syslog dalla command line
					commandLineTagList.updteCommandLineTagList(3, "clear_cmg");
					createCommandLine();
				}
			}
		});
		cmgButton.setText("CMG mode");
		cmgButton.setBounds(160, 50, 130, 22);

		turnOffAlertingButton = new Button(alertOptionGroup, SWT.RADIO);
		turnOffAlertingButton.setEnabled(false);
		turnOffAlertingButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) 
			{
				if(turnOffAlertingButton.getSelection())
				{
					commandLineTagList.updteCommandLineTagList(3, "no_alert");
					createCommandLine();
				}
				else
				{
					//	Tolgo il riferimento alla modalità di alert "none" dalla command line
					commandLineTagList.updteCommandLineTagList(3, "clear_no_alert");
					createCommandLine();
				}
			}
		});
		turnOffAlertingButton.setText("Turn off alerting");
		turnOffAlertingButton.setBounds(160, 75, 130, 22);

		final Group groupOtherOptions = new Group(shell, SWT.NONE);
		groupOtherOptions.setText("Other alert options");
		groupOtherOptions.setBounds(356, 427, 241, 105);

		buttonBinaryFormat = new Button(groupOtherOptions, SWT.CHECK);
		buttonBinaryFormat.setEnabled(false);
		buttonBinaryFormat.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) 
			{
				if(buttonBinaryFormat.getSelection())
				{
					commandLineTagList.updteCommandLineTagList(3, "binary_format_log");
					createCommandLine();
				}
				else
				{
					//	Tolgo il riferimento all'opzione di alert "binary_format_log" dalla command line
					commandLineTagList.updteCommandLineTagList(3, "clear_binary_format_log");
					createCommandLine();
				}
			}
		});
		buttonBinaryFormat.setText("Binary format");
		buttonBinaryFormat.setBounds(10, 25, 130, 22);

		msgToSysButton = new Button(groupOtherOptions, SWT.CHECK);
		msgToSysButton.setEnabled(false);
		msgToSysButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) 
			{
				if(msgToSysButton.getSelection())
				{
					commandLineTagList.updteCommandLineTagList(3, "msg_to_syslog");
					createCommandLine();
				}
				else
				{
					//	Tolgo il riferimento all'opzione di alert "msg_to_syslog" dalla command line
					commandLineTagList.	updteCommandLineTagList(3, "clear_msg_to_syslog");
					createCommandLine();
				}
			}
		});
		msgToSysButton.setText("Msg to SysLog");
		msgToSysButton.setBounds(10, 50, 130, 22);

		noLogButton = new Button(groupOtherOptions, SWT.CHECK);
		noLogButton.setEnabled(false);
		noLogButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) 
			{
				if(noLogButton.getSelection())
				{
					commandLineTagList.updteCommandLineTagList(3, "no_log");
					createCommandLine();
				}
				else
				{
					//	Tolgo il riferimento all'opzione di alert "no_log" dalla command line
					commandLineTagList.updteCommandLineTagList(3, "clear_no_log");
					createCommandLine();
				}
				
			}
		});
		noLogButton.setText("No log");
		noLogButton.setBounds(10, 75, 130, 22);

		final Button BackButton = new Button(shell, SWT.NONE);
		BackButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) 
			{
				mainInterface.reloadLabelsAndButtons();
				shell.close();
			}
		});
		BackButton.setText("Back to main interface");
		BackButton.setBounds(420, 631, 180, 29);

		final Button testIdsButton = new Button(shell, SWT.NONE);
		testIdsButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) 
			{
				
				TestIDSInterface testing = new TestIDSInterface();
				testing.open(mainInterface);
				
			}
		});
		testIdsButton.setText("Test IDS");
		testIdsButton.setEnabled(false);
		testIdsButton.setBounds(210, 630, 81, 29);
		
		final Button ExecuteAnalysisButton = new Button(shell, SWT.NONE);
		ExecuteAnalysisButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) 
			{
				/*
				 * Questo metodo dovrà attivare l'input handler, l'attività di analisi dell'IDS e l'output handler
				 * */
				//	PER TESTARE ADESSO LANCIAMO SUBITO L'ANALISI DELL'IDS
				mainInterface.getIDSDataFromIdsWrapper().analizeTraffic();
				
				testIdsButton.setEnabled(true);
				
			}
		});
		ExecuteAnalysisButton.setText("Execute Analysis");
		ExecuteAnalysisButton.setBounds(50, 631, 145, 29);
		
		//
		loadData();
	}
	
	public void loadData()
	{
		IDS ids = mainInterface.getIDSDataFromIdsWrapper();
		String idsName = ids.getName();
		IDSNameAndVersionLabel.setText(idsName);
		commandLineTagList.inizializeCommandLineTagList();
//createPanelFeaturesList();		inutile?????
		enableIDSFeatures();
		rulesFileText.setText(ids.getPath());
	}
	
	private String createCommandLine()
	{
		mainInterface.getIDS().getAnalysisCommandLine().updateCommandLine(commandLineTagList.getTagList());
		CLPreviewText.setText(mainInterface.getIDS().getAnalysisCommandLine().getTextualCL());
		return mainInterface.getIDS().getAnalysisCommandLine().getTextualCL();
	}
	
	private void enableIDSFeatures()
	{
		 CLFeatures = (ArrayList<String>) mainInterface.getIDS().getAnalysisCommandLine().getCLFeatures();
		
		if(isFeatureInList("dump_app_layer"))
			dumpApplicationLayerButton.setEnabled(true);
		if(isFeatureInList("show_second_layer"))
			displaySecondLayerButton.setEnabled(true);
		if(isFeatureInList("verbose_info"))
			verboseButton.setEnabled(true);
		if(isFeatureInList("full"))
			fullModeButton.setEnabled(true);
		if(isFeatureInList("fast"))
			fastModeButton.setEnabled(true);
		if(isFeatureInList("unsock"))
			unsockModeButton.setEnabled(true);
		if(isFeatureInList("console"))
			consoleButton.setEnabled(true);
		if(isFeatureInList("cmg"))
			cmgButton.setEnabled(true);
		if(isFeatureInList("no_alert"))
			turnOffAlertingButton.setEnabled(true);
		if(isFeatureInList("binary_format_log"))
			buttonBinaryFormat.setEnabled(true);
		if(isFeatureInList("msg_to_syslog"))
			msgToSysButton.setEnabled(true);
		if(isFeatureInList("no_log"))
			noLogButton.setEnabled(true);
		
		
	}
	
	private boolean isFeatureInList(String feature)
	{
		for(int i=0; i<CLFeatures.size(); i++)
		{
			if(CLFeatures.get(i).equals(feature))
				return true;
		}
		
		return false;
	}
	
	@SuppressWarnings("unused")
	private ArrayList<String> createPanelFeaturesList()
	{
		panelFeaturesList = new ArrayList<String>();
		panelFeaturesList.add("dump_app_layer");
		panelFeaturesList.add("show_second_layer");
		panelFeaturesList.add("verbose_info");
		panelFeaturesList.add("full");
		panelFeaturesList.add("fast");
		panelFeaturesList.add("unsock");
		panelFeaturesList.add("console");
		panelFeaturesList.add("cmg");
		panelFeaturesList.add("no_alert");
		panelFeaturesList.add("binary_format_log");
		panelFeaturesList.add("msg_to_syslog");
		panelFeaturesList.add("no_log");
		
		return panelFeaturesList;
		
	}

}
