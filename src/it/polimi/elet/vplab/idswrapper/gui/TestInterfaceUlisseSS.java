package it.polimi.elet.vplab.idswrapper.gui;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import it.polimi.elet.vplab.idswrapper.filemanager.ConfigureUlPreprocessor;
import it.polimi.elet.vplab.idswrapper.ids.IDSTestingInterface;

import com.swtdesigner.SWTResourceManager;

public class TestInterfaceUlisseSS
       extends IDSTestingInterface
{

	private Text detectionRate;
	private Text sensitivity;
	private Text baseName;
	private Text CommandLinePreviewText2;
	private Text CommandLinePreviewText1;
	private Text SecondexecText;
	private Text MasterListFileText;
	private Text TestingDataPathText;
	private Text FirstexecText;
	protected Shell shell;
	protected Button confirm2Button ;
	protected Button ExecuteSecondStepButton;
	
	String IDSpath;
	String SSBaseName;
	String commandLine1;
	String commandLine2;

	/**
	 * Launch the application
	 * @param args
	 */
/*	public static void main(String[] args) 
	{
		try {
			TestInterfaceUlisseSS window = new TestInterfaceUlisseSS();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
*/
	
	/*public void startInterface(String SSBase)
	//public void run()
	{
		this.SSBaseName = SSBase;
		try {
			window = new TestInterfaceUlisseSS();
			//window = this.idsWrapper.getVisualInterface();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	/**
	 * Open the window
	 */
	public void open(String SSBase) 
	{
		this.SSBaseName = SSBase;
		
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
		shell.setSize(730, 537);
		shell.setText("IDSWrapper - Ulisse SS test");

		final Label TestingTitleLabel = new Label(shell, SWT.NONE);
		TestingTitleLabel.setForeground(SWTResourceManager.getColor(22, 22, 145));
		TestingTitleLabel.setAlignment(SWT.CENTER);
		TestingTitleLabel.setFont(SWTResourceManager.getFont("Sans", 14, SWT.NONE));
		TestingTitleLabel.setText("Ulisse Testing Interface");
		TestingTitleLabel.setBounds(10, 10, 712, 30);

		final Group ulPreprocessorSettingsGroup = new Group(shell, SWT.NONE);
		ulPreprocessorSettingsGroup.setText("UL preprocessor settings");
		ulPreprocessorSettingsGroup.setBounds(10, 46, 708, 118);

		baseName = new Text(ulPreprocessorSettingsGroup, SWT.BORDER);
		baseName.setBounds(162, 22, 200, 25);
		baseName.setText(SSBaseName);

		final Label smartsifterBasenameLabel = new Label(ulPreprocessorSettingsGroup, SWT.NONE);
		smartsifterBasenameLabel.setText("SmartSifter basename");
		smartsifterBasenameLabel.setBounds(10, 30, 146, 17);

		final Label sensitivityLabel = new Label(ulPreprocessorSettingsGroup, SWT.NONE);
		sensitivityLabel.setText("Sensitivity");
		sensitivityLabel.setBounds(10, 60, 146, 17);

		sensitivity = new Text(ulPreprocessorSettingsGroup, SWT.BORDER);
		sensitivity.setBounds(162, 52, 200, 25);

		detectionRate = new Text(ulPreprocessorSettingsGroup, SWT.BORDER);
		detectionRate.setBounds(162, 82, 200, 25);

		final Label detectionRateLabel = new Label(ulPreprocessorSettingsGroup, SWT.NONE);
		detectionRateLabel.setText("Detection Rate");
		detectionRateLabel.setBounds(10, 90, 146, 17);

		final Button applyChangesButton = new Button(ulPreprocessorSettingsGroup, SWT.NONE);
		applyChangesButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				
				baseName.setEnabled(false);
				sensitivity.setEnabled(false);
				detectionRate.setEnabled(false);
				applyChangesButton.setEnabled(false);
				
				String basename = baseName.getText();
				String sens = sensitivity.getText();
				String rate = detectionRate.getText();
				
				ConfigureUlPreprocessor conf = new ConfigureUlPreprocessor(IDSpath);
				if (conf.setParameters(basename, sens, rate)) {
					
					baseName.setEnabled(true);
					sensitivity.setEnabled(true);
					detectionRate.setEnabled(true);
					applyChangesButton.setEnabled(true);
					
				}
				
			}
		});
		applyChangesButton.setText("Apply changes");
		applyChangesButton.setBounds(520, 80, 120, 29);
		
		final Group FirstStepTestingGroup = new Group(shell, SWT.NONE);
		FirstStepTestingGroup.setText("First step");
		FirstStepTestingGroup.setBounds(10, 185, 353, 267);

		final Label FirstexecLabel = new Label(FirstStepTestingGroup, SWT.NONE);
		FirstexecLabel.setText("First exec path");
		FirstexecLabel.setBounds(10, 26, 111, 17);

		FirstexecText = new Text(FirstStepTestingGroup, SWT.BORDER);
		FirstexecText.setBounds(144, 25, 180, 23);

		final Label testingFileLabel = new Label(FirstStepTestingGroup, SWT.NONE);
		testingFileLabel.setText("Testing data path");
		testingFileLabel.setBounds(10, 55, 111, 17);

		final Label ListFileLabel = new Label(FirstStepTestingGroup, SWT.NONE);
		ListFileLabel.setText("Master list-file path");
		ListFileLabel.setBounds(10, 84, 128, 17);

		TestingDataPathText = new Text(FirstStepTestingGroup, SWT.BORDER);
		TestingDataPathText.setBounds(144, 54, 180, 23);

		MasterListFileText = new Text(FirstStepTestingGroup, SWT.BORDER);
		MasterListFileText.setBounds(144, 82, 180, 23);

		final Button confirm1Button = new Button(FirstStepTestingGroup, SWT.CHECK);
		confirm1Button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) 
			{
				if(confirm1Button.getSelection())
				{
					commandLine1 = "";
					if(FirstexecText.getText().endsWith("/"))
						commandLine1 = FirstexecText.getText()+"test_ss_first" + " "+SSBaseName+" "+TestingDataPathText.getText()+ " "+MasterListFileText.getText();
					else
						commandLine1 = FirstexecText.getText()+"/test_ss_first" + " "+SSBaseName+" "+TestingDataPathText.getText()+ " "+MasterListFileText.getText();
					CommandLinePreviewText1.setText(commandLine1);
					
					FirstexecText.setEnabled(false);
					TestingDataPathText.setEnabled(false);
					MasterListFileText.setEnabled(false);
				}
				else
				{
					FirstexecText.setEnabled(true);
					TestingDataPathText.setEnabled(true);
					MasterListFileText.setEnabled(true);
					CommandLinePreviewText1.setText("");
					commandLine1 = "";
				}
			}
		});
		confirm1Button.setText("Confirm selection");
		confirm1Button.setBounds(10, 116, 135, 22);

		final Label PreviewLabel = new Label(FirstStepTestingGroup, SWT.NONE);
		PreviewLabel.setText("CommandLine Preview");
		PreviewLabel.setBounds(10, 160, 152, 17);

		CommandLinePreviewText1 = new Text(FirstStepTestingGroup, SWT.WRAP | SWT.MULTI | SWT.BORDER);
		CommandLinePreviewText1.setBounds(10, 183, 334, 45);

		final Button ExecuteFirstStepButton = new Button(FirstStepTestingGroup, SWT.NONE);
		ExecuteFirstStepButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) 
			{
				try
		        {            
					Process testingProgess;
					testingProgess = Runtime.getRuntime().exec(commandLine1);
					
					BufferedReader input = new BufferedReader(new InputStreamReader(testingProgess.getInputStream()));
					String line = "";
					while ((line = input.readLine()) != null) 
				    {
				        System.out.println(line);
				    }
				      input.close();
				   
		System.out.println("testing lanciato...");
		SecondexecText.setEnabled(true);
		confirm2Button.setEnabled(true);
		ExecuteSecondStepButton.setEnabled(true);
		ExecuteFirstStepButton.setEnabled(false);
		confirm1Button.setEnabled(false);
		        } catch (Throwable t)
		          {
		System.out.println("ERRORE nel testing");
		            t.printStackTrace();
		          }
			}
		});
		ExecuteFirstStepButton.setBounds(10, 234, 115, 25);
		ExecuteFirstStepButton.setText("Do it");

		final Button browseButton_1 = new Button(FirstStepTestingGroup, SWT.NONE);
		browseButton_1.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				
				FileDialog fd = new FileDialog(shell, SWT.OPEN);
				fd.setText("Select");
				String selected = fd.open();
				if(selected != null)
					FirstexecText.setText(selected);
				
			}
		});
		browseButton_1.setText("'");
		browseButton_1.setBounds(324, 25, 23, 23);

		final Button browseButton_2 = new Button(FirstStepTestingGroup, SWT.NONE);
		browseButton_2.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				
				FileDialog fd = new FileDialog(shell, SWT.OPEN);
				fd.setText("Select");
				String selected = fd.open();
				if(selected != null)
					TestingDataPathText.setText(selected);
				
			}
		});
		browseButton_2.setText("'");
		browseButton_2.setBounds(324, 55, 23, 23);

		final Button browseButton_3 = new Button(FirstStepTestingGroup, SWT.NONE);
		browseButton_3.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				
				FileDialog fd = new FileDialog(shell, SWT.OPEN);
				fd.setText("Select");
				String selected = fd.open();
				if(selected != null)
					MasterListFileText.setText(selected);
				
			}
		});
		browseButton_3.setText("'");
		browseButton_3.setBounds(324, 83, 23, 23);

		final Group SecondStepTestingGroup = new Group(shell, SWT.NONE);
		SecondStepTestingGroup.setText("Second step");
		SecondStepTestingGroup.setBounds(369, 185, 353, 267);

		final Label SecondexecLabel = new Label(SecondStepTestingGroup, SWT.NONE);
		SecondexecLabel.setBounds(10, 27, 128, 17);
		SecondexecLabel.setText("Second exec path");

		SecondexecText = new Text(SecondStepTestingGroup, SWT.BORDER);
		SecondexecText.setEnabled(false);
		SecondexecText.setBounds(144, 24, 180, 23);

		final Label PreviewLabel_1 = new Label(SecondStepTestingGroup, SWT.NONE);
		PreviewLabel_1.setBounds(10, 158, 152, 17);
		PreviewLabel_1.setText("CommandLine Preview");

		CommandLinePreviewText2 = new Text(SecondStepTestingGroup, SWT.WRAP | SWT.MULTI | SWT.BORDER);
		CommandLinePreviewText2.setBounds(10, 181, 334, 45);

		confirm2Button= new Button(SecondStepTestingGroup, SWT.CHECK);
		confirm2Button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) 
			{
				if(confirm2Button.getSelection())
				{
					commandLine2 = "";
					if(SecondexecText.getText().endsWith("/"))
						commandLine2 = SecondexecText.getText()+"test_ss_second" + " "+SSBaseName;
					else
						commandLine2 = SecondexecText.getText()+"/test_ss_second" + " "+SSBaseName;
					CommandLinePreviewText2.setText(commandLine2);
					
					SecondexecText.setEnabled(false);
				}
				else
				{
					SecondexecText.setEnabled(true);
					CommandLinePreviewText2.setText("");
					commandLine2 = "";
				}
			}
		});
		confirm2Button.setEnabled(false);
		confirm2Button.setBounds(10, 50, 100, 22);
		confirm2Button.setText("Confirm");

		ExecuteSecondStepButton = new Button(SecondStepTestingGroup, SWT.NONE);
		ExecuteSecondStepButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) 
			{
				try
		        {            
					Process testingProgess;
					testingProgess = Runtime.getRuntime().exec(commandLine2);
					
					BufferedReader input = new BufferedReader(new InputStreamReader(testingProgess.getInputStream()));
					String line = "";
					while ((line = input.readLine()) != null) 
				    {
				        System.out.println(line);
				    }
				      input.close();
				   
		System.out.println("testing lanciato...");
		SecondexecText.setEnabled(false);
		confirm2Button.setEnabled(false);
		ExecuteSecondStepButton.setEnabled(false);
		        } catch (Throwable t)
		          {
		System.out.println("ERRORE nel testing");
		            t.printStackTrace();
		          }
			}
		});
		ExecuteSecondStepButton.setEnabled(false);
		ExecuteSecondStepButton.setText("Do it");
		ExecuteSecondStepButton.setBounds(10, 232, 115, 25);

		final Button browseButton_4 = new Button(SecondStepTestingGroup, SWT.NONE);
		browseButton_4.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				
				FileDialog fd = new FileDialog(shell, SWT.OPEN);
				fd.setText("Select");
				String selected = fd.open();
				if(selected != null)
					SecondexecText.setText(selected);
				
			}
		});
		browseButton_4.setBounds(324, 25, 23, 23);
		browseButton_4.setText("'");

		final Button BackButton = new Button(shell, SWT.NONE);
		BackButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) 
			{
				shell.close();
			}
		});
		BackButton.setText("Back to main interface");
		BackButton.setBounds(287, 468, 156, 29);
		//
		
		loadData();
	}
	
	private void loadData()
	{
		
	}

	public String getIDSpath() {
		return IDSpath;
	}

	public void setIDSpath(String idsPath) {
		IDSpath = idsPath;
	}
	

}
