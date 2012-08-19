package com.robocloud.project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ListActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class ListItemActivity extends ListActivity {
	
//	private static final String URL_LIST = "http://192.168.161.55/project/robocloud/list.php";
	private static final String ITEM_ID = "id";
    private static final String ITEM_NAME = "name";
    private static final String ITEM_COUNTER = "counter";
    private static final String ITEM_TYPE = "type";
    private static final String ITEM_URL = "url";
    private static final String ITEM_URL_PAGE = "urlpage";
	
//	JSONArray items = null;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main);
        
        String URL_LIST = "http://172.21.0.75/project/robocloud/list.php";
        
        Map<String, String> params = new HashMap<String, String>();
		params.put("param", "");
		HttpRequestClass login = new HttpRequestClass(URL_LIST, params, HttpRequestClass.Method.POST);
		String response = login.sendRequest();
		response = response.trim(); // response from server
		
		String item[] = response.split("<item>");
		
//		item[] = 
		ArrayList<HashMap<String, String>> myItems = new ArrayList<HashMap<String, String>>();
		
		for(int i = 0; i < item.length; i++)
		{
			String each_item[] = item[i].split("<single>");
//			
			String item_id = each_item[0];
			String item_name = each_item[1];
			String item_counter = each_item[2];
			
			String item_url = each_item[4];
			String item_urlpage = each_item[5];
			
			
			String item_type = each_item[3]+" file type";
			
			HashMap<String, String> map = new HashMap<String, String>();
			
			map.put(ITEM_ID, item_id);
			map.put(ITEM_NAME, item_name);
			map.put(ITEM_COUNTER, item_counter);
			map.put(ITEM_TYPE, item_type);
			map.put(ITEM_URL, item_url);
			map.put(ITEM_URL_PAGE, item_urlpage);
			
			myItems.add(map);
//			System.out.println(item_id+"+nbjb"+item_name);
		}
		
		ListAdapter mItem = 	new SimpleAdapter(this, myItems, R.layout.list_item_mod,
	            				new String[] {ITEM_ID, ITEM_NAME, ITEM_COUNTER, ITEM_TYPE, ITEM_URL, ITEM_URL_PAGE},
	            				new int[] {R.id.item_id, R.id.item_name, R.id.item_counter, R.id.item_type, R.id.item_url, R.id.item_urlpage});
		
		setListAdapter(mItem);
		
		
		
        // storing string resources into Array
//        String[] adobe_products = getResources().getStringArray(R.array.adobe_products);

        
        // Binding Array to ListAdapter
//        this.setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, R.id.item_id, item));
        
        ListView lv = getListView();

        // listening to single list item on click
        lv.setOnItemClickListener(new OnItemClickListener() {
          public void onItemClick(AdapterView<?> parent, View view,
              int position, long id) {
//        	  
        	  // selected item 
        	  String name_item = ((TextView) view.findViewById(R.id.item_name)).getText().toString();
        	  String url_item = ((TextView) view.findViewById(R.id.item_url)).getText().toString();
        	  String urlpage_item = ((TextView) view.findViewById(R.id.item_urlpage)).getText().toString();
//        	  Toast.makeText(ListItemActivity.this, product, Toast.LENGTH_SHORT).show();
        	  
        	  // Launching new Activity on selecting single List Item
        	  Intent i = new Intent(getApplicationContext(), SingleItemActivity.class);
        	  // sending data to new activity
        	  i.putExtra("name_item", name_item);
        	  i.putExtra("url_item", url_item);
        	  i.putExtra("urlpage_item", urlpage_item);
        	  
        	  startActivity(i);
        	
          }
        });
        
//		String text = response;
//        TextView list = (TextView) findViewById(R.id.list);
//		list.setText(text);

		
		
        
    }
	
	// Initiating Menu XML file (menu.xml)
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.layout.menu, menu);
        return true;
    }
 
    /**
     * Event Handling for Individual menu item selected
     * Identify single menu item by it's id
     * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
 
        switch (item.getItemId())
        {
        case R.id.menu_bookmark:
            // Single menu item is selected do something
            // Ex: launching new activity/screen or show alert message
            Toast.makeText(ListItemActivity.this, "Bookmark is Selected", Toast.LENGTH_SHORT).show();
            return true;
 
        case R.id.menu_save:
        	
        	Intent i = new Intent(getApplicationContext(), FileDialog.class);
			startActivity(i);
 
        case R.id.menu_search:
        	Intent j = new Intent(getApplicationContext(), LoginActivity.class);
			startActivity(j);
 
        default:
            return super.onOptionsItemSelected(item);
        }
    }    


}
