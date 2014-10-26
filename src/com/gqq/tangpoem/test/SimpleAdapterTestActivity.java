package com.gqq.tangpoem.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.gqq.tangpoem.R;

public class SimpleAdapterTestActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_simple_adapter_test);

		ListView view = (ListView) findViewById(R.id.list1);

		// 定义数据源
		// 图片资源的ID
		int[] images = new int[] { R.drawable.item_image_a, R.drawable.item_image_b, R.drawable.item_image_c, R.drawable.item_image_d,
				R.drawable.item_image_e, R.drawable.item_image_a, R.drawable.item_image_b, R.drawable.item_image_c, R.drawable.item_image_d,
				R.drawable.item_image_e, R.drawable.item_image_a, R.drawable.item_image_b, R.drawable.item_image_c, R.drawable.item_image_d,
				R.drawable.item_image_e };

		// 创建动态数组数据源
		List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < 15; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("ItemImage", images[i]);
			map.put("ItemTitle", "This is Title " + i);
			map.put("ItemText", "This is text " + i);
			data.add(map);
		}

		// 定义from，表示那些内容需要显示，只要是map中的key就可以。
		String[] from = { "ItemImage", "ItemTitle", "ItemText" };

		// 定义to，表示应该显示在什么地方，即一个图片控件，两个text控件
		int[] to = { R.id.ItemImage, R.id.ItemTitle, R.id.ItemText };

		// SimpleAdapter sa = new SimpleAdapter(this, data,
		// R.layout.simple_list_item, from, to);

		MyBaseAdapter ma = new MyBaseAdapter(SimpleAdapterTestActivity.this, data);
		view.setAdapter(ma);
	}

}
