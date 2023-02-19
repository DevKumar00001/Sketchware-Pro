package mod.tsd.activities;

import com.sketchware.remod.R;
import android.animation.*;
import android.app.*;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.*;
import android.content.Intent;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.net.Uri;
import android.os.*;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.*;
import org.json.*;

public class DocumentationActivity extends Activity {
	
	private HashMap<String, Object> MM = new HashMap<>();
	
	private ArrayList<HashMap<String, Object>> Data = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> Data2 = new ArrayList<>();
	
	private ScrollView Main;
	private LinearLayout Log;
	private HorizontalScrollView hscroll1;
	private LinearLayout linear2;
	private ImageView imageview1;
	private TextView textview1;
	private TextView textview2;
	
	private View LI;
	private Intent Link = new Intent();
	private RequestNetwork FetchFile;
	private RequestNetwork.RequestListener _FetchFile_request_listener;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.documentation);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		Main = findViewById(R.id.Main);
		Log = findViewById(R.id.Log);
		hscroll1 = findViewById(R.id.hscroll1);
		linear2 = findViewById(R.id.linear2);
		imageview1 = findViewById(R.id.imageview1);
		textview1 = findViewById(R.id.textview1);
		textview2 = findViewById(R.id.textview2);
		FetchFile = new RequestNetwork(this);
		
		_FetchFile_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				final String _tag = _param1;
				final String _response = _param2;
				final HashMap<String, Object> _responseHeaders = _param3;
				_listAllDataFromJSON(_response, linear2);
				Main.setVisibility(View.VISIBLE);
				Log.setVisibility(View.GONE);
			}
			
			@Override
			public void onErrorResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _message = _param2;
				
			}
		};
	}
	
	private void initializeLogic() {
		Log.setVisibility(View.VISIBLE);
		Main.setVisibility(View.GONE);
		FetchFile.startRequestNetwork(RequestNetworkController.GET, "https://technicalstudiodeveloper.github.io/Sketchware-pro-data.github.io/main.json", "A", _FetchFile_request_listener);
	}
	
	public void _listAllDataFromJSON(final String _json, final View _view) {
		try{
			final JSONArray MyLoadedData = new JSONArray(_json);
			for(int _repeat13 = 0; _repeat13 < (int)(MyLoadedData.length()); _repeat13++) {
				JSONObject JSONObj = MyLoadedData.getJSONObject(_repeat13);

				if (JSONObj.has("title")) {
					LI = getLayoutInflater().inflate(R.layout.documentation_adapter, null);
					TextView title = ((TextView)LI.findViewById(R.id.textview1));
					final LinearLayout myl1 = ((LinearLayout)LI.findViewById(R.id.linear1));
					final LinearLayout myl2 = ((LinearLayout)LI.findViewById(R.id.linear2));
					final LinearLayout myl3 = ((LinearLayout)LI.findViewById(R.id.linear3));
					final ImageView img1 =((ImageView)LI.findViewById(R.id.imageview1));
					title.setText(JSONObj.getString("title"));
					myl3.setVisibility(View.GONE);
					final int _r = _repeat13;
					
					if (JSONObj.has("clickable")) {
						if (JSONObj.getString("clickable").equals("true")) {
							final String url = JSONObj.getString("url");
							myl2.setOnClickListener(new View.OnClickListener() {
									@Override
									public void onClick(View _view) {
									Link.setData(Uri.parse(url));
									Link.setAction(Intent.ACTION_VIEW);
									startActivity(Link);
									}
							});
						}
					}
					if (JSONObj.has("hasChild")) {
						if (JSONObj.getString("hasChild").equals("true")) {
							JSONArray JSArray = JSONObj.getJSONArray("Child");
							_listAllDataFromJSON(JSArray.toString(), myl3);
							if (myl3.getVisibility() == View.VISIBLE) {
								img1.setImageResource(R.drawable.ic_arrow_drop_up_black);
							}
							else {
								img1.setImageResource(R.drawable.ic_arrow_drop_down_black);
							}
							img1.setOnClickListener(new View.OnClickListener() {
									@Override
									public void onClick(View _view) {
									if (myl3.getVisibility() == View.VISIBLE) {
										myl3.setVisibility(View.GONE);
										img1.setImageResource(R.drawable.ic_arrow_drop_down_black);
									}
									else {
										myl3.setVisibility(View.VISIBLE);
										img1.setImageResource(R.drawable.ic_arrow_drop_up_black);
									}
									}
							});
						}
						else {
							img1.setVisibility(View.INVISIBLE);
							img1.setEnabled(false);
						}
					}
					else {
						img1.setVisibility(View.INVISIBLE);
						img1.setEnabled(false);
					}
					if (JSONObj.has("TextviewSize")) {
						title.setTextSize((int)JSONObj.getInt("TextviewSize"));
					}
					if (myl1 != null) {
						((LinearLayout)myl1.getParent()).removeView(myl1);
					}
					((LinearLayout)_view).addView(myl1);
				}
			}
		}
		catch(JSONException e){
			 
		}
	}
	
}
