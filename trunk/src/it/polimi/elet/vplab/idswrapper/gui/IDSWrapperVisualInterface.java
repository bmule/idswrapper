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

import it.polimi.elet.vplab.idswrapper.concrete.*;
import it.polimi.elet.vplab.idswrapper.ids.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.swtdesigner.SWTResourceManager;

import java.util.ArrayList;

public class IDSWrapperVisualInterface extends Thread {

	protected Shell shell;
	static IDSWrapperVisualInterface window;
	IDSSelectionInterface IDSSelection;
	InputTypeSelection inputSelection;
	OutputTypeSelection outputSelection;
	AnalysisInterfaceNetwork analysisInterfaceNet;
	AnalysisInterfaceHost analysisInterfaceHost;
	TrainInterfaceNetwork trainingInterfaceNet;
	TrainInterfaceHost trainingInterfaceHost;
	RulesManagerInterface ruleManagerInterface;
	Button selectIdsButton;
	Label pressButtonToLabel;
	Button selectInputTypesButton;
	Label selectInputTypeLabel;
	Button selectOutputTypesButton;
	Button idsConfigurationButton;
	Button analysisConfigurationButton;
	Button trainTheIdsButton;
	Label selectOutputTypeLabel;
	IDSWrapper idsWrapper;
	IDS selectedIds = null;
	//	the following variable will be set to true if the ids and in&output types selection is terminated
	boolean selectionFinished = false;
		
	/**
	 * Launch the application
	 * @param args
	 */
	public void startInterface(IDSWrapper idsW)
	{
		this.idsWrapper = idsW;
		try {
			window = this.idsWrapper.getVisualInterface();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window
	 */
	public void open() {
		//	create the objects related by the others interfaces
		IDSSelection = new IDSSelectionInterface();
		inputSelection = new InputTypeSelection();
		outputSelection = new OutputTypeSelection();
		
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
		shell.setSize(650, 450);
		shell.setText("IDSWrapper - Main Interface");

		final Label idwwrapperVisualInterfaceLabel = new Label(shell, SWT.NONE);
		idwwrapperVisualInterfaceLabel.setForeground(SWTResourceManager.getColor(22, 22, 145));
		idwwrapperVisualInterfaceLabel.setFont(SWTResourceManager.getFont("Sans", 14, SWT.NONE));
		idwwrapperVisualInterfaceLabel.setAlignment(SWT.CENTER);
		idwwrapperVisualInterfaceLabel.setText("IDSWrapper Visual Interface");
		idwwrapperVisualInterfaceLabel.setBounds(50, 13, 550, 35);

		selectIdsButton= new Button(shell, SWT.NONE);
		selectIdsButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) 
			{
				IDSSelection.open(window);
				selectInputTypesButton.setEnabled(true);
			}
		});
		selectIdsButton.setText("Select IDS");
		selectIdsButton.setBounds(10, 80, 135, 30);

		selectInputTypeLabel = new Label(shell, SWT.NONE);
		selectInputTypeLabel.setForeground(SWTResourceManager.getColor(255, 0, 0));
		selectInputTypeLabel.setText("Press button to select input types");
		selectInputTypeLabel.setBounds(190, 135, 270, 15);

		selectOutputTypeLabel = new Label(shell, SWT.NONE);
		selectOutputTypeLabel.setForeground(SWTResourceManager.getColor(255, 0, 0));
		selectOutputTypeLabel.setText("Press button to select output types");
		selectOutputTypeLabel.setBounds(191, 185, 270, 15);

		pressButtonToLabel = new Label(shell, SWT.NONE);
		pressButtonToLabel.setForeground(SWTResourceManager.getColor(255, 0, 0));
		pressButtonToLabel.setText("Press button to select and configure IDS");
		pressButtonToLabel.setBounds(190, 85, 270, 15);

