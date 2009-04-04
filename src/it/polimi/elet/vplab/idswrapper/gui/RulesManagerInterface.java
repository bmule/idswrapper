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

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import it.polimi.elet.vplab.idswrapper.filemanager.FileManager;

import com.swtdesigner.SWTResourceManager;

public class RulesManagerInterface {

	private Table table;
	private Text ruleText;
	private Text rulesFilePathText;
	private Label IDSNameAndVersionLabel;
	protected Shell shell;
	private Label responseLabel;
	private TableViewer tableViewer;
	private Group rulesBrowserGroup;
	private Label kindOfActionLabel;
	private Button reloadDataButton;
	private Button saveRuleButton;
	private Button deleteRuleButton;
	private Button clearTextFieldButton;
	
	
	IDSWrapperVisualInterface mainInterface;
	@SuppressWarnings("unchecked")
	ArrayList rulesList;
	String workMode = "";


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
	protected void createContents() 
	{
		shell = new Shell();
		shell.setSize(650, 750);
		shell.setText("IDSWrapper - Rules manager");

		final Label rulesManagerLabel = new Label(shell, SWT.NONE);
		rulesManagerLabel.setForeground(SWTResourceManager.getColor(22, 22, 145));
		rulesManagerLabel.setAlignment(SWT.CENTER);
		rulesManagerLabel.setFont(SWTResourceManager.getFont("Sans", 14, SWT.NONE));
		rulesManagerLabel.setText("Rules manager control panel");
		rulesManagerLabel.setBounds(50, 13, 550, 35);

		final Label IDSLabel = new Label(shell, SWT.NONE);
		IDSLabel.setBounds(50, 55, 45, 18);
		IDSLabel.setForeground(SWTResourceManager.getColor(0, 0, 255));
		IDSLabel.setFont(SWTResourceManager.getFont("Sans", 12, SWT.NONE));
		IDSLabel.setText("IDS: ");

		IDSNameAndVersionLabel = new Label(shell, SWT.NONE);
		IDSNameAndVersionLabel.setBounds(101, 55, 145, 18);
		IDSNameAndVersionLabel.setForeground(SWTResourceManager.getColor(19, 139, 11));
		IDSNameAndVersionLabel.setFont(SWTResourceManager.getFont("Sans", 12, SWT.NONE));

		final Group rulesFileSelectionGroup = new Group(shell, SWT.NONE);
		rulesFileSelectionGroup.setText("Rules file selection");
		rulesFileSelectionGroup.setBounds(50, 90, 550, 104);

		final Label trainDatasetPathLabel = new Label(rulesFileSelectionGroup, SWT.NONE);
		trainDatasetPathLabel.setBounds(10, 32, 120, 17);
		trainDatasetPathLabel.setText("Rules file path");

		rulesFilePathText = new Text(rulesFileSelectionGroup, SWT.BORDER);
		rulesFilePathText.setBounds(130, 28, 201, 25);

		final Button loadRulesFileButton = new Button(rulesFileSelectionGroup, SWT.NONE);
		loadRulesFileButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) 
			{
				updateInterface();
			}
		});
		loadRulesFileButton.setText("Load file");
		loadRulesFileButton.setBounds(450, 27, 76, 25);

		final Label responseLabelStatic = new Label(rulesFileSelectionGroup, SWT.NONE);
		responseLabelStatic.setText("Response");
		responseLabelStatic.setBounds(10, 72, 76, 17);

		responseLabel = new Label(rulesFileSelectionGroup, SWT.NONE);
		responseLabel.setBounds(96, 72, 334, 17);

		final Button browseButton = new Button(rulesFileSelectionGroup, SWT.NONE);
		browseButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				
				FileDialog fd = new FileDialog(shell, SWT.OPEN);
		        fd.setText("Select the Rule File");
		        fd.setFilterPath(mainInterface.getIDS().getPath());
		        String selected = fd.open();
		        if(selected != null)
		        	rulesFilePathText.setText(selected);
				
			}
		});
		browseButton.setText("Browse");
		browseButton.setBounds(350, 28, 80, 25);

		rulesBrowserGroup = new Group(shell, SWT.NONE);
		rulesBrowserGroup.setText("Browse rules");
		rulesBrowserGroup.setBounds(50, 210, 550, 290);

		final Button changeRuleButton = new Button(rulesBrowserGroup, SWT.NONE);
		changeRuleButton.setEnabled(false);
		changeRuleButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) 
			{
				int selectedIndex = table.getSelectionIndex();
				kindOfActionLabel.setText("Changing a rule...");
				ruleText.setText(table.getItem(selectedIndex).getText());
				ruleText.forceFocus();
				workMode = "Change";
				saveRuleButton.setEnabled(true);
				ruleText.setEditable(true);
				clearTextFieldButton.setEnabled(true);
			}
		});
		changeRuleButton.setText("Change rule");
		changeRuleButton.setBounds(10, 251, 90, 29);

		final Button newRuleButton = new Button(rulesBrowserGroup, SWT.NONE);
		newRuleButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) 
			{
				kindOfActionLabel.setText("Writing a new rule...");
				ruleText.setText("");
				table.setSelection(-1);
				changeRuleButton.setEnabled(false);
				deleteRuleButton.setEnabled(false);
				reloadDataButton.setEnabled(false);
				ruleText.forceFocus();
				workMode = "New";
				saveRuleButton.setEnabled(true);
				ruleText.setEditable(true);
				clearTextFieldButton.setEnabled(true);
			}
		});
		newRuleButton.setText("Add new Rule");
		newRuleButton.setBounds(125, 251, 100, 29);

		tableViewer = new TableViewer(rulesBrowserGroup, SWT.BORDER);
		tableViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(final SelectionChangedEvent arg0) 
			{
				//	Evento di selezione di una riga di tableViewer
				int selectedIndex = table.getSelectionIndex();
				ruleText.setText(table.getItem(selectedIndex).getText());
				kindOfActionLabel.setText("Viewing a rule...");
				changeRuleButton.setEnabled(true);
				deleteRuleButton.setEnabled(true);
				reloadDataButton.setEnabled(true);
				workMode = "";
				saveRuleButton.setEnabled(false);
				saveRuleButton.setText("Save rule");
			}
		});
		table = tableViewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setBounds(10, 30, 530, 215);

		deleteRuleButton = new Button(rulesBrowserGroup, SWT.NONE);
		deleteRuleButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) 
			{
				int selectedIndex = table.getSelectionIndex();
				kindOfActionLabel.setText("Removing a rule...");
				ruleText.setText(table.getItem(selectedIndex).getText());
				saveRuleButton.setEnabled(true);
				saveRuleButton.forceFocus();
				saveRuleButton.setText("Remove it");
				workMode = "Remove";
				ruleText.setEditable(false);
				clearTextFieldButton.setEnabled(false);
			}
		});
		deleteRuleButton.setEnabled(false);
		deleteRuleButton.setText("Remove Rule");
		deleteRuleButton.setBounds(250, 251, 95, 29);

		final Group ruleHandlerGroup = new Group(shell, SWT.NONE);
		ruleHandlerGroup.setText("Rule handler");
		ruleHandlerGroup.setBounds(50, 515, 550, 185);

		ruleText = new Text(ruleHandlerGroup, SWT.WRAP | SWT.V_SCROLL | SWT.MULTI | SWT.BORDER);
		ruleText.setBounds(10, 50, 530, 88);

		clearTextFieldButton = new Button(ruleHandlerGroup, SWT.NONE);
		clearTextFieldButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) 
			{
				ruleText.setText("");
				//kindOfActionLabel.setText("");
				//workMode = "";
				//saveRuleButton.setEnabled(false);
			}
		});
		clearTextFieldButton.setText("Clear text");
		clearTextFieldButton.setBounds(10, 145, 90, 29);

		kindOfActionLabel = new Label(ruleHandlerGroup, SWT.NONE);
		kindOfActionLabel.setForeground(SWTResourceManager.getColor(151, 54, 124));
		kindOfActionLabel.setBounds(10, 25, 290, 17);

		reloadDataButton = new Button(ruleHandlerGroup, SWT.NONE);
		reloadDataButton.setEnabled(false);
		reloadDataButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) 
			{
				int selectedIndex = table.getSelectionIndex();
				ruleText.setText(table.getItem(selectedIndex).getText());
			}
		});
		reloadDataButton.setText("Reload rule's data");
		reloadDataButton.setBounds(125, 145, 125, 29);

		saveRuleButton = new Button(ruleHandlerGroup, SWT.NONE);
		saveRuleButton.setEnabled(false);
		saveRuleButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) 
			{
				String editedRule = handleWritedRule((ruleText.getText()));
				
				if(workMode.equals("Change"))
				{
					//	eseguire le operazioni di modifica
					FileManager fm = new FileManager();		
					
					if(!ruleText.getText().equals(""))
					{
						if(fm.changeARule(rulesFilePathText.getText(), table.getItem(table.getSelectionIndex()).getText(), editedRule))
						{
							ruleText.setText("");
							updateInterface();
							kindOfActionLabel.setText("Rule changed!");
						}
						else
						{
							kindOfActionLabel.setText("Error in modification procedure!");
						}
					}
					else
					{
						kindOfActionLabel.setText("The rule text area is empty. Write something!");
					}
				}
				else if(workMode.equals("New"))
				{
					//	eseguire le operazioni di inserimento della nuova regola
					FileManager fm = new FileManager();
					if(!ruleText.getText().equals(""))
					{
						if(fm.insertNewRule(rulesFilePathText.getText(), editedRule))
						{
							ruleText.setText("");
							updateInterface();
							kindOfActionLabel.setText("Rule created!");
						}
						else
						{
							kindOfActionLabel.setText("Error in creation procedure!");
						}
					}
					else
					{
						kindOfActionLabel.setText("The rule text area is empty. Write something!");
					}
				}
				else if(workMode.equals("Remove"))
				{
					//	eseguire le operazioni di eliminazione della nuova regola
					FileManager fm = new FileManager();
					
					if(fm.changeARule(rulesFilePathText.getText(), table.getItem(table.getSelectionIndex()).getText(), ""))
					{
						ruleText.setText("");
						updateInterface();
						kindOfActionLabel.setText("Rule removed!");
						ruleText.setEditable(true);		
						clearTextFieldButton.setEnabled(true);		
					}
					else
					{
						kindOfActionLabel.setText("Error while removing rule!");
					}			
				}
			}
		});
		saveRuleButton.setText("Save rule");
		saveRuleButton.setBounds(450, 145, 90, 29);

		final Button BackButton = new Button(shell, SWT.NONE);
		BackButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) 
			{
				mainInterface.reloadLabelsAndButtons();
				shell.close();
			}
		});
		BackButton.setText("Back to main interface");
		BackButton.setBounds(50, 710, 160, 29);
		//
		
		loadData();
	}
	
	private void loadData()
	{
		IDSNameAndVersionLabel.setText(mainInterface.getIDS().getName());
		rulesFilePathText.setText(mainInterface.getIDS().getPath());
	}
	
	private void updateInterface()
	{
		FileManager fmanager = new FileManager();
		boolean fileFound = fmanager.loadRuleFile(rulesFilePathText.getText());
		
		//	Elimino i dati vecchi dall'interfaccia
		table.removeAll();
		ruleText.setText("");
		kindOfActionLabel.setText("");
		
		//	Controllo che il file selezionato esista
		if(fileFound)
			{
				responseLabel.setText("Rules file found");
				responseLabel.setForeground(SWTResourceManager.getColor(19, 139, 11));
				//	Il file esiste, carico le regole che vi sono contenute (carica solo le regole coerenti con 
				//	l'IDS selezionato)
				rulesList = mainInterface.getIDS().getRulesManager().loadRules(rulesFilePathText.getText());
				//	Inserisco i dati presenti in rulesList all'interno della tabella di visualizzazione
				for (int i = 0; i < rulesList.size(); i++) 
			    {
					TableItem item = new TableItem(table, SWT.NONE);
					item.setText((String)(rulesList.get(i)));
			    }	
			}		
		else
			{
				responseLabel.setText("Rules file not found");
				responseLabel.setForeground(SWTResourceManager.getColor(255, 0, 0));
			}
	}
	
	private String handleWritedRule(String writedRule)
	{
		//	Questo metodo elimina i caratteri di newline ("\n"), per compattare la nuova regola inserita o modificata
		//	e per fare in modo che le funzioni di sostituzione di stringhe della classe FileManager funzionino correttamente
		String handledRule;
		
		handledRule = writedRule.replaceAll("\n", "");
		return(handledRule);
	}
	

}
