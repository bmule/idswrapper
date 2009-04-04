/*
 * $Id$
 *
 * $Revision$
 *
 * $Date$
 * 
 * IDSWrapper - An extendable wrapping interface to manage, run your IDS and to
 * evaluate its performances.
 *
 * Copyright (C) 2009 Davide Polino, Paolo Rigoldi, Federico Maggi. 
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package it.polimi.elet.vplab.idswrapper.gui;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import it.polimi.elet.vplab.idswrapper.ids.IDS;
import it.polimi.elet.vplab.idswrapper.concrete.TagListTraining;

import com.swtdesigner.SWTResourceManager;

@SuppressWarnings  ({"unchecked"})
public class TrainInterfaceNetwork 
{
	private Text XMLBaseNameText;
	private Text XMLNetMaskText;
	private Text XMLIPText;
	private Button startTestingButton;
	IDSWrapperVisualInterface mainInterface;
	private Text randomSeedText;
	private Text saveFrequencyText;
	private Text bufferSizeText;
	private Text packetsToSkipText;
	private Text numberOfPacketsText;
	private Text logFileText;
	private Text xmlSettingsPathText;
	private List datasetList;
	private Text CLPreviewText;
	private Text datasetPathText;
	private Label IDSNameAndVersionLabel;
	protected Shell shell;
	private Button restartTrainingCheck;
	private Button insertLogFileCheck;
	private Button saveRedundantInfoCheck;
	private Button confirmBufferSizeButton;
	private Button savePeriodButton;
	private Button randomSeedCheck;
	private Button sequentialRadio;
	private Button pseudocasualRadio;
	private Button startTrainingButton;
	private Button selectXMLFileCheck;
	private Button regularTrainingButton;
	private Button confirmDatasetCheck;
	private Button backToMainButton;
	private Button threatTrainingButton;
	
	TagListTraining commandLineTagList = new TagListTraining();
	ArrayList<String> CLFeatures;
	ArrayList packetStruct = new ArrayList();
	ArrayList packet;
	ArrayList packetInfo;
	IDS ids;
	String firstTrainBase = "";
	String concatStrFirst = "";
	boolean canTest = false;
	String concatStr = "";
	
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
	 * Open the window
	 */
	public void open() {
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
		shell.setSize(850, 760);
		shell.setText("IDSWrapper - Training");

		final Label trainControlPanelLabel = new Label(shell, SWT.NONE);
		trainControlPanelLabel.setAlignment(SWT.CENTER);
		trainControlPanelLabel.setForeground(SWTResourceManager.getColor(22, 22, 145));
		trainControlPanelLabel.setFont(SWTResourceManager.getFont("Sans", 14, SWT.NONE));
		trainControlPanelLabel.setText("Train control panel for network based IDS");
		trainControlPanelLabel.setBounds(50, 13, 550, 35);

		final Label IDSLabel = new Label(shell, SWT.NONE);
		IDSLabel.setBounds(50, 72, 45, 18);
		IDSLabel.setForeground(SWTResourceManager.getColor(0, 0, 255));
		IDSLabel.setFont(SWTResourceManager.getFont("Sans", 12, SWT.NONE));
		IDSLabel.setText("IDS: ");

		IDSNameAndVersionLabel = new Label(shell, SWT.NONE);
		IDSNameAndVersionLabel.setBounds(101, 72, 145, 18);
		IDSNameAndVersionLabel.setForeground(SWTResourceManager.getColor(19, 139, 11));
		IDSNameAndVersionLabel.setFont(SWTResourceManager.getFont("Sans", 12, SWT.NONE));

		final Group trainDatasetPathGroup = new Group(shell, SWT.NONE);
		trainDatasetPathGroup.setText("Dataset && configuration files");
		trainDatasetPathGroup.setBounds(50, 169, 550, 236);

		final Label trainDatasetPathLabel = new Label(trainDatasetPathGroup, SWT.NONE);
		trainDatasetPathLabel.setText("Train dataset path");
		trainDatasetPathLabel.setBounds(10, 27, 120, 17);

		datasetPathText = new Text(trainDatasetPathGroup, SWT.BORDER);
		datasetPathText.setBounds(136, 23, 254, 25);

		datasetList = new List(trainDatasetPathGroup, SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		datasetList.setBounds(10, 112, 380, 70);

		final Button AddButton = new Button(trainDatasetPathGroup, SWT.NONE);
		AddButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) 
			{
				packet = new ArrayList();
				packetInfo = new ArrayList();
				packetInfo.add(numberOfPacketsText.getText());
				packetInfo.add(packetsToSkipText.getText());
				packet.add(packetInfo);
				packet.add(datasetPathText.getText());
				packetStruct.add(packet);
				
				
				datasetList.add(datasetPathText.getText()+" - Packet number: "+numberOfPacketsText.getText()+" - Packet to skip: "+packetsToSkipText.getText());
				
				datasetPathText.setText("");
				numberOfPacketsText.setText("");
				packetsToSkipText.setText("");
			}
		});
		AddButton.setText("Add it");
		AddButton.setBounds(420, 70, 98, 25);

		final Button removeButton = new Button(trainDatasetPathGroup, SWT.NONE);
		removeButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) 
			{
				packetStruct = new ArrayList();
				datasetList.removeAll();
			}
		});
		removeButton.setText("Remove all");
		removeButton.setBounds(396, 112, 144, 25);

		confirmDatasetCheck = new Button(trainDatasetPathGroup, SWT.CHECK);
		confirmDatasetCheck.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) 
			{
				if(confirmDatasetCheck.getSelection())
				{
					AddButton.setEnabled(false);
					removeButton.setEnabled(false);
					commandLineTagList.updteCommandLineTagList(4, packetStruct);
					createCommandLine();
				}
				else
				{
					AddButton.setEnabled(true);
					removeButton.setEnabled(true);
					commandLineTagList.updteCommandLineTagList(4, null);
					createCommandLine();
				}
			}
		});
		confirmDatasetCheck.setText("Confirm selection");
		confirmDatasetCheck.setBounds(396, 160, 144, 22);

		final Label numberOfPacketsToUse = new Label(trainDatasetPathGroup, SWT.NONE);
		numberOfPacketsToUse.setText("Packets to use");
		numberOfPacketsToUse.setBounds(10, 73, 93, 17);

		final Label numberOfPacketsToSkip = new Label(trainDatasetPathGroup, SWT.NONE);
		numberOfPacketsToSkip.setText("Packets to skip");
		numberOfPacketsToSkip.setBounds(195, 75, 101, 17);

		final Label insertLogFileLabel = new Label(trainDatasetPathGroup, SWT.NONE);
		insertLogFileLabel.setText("Log file path");
		insertLogFileLabel.setBounds(10, 202, 80, 17);

		logFileText = new Text(trainDatasetPathGroup, SWT.BORDER);
		logFileText.setEnabled(false);
		logFileText.setBounds(109, 200, 254, 25);

		insertLogFileCheck = new Button(trainDatasetPathGroup, SWT.CHECK);
		insertLogFileCheck.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) 
			{
				if(insertLogFileCheck.getSelection())
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
		insertLogFileCheck.setEnabled(false);
		insertLogFileCheck.setBounds(396, 202, 144, 22);
		insertLogFileCheck.setText("Confirm selection");

		numberOfPacketsText = new Text(trainDatasetPathGroup, SWT.BORDER);
		numberOfPacketsText.setEnabled(false);
		numberOfPacketsText.setBounds(109, 70, 80, 25);

		packetsToSkipText = new Text(trainDatasetPathGroup, SWT.BORDER);
		packetsToSkipText.setEnabled(false);
		packetsToSkipText.setBounds(305, 70, 85, 25);

		final Button browseTrainFileButton = new Button(trainDatasetPathGroup, SWT.NONE);
		browseTrainFileButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				
				FileDialog fd = new FileDialog(shell, SWT.OPEN);
				fd.setText("Select");
				String selected = fd.open();
				if(selected != null)
					datasetPathText.setText(selected);
				
			}
		});
		browseTrainFileButton.setText("Browse");
		browseTrainFileButton.setBounds(420, 20, 98, 25);

		final Button browseLogFileButton = new Button(trainDatasetPathGroup, SWT.NONE);
		browseLogFileButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				
				FileDialog fd = new FileDialog(shell, SWT.OPEN);
				fd.setText("Select");
				String selected = fd.open();
				if(selected != null)
					logFileText.setText(selected);
				
			}
		});
		browseLogFileButton.setText("'");
		browseLogFileButton.setBounds(365, 200, 23, 23);

		final Group OtherTrainOptionGroup = new Group(shell, SWT.NONE);
		OtherTrainOptionGroup.setText("Other train options");
		OtherTrainOptionGroup.setBounds(606, 169, 180, 236);

		restartTrainingCheck = new Button(OtherTrainOptionGroup, SWT.CHECK);
		restartTrainingCheck.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) 
			{
				if(restartTrainingCheck.getSelection())
				{
					commandLineTagList.updteCommandLineTagList(1, "restart_training");
					createCommandLine();
				}
				else
				{
					//	Tolgo il riferimento al riavvio del training dalla command line
					commandLineTagList.updteCommandLineTagList(1, "clear_restart_training");
					createCommandLine();
				}
			}
		});
		restartTrainingCheck.setEnabled(false);
		restartTrainingCheck.setText("Restart training");
		restartTrainingCheck.setBounds(10, 25, 130, 22);

		saveRedundantInfoCheck = new Button(OtherTrainOptionGroup, SWT.CHECK);
		saveRedundantInfoCheck.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) 
			{
				if(saveRedundantInfoCheck.getSelection())
				{
					commandLineTagList.updteCommandLineTagList(1, "save_redundant_data");
					createCommandLine();
				}
				else
				{
					//	Tolgo il riferimento al salvataggio dei dati ridondanti dalla command line
					commandLineTagList.updteCommandLineTagList(1, "clear_save_redundant_data");
					createCommandLine();
				}
			}
		});
		saveRedundantInfoCheck.setEnabled(false);
		saveRedundantInfoCheck.setText("Save redundant data");
		saveRedundantInfoCheck.setBounds(10, 50, 160, 22);

		startTrainingButton = new Button(shell, SWT.NONE);
		startTrainingButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e)
			{
		//	Disabilito per prova: RIABILITARE!!!!!!!!!!!!!!!!!!!!!
	//	mainInterface.getIDSDataFromIdsWrapper().trainIDS();
				if(regularTrainingButton.getSelection())
				{
					threatTrainingButton.setEnabled(true);
					regularTrainingButton.setSelection(false);
					threatTrainingButton.setSelection(true);
					commandLineTagList.inizializeCommandLineTagList();
					resetInterfaceFields();
					//xmlSettingsPathText.setEnabled(true);
					//XMLIPText.setEnabled(true);
					//XMLNetMaskText.setEnabled(true);
					XMLBaseNameText.setEnabled(true);
			
					createCommandLine();
				}
				else
				{
					commandLineTagList.inizializeCommandLineTagList();
					resetInterfaceFields();
					startTrainingButton.setEnabled(false);
					if(canTest)
						startTestingButton.setEnabled(true);
					//	Inserire qui la command line per la fase di testing (oppure creare un'altra interfaccia)
				}
				
			}
		});
		startTrainingButton.setText("Start training");
		startTrainingButton.setBounds(50, 684, 160, 26);

		final Group commandlinePreviewGroup = new Group(shell, SWT.NONE);
		commandlinePreviewGroup.setText("Command line preview");
		commandlinePreviewGroup.setBounds(50, 594, 736, 80);

		CLPreviewText = new Text(commandlinePreviewGroup, SWT.WRAP | SWT.MULTI | SWT.BORDER);
		CLPreviewText.setBounds(10, 25, 716, 44);
		CLPreviewText.setEditable(false);

		final Group KindOfActionGroup = new Group(shell, SWT.NONE);
		KindOfActionGroup.setText("Kind of training");
		KindOfActionGroup.setBounds(50, 104, 736, 55);

		 @SuppressWarnings("unused")
		final IDSWrapperVisualInterface mainInterface; 
		 
		regularTrainingButton = new Button(KindOfActionGroup, SWT.RADIO);
		regularTrainingButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) 
			{
				xmlSettingsPathText.setEnabled(true);
				XMLIPText.setEnabled(true);
				XMLNetMaskText.setEnabled(true);
				XMLBaseNameText.setEnabled(true);
				bufferSizeText.setEnabled(true);
				selectXMLFileCheck.setSelection(false);
				commandLineTagList.updteCommandLineTagList(0, "first_train");
				createCommandLine();
			}
		});
		regularTrainingButton.setSelection(true);
		regularTrainingButton.setText("First train activity");
		regularTrainingButton.setBounds(125, 23, 163, 22);

		threatTrainingButton = new Button(KindOfActionGroup, SWT.RADIO);
		threatTrainingButton.setEnabled(false);
		threatTrainingButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) 
			{
				commandLineTagList.updteCommandLineTagList(0, "second_train");
				createCommandLine();
			}
		});
		threatTrainingButton.setBounds(460, 23, 157, 22);
		threatTrainingButton.setText("Second train activity");

		final Group trainOptionGroup = new Group(shell, SWT.NONE);
		trainOptionGroup.setText("Train options");
		trainOptionGroup.setBounds(50, 419, 332, 170);

		final Label bufferDimensionLabel = new Label(trainOptionGroup, SWT.NONE);
		bufferDimensionLabel.setText("Buffer size");
		bufferDimensionLabel.setBounds(10, 25, 67, 17);

		final Label savePeriodLabel = new Label(trainOptionGroup, SWT.NONE);
		savePeriodLabel.setText("Save period");
		savePeriodLabel.setBounds(10, 55, 76, 17);

		confirmBufferSizeButton = new Button(trainOptionGroup, SWT.CHECK);
		confirmBufferSizeButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) 
			{
				if(confirmBufferSizeButton.getSelection())
				{
					bufferSizeText.setEnabled(false);
					//	Creazione di un ArrayList di 2 posizioni
					ArrayList dataStruct = new ArrayList();
					dataStruct.add("packet_buffer_dimension");
					dataStruct.add(bufferSizeText.getText());
					commandLineTagList.updteCommandLineTagList(1, dataStruct);
					createCommandLine();
				}
				else
				{
					bufferSizeText.setEnabled(true);
					//	Creazione di un ArrayList di 2 posizioni
					ArrayList dataStruct = new ArrayList();
					dataStruct.add("clear_packet_buffer_dimension");
					dataStruct.add("");
					commandLineTagList.updteCommandLineTagList(1, dataStruct);
					createCommandLine();
				}
			}
		});
		confirmBufferSizeButton.setEnabled(false);
		confirmBufferSizeButton.setText("Confirm selection");
		confirmBufferSizeButton.setBounds(184, 23, 135, 22);

		savePeriodButton = new Button(trainOptionGroup, SWT.CHECK);
		savePeriodButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) 
			{
				if(savePeriodButton.getSelection())
				{
					saveFrequencyText.setEnabled(false);
					//	Creazione di un ArrayList di 2 posizioni
					ArrayList dataStruct = new ArrayList();
					dataStruct.add("save_frequency");
					dataStruct.add(saveFrequencyText.getText());
					commandLineTagList.updteCommandLineTagList(1, dataStruct);
					createCommandLine();
				}
				else
				{
					saveFrequencyText.setEnabled(true);
					//	Creazione di un ArrayList di 2 posizioni
					ArrayList dataStruct = new ArrayList();
					dataStruct.add("clear_save_frequency");
					dataStruct.add("");
					commandLineTagList.updteCommandLineTagList(1, dataStruct);
					createCommandLine();
				}
			}
		});
		savePeriodButton.setEnabled(false);
		savePeriodButton.setText("Confirm selection");
		savePeriodButton.setBounds(184, 53, 135, 22);

		final Label randomSeedLabel = new Label(trainOptionGroup, SWT.NONE);
		randomSeedLabel.setBounds(10, 85, 76, 17);
		randomSeedLabel.setText("Rnd seed");

		randomSeedCheck = new Button(trainOptionGroup, SWT.CHECK);
		randomSeedCheck.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) 
			{
				if(randomSeedCheck.getSelection())
				{
					randomSeedText.setEnabled(false);
					//	Creazione di un ArrayList di 2 posizioni
					ArrayList dataStruct = new ArrayList();
					dataStruct.add("random_seed");
					dataStruct.add(randomSeedText.getText());
					commandLineTagList.updteCommandLineTagList(1, dataStruct);
					createCommandLine();
				}
				else
				{
					sequentialRadio.setSelection(false);
					pseudocasualRadio.setSelection(false);
					commandLineTagList.updteCommandLineTagList(1, "clear_random_pseudocasual");
					commandLineTagList.updteCommandLineTagList(1, "clear_random_sequential");
					randomSeedText.setEnabled(true);
					//	Creazione di un ArrayList di 2 posizioni
					ArrayList dataStruct = new ArrayList();
					dataStruct.add("clear_random_seed");
					dataStruct.add("");
					commandLineTagList.updteCommandLineTagList(1, dataStruct);
					createCommandLine();
				}
			}
		});
		randomSeedCheck.setEnabled(false);
		randomSeedCheck.setBounds(184, 83, 135, 22);
		randomSeedCheck.setText("Confirm selection");

		sequentialRadio = new Button(trainOptionGroup, SWT.RADIO);
		sequentialRadio.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) 
			{
				if(randomSeedCheck.getSelection())
				{
					commandLineTagList.updteCommandLineTagList(1, "random_sequential");
					commandLineTagList.updteCommandLineTagList(1, "clear_random_pseudocasual");
					createCommandLine();
				}
				else
				{
					commandLineTagList.updteCommandLineTagList(1, "clear_random_sequential");
					createCommandLine();
				}
			}
		});
		sequentialRadio.setEnabled(false);
		sequentialRadio.setText("Sequential");
		sequentialRadio.setBounds(184, 111, 135, 22);

		pseudocasualRadio = new Button(trainOptionGroup, SWT.RADIO);
		pseudocasualRadio.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) 
			{
				if(pseudocasualRadio.getSelection())
				{
					commandLineTagList.updteCommandLineTagList(1, "random_pseudocasual");
					commandLineTagList.updteCommandLineTagList(1, "clear_random_sequential");
					createCommandLine();
				}
				else
				{
					commandLineTagList.updteCommandLineTagList(1, "clear_random_pseudocasual");
					createCommandLine();
				}
			}
		});
		pseudocasualRadio.setEnabled(false);
		pseudocasualRadio.setText("Pseudocasual");
		pseudocasualRadio.setBounds(184, 138, 135, 22);

		bufferSizeText = new Text(trainOptionGroup, SWT.BORDER);
		bufferSizeText.setEnabled(false);
		bufferSizeText.setBounds(98, 20, 70, 25);

		saveFrequencyText = new Text(trainOptionGroup, SWT.BORDER);
		saveFrequencyText.setEnabled(false);
		saveFrequencyText.setBounds(98, 50, 70, 25);

		randomSeedText = new Text(trainOptionGroup, SWT.BORDER);
		randomSeedText.setEnabled(false);
		randomSeedText.setBounds(98, 81, 70, 25);

		backToMainButton = new Button(shell, SWT.NONE);
		backToMainButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) 
			{
				shell.close();
			}
		});
		backToMainButton.setBounds(626, 684, 160, 26);
		backToMainButton.setText("Back to main interface");

		startTestingButton = new Button(shell, SWT.NONE);
		startTestingButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) 
			{
				//	Carico l'interfaccia di testing specifica per ogni IDS... 
				if( (canTest) && (ids.getName().equals("Ulisse")) )
				{
					TestInterfaceUlisseSS  testInterface= (TestInterfaceUlisseSS) ids.getTestingInterface(concatStr);
					testInterface.open(concatStr);
				}
			}
		});
		startTestingButton.setEnabled(false);
		startTestingButton.setBounds(342, 684, 160, 26);
		startTestingButton.setText("Start testing");

		final Group XMLSettingsGroup = new Group(shell, SWT.NONE);
		XMLSettingsGroup.setText("XML settings");
		XMLSettingsGroup.setBounds(388, 419, 398, 169);

		final Label XMLSelectionLabel = new Label(XMLSettingsGroup, SWT.NONE);
		XMLSelectionLabel.setBounds(10, 28,113, 17);
		XMLSelectionLabel.setText("XML settings path");

		xmlSettingsPathText = new Text(XMLSettingsGroup, SWT.BORDER);
		xmlSettingsPathText.setBounds(126, 26,240, 25);
		xmlSettingsPathText.setEnabled(false);

		selectXMLFileCheck = new Button(XMLSettingsGroup, SWT.CHECK);
		selectXMLFileCheck.setBounds(246, 142,144, 20);
		selectXMLFileCheck.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) 
			{
				if(selectXMLFileCheck.getSelection())
				{
					xmlSettingsPathText.setEnabled(false);
					XMLIPText.setEnabled(false);
					XMLNetMaskText.setEnabled(false);
					XMLBaseNameText.setEnabled(false);
					concatStr = "";
					if( (xmlSettingsPathText.getText().endsWith("/")) || (xmlSettingsPathText.getText().equals("")) )
						concatStr = xmlSettingsPathText.getText() + XMLBaseNameText.getText();
					else
						concatStr = xmlSettingsPathText.getText() + "/" + XMLBaseNameText.getText();
					commandLineTagList.updteCommandLineTagList(3, concatStr);
					
					if(regularTrainingButton.getSelection())
					{
						firstTrainBase = XMLBaseNameText.getText();
						
						if( (xmlSettingsPathText.getText().endsWith("/")) || (xmlSettingsPathText.getText().equals("")) )
							concatStrFirst = xmlSettingsPathText.getText() + firstTrainBase;
						else
							concatStrFirst = xmlSettingsPathText.getText() + "/" + firstTrainBase;
System.out.println("Il basename del primo pezzo è "+concatStrFirst);
					}
					
					if(threatTrainingButton.getSelection())
					{
						ids.changeTrainSettings(concatStr, concatStrFirst, XMLIPText.getText(), XMLNetMaskText.getText());
					}
					
					createCommandLine();
				}
				else
				{
					xmlSettingsPathText.setEnabled(true);
					XMLIPText.setEnabled(true);
					XMLNetMaskText.setEnabled(true);
					XMLBaseNameText.setEnabled(true);
					//	Tolgo il riferimento all'XML file dalla command line
					commandLineTagList.updteCommandLineTagList(3, "");
					createCommandLine();
				}
			}
		});
		selectXMLFileCheck.setEnabled(false);
		selectXMLFileCheck.setText("Confirm selection");

		final Label XMLIPLabel = new Label(XMLSettingsGroup, SWT.NONE);
		XMLIPLabel.setBounds(10, 57, 110, 17);
		XMLIPLabel.setText("Network Address");

		XMLIPText = new Text(XMLSettingsGroup, SWT.BORDER);
		XMLIPText.setEnabled(false);
		XMLIPText.setBounds(126, 55, 264, 25);

		final Label XMLNetMaskLabel = new Label(XMLSettingsGroup, SWT.NONE);
		XMLNetMaskLabel.setBounds(10, 86, 110, 17);
		XMLNetMaskLabel.setText("Network Mask");

		XMLNetMaskText = new Text(XMLSettingsGroup, SWT.BORDER);
		XMLNetMaskText.setEnabled(false);
		XMLNetMaskText.setBounds(126, 83, 264, 25);

		final Label XMLBaseNameLabel = new Label(XMLSettingsGroup, SWT.NONE);
		XMLBaseNameLabel.setBounds(10, 114, 110, 17);
		XMLBaseNameLabel.setText("Base Name");

		XMLBaseNameText = new Text(XMLSettingsGroup, SWT.BORDER);
		XMLBaseNameText.setEnabled(false);
		XMLBaseNameText.setBounds(126, 111, 264, 25);

		final Button browseXMLButton = new Button(XMLSettingsGroup, SWT.NONE);
		browseXMLButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				
				DirectoryDialog dd = new DirectoryDialog(shell, SWT.OPEN);
		        dd.setText("Select the folder");
		        dd.setFilterPath(xmlSettingsPathText.getText());
		        String selected = dd.open();
		        if(selected != null)
		        	xmlSettingsPathText.setText(selected);
				
			}
		});
		browseXMLButton.setBounds(365, 28, 23, 23);
		browseXMLButton.setText("'");
	
		//
		
		loadData();
	}
	
	
	public void loadData()
	{
		ids = mainInterface.getIDSDataFromIdsWrapper();
		String idsName = ids.getName();
		IDSNameAndVersionLabel.setText(idsName);
		commandLineTagList.inizializeCommandLineTagList();
		enableIDSFeatures();
	}
	
	private void enableIDSFeatures()
	{
		 CLFeatures = (ArrayList<String>) mainInterface.getIDS().getTrainingCommandLine().getCLFeatures();
		
		if(isFeatureInList("restart_training"))
			restartTrainingCheck.setEnabled(true);
		if(isFeatureInList("use_log_file"))
		{
			logFileText.setEnabled(true);
			insertLogFileCheck.setEnabled(true);
		}
		if(isFeatureInList("save_redundant_data"))
			saveRedundantInfoCheck.setEnabled(true);
		if(isFeatureInList("packet_buffer_dimension"))
		{
			bufferSizeText.setEnabled(true);
			confirmBufferSizeButton.setEnabled(true);
		}
		if(isFeatureInList("save_frequency"))
		{
			saveFrequencyText.setEnabled(true);
			savePeriodButton.setEnabled(true);				
		}
		if(isFeatureInList("random_seed"))
		{
			randomSeedText.setEnabled(true);
			randomSeedCheck.setEnabled(true);
			if(isFeatureInList("random_sequential"))
				sequentialRadio.setEnabled(true);
			if(isFeatureInList("random_pseudocasual"))
				pseudocasualRadio.setEnabled(true);
		}
		if(isFeatureInList("number_of_packets_to_use"))
			numberOfPacketsText.setEnabled(true);
		if(isFeatureInList("number_of_packets_to_skip"))
			packetsToSkipText.setEnabled(true);
		if(isFeatureInList("xml_settings_file"))
		{
			xmlSettingsPathText.setEnabled(true);
			selectXMLFileCheck.setEnabled(true);
		}
		if(isFeatureInList("xml_basename"))
		{
			XMLBaseNameText.setEnabled(true);
		}
		if(isFeatureInList("network_address"))
		{
			XMLIPText.setEnabled(true);
		}
		if(isFeatureInList("network_netmask"))
		{
			XMLNetMaskText.setEnabled(true);
		}
		if(isFeatureInList("testing_capability"))
		{
			canTest = true;
		}
	}
	
	private String createCommandLine()
	{
		if(regularTrainingButton.getSelection())
			commandLineTagList.updteCommandLineTagList(0, "first_train");
		else
			commandLineTagList.updteCommandLineTagList(0, "second_train");
		mainInterface.getIDS().getTrainingCommandLine().updateCommandLine(commandLineTagList.getTagList());
		CLPreviewText.setText(mainInterface.getIDS().getTrainingCommandLine().getTextualCL());
		return mainInterface.getIDS().getTrainingCommandLine().getTextualCL();
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
	
	private void resetInterfaceFields()
	{
		datasetPathText.setText("");
		numberOfPacketsText.setText("");
		packetsToSkipText.setText("");
		packetStruct = new ArrayList();
		datasetList.removeAll();
		logFileText.setText("");
		bufferSizeText.setText("");
		saveFrequencyText.setText("");
		randomSeedText.setText("");
		XMLBaseNameText.setText("");
		confirmDatasetCheck.setSelection(false);
		selectXMLFileCheck.setSelection(false);
		insertLogFileCheck.setSelection(false);
		confirmBufferSizeButton.setSelection(false);
		savePeriodButton.setSelection(false);
		randomSeedCheck.setSelection(false);
		restartTrainingCheck.setSelection(false);
		saveRedundantInfoCheck.setSelection(false);
		sequentialRadio.setSelection(false);
		pseudocasualRadio.setSelection(false);	
	}

}
