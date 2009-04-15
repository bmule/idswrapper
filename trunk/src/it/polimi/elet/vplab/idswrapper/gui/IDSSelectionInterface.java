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

import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import it.polimi.elet.vplab.idswrapper.concrete.IDSCreator;
import it.polimi.elet.vplab.idswrapper.filemanager.FileManager;

import com.swtdesigner.SWTResourceManager;

public class IDSSelectionInterface{

	private List list;
	private Text textIDSPath;
	private Combo comboIDSSelection;
	protected Shell shell;
	private boolean selectionDone = false;
	private IDSWrapperVisualInterface mainInterface;
	Label fileAccessReportLabel; 
	private FileManager fmanager = new FileManager();
	boolean fileLoaded = false;
	ListViewer listViewer;
	Button confirmButton;
	@SuppressWarnings("unchecked")
	ArrayList alist;
	int selectedIndex = 0;
	String IDSpath = "";
	

	/**
	 * Launch the application
	 * @param args
	 */

	public boolean isSelectionDone() {
		return selectionDone;
	}

	public void setSelectionDone(boolean selectionDone) {
		this.selectionDone = selectionDone;
	}

	/**
	 * Open the window
	 */
	public void open(IDSWrapperVisualInterface iwvi) {
		mainInterface = iwvi;
		
		Display display = Display.getDefault();
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
		shell.setSize(650, 470);
		shell.setText("IDSWrapper - IDS");

		final Label idsSelectionAndLabel = new Label(shell, SWT.NONE);
		idsSelectionAndLabel.setForeground(SWTResourceManager.getColor(6, 6, 128));
		idsSelectionAndLabel.setAlignment(SWT.CENTER);
		idsSelectionAndLabel.setFont(SWTResourceManager.getFont("Sans", 14, SWT.BOLD));
		idsSelectionAndLabel.setText("IDS selection");
		idsSelectionAndLabel.setBounds(50, 13, 550, 35);

		final Label selectIdsLabel = new Label(shell, SWT.NONE);
		selectIdsLabel.setFont(SWTResourceManager.getFont("Sans", 12, SWT.NONE));
		selectIdsLabel.setForeground(SWTResourceManager.getColor(0, 0, 255));
		selectIdsLabel.setText("Select IDS");
		selectIdsLabel.setBounds(50, 84, 110, 20);

		comboIDSSelection = new Combo(shell, SWT.NONE);
		comboIDSSelection.setBounds(170, 77, 187, 27);

		final Label selectPathLabel = new Label(shell, SWT.NONE);
		selectPathLabel.setForeground(SWTResourceManager.getColor(0, 0, 255));
		selectPathLabel.setFont(SWTResourceManager.getFont("Sans", 12, SWT.NONE));
		selectPathLabel.setText("Select path");
		selectPathLabel.setBounds(50, 141, 110, 20);

		textIDSPath = new Text(shell, SWT.BORDER);
		textIDSPath.setBounds(170, 136, 250, 25);

		final Label label = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setBounds(11, 173, 610, 17);

		final Label reportLabel = new Label(shell, SWT.NONE);
		reportLabel.setForeground(SWTResourceManager.getColor(0, 0, 255));
		reportLabel.setFont(SWTResourceManager.getFont("Sans", 12, SWT.NONE));
		reportLabel.setText("IDS property");
		reportLabel.setBounds(50, 200, 110, 20);

		confirmButton = new Button(shell, SWT.NONE);
		confirmButton.setEnabled(false);
		confirmButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) 
			{
				//	set of the ids-end-selection variable
				selectionDone = true;
				mainInterface.reloadLabelsAndButtons();
				shell.close();
				
				//	call of the IDS creatore method
				IDSCreator idsCreator = new IDSCreator();
				mainInterface.readIDSFromInterface(idsCreator.createIDSIstance(alist, selectedIndex, IDSpath));
				
			}
		});
		confirmButton.setText("Done");
		confirmButton.setBounds(50, 415, 80, 29);

		final Button okButton = new Button(shell, SWT.NONE);
		okButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) 
			{
				if((comboIDSSelection.getSelectionIndex() >= 0) && ((textIDSPath.getText()).length()>0))
				{
					IDSpath = textIDSPath.getText();
					list.removeAll();
					confirmButton.setEnabled(true);
					selectedIndex = comboIDSSelection.getSelectionIndex();
					writeReport();
				}
			}
		});
		okButton.setText("Confirm");
		okButton.setBounds(530, 132, 80, 29);
		
		listViewer = new ListViewer(shell, SWT.BORDER);
		list = listViewer.getList();
		list.setBounds(50, 235, 445, 160);

		fileAccessReportLabel = new Label(shell, SWT.NONE);
		fileAccessReportLabel.setBounds(400, 81, 200, 20);

		final Button browseButton = new Button(shell, SWT.NONE);
		browseButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				
				DirectoryDialog dlg = new DirectoryDialog(shell, SWT.OPEN);
				dlg.setFilterPath("/");
				dlg.setText("IDS path");
				dlg.setMessage("Select a directory");
				String dir = dlg.open();
				if (dir != null)
					textIDSPath.setText(dir);
				
			}
		});
		browseButton.setText("Browse");
		browseButton.setBounds(426, 132, 70, 29);
		
		//	call of the content update method
		loadData();
	}
	
	@SuppressWarnings("unchecked")
	private boolean loadData()
	{
		alist = ArrayList.class.cast(fmanager.readIDSFile());
		if(alist.size() > 0)
			{
				fileAccessReportLabel.setForeground(SWTResourceManager.getColor(19, 139, 11));
				fileAccessReportLabel.setText("IDS.txt file has been loaded");
				fileLoaded = true;

				for(int k=0;k<alist.size();k++)
				{
					comboIDSSelection.add((((ArrayList)(alist.get(k))).get(0)).toString());
				}
			}
		else
			{
				fileAccessReportLabel.setForeground(SWTResourceManager.getColor(255, 0, 0));
				fileAccessReportLabel.setText("Error: cannot find IDS.txt file");	
			}
		
		return fileLoaded;
	}
	
	@SuppressWarnings("unchecked")
	private void writeReport()
	{
			list.add("Name: "+(((ArrayList)(alist.get(selectedIndex))).get(0)).toString()+"     Version: "+(((ArrayList)(alist.get(selectedIndex))).get(1)).toString());
			list.add("Type: "+(((ArrayList)(alist.get(selectedIndex))).get(2)).toString());
			list.add("Paradigm: "+(((ArrayList)(alist.get(selectedIndex))).get(3)).toString());
			list.add("File system path: "+textIDSPath.getText());
			list.add("Supported input format: "+(((ArrayList)(alist.get(selectedIndex))).get(4)).toString());
			list.add("Supported output format: "+(((ArrayList)(alist.get(selectedIndex))).get(5)).toString());
	}
	

}
