package com.gqq.tangpoem.test;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.gqq.tangpoem.R;

public class ItemTestActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_test);

		String[] data = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };

		ListView view = (ListView) findViewById(R.id.list1);

		// 这个Arrayadapter，第一个参数是上下文，第二个参数是xml，第三个参数是data
		// 这个例子说明了如何将一个数组传入ArrayAdapter中，并且，ArrayAdapter对于显示列表特别有用
		ArrayAdapter<String> aa = new ArrayAdapter<String>(this, R.layout.list_item_test, data);
		view.setAdapter(aa);
	}

}
