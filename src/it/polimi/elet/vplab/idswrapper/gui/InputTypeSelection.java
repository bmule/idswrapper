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
import it.polimi.elet.vplab.idswrapper.ids.InputHandler;
import it.polimi.elet.vplab.idswrapper.concrete.pcapHandler;

import com.swtdesigner.SWTResourceManager;

public class InputTypeSelection {

	private List list;
	private Combo comboInputTypes;
	protected Shell shell;
	private boolean selectionDone = false;
	private IDSWrapperVisualInterface mainInterface;
	private Label selectedIDSLabel;
	private Button addButton;
	ListViewer listViewer;
	Label labelSelectionResponse;
	Button confirmButton;
	private IDS ids;

	/**
	 * Launch the application
	 * @param args
	 */
	/*public static void main(String[] args) {
		try {
			InputTypeSelection window = new InputTypeSelection();
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
		shell.setText("IDSWrapper - Input types");

		final Label selectInputTypesLabel = new Label(shell, SWT.NONE);
		selectInputTypesLabel.setForeground(SWTResourceManager.getColor(9, 9, 109));
		selectInputTypesLabel.setAlignment(SWT.CENTER);
		selectInputTypesLabel.setFont(SWTResourceManager.getFont("Sans", 14, SWT.BOLD));
		selectInputTypesLabel.setText("Select input types");
		selectInputTypesLabel.setBounds(50, 13, 550, 35);

		final Label selectOneOrLabel = new Label(shell, SWT.NONE);
		selectOneOrLabel.setForeground(SWTResourceManager.getColor(0, 0, 255));
		selectOneOrLabel.setFont(SWTResourceManager.getFont("Sans", 12, SWT.NONE));
		selectOneOrLabel.setText("Select one or more input types");
		selectOneOrLabel.setBounds(50, 142, 246, 27);

		comboInputTypes = new Combo(shell, SWT.NONE);
		comboInputTypes.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) 
			{
				if(comboInputTypes.getSelectionIndex()>=0)
					addButton.setEnabled(true);
			}
		});
		comboInputTypes.setBounds(315, 140, 168, 27);

		addButton = new Button(shell, SWT.NONE);
		addButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) 
			{
				//comboInputTypes
				if(!typeAlreadySelected(comboInputTypes.getItem(comboInputTypes.getSelectionIndex())))
					{
					list.add(comboInputTypes.getItem(comboInputTypes.getSelectionIndex()));
					labelSelectionResponse.setText("Type added");
					labelSelectionResponse.setForeground(SWTResourceManager.getColor(19, 139, 11));
					confirmButton.setEnabled(true);
					}
				else
				{
					labelSelectionResponse.setText("Type is already in list");
					labelSelectionResponse.setForeground(SWTResourceManager.getColor(255, 0, 0));
				}
				//controllo che il tipo di formato da gestire non sia già stato selezionato
				
			}
		});
		addButton.setEnabled(false);
		addButton.setText("Add type");
		addButton.setBounds(520, 141, 80, 27);

		ListViewer listViewer = new ListViewer(shell, SWT.BORDER);
		list = listViewer.getList();
		list.setBounds(50, 233, 246, 100);

		final Label inputTypesLabel = new Label(shell, SWT.NONE);
		inputTypesLabel.setForeground(SWTResourceManager.getColor(0, 0, 255));
		inputTypesLabel.setFont(SWTResourceManager.getFont("Sans", 12, SWT.NONE));
		inputTypesLabel.setText("Selected input types");
		inputTypesLabel.setBounds(50, 210, 200, 17);

		final Label selectedIdsLbl = new Label(shell, SWT.NONE);
		selectedIdsLbl.setForeground(SWTResourceManager.getColor(0, 0, 255));
		selectedIdsLbl.setFont(SWTResourceManager.getFont("Sans", 12, SWT.NONE));
		selectedIdsLbl.setText("Selected IDS:");
		selectedIdsLbl.setBounds(50, 90, 110, 20);

		confirmButton = new Button(shell, SWT.NONE);
		confirmButton.setEnabled(false);
		confirmButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) 
			{
				mainInterface.readInputHandlerFromInterface(createInputHandlerInstance());
				selectionDone = true;
				mainInterface.reloadLabelsAndButtons();
				shell.close();
				/*
				 * TODO: salvare le impostazioni all'interno di un'istanza dell'opportuno InputHandler
				 * */
				
			}
		});
		confirmButton.setText("Confirm");
		confirmButton.setBounds(50, 380, 80, 29);

		final Button removeElementsButton = new Button(shell, SWT.NONE);
		removeElementsButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) 
			{
				list.removeAll();
				confirmButton.setEnabled(false);
			}
		});
		removeElementsButton.setText("Remove elements");
		removeElementsButton.setBounds(315, 302, 130, 29);

		selectedIDSLabel = new Label(shell, SWT.NONE);
		selectedIDSLabel.setForeground(SWTResourceManager.getColor(6, 6, 71));
		selectedIDSLabel.setFont(SWTResourceManager.getFont("Sans", 12, SWT.NONE));
		selectedIDSLabel.setBounds(166, 90, 130, 20);

		labelSelectionResponse= new Label(shell, SWT.NONE);
		labelSelectionResponse.setFont(SWTResourceManager.getFont("Sans", 12, SWT.NONE));
		labelSelectionResponse.setBounds(315, 182, 168, 27);
		//
	}
	
	private void loadInitialData()
	{
		ids = this.mainInterface.getIDSDataFromIdsWrapper();
		
		selectedIDSLabel.setText(ids.getName() + "   ["+ids.getVersion()+"]");
		for(int k=0; k < (ids.getInputFormat()).size();k++)
		{
			comboInputTypes.add(ids.getInputFormat().get(k));
		}
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
	
	
//	Qui andranno inserite le istanziazioni delle classi di tutti i nuovi InputHandler aggiunti
	@SuppressWarnings("unchecked")
	private ArrayList<InputHandler> createInputHandlerInstance()
	{
		String[] strList = list.getItems();
		ArrayList<InputHandler> alist = new ArrayList();
		
		for(int i=0;i<strList.length;i++)
		{
//	System.out.println("ITEM NELLA LISTA: "+strList[i]);
			if(strList[i].equals("pcap"))
			{
				InputHandler inHandler = null;
				inHandler = new pcapHandler();
				alist.add(inHandler);
				
			}
			else if(strList[i].equals("..."))
			{
				
			}
		}
		return alist;
	}
	

}
