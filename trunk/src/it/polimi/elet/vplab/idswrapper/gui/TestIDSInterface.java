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

import javax.swing.JFrame;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import it.polimi.elet.vplab.idswrapper.testing.*;

import com.swtdesigner.SWTResourceManager;

public class TestIDSInterface {
	
	private IDSWrapperVisualInterface mainWindow;
	private Text attacksNumber;
	private Text alertPath;
	private Text truthPath;
	private Text idmefFolderText;
	private boolean flag1 = false;
	private boolean flag2 = false;
	protected Shell shell;


	/**
	 * Open the window
	 */
	public void open(IDSWrapperVisualInterface window) {
		
		this.mainWindow = window;
		
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
		shell.setSize(500, 500);
		shell.setText("IDSWrapper - IDS Testing");

		final Label idsTestingLabel = new Label(shell, SWT.NONE);
		idsTestingLabel.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_DARK_BLUE));
		idsTestingLabel.setFont(SWTResourceManager.getFont("Sans", 14, SWT.NONE));
		idsTestingLabel.setText("IDS Testing");
		idsTestingLabel.setBounds(180, 10, 104, 23);

		final Label descriptionLabel1 = new Label(shell, SWT.NONE);
		descriptionLabel1.setText("The testing is realized comparing the alerts raised from the IDS and a truth f");
		descriptionLabel1.setBounds(10, 50, 478, 23);

		final Label descriptionLabel2 = new Label(shell, SWT.NONE);
		descriptionLabel2.setText("file containing the real attacks that the traffic file contains.");
		descriptionLabel2.setBounds(10, 75, 478, 17);

		final Label descriptionLabel3 = new Label(shell, SWT.NONE);
		descriptionLabel3.setText("The comparing is performed between alerts and truths in IDMEF format.");
		descriptionLabel3.setBounds(10, 98, 478, 17);
		
		final Label descriptionLabel4 = new Label(shell, SWT.NONE);
		descriptionLabel4.setText("The results of the test is showed through a ROC curve.");
		descriptionLabel4.setBounds(10, 121, 478, 17);
		
		final Group settingsGroup = new Group(shell, SWT.NONE);
		settingsGroup.setText("Settings");
		settingsGroup.setBounds(10, 144, 478, 281);

		final Label selectFolderLabel = new Label(settingsGroup, SWT.NONE);
		selectFolderLabel.setText("Select the folder where to save the files in IDMEF format:");
		selectFolderLabel.setBounds(10, 30, 458, 17);

		idmefFolderText = new Text(settingsGroup, SWT.BORDER);
		idmefFolderText.setBounds(10, 53, 260, 25);

		final Label truthFilePathLabel = new Label(settingsGroup, SWT.NONE);
		truthFilePathLabel.setText("Truth file path:");
		truthFilePathLabel.setBounds(10, 100, 100, 17);

		truthPath = new Text(settingsGroup, SWT.BORDER);
		truthPath.setEnabled(false);
		truthPath.setBounds(10, 123, 250, 25);
		
		attacksNumber = new Text(settingsGroup, SWT.BORDER);
		attacksNumber.setEnabled(false);
		attacksNumber.setBounds(266, 123, 90, 25);

		final Label numberOfAttacksLabel = new Label(settingsGroup, SWT.NONE);
		numberOfAttacksLabel.setText("Nr. of Attacks:");
		numberOfAttacksLabel.setBounds(266, 100, 90, 17);
		
		final Label alertFilePathLabel = new Label(settingsGroup, SWT.NONE);
		alertFilePathLabel.setText("Alert file path:");
		alertFilePathLabel.setBounds(10, 180, 100, 17);

		alertPath = new Text(settingsGroup, SWT.BORDER);
		alertPath.setEnabled(false);
		alertPath.setBounds(10, 200, 346, 25);

		final Button convertButton1 = new Button(settingsGroup, SWT.NONE);
		convertButton1.setAlignment(SWT.UP);
		convertButton1.setText("Convert");
		convertButton1.setBounds(360, 160, 70, 25);

		final Button convertButton2 = new Button(settingsGroup, SWT.NONE);
		convertButton2.setAlignment(SWT.UP);
		convertButton2.setText("Convert");
		convertButton2.setBounds(360, 235, 70, 25);

		final Button selectButton = new Button(settingsGroup, SWT.CHECK);
		selectButton.setText("select");
		selectButton.setBounds(362, 53, 59, 25);

		final Button testButton = new Button(shell, SWT.NONE);
		testButton.setText("Test");
		testButton.setBounds(180, 431, 104, 29);
		testButton.setEnabled(false);
		
		selectButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				
				if(selectButton.getSelection()) {
					
					if((idmefFolderText.getText().compareTo("") == 0) ||
							(idmefFolderText.getText() == null)) {
						
						selectButton.setSelection(false);
					
					}
					
					else if (idmefFolderText.getText() != null) {
						
						idmefFolderText.setEditable(false);
						truthPath.setEnabled(true);
						attacksNumber.setEnabled(true);
						alertPath.setEnabled(true);
						
					}
				
				}
				
			}
		});
		
		convertButton1.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				
				if (truthPath.getText() != null && attacksNumber.getText() != null 
						&& selectButton.getSelection()) {
					
					int numAttacks = Integer.parseInt(attacksNumber.getText());
					
					TruthConverter truth = new TruthConverter(mainWindow.idsWrapper, 
							truthPath.getText(),idmefFolderText.getText(), null, numAttacks );
					
					truth.convert();
					
					flag1 = true;
					
					if(selectButton.getSelection() && flag1 && flag2)
						
						testButton.setEnabled(true);
					
				}
				
			}
		});
		
		convertButton2.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				
				if(alertPath.getText() != null && selectButton.getSelection()) {
					
					AlertConverter alert = new AlertConverter(alertPath.getText(), 
							idmefFolderText.getText());
					
					alert.convert();
					
					flag2 = true;
					
					if(selectButton.getSelection() && flag1 && flag2)
						
						testButton.setEnabled(true);
					
				}
				
				
			}
		});

		final Button browseTruthFileButton = new Button(settingsGroup, SWT.NONE);
		browseTruthFileButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				
				FileDialog fd = new FileDialog(shell, SWT.OPEN);
		        fd.setText("Select the Truth File");
		        String selected = fd.open();
		        if(selected != null)
		        	truthPath.setText(selected);
				
			}
		});
		browseTruthFileButton.setText("Browse");
		browseTruthFileButton.setBounds(362, 123, 68, 25);

		final Button browseAlertFileButton = new Button(settingsGroup, SWT.NONE);
		browseAlertFileButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				
				FileDialog fd = new FileDialog(shell, SWT.OPEN);
		        fd.setText("Select the Rules File");
		        String selected = fd.open();
		        if(selected != null)
		        	alertPath.setText(selected);
				
			}
		});
		browseAlertFileButton.setText("Browse");
		browseAlertFileButton.setBounds(362, 200, 68, 25);

		final Button browseIdmefFolderButton = new Button(settingsGroup, SWT.NONE);
		browseIdmefFolderButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				
				DirectoryDialog dd = new DirectoryDialog(shell, SWT.OPEN);
		        dd.setText("Select the IDMEF folder");
		        dd.setFilterPath(idmefFolderText.getText());
		        String selected = dd.open();
		        if(selected != null)
		        	idmefFolderText.setText(selected);
				
			}
		});
		browseIdmefFolderButton.setText("Browse");
		browseIdmefFolderButton.setBounds(276, 53, 80, 25);

		testButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				
				String idmefFolder = idmefFolderText.getText();
				int attacksConsidered = Integer.parseInt(attacksNumber.getText());
				
				IDMEFAnalyzer analyzer = new IDMEFAnalyzer();
				analyzer.setTruthFile(idmefFolder+"/truth.xml");
				analyzer.setAlertFile(idmefFolder+"/alert.xml");
				analyzer.setNumSeq(attacksConsidered);
				
				analyzer.compare();
								
				double[][] series = new double[2][2];
				series[0][0] = 0;
				series[1][0] = 0;
				series[0][1] = (double)analyzer.getTPR();
				series[1][1] = (double)analyzer.getFPR();
				sort(series);
				
				Chart chart = new Chart("Test results");
				chart.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				chart.addSeries("ROC curve", series);
				chart.draw();
				
			}
		});
		
		//
	}
	
	private void sort(double[][] series) {
		double temp;
		for (int i = 1; i < series[0].length - 1; i++) {
			for (int j = i + 1; j < series[0].length; j++) {
				if (series[0][i] > series[0][j]) {
					temp = series[0][j];
					series[0][j] = series[0][i];
					series[0][i] = temp;
					
					temp = series[1][j];
					series[1][j] = series[1][i];
					series[1][i] = temp;
				}
			}
		}
	}

}
