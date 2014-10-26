package com.gqq.tangpoem.test;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gqq.tangpoem.R;

public class MyBaseAdapter extends BaseAdapter {

	private int[] colors = new int[] { 0xff3cb371, 0xffa0a0a0 };
	private Context mContext;
	private List<HashMap<String, Object>> dataList;

	public MyBaseAdapter(Context context, List<HashMap<String, Object>> dataList) {
		this.mContext = context;
		this.dataList = dataList;
	}

	/**
	 * getCount()方法得到绘制次数
	 */
	@Override
	public int getCount() {
		return dataList.size();
	}

	/**
	 * 而getItem()和getItemId()则在需要处理和取得Adapter中的数据时调用
	 */
	@Override
	public Object getItem(int position) {
		return dataList.get(position);
	}

	/**
	 * 而getItem()和getItemId()则在需要处理和取得Adapter中的数据时调用
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * 通过getView()方法一层一层进行绘制
	 * getView()方法用来获得绘制每个item的View对象，如果每次getView()被执行都new出一个View对象
	 * ，长此以往会产生很大的消耗， 特别当item中还有Bitmap等，甚至会造成OOM的错误导致程序崩溃。<br>
	 * 从上面的代码可以看到getView()有一个convertView参数 ，这个参数用来缓存View对象
	 * 。当ListView滑动的过程中，会有item被滑出屏幕而不再被使用，这时候Android会回收这个item的view
	 * ，这个view也就是这里的convertView
	 * 。这样如果convertView不为null，就不用new出一个新的View对象，只用往convertView中填充新的item
	 * ，这样就省去了new View的大量开销。
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.simple_list_item, null);
			holder.image = (ImageView) convertView.findViewById(R.id.ItemImage);
			holder.title = (TextView) convertView.findViewById(R.id.ItemTitle);
			holder.text = (TextView) convertView.findViewById(R.id.ItemText);

			// 将holder绑定到convertView
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		// 向ViewHolder中填入的数据
		holder.image.setImageResource((Integer) ((HashMap<String, Object>) getItem(position)).get("ItemImage"));
		holder.title.setText("just a test");
		// 由于getItem这个函数已经被重写了，并且写的是dataList.get(position),
		// 那么返回的当然是HashMap了，这个有什么问题吗？
		holder.text.setText((String) (((HashMap<String, Object>) getItem(position)).get("ItemText")));

		int colorPos = position % colors.length;
		convertView.setBackgroundColor(colors[colorPos]);

		return convertView;
	}

	/**
	 * ViewHolder类用以储存item中控件的引用
	 */
	final class ViewHolder {
		ImageView image;
		TextView title;
		TextView text;
	}

}
