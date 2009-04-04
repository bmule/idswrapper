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
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;

import it.polimi.elet.vplab.idswrapper.ids.IDS;
import it.polimi.elet.vplab.idswrapper.ids.OutputHandler;
import it.polimi.elet.vplab.idswrapper.concrete.IDMEFHandler;

import com.swtdesigner.SWTResourceManager;

public class OutputTypeSelection {

	private Label labelSelectionResponse;
	private Label selectedIDSLabel;
	private List list;
	private Combo comboOutputTypes;
	protected Shell shell;
	private boolean selectionDone = false;
	private IDSWrapperVisualInterface mainInterface;

	/**
	 * Launch the application
	 * @param args
	 */
	/*public static void main(String[] args) {
		try {
			OutputTypeSelection window = new OutputTypeSelection();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/


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
		final Display display = Display.getDefault();
		createContents();
		shell.open();
		loadInitialData();
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
		shell.setText("IDSWrapper - Output types");

		final Label selectOutputTypesLabel = new Label(shell, SWT.NONE);
		selectOutputTypesLabel.setBounds(50, 44, 550, 35);
		selectOutputTypesLabel.setForeground(SWTResourceManager.getColor(9, 9, 109));
		selectOutputTypesLabel.setFont(SWTResourceManager.getFont("Sans", 14, SWT.BOLD));
		selectOutputTypesLabel.setAlignment(SWT.CENTER);
		selectOutputTypesLabel.setText("Select output types");

		final Label selectOneOrLabel = new Label(shell, SWT.NONE);
		selectOneOrLabel.setBounds(50, 173, 259, 27);
		selectOneOrLabel.setForeground(SWTResourceManager.getColor(0, 0, 255));
		selectOneOrLabel.setFont(SWTResourceManager.getFont("Sans", 12, SWT.NONE));
		selectOneOrLabel.setText("Select one or more output types");
		
		final Button confirmButton = new Button(shell, SWT.NONE);
		confirmButton.setEnabled(false);
		confirmButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) 
			{
				mainInterface.readOutputHandlerFromInterface(createOutputHandlerInstance());
				selectionDone = true;
				mainInterface.reloadLabelsAndButtons();
				shell.close();
				/*
				 * TODO: salvare le impostazioni all'interno di un'istanza dell'opportuno OutputHandler
				 * */
			}
		});
		confirmButton.setBounds(50, 411, 80, 29);
		confirmButton.setText("Confirm");

		final Button addButton = new Button(shell, SWT.NONE);
		addButton.setEnabled(false);
		addButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) 
			{
				//comboInputTypes
				if(!typeAlreadySelected(comboOutputTypes.getItem(comboOutputTypes.getSelectionIndex())))
					{
					list.add(comboOutputTypes.getItem(comboOutputTypes.getSelectionIndex()));
					labelSelectionResponse.setText("Type added");
					labelSelectionResponse.setForeground(SWTResourceManager.getColor(19, 139, 11));
					confirmButton.setEnabled(true);
					}
				else
				{
					labelSelectionResponse.setText("Type is already in list");
					labelSelectionResponse.setForeground(SWTResourceManager.getColor(255, 0, 0));
				}
			}
		});
		addButton.setBounds(520, 172, 80, 27);
		addButton.setText("Add type");
		
		comboOutputTypes = new Combo(shell, SWT.NONE);
		comboOutputTypes.select(0);
		comboOutputTypes.setItems(new String[] {"IDMEF"});
		comboOutputTypes.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) 
			{
				if(comboOutputTypes.getSelectionIndex()>=0)
					addButton.setEnabled(true);
			}
		});
		comboOutputTypes.setBounds(315, 171, 168, 27);

		final ListViewer listViewer = new ListViewer(shell, SWT.BORDER);
		list = listViewer.getList();
		list.setBounds(50, 264, 246, 100);

		final Label outputTypesLabel = new Label(shell, SWT.NONE);
		outputTypesLabel.setBounds(50, 241, 200, 17);
		outputTypesLabel.setForeground(SWTResourceManager.getColor(0, 0, 255));
		outputTypesLabel.setFont(SWTResourceManager.getFont("Sans", 12, SWT.NONE));
		outputTypesLabel.setText("Selected output types");

		final Label selectedIdsLabel = new Label(shell, SWT.NONE);
		selectedIdsLabel.setBounds(50, 121, 108, 20);
		selectedIdsLabel.setForeground(SWTResourceManager.getColor(0, 0, 255));
		selectedIdsLabel.setFont(SWTResourceManager.getFont("Sans", 12, SWT.NONE));
		selectedIdsLabel.setText("Selected IDS:");

		final Button removeElementsButton = new Button(shell, SWT.NONE);
		removeElementsButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) 
			{
				list.removeAll();
				confirmButton.setEnabled(false);
			}
		});
		removeElementsButton.setBounds(315, 333, 130, 29);
		removeElementsButton.setText("Remove elements");

		selectedIDSLabel = new Label(shell, SWT.NONE);
		selectedIDSLabel.setBounds(179, 121, 130, 20);
		selectedIDSLabel.setForeground(SWTResourceManager.getColor(6, 6, 71));
		selectedIDSLabel.setFont(SWTResourceManager.getFont("Sans", 12, SWT.NONE));

		labelSelectionResponse = new Label(shell, SWT.NONE);
		labelSelectionResponse.setBounds(315, 206, 168, 27);
		labelSelectionResponse.setFont(SWTResourceManager.getFont("Sans", 12, SWT.NONE));
		//
		
	}
	
	private void loadInitialData()
	{
		IDS ids = this.mainInterface.getIDSDataFromIdsWrapper();
		
		selectedIDSLabel.setText(ids.getName() + "   ["+ids.getVersion()+"]");
		/*for(int k=0; k < (ids.getOutputFormat()).size();k++)
		{
			comboOutputTypes.add(ids.getOutputFormat().get(k));
		}*/
	}
	
	private boolean typeAlreadySelected(String strType)
	{
		String[] strList = list.getItems();
		for(int k=0;k<strList.length;k++)
		{
			if(strList[k].equals(strType))
				return true;
		}
		return false;
	}
	
//	Qui andranno inserite le istanziazioni delle classi di tutti i nuovi OutputHandler aggiunti
	@SuppressWarnings("unchecked")
	private ArrayList<OutputHandler> createOutputHandlerInstance()
	{
		String[] strList = list.getItems();
		ArrayList<OutputHandler> alist = new ArrayList();
		
		for(int i=0;i<strList.length;i++)
		{
//	System.out.println("ITEM NELLA LISTA: "+strList[i]);
			if(strList[i].equals("IDMEF"))
			{
				OutputHandler outHandler = null;
				outHandler = new IDMEFHandler();
				alist.add(outHandler);
				
			}
			else if(strList[i].equals("..."))
			{
				
			}
		}
		return alist;
	}
	
}
