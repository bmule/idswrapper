package it.polimi.elet.vplab.idswrapper.gui;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class AnalysisInterfaceHost 
{
	
	protected Shell shell;
	IDSWrapperVisualInterface mainInterface;

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
		shell.setSize(500, 375);
		shell.setText("SWT Application");
	}

}
