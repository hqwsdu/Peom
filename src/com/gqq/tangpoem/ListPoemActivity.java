package com.gqq.tangpoem;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

//在继承的过程中，你是想继承Activity还是ListActivity，取决于你的编程能力和需求。
//一般情况下，继承自更加基本的类，你的灵活性就越强。继承自更加高层的类，你的灵活性就越弱。这点是必须要明确的。
public class ListPoemActivity extends Activity {

	private ListView lv;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//

		setContentView(R.layout.activity_list_poem);

		lv = (ListView) findViewById(R.id.poemlist);

		DataDb poemdb = new DataDb(getBaseContext(), PoemApplication.POEMDB);
		List<Poem> poems = poemdb.getAllPoems();

		// setListAdapter(new PoemArrayAdapter(this, poems));
		lv.setAdapter(new PoemArrayAdapter(this, poems));

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> l, View v, int position, long id) {
				// super.onListItemClick(l, v, position, id);
				Log.d("Fanshe", "l " + l.getClass().getName());
				Log.d("Fanshe", "v " + v.getClass().getName());
				// 这里的l和v到底指什么？
				// l是parent，指的是item的父亲，即是ListView。ListView是每个Item的父亲。
				// v指的是什么？v指的是这个Item由什么构成的，自然是由list_poem.xml中确定的。
				// 这个Item是由LinerLayout构成的，当然，如果修改成RalativeLayout也是可以的。
				ListView view = (ListView) l;
				Poem poem = (Poem) view.getAdapter().getItem(position);

				Intent intent = new Intent();
				// 通过Intent对象返回结果，调用setResult方法
				intent.putExtra("selectedPoemId", poem.getId());
				setResult(MainActivity.LIST_POEM_RESULT, intent);
				ListPoemActivity.this.finish();
			}
		});

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
}
