package it.polimi.elet.vplab.idswrapper.concrete;

import java.util.ArrayList;


/*	This class manages the commandLineTagList, a data structure that lets you to link the features selected by the user 
 * on the generic interface and the "real" command line of the specific IDS
 * 
 */

@SuppressWarnings({"unchecked"})
public class TagListAnalysis 
{
	ArrayList commandLineTagList;
	ArrayList<String> commandLineTagSubListVisualOptions;
	ArrayList<String> commandLineTagSubListAlertOptions;
	
	
	public ArrayList inizializeCommandLineTagList()
	{
		//	Inizialize commandLineTagList
		commandLineTagSubListVisualOptions = new ArrayList();
		commandLineTagSubListVisualOptions.add("");
		commandLineTagSubListVisualOptions.add("");
		commandLineTagSubListVisualOptions.add("");
		
		commandLineTagSubListAlertOptions = new ArrayList();
		commandLineTagSubListAlertOptions.add("");
		commandLineTagSubListAlertOptions.add("");
		commandLineTagSubListAlertOptions.add("");
		commandLineTagSubListAlertOptions.add("");
		
		commandLineTagList = new ArrayList();
		commandLineTagList.add("");
		commandLineTagList.add(commandLineTagSubListVisualOptions);
		commandLineTagList.add("");
		commandLineTagList.add(commandLineTagSubListAlertOptions);
		commandLineTagList.add("");
		commandLineTagList.add("");
		commandLineTagList.add("");
		
		return commandLineTagList;
	}
	
	public ArrayList updteCommandLineTagList(int position, String data)
	{
		//	The position 1 is different!
		
		if(position == 1)
		{
				if(data.equals("dump_app_layer"))
					((ArrayList)(commandLineTagList.get(1))).set(0,data);
				else if(data.equals("show_second_layer"))
					((ArrayList)(commandLineTagList.get(1))).set(1,data);
				else if(data.equals("verbose_info"))
					((ArrayList)(commandLineTagList.get(1))).set(2,data);
				else if(data.equals("clear_dump_app_layer"))
					((ArrayList)(commandLineTagList.get(1))).set(0,"");
				else if(data.equals("clear_show_second_layer"))
					((ArrayList)(commandLineTagList.get(1))).set(1,"");
				else if(data.equals("clear_verbose_info"))
					((ArrayList)(commandLineTagList.get(1))).set(2,"");
		}
		else if(position == 3)
		{
			if(data.equals("full") || data.equals("fast") || data.equals("unsock") || data.equals("console") || data.equals("cmg") || data.equals("no_alert"))
				((ArrayList)(commandLineTagList.get(3))).set(0,data);
			else if(data.equals("binary_format_log"))
				((ArrayList)(commandLineTagList.get(3))).set(1,data);
			else if(data.equals("msg_to_syslog"))
				((ArrayList)(commandLineTagList.get(3))).set(2,data);
			else if(data.equals("no_log"))
				((ArrayList)(commandLineTagList.get(3))).set(3,data);	
			else if(data.equals("clear_full") || data.equals("clear_fast") || data.equals("clear_unsock") || data.equals("clear_console") || data.equals("clear_cmg") || data.equals("clear_no_alert"))
				((ArrayList)(commandLineTagList.get(3))).set(0,"");
			else if(data.equals("clear_binary_format_log"))
				((ArrayList)(commandLineTagList.get(3))).set(1,"");
			else if(data.equals("clear_msg_to_syslog"))
				((ArrayList)(commandLineTagList.get(3))).set(2,"");
			else if(data.equals("clear_no_log"))
				((ArrayList)(commandLineTagList.get(3))).set(3,"");	
		}
		else
		{
			commandLineTagList.set(position, data);
		}
		
		return commandLineTagList;
	}
	
	public ArrayList getTagList()
	{
		return commandLineTagList;
	}

}
