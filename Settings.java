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
import java.util.ArrayList;
import java.util.HashMap;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Switch;
import android.app.Activity;
import android.content.SharedPreferences;
import android.app.AlertDialog;
import android.content.DialogInterface;
import java.util.Timer;
import java.util.TimerTask;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.CompoundButton;
import android.graphics.Typeface;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.pm.PackageManager;


public class SettingsActivity extends  AppCompatActivity  { 
	
	private Timer _timer = new Timer();
	
	private double ThemePosition = 0;
	private double SizePosition = 0;
	
	private ArrayList<String> TextSize = new ArrayList<>();
	private ArrayList<String> Themes = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> All_Notes = new ArrayList<>();
	
	private LinearLayout toolbar;
	private ScrollView vscroll;
	private ImageView back_img;
	private TextView toolbar_txt;
	private LinearLayout main;
	private LinearLayout text_size_lin;
	private LinearLayout line1;
	private LinearLayout theme_lin;
	private LinearLayout line2;
	private LinearLayout detect_link_lin;
	private LinearLayout line3;
	private LinearLayout backup_lin;
	private LinearLayout Restore_lin;
	private LinearLayout line5;
	private LinearLayout linear1;
	private LinearLayout line6;
	private LinearLayout linear3;
	private LinearLayout line7;
	private LinearLayout line8;
	private TextView size_title;
	private TextView size_subtitle;
	private TextView theme_title;
	private TextView theme_subtitle;
	private TextView link_title;
	private Switch link_subtitle;
	private TextView Backup_title;
	private TextView backup_subtitle;
	private LinearLayout line4;
	private TextView restore_title;
	private TextView restore_subtitle;
	private TextView version_title;
	private TextView version_subtitle;
	private TextView dev_title;
	private TextView dev_subtitle;
	
