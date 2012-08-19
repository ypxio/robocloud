package com.robocloud.project;

import com.robocloud.project.R;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class Dashboard extends TabActivity {
	// TabSpec Names
	private static final String LIST_TAB = "List File";
	private static final String UPLOAD_TAB = "Upload File";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_dashboard);
        
        TabHost tabHost = getTabHost();
        
        // Inbox Tab
        TabSpec listTab = tabHost.newTabSpec(LIST_TAB);
        // Tab Icon
        listTab.setIndicator(LIST_TAB, getResources().getDrawable(R.drawable.file_list));
        Intent inboxIntent = new Intent(this, ListItemActivity.class);
        // Tab Content
        listTab.setContent(inboxIntent);
        
        // Outbox Tab
        TabSpec uplaodTab = tabHost.newTabSpec(UPLOAD_TAB);
        uplaodTab.setIndicator(UPLOAD_TAB, getResources().getDrawable(R.drawable.upload));
        Intent outboxIntent = new Intent(this, FileDialog.class);
        uplaodTab.setContent(outboxIntent);
        
        
        // Adding all TabSpec to TabHost
        tabHost.addTab(listTab); // Adding Inbox tab
        tabHost.addTab(uplaodTab); // Adding Outbox tab
    }
}