		selectInputTypesButton = new Button(shell, SWT.NONE);
		selectInputTypesButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) 
			{
				inputSelection.open(window);
				//	enable the output selection button
				selectOutputTypesButton.setEnabled(true);
			}
		});
		selectInputTypesButton.setText("Select input types");
		selectInputTypesButton.setBounds(10, 130, 135, 30);
		selectInputTypesButton.setEnabled(false);

		selectOutputTypesButton = new Button(shell, SWT.NONE);
		selectOutputTypesButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) 
			{
				outputSelection.open(window);
			}
		});
		selectOutputTypesButton.setText("Select output types");
		selectOutputTypesButton.setBounds(10, 180, 135, 30);
		selectOutputTypesButton.setEnabled(false);

		idsConfigurationButton = new Button(shell, SWT.NONE);
		idsConfigurationButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) 
			{
				ruleManagerInterface = new RulesManagerInterface();
				ruleManagerInterface.open(window);
			}
		});
		idsConfigurationButton.setEnabled(false);
		idsConfigurationButton.setText("IDS Configuration");
		idsConfigurationButton.setBounds(25, 295, 155, 29);

		analysisConfigurationButton = new Button(shell, SWT.NONE);
		analysisConfigurationButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) 
			{
				//	Check the selected ids type and create the correspondent visual interfaces
				if((idsWrapper.getIDS().getType()).equals("network"))
				{
					analysisInterfaceNet = new AnalysisInterfaceNetwork();
					analysisInterfaceNet.open(window);
				}
				else
				{
					analysisInterfaceHost = new AnalysisInterfaceHost();
				}
			}
		});
		analysisConfigurationButton.setEnabled(false);
		analysisConfigurationButton.setText("Analysis Configuration");
		analysisConfigurationButton.setBounds(246, 295, 155, 29);

		trainTheIdsButton = new Button(shell, SWT.NONE);
		trainTheIdsButton.setEnabled(false);
		trainTheIdsButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) 
			{
				//	Check the selected ids type and create the correspondent visual interfaces
				if((idsWrapper.getIDS().getType()).equals("network"))
				{
					trainingInterfaceNet = new TrainInterfaceNetwork();
					trainingInterfaceNet.open(window);
				}
				else
				{
					trainingInterfaceHost = new TrainInterfaceHost();
				}
			}
		});
		trainTheIdsButton.setText("Train the IDS");
		trainTheIdsButton.setBounds(470, 295, 155, 29);
		//
	}
	
	public void reloadLabelsAndButtons()
	{
		if(IDSSelection.isSelectionDone())
		{			
			selectIdsButton.setEnabled(false);
			pressButtonToLabel.setForeground(SWTResourceManager.getColor(19, 139, 11));
			pressButtonToLabel.setText("IDS correctly selected and configured");
			
		}
		
		if(inputSelection.isSelectionDone())
		{			
			selectInputTypesButton.setEnabled(false);
			selectInputTypeLabel.setForeground(SWTResourceManager.getColor(19, 139, 11));
			selectInputTypeLabel.setText("Input types selected");
			
		}
		
		if(outputSelection.isSelectionDone())
		{			
			selectOutputTypesButton.setEnabled(false);
			selectOutputTypeLabel.setForeground(SWTResourceManager.getColor(19, 139, 11));
			selectOutputTypeLabel.setText("Output types selected");
			
			//	enable the configuration buttons
			idsConfigurationButton.setEnabled(true);
			analysisConfigurationButton.setEnabled(true);
			//	enable the training button only if the selected ids type is "anomaly based"
			if(selectedIds.getIdsParadigm().equals("anomaly"))
				trainTheIdsButton.setEnabled(true);
		}
	}

	public void readIDSFromInterface(IDS selectedIds)
	{
		this.selectedIds = selectedIds;
		idsWrapper.selectIDS(this.selectedIds);
	}
	
	@SuppressWarnings("unchecked")
	public void readInputHandlerFromInterface(ArrayList inHandlerList)
	{
		for(int i=0;i<inHandlerList.size();i++)
		{
			idsWrapper.selectInputType(InputHandler.class.cast(inHandlerList.get(i)));
		}
	}
	
	@SuppressWarnings("unchecked")
	public void readOutputHandlerFromInterface(ArrayList outHandlerList)
	{
		for(int i=0;i<outHandlerList.size();i++)
		{
			idsWrapper.selectOutputType(OutputHandler.class.cast(outHandlerList.get(i)));
		}
	}
	
	public IDS getIDS()
	{
		return this.selectedIds;
	}
	
	public IDS getIDSDataFromIdsWrapper()
	{
		return this.idsWrapper.getIDS();
	}

}