	private SharedPreferences Settings;
	private AlertDialog.Builder TextSizeDialog;
	private AlertDialog.Builder ThemeDialog;
	private SharedPreferences AllNotes;
	private TimerTask Timer;
	private SharedPreferences AllNote;
	private Intent res = new Intent();
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.settings);
		initialize(_savedInstanceState);
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
		|| ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
			ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
		}
		else {
			initializeLogic();
		}
	}
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == 1000) {
			initializeLogic();
		}
	}
	
	private void initialize(Bundle _savedInstanceState) {
		
		toolbar = (LinearLayout) findViewById(R.id.toolbar);
		vscroll = (ScrollView) findViewById(R.id.vscroll);
		back_img = (ImageView) findViewById(R.id.back_img);
		toolbar_txt = (TextView) findViewById(R.id.toolbar_txt);
		main = (LinearLayout) findViewById(R.id.main);
		text_size_lin = (LinearLayout) findViewById(R.id.text_size_lin);
		line1 = (LinearLayout) findViewById(R.id.line1);
		theme_lin = (LinearLayout) findViewById(R.id.theme_lin);
		line2 = (LinearLayout) findViewById(R.id.line2);
		detect_link_lin = (LinearLayout) findViewById(R.id.detect_link_lin);
		line3 = (LinearLayout) findViewById(R.id.line3);
		backup_lin = (LinearLayout) findViewById(R.id.backup_lin);
		Restore_lin = (LinearLayout) findViewById(R.id.Restore_lin);
		line5 = (LinearLayout) findViewById(R.id.line5);
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		line6 = (LinearLayout) findViewById(R.id.line6);
		linear3 = (LinearLayout) findViewById(R.id.linear3);
		line7 = (LinearLayout) findViewById(R.id.line7);
		line8 = (LinearLayout) findViewById(R.id.line8);
		size_title = (TextView) findViewById(R.id.size_title);
		size_subtitle = (TextView) findViewById(R.id.size_subtitle);
		theme_title = (TextView) findViewById(R.id.theme_title);
		theme_subtitle = (TextView) findViewById(R.id.theme_subtitle);
		link_title = (TextView) findViewById(R.id.link_title);
		link_subtitle = (Switch) findViewById(R.id.link_subtitle);
		Backup_title = (TextView) findViewById(R.id.Backup_title);
		backup_subtitle = (TextView) findViewById(R.id.backup_subtitle);
		line4 = (LinearLayout) findViewById(R.id.line4);
		restore_title = (TextView) findViewById(R.id.restore_title);
		restore_subtitle = (TextView) findViewById(R.id.restore_subtitle);
		version_title = (TextView) findViewById(R.id.version_title);
		version_subtitle = (TextView) findViewById(R.id.version_subtitle);
		dev_title = (TextView) findViewById(R.id.dev_title);
		dev_subtitle = (TextView) findViewById(R.id.dev_subtitle);
		Settings = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
		TextSizeDialog = new AlertDialog.Builder(this);
		ThemeDialog = new AlertDialog.Builder(this);
		AllNotes = getSharedPreferences("All Notes", Activity.MODE_PRIVATE);
		AllNote = getSharedPreferences("All Notes", Activity.MODE_PRIVATE);
		
		back_img.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				finish();
			}
		});
		
		text_size_lin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_GetTextSize();
				TextSizeDialog.setTitle("Select Text Size");
				_TextSizeChoice(TextSizeDialog, TextSize);
				TextSizeDialog.setPositiveButton("Select", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						if (SizePosition == 0) {
							Settings.edit().putString("Text Size", "Small").commit();
						}
						else {
							if (SizePosition == 1) {
								Settings.edit().putString("Text Size", "Medium").commit();
							}
							else {
								if (SizePosition == 2) {
									Settings.edit().putString("Text Size", "Large").commit();
								}
								else {
									
								}
							}
						}
						finishAffinity();
					}
				});
				TextSizeDialog.create().show();
			}
		});
		
		theme_lin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_GetThemeData();
				ThemeDialog.setTitle("Choose Theme");
				_ThemeChoice(ThemeDialog, Themes);
				ThemeDialog.setPositiveButton("Select", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						if (ThemePosition == 0) {
							Settings.edit().putString("Theme", "Default").commit();
						}
						else {
							if (ThemePosition == 1) {
								Settings.edit().putString("Theme", "Dark").commit();
							}
							else {
								if (ThemePosition == 2) {
									Settings.edit().putString("Theme", "Blue Grey").commit();
								}
								else {
									if (ThemePosition == 3) {
										Settings.edit().putString("Theme", "Orange").commit();
									}
									else {
										if (ThemePosition == 4) {
											Settings.edit().putString("Theme", "Indigo").commit();
										}
										else {
											if (ThemePosition == 5) {
												Settings.edit().putString("Theme", "Pink").commit();
											}
											else {
												
											}
										}
									}
								}
							}
						}
						finishAffinity();
					}
				});
				ThemeDialog.create().show();
			}
		});
		
		backup_lin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				FileUtil.writeFile(FileUtil.getExternalStorageDir().concat("/".concat(".Advanced Notepad__Backup".concat("/".concat("Notes.json")))), AllNotes.getString("Notes", ""));
				SketchwareUtil.showMessage(getApplicationContext(), "backup created successfuly !!! ");
			}
		});
		
		Restore_lin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_restore();
			}
		});
		
		link_subtitle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton _param1, boolean _param2)  {
				final boolean _isChecked = _param2;
				if (_isChecked) {
					link_subtitle.setChecked(true);
					Settings.edit().putString("Detect Link", "True").commit();
					link_subtitle.setText("Link detection - On");
				}
				else {
					if (!_isChecked) {
						link_subtitle.setChecked(false);
						Settings.edit().putString("Detect Link", "False").commit();
						link_subtitle.setText("Link detection - Off");
					}
					else {
						
					}
				}
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
	
	public void _OnCreate () {
		toolbar_txt.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/productsansbold.ttf"), 0);
		size_title.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/productsansbold.ttf"), 0);
		size_subtitle.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/productsansmedium.ttf"), 0);
		theme_title.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/productsansbold.ttf"), 0);
		theme_subtitle.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/productsansmedium.ttf"), 0);
		link_title.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/productsansbold.ttf"), 0);
		link_subtitle.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/productsansmedium.ttf"), 0);
		Backup_title.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/productsansmedium.ttf"), 0);
		backup_subtitle.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/productsansmedium.ttf"), 0);
		restore_title.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/productsansmedium.ttf"), 0);
		restore_subtitle.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/productsansmedium.ttf"), 0);
		version_title.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/productsansmedium.ttf"), 0);
		version_subtitle.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/productsansmedium.ttf"), 0);
		dev_title.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/productsansmedium.ttf"), 0);
		dev_subtitle.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/productsansmedium.ttf"), 0);
		_GetSettingsData();
		_ChoiceDialogData();
	}
	
	
	public void _Add (final String _Colour, final ImageView _Imageview) {
		_Imageview.getDrawable().setColorFilter(Color.parseColor(_Colour), PorterDuff.Mode.SRC_IN);
	}
	
	
	public void _GetSettingsData () {
		size_subtitle.setText("Select The Text Size - ".concat(Settings.getString("Text Size", "")));
		theme_subtitle.setText("Selected Theme - ".concat(Settings.getString("Theme", "")));
		if (Settings.getString("Detect Link", "").equals("True")) {
			link_subtitle.setChecked(true);
			link_subtitle.setText("Link detection - On");
		}
		else {
			link_subtitle.setChecked(false);
			link_subtitle.setText("Link detection - Off");
		}
		if (Settings.getString("Text Size", "").equals("Small")) {
			
		}
		else {
			if (Settings.getString("Text Size", "").equals("Medium")) {
				_textSize(toolbar_txt, 20);
				_textSize(size_title, 20);
				_textSize(size_subtitle, 16);
				_textSize(theme_title, 20);
				_textSize(theme_subtitle, 16);
				_textSize(link_title, 20);
				_textSize(link_subtitle, 16);
				_textSize(Backup_title, 20);
				_textSize(backup_subtitle, 16);
				_textSize(restore_title, 20);
				_textSize(restore_subtitle, 16);
				_textSize(version_title, 20);
				_textSize(version_subtitle, 16);
				_textSize(dev_title, 20);
				_textSize(dev_subtitle, 16);
			}
			else {
				if (Settings.getString("Text Size", "").equals("Large")) {
					_textSize(toolbar_txt, 22);
					_textSize(size_title, 22);
					_textSize(size_subtitle, 18);
					_textSize(theme_title, 22);
					_textSize(theme_subtitle, 18);
					_textSize(link_title, 22);
					_textSize(link_subtitle, 18);
					_textSize(Backup_title, 22);
					_textSize(backup_subtitle, 18);
					_textSize(restore_title, 22);
					_textSize(restore_subtitle, 18);
					_textSize(version_title, 22);
					_textSize(version_subtitle, 18);
					_textSize(dev_title, 22);
					_textSize(dev_subtitle, 18);
				}
				else {
					
				}
			}
		}
		if (Settings.getString("Theme", "").equals("Default")) {
			Window w = SettingsActivity.this.getWindow();w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS); w.setStatusBarColor(Color.parseColor("#FFFFFF")); main.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
			_Add("#FF009688", back_img);
		}
		else {
			if (Settings.getString("Theme", "").equals("Dark")) {
				Window w = SettingsActivity.this.getWindow();w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS); w.setStatusBarColor(Color.parseColor("#263238"));
				_Add("#263238", back_img);
				toolbar.setBackgroundColor(0xFF263238);
				vscroll.setBackgroundColor(0xFF263238);
				toolbar_txt.setTextColor(0xFFFFFFFF);
				size_title.setTextColor(0xFFFFFFFF);
				size_subtitle.setTextColor(0xFFFFFFFF);
				theme_title.setTextColor(0xFFFFFFFF);
				theme_subtitle.setTextColor(0xFFFFFFFF);
				link_title.setTextColor(0xFFFFFFFF);
				link_subtitle.setTextColor(0xFFFFFFFF);
				Backup_title.setTextColor(0xFFFFFFFF);
				backup_subtitle.setTextColor(0xFFFFFFFF);
				restore_title.setTextColor(0xFFFFFFFF);
				restore_subtitle.setTextColor(0xFFFFFFFF);
				version_title.setTextColor(0xFFFFFFFF);
				version_subtitle.setTextColor(0xFFFFFFFF);
				dev_title.setTextColor(0xFFFFFFFF);
				dev_subtitle.setTextColor(0xFFFFFFFF);
				link_subtitle.getTrackDrawable().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
				line1.setBackgroundColor(0xFFFFFFFF);
				line2.setBackgroundColor(0xFFFFFFFF);
				line3.setBackgroundColor(0xFFFFFFFF);
				line4.setBackgroundColor(0xFFFFFFFF);
				line5.setBackgroundColor(0xFFFFFFFF);
				line6.setBackgroundColor(0xFFFFFFFF);
				line7.setBackgroundColor(0xFFFFFFFF);
				line8.setBackgroundColor(0xFFFFFFFF);
			}
			else {
				if (Settings.getString("Theme", "").equals("Blue Grey")) {
					Window w = SettingsActivity.this.getWindow();w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS); w.setStatusBarColor(Color.parseColor("#FFFFFF")); main.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
					_Add("#607D8B", back_img);
					toolbar.setBackgroundColor(0xFFFFFFFF);
					vscroll.setBackgroundColor(0xFFFFFFFF);
					toolbar_txt.setTextColor(0xFF607D8B);
					size_title.setTextColor(0xFF607D8B);
					size_subtitle.setTextColor(0xFF616161);
					theme_title.setTextColor(0xFF607D8B);
					theme_subtitle.setTextColor(0xFF616161);
					link_title.setTextColor(0xFF607D8B);
					link_subtitle.setTextColor(0xFF616161);
					Backup_title.setTextColor(0xFF607D8B);
					backup_subtitle.setTextColor(0xFF616161);
					restore_title.setTextColor(0xFF607D8B);
					restore_subtitle.setTextColor(0xFF616161);
					version_title.setTextColor(0xFF607D8B);
					version_subtitle.setTextColor(0xFF616161);
					dev_title.setTextColor(0xFF607D8B);
					dev_subtitle.setTextColor(0xFF616161);
					link_subtitle.getTrackDrawable().setColorFilter(Color.parseColor("#607D8B"), PorterDuff.Mode.SRC_IN);
					line1.setBackgroundColor(0xFFE0E0E0);
					line2.setBackgroundColor(0xFFE0E0E0);
					line3.setBackgroundColor(0xFFE0E0E0);
					line4.setBackgroundColor(0xFFE0E0E0);
					line5.setBackgroundColor(0xFFE0E0E0);
					line6.setBackgroundColor(0xFFE0E0E0);
					line7.setBackgroundColor(0xFFE0E0E0);
					line8.setBackgroundColor(0xFFE0E0E0);
				}
				else {
					if (Settings.getString("Theme", "").equals("Orange")) {
						Window w = SettingsActivity.this.getWindow();w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS); w.setStatusBarColor(Color.parseColor("#FFFFFF")); main.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
						_Add("#FF5722", back_img);
						toolbar.setBackgroundColor(0xFFFFFFFF);
						vscroll.setBackgroundColor(0xFFFFFFFF);
						toolbar_txt.setTextColor(0xFFFF5722);
						size_title.setTextColor(0xFFFF5722);
						size_subtitle.setTextColor(0xFF616161);
						theme_title.setTextColor(0xFFFF5722);
						theme_subtitle.setTextColor(0xFF616161);
						link_title.setTextColor(0xFFFF5722);
						link_subtitle.setTextColor(0xFF616161);
						Backup_title.setTextColor(0xFFFF5722);
						backup_subtitle.setTextColor(0xFF616161);
						restore_title.setTextColor(0xFFFF5722);
						restore_subtitle.setTextColor(0xFF616161);
						version_title.setTextColor(0xFFFF5722);
						version_subtitle.setTextColor(0xFF616161);
						dev_title.setTextColor(0xFFFF5722);
						dev_subtitle.setTextColor(0xFF616161);
					}
					else {
						if (Settings.getString("Theme", "").equals("Indigo")) {
							Window w = SettingsActivity.this.getWindow();w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS); w.setStatusBarColor(Color.parseColor("#FFFFFF")); main.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
							_Add("#3F51B5", back_img);
							toolbar.setBackgroundColor(0xFFFFFFFF);
							vscroll.setBackgroundColor(0xFFFFFFFF);
							toolbar_txt.setTextColor(0xFF3F51B5);
							size_title.setTextColor(0xFF3F51B5);
							size_subtitle.setTextColor(0xFF616161);
							theme_title.setTextColor(0xFF3F51B5);
							theme_subtitle.setTextColor(0xFF616161);
							link_title.setTextColor(0xFF3F51B5);
							link_subtitle.setTextColor(0xFF616161);
							Backup_title.setTextColor(0xFF3F51B5);
							backup_subtitle.setTextColor(0xFF616161);
							restore_title.setTextColor(0xFF3F51B5);
							restore_subtitle.setTextColor(0xFF616161);
							version_title.setTextColor(0xFF3F51B5);
							version_subtitle.setTextColor(0xFF616161);
							dev_title.setTextColor(0xFF3F51B5);
							dev_subtitle.setTextColor(0xFF616161);
							link_subtitle.getTrackDrawable().setColorFilter(Color.parseColor("#3F51B5"), PorterDuff.Mode.SRC_IN);
							line1.setBackgroundColor(0xFFE0E0E0);
							line2.setBackgroundColor(0xFFE0E0E0);
							line3.setBackgroundColor(0xFFE0E0E0);
							line4.setBackgroundColor(0xFFE0E0E0);
							line5.setBackgroundColor(0xFFE0E0E0);
							line6.setBackgroundColor(0xFFE0E0E0);
							line7.setBackgroundColor(0xFFE0E0E0);
							line8.setBackgroundColor(0xFFE0E0E0);
						}
						else {
							if (Settings.getString("Theme", "").equals("Pink")) {
								Window w = SettingsActivity.this.getWindow();w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS); w.setStatusBarColor(Color.parseColor("#FFFFFF")); main.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
								_Add("#E91E63", back_img);
								toolbar.setBackgroundColor(0xFFFFFFFF);
								vscroll.setBackgroundColor(0xFFFFFFFF);
								toolbar_txt.setTextColor(0xFFE91E63);
								size_title.setTextColor(0xFFE91E63);
								size_subtitle.setTextColor(0xFF616161);
								theme_title.setTextColor(0xFFE91E63);
								theme_subtitle.setTextColor(0xFF616161);
								link_title.setTextColor(0xFFE91E63);
								link_subtitle.setTextColor(0xFF616161);
								Backup_title.setTextColor(0xFFE91E63);
								backup_subtitle.setTextColor(0xFF616161);
								restore_title.setTextColor(0xFFE91E63);
								restore_subtitle.setTextColor(0xFF616161);
								version_title.setTextColor(0xFFE91E63);
								version_subtitle.setTextColor(0xFF616161);
								dev_title.setTextColor(0xFFE91E63);
								dev_subtitle.setTextColor(0xFF616161);
								link_subtitle.getTrackDrawable().setColorFilter(Color.parseColor("#E91E63"), PorterDuff.Mode.SRC_IN);
								line1.setBackgroundColor(0xFFE0E0E0);
								line2.setBackgroundColor(0xFFE0E0E0);
								line3.setBackgroundColor(0xFFE0E0E0);
								line4.setBackgroundColor(0xFFE0E0E0);
								line5.setBackgroundColor(0xFFE0E0E0);
								line6.setBackgroundColor(0xFFE0E0E0);
								line7.setBackgroundColor(0xFFE0E0E0);
								line8.setBackgroundColor(0xFFE0E0E0);
							}
							else {
								
							}
						}
					}
				}
			}
		}
	}
	
	
	public void _ThemeChoice (final AlertDialog.Builder _Dialog, final ArrayList<String> _ListString) {
		final CharSequence[] _items = _ListString.toArray(new String[_ListString.size()]);
		_Dialog.setSingleChoiceItems(_items, (int)ThemePosition, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				ThemePosition = which;
			}});
	}
	
	
	public void _TextSizeChoice (final AlertDialog.Builder _Dialog, final ArrayList<String> _ListString) {
		final CharSequence[] _items = _ListString.toArray(new String[_ListString.size()]);
		_Dialog.setSingleChoiceItems(_items, (int)SizePosition, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				SizePosition = which;
			}});
	}
	
	
	public void _ChoiceDialogData () {
		TextSize.add("Small");
		TextSize.add("Medium");
		TextSize.add("Large");
		// Divide
		Themes.add("Default");
		Themes.add("Dark");
		Themes.add("Blue Grey");
		Themes.add("Orange");
		Themes.add("Indigo");
		Themes.add("Pink");
	}
	
	
	public void _textSize (final TextView _TextView1, final double _Size) {
		int j = (int) _Size;
		_TextView1.setTextSize(j);
	}
	
	
	public void _GetTextSize () {
		if (Settings.getString("Text Size", "").equals("Small")) {
			SizePosition = 0;
		}
		else {
			if (Settings.getString("Text Size", "").equals("Medium")) {
				SizePosition = 1;
			}
			else {
				if (Settings.getString("Text Size", "").equals("Large")) {
					SizePosition = 2;
				}
				else {
					
				}
			}
		}
	}
	
	
	public void _GetThemeData () {
		if (Settings.getString("Theme", "").equals("Default")) {
			ThemePosition = 0;
		}
		else {
			if (Settings.getString("Theme", "").equals("Dark")) {
				ThemePosition = 1;
				TextSizeDialog = new AlertDialog.Builder(this,AlertDialog.THEME_DEVICE_DEFAULT_DARK);
				ThemeDialog = new AlertDialog.Builder(this,AlertDialog.THEME_DEVICE_DEFAULT_DARK);
			}
			else {
				if (Settings.getString("Theme", "").equals("Blue Grey")) {
					ThemePosition = 2;
				}
				else {
					if (Settings.getString("Theme", "").equals("Orange")) {
						ThemePosition = 3;
					}
					else {
						if (Settings.getString("Theme", "").equals("Indigo")) {
							ThemePosition = 4;
						}
						else {
							if (Settings.getString("Theme", "").equals("Pink")) {
								ThemePosition = 5;
							}
							else {
								
							}
						}
					}
				}
			}
		}
	}
	
	
	public void _restore () {
		AllNotes.edit().putString("Notes", FileUtil.readFile(FileUtil.getExternalStorageDir().concat("/".concat(".Advanced Notepad__Backup".concat("/".concat("Notes.json")))))).commit();
		res.setClass(getApplicationContext(), MainActivity.class);
		startActivity(res);
		SketchwareUtil.showMessage(getApplicationContext(), "Restored successfully  !!! ");
		finishAffinity();
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
