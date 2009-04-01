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
	//	la seguente variabile diventa true se si è terminato di selezionare IDS, tipi di input e tipi di output
	boolean selectionFinished = false;
		
	/**
	 * Launch the application
	 * @param args
	 */
	/*public static void main(String[] args) {
		try {
			window = new IDSWrapperVisualInterface();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	public void startInterface(IDSWrapper idsW)
	//public void run()
	{
		this.idsWrapper = idsW;
		try {
			//window = new IDSWrapperVisualInterface();
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
		//	Istanzio gli oggetti relativi alle altre interfacce
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
				//setIDSFromInterface((IDSSelection.open(window)));
				//getIDS();
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
				//	terminata la selezione degli input, abilito il pulsante degli output
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
				//	Controllo il tipo di IDS selezionato ed istanzio l'interfaccia visuale di analisi
				//	corrispondente
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
				//	Controllo il tipo di IDS selezionato ed istanzio l'interfaccia visuale di analisi
				//	corrispondente
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
			
			//	Abilito i pulsanti che consentono la configurazione, l'analisi e l'addestramento dell'IDS
			idsConfigurationButton.setEnabled(true);
			analysisConfigurationButton.setEnabled(true);
			//	Abilito il tasto di addestramento solo se l'IDS è di tipo anomaly based
			if(selectedIds.getIdsParadigm().equals("anomaly"))
				trainTheIdsButton.setEnabled(true);
	//	INSERIRE QUI IL CODICE CHE CONSENTE DI PROCEDERE CON L'UTILIZZO DELL'IDSWRAPPER CONFIGURATO
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
	//	System.out.println("Lunghezza vettore input: "+inHandlerList.size());
		for(int i=0;i<inHandlerList.size();i++)
		{
			idsWrapper.selectInputType(InputHandler.class.cast(inHandlerList.get(i)));
		}
	}
	
	@SuppressWarnings("unchecked")
	public void readOutputHandlerFromInterface(ArrayList outHandlerList)
	{
	//	System.out.println("Lunghezza vettore input: "+inHandlerList.size());
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
