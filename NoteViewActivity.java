package mkt.advnotepad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.*;
import android.app.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.webkit.*;
import android.animation.*;
import android.view.animation.*;
import java.util.*;
import java.util.regex.*;
import java.text.*;
import org.json.*;
import java.util.HashMap;
import java.util.ArrayList;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.ImageView;
import android.widget.TextView;
import android.speech.tts.TextToSpeech;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.graphics.Typeface;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import android.content.ClipData;
import android.content.ClipboardManager;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;


public class NoteViewActivity extends  AppCompatActivity  { 
	
	
	private String Title = "";
	private String Note = "";
	private HashMap<String, Object> Get_Note = new HashMap<>();
	private double Position = 0;
	private String a = "";
	
	private ArrayList<HashMap<String, Object>> Notes_List = new ArrayList<>();
	
	private LinearLayout toolbar;
	private ScrollView main_scroll;
	private ImageView back;
	private TextView title;
	private ImageView copy;
	private ImageView share;
	private ImageView edit_img;
	private LinearLayout main;
	private TextView note;
	
	private TextToSpeech NoteSpeak;
	private SharedPreferences Settings;
	private SharedPreferences AllNotes;
	private Intent Note_Edit = new Intent();
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.note_view);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		
		toolbar = (LinearLayout) findViewById(R.id.toolbar);
		main_scroll = (ScrollView) findViewById(R.id.main_scroll);
		back = (ImageView) findViewById(R.id.back);
		title = (TextView) findViewById(R.id.title);
		copy = (ImageView) findViewById(R.id.copy);
		share = (ImageView) findViewById(R.id.share);
		edit_img = (ImageView) findViewById(R.id.edit_img);
		main = (LinearLayout) findViewById(R.id.main);
		note = (TextView) findViewById(R.id.note);
		NoteSpeak = new TextToSpeech(getApplicationContext(), null);
		Settings = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
		AllNotes = getSharedPreferences("All Notes", Activity.MODE_PRIVATE);
		
		copy.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				((ClipboardManager) getSystemService(getApplicationContext().CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("clipboard", note.getText().toString()));
				SketchwareUtil.showMessage(getApplicationContext(), "copid to clipboard !!! ");
			}
		});
		
		share.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				a = title.getText().toString().concat("\n".concat(note.getText().toString()));
				Intent i = new Intent(android.content.Intent.ACTION_SEND);
				i.setType("text/plain");  
				i.putExtra(android.content.Intent.EXTRA_TEXT, a);
				startActivity(Intent.createChooser(i, "Share via"));
			}
		});
		
		edit_img.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				Note_Edit.setAction(Intent.ACTION_VIEW);
				Note_Edit.setClass(getApplicationContext(), NoteEditActivity.class);
				Note_Edit.putExtra("Position", getIntent().getStringExtra("Position"));
				startActivity(Note_Edit);
				finish();
			}
		});
	}
	
	private void initializeLogic() {
		_OnCreate();
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			
			default:
			break;
		}
	}
	
	@Override
	public void onStart() {
		super.onStart();
		_GetNote();
	}
	public void _Typeface () {
		title.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/productsansbold.ttf"), 0);
		note.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/productsansmedium.ttf"), 0);
	}
	
	
	public void _OnCreate () {
		back.setVisibility(View.INVISIBLE);
		Position = Double.parseDouble(getIntent().getStringExtra("Position"));
		_Typeface();
		_GetNote();
		if (Settings.getString("Text Size", "").equals("Small")) {
			if (Title.length() > 35) {
				title.setText(Title.substring((int)(0), (int)(35)).concat("..."));
			}
			else {
				title.setText(Title);
			}
		}
		else {
			if (Settings.getString("Text Size", "").equals("Medium")) {
				if (Title.length() > 32) {
					title.setText(Title.substring((int)(0), (int)(32)).concat("..."));
				}
				else {
					title.setText(Title);
				}
			}
			else {
				if (Settings.getString("Text Size", "").equals("Large")) {
					if (Title.length() > 29) {
						title.setText(Title.substring((int)(0), (int)(29)).concat("..."));
					}
					else {
						title.setText(Title);
					}
				}
				else {
					
				}
			}
		}
		note.setText(Note);
		_GetSettingsData();
	}
	
	
	public void _Add (final String _Colour, final ImageView _Imageview) {
		_Imageview.getDrawable().setColorFilter(Color.parseColor(_Colour), PorterDuff.Mode.SRC_IN);
	}
	
	
	public void _GetSettingsData () {
		if (Settings.getString("Detect Link", "").equals("True")) {
			_detectLinks(note);
		}
		else {
			
		}
		if (Settings.getString("Text Size", "").equals("Small")) {
			
		}
		else {
			if (Settings.getString("Text Size", "").equals("Medium")) {
				_textSize(title, 18);
				_textSize(note, 18);
			}
			else {
				if (Settings.getString("Text Size", "").equals("Large")) {
					_textSize(title, 20);
					_textSize(note, 20);
				}
				else {
					
				}
			}
		}
		if (Settings.getString("Theme", "").equals("Default")) {
			Window w = NoteViewActivity.this.getWindow();w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS); w.setStatusBarColor(Color.parseColor("#FFFFFF")); main.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
			_Add("#FF009688", back);
			_Add("#FF009688", edit_img);
			_Add("#FF009688", copy);
			_Add("#FF009688", share);
		}
		else {
			if (Settings.getString("Theme", "").equals("Dark")) {
				Window w = NoteViewActivity.this.getWindow();w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS); w.setStatusBarColor(Color.parseColor("#263238"));
				toolbar.setBackgroundColor(0xFF263238);
				main_scroll.setBackgroundColor(0xFF263238);
				title.setTextColor(0xFFFFFFFF);
				note.setTextColor(0xFFFFFFFF);
				
				_Add("#FFFFFF", back);
				_Add("#FFFFFF", edit_img);
				_Add("#FFFFFF", copy);
				_Add("#FFFFFF", share);
			}
			else {
				if (Settings.getString("Theme", "").equals("Blue Grey")) {
					toolbar.setBackgroundColor(0xFFFFFFFF);
					main_scroll.setBackgroundColor(0xFFFFFFFF);
					title.setTextColor(0xFF607D8B);
					note.setTextColor(0xFF000000);
					Window w = NoteViewActivity.this.getWindow();w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS); w.setStatusBarColor(Color.parseColor("#FFFFFF")); main.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
					
					_Add("#607D8B", back);
					_Add("#607D8B", edit_img);
					_Add("#607D8B", copy);
					_Add("#607D8B", share);
				}
				else {
					if (Settings.getString("Theme", "").equals("Orange")) {
						toolbar.setBackgroundColor(0xFFFFFFFF);
						main_scroll.setBackgroundColor(0xFFFFFFFF);
						title.setTextColor(0xFFFF5722);
						note.setTextColor(0xFF000000);
						Window w = NoteViewActivity.this.getWindow();w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS); w.setStatusBarColor(Color.parseColor("#FFFFFF")); main.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
						
						_Add("#FF5722", back);
						_Add("#FF5722", edit_img);
						_Add("#FF5722", copy);
						_Add("#FF5722", share);
					}
					else {
						if (Settings.getString("Theme", "").equals("Indigo")) {
							toolbar.setBackgroundColor(0xFFFFFFFF);
							main_scroll.setBackgroundColor(0xFFFFFFFF);
							title.setTextColor(0xFF3F51B5);
							note.setTextColor(0xFF000000);
							Window w = NoteViewActivity.this.getWindow();w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS); w.setStatusBarColor(Color.parseColor("#FFFFFF")); main.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
							
							_Add("#3F51B5", back);
							_Add("#3F51B5", edit_img);
							_Add("#3F51B5", copy);
							_Add("#3F51B5", share);
						}
						else {
							if (Settings.getString("Theme", "").equals("Pink")) {
								toolbar.setBackgroundColor(0xFFFFFFFF);
								main_scroll.setBackgroundColor(0xFFFFFFFF);
								title.setTextColor(0xFFE91E63);
								note.setTextColor(0xFF000000);
								Window w = NoteViewActivity.this.getWindow();w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS); w.setStatusBarColor(Color.parseColor("#FFFFFF")); main.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
								
								_Add("#E91E63", back);
								_Add("#E91E63", edit_img);
								_Add("#E91E63", copy);
								_Add("#E91E63", share);
							}
							else {
								
							}
						}
					}
				}
			}
		}
	}
	
	
	public void _detectLinks (final TextView _txt_linkify) {
		_txt_linkify.setClickable(true);
		android.text.util.Linkify.addLinks(_txt_linkify, android.text.util.Linkify.ALL);
		_txt_linkify.setLinkTextColor(Color.parseColor("#FF009688"));
		_txt_linkify.setLinksClickable(true);
	}
	
	
	public void _textSize (final TextView _TextView1, final double _Size) {
		int j = (int) _Size;
		_TextView1.setTextSize(j);
	}
	
	
	public void _GetNote () {
		back.setVisibility(View.INVISIBLE);
		if (!AllNotes.getString("Notes", "").equals("")) {
			Notes_List = new Gson().fromJson(AllNotes.getString("Notes", ""), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
			Get_Note = Notes_List.get((int)Position);
			Title = Get_Note.get("Title").toString();
			Note = Get_Note.get("Note").toString();
		}
	}
	
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	@Deprecated
	public int getLocationX(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}
	
	@Deprecated
	public int getLocationY(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[1];
	}
	
	@Deprecated
	public int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}
	
	@Deprecated
	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
			_result.add((double)_arr.keyAt(_iIdx));
		}
		return _result;
	}
	
	@Deprecated
	public float getDip(int _input){
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels(){
		return getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels(){
		return getResources().getDisplayMetrics().heightPixels;
	}
	
}
