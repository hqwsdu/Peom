package com.gqq.tangpoem;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewManager;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

//在继承的过程中，你是想继承Activity还是ListActivity，取决于你的编程能力和需求。
//一般情况下，继承自更加基本的类，你的灵活性就越强。继承自更加高层的类，你的灵活性就越弱。这点是必须要明确的。
public class ListPoemActivity extends Activity {

	private ListView lv;

	// 几个分类按钮，诗、词、文
	ImageView imgPoem, imgSong, imgAll, imgEssay;

	// 当前显示方式
	DisplayMode mode;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//

		setContentView(R.layout.activity_list_poem);

		imgAll = (ImageView) findViewById(R.id.imgAll);
		imgPoem = (ImageView) findViewById(R.id.imgPoem);
		imgSong = (ImageView) findViewById(R.id.imgSong);
		imgEssay = (ImageView) findViewById(R.id.imgEssay);
		lv = (ListView) findViewById(R.id.poem_list);

		DataDb poemdb = new DataDb(getBaseContext(), PoemApplication.POEMDB);
		List<Poem> poems = poemdb.getAll();

		// setListAdapter(new PoemArrayAdapter(this, poems));
		lv.setAdapter(new PoemArrayAdapter(this, poems));
		mode = DisplayMode.All;

		// 绑定操作事件
		lv.setOnItemClickListener(new ItemClickClass());

		setListener(imgAll);
		setListener(imgPoem);
		setListener(imgSong);
		setListener(imgEssay);
	}

	private void setListener(ImageView v) {
		v.setOnClickListener(new ImgClickClass(v.getId()));
	}

	class ImgClickClass implements View.OnClickListener {
		int viewId;

		final String buttonTag = "Image Button";

		public ImgClickClass(int viewId) {
			this.viewId = viewId;
		}

		@Override
		public void onClick(View v) {
			switch (viewId) {
			case R.id.imgAll:
				Log.d(buttonTag, "all");
				getPoemsFromMode(DisplayMode.All);
				// imgAll.setImageDrawable(getResources().getDrawable(R.drawable.essay));
				break;
			case R.id.imgPoem:
				getPoemsFromMode(DisplayMode.Poem);
				Log.d(buttonTag, "peom");
				break;
			case R.id.imgSong:
				getPoemsFromMode(DisplayMode.Song);
				Log.d(buttonTag, "song");
				break;
			case R.id.imgEssay:
				getPoemsFromMode(DisplayMode.Essay);
				Log.d(buttonTag, "essay");
				break;
			default:
				break;
			}
		}

	}

	/**
	 * 得到所有的诗
	 */
	private void getPoemsFromMode(DisplayMode md) {
		if (mode == md)
			return;
		DataDb poemdb = new DataDb(getBaseContext(), PoemApplication.POEMDB);
		List<Poem> poems = null;

		if (md == DisplayMode.All)
			poems = poemdb.getAll();
		else if (md == DisplayMode.Poem) {
			poems = poemdb.getPoems();
		} else if (md == DisplayMode.Song) {
			poems = poemdb.getSongs();
		} else {
			poems = poemdb.getEssays();
		}

		lv.setAdapter(new PoemArrayAdapter(this, poems));
		mode = md;
	}

	enum DisplayMode {
		All, Poem, Song, Essay
	}

	class ItemClickClass implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
			// super.onListItemClick(l, v, position, id);
			Log.d("Fanshe", "l " + parent.getClass().getName());
			Log.d("Fanshe", "v " + v.getClass().getName());
			// 这里的l和v到底指什么？
			// l是parent，指的是item的父亲，即是ListView。ListView是每个Item的父亲。
			// v指的是什么？v指的是这个Item由什么构成的，自然是由list_poem.xml中确定的。
			// 这个Item是由LinerLayout构成的，当然，如果修改成RalativeLayout也是可以的。
			ListView view = (ListView) parent;
			Poem poem = (Poem) view.getAdapter().getItem(position);

			Intent intent = new Intent();
			// 通过Intent对象返回结果，调用setResult方法
			intent.putExtra("selectedPoemId", poem.getId());
			setResult(MainActivity.LIST_POEM_RESULT, intent);
			ListPoemActivity.this.finish();
		}

	}
}

// 如果继承自ListActivity和直接继承自Activity是完全不一样的。
// @Override
// protected void onListItemClick(ListView l, View v, int position, long id)
// {
// // super.onListItemClick(l, v, position, id);
// Log.d("Fanshe", "l" + l.getClass().getName());
// Log.d("Fanshe", "v" + v.getClass().getName());
// Poem poem = (Poem) getListAdapter().getItem(position);
//
// Intent intent = new Intent();
// // 通过Intent对象返回结果，调用setResult方法
// intent.putExtra("selectedPoemId", poem.getId());
// setResult(MainActivity.LIST_POEM_RESULT, intent);
// ListPoemActivity.this.finish();
// }

// TextView tv = (TextView) findViewById(R.id.btnTest);
// tv.setOnClickListener(new View.OnClickListener() {
//
// @Override
// public void onClick(View v) {
// Log.d("Button", "clicked");
// DataDb poemdb = new DataDb(getBaseContext(), PoemApplication.POEMDB);
// List<Poem> poems = poemdb.getAllDeletedPoems();
//
// // setListAdapter(new PoemArrayAdapter(this, poems));
// lv.setAdapter(new PoemArrayAdapter(ListPoemActivity.this, poems));
// lv.setOnItemClickListener(new ItemClickClass());
// }
// });

// ListView listView = getListView();
// listView.setTextFilterEnabled(true);
//
// listView.setOnItemClickListener(new OnItemClickListener() {
// public void onItemClick(AdapterView<?> parent, View view, int
// position, long id) {
// // When clicked, show a toast with the TextView text
// Toast.makeText(getApplicationContext(), ((TextView) view).getText(),
// Toast.LENGTH_SHORT).show();
// }
// });
