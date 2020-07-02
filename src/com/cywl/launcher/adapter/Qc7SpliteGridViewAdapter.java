package com.cywl.launcher.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cywl.launcher.HomeActivity;
import com.cywl.launcher.R;
import com.cywl.launcher.model.Constances;
import com.cywl.launcher.model.ItemData;

public class Qc7SpliteGridViewAdapter extends BaseAdapter {
	private Context mContext;
	private List<ItemData> datas;
	private LayoutInflater inflater;
	private int mWindowsStatus;

	public Qc7SpliteGridViewAdapter(Context mContext, List<ItemData> datas) {
		this.mContext = mContext;
		this.datas = datas;
		this.mWindowsStatus = HomeActivity.MW_DEFAULT_STACK_WINDOW;
		inflater = LayoutInflater.from(this.mContext);
	}

	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	public ItemData getItem(int position) {
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (null == convertView) {
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_view_qc7_splite, null);
			viewHolder.imageView = (ImageView) convertView.findViewById(R.id.image);
//			viewHolder.name = (TextView) convertView.findViewById(R.id.name);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		ItemData data = datas.get(position);
		viewHolder.imageView.setImageResource(data.getmImage());
//		viewHolder.name.setText(mContext.getResources().getString(data.getmName()));
//		viewHolder.name.setTextSize(mWindowsStatus == HomeActivity.MW_NORMAL_STACK_WINDOW ? 20 : 30);
		return convertView;
	}

	private static class ViewHolder {
		ImageView imageView;
		TextView name;
	}

	public void updateViews(int status) {
		if (status == HomeActivity.MW_MAX_STACK_WINDOW) {
			datas.clear();
			ItemData data = null;
			if (Constances.isOversea) {
				for (int i = 0; i < Constances.m9ThreePackageOversea.length; i++) {
					data = new ItemData(Constances.m9ThreePackageOversea[i],
							Constances.img9ThreeNameStringID[i],
							Constances.img9ThreeImageMax[i]
							);
					datas.add(data);
				}
			} else {
				for (int i = 0; i < Constances.m9ThreePackage.length; i++) {
					data = new ItemData(Constances.m9ThreePackage[i],
							Constances.img9ThreeNameStringID[i],
							Constances.img9ThreeImageMax[i]
							);
					datas.add(data);
				}
			}
			mWindowsStatus = status;
			notifyDataSetChanged();
		} else if (status == HomeActivity.MW_NORMAL_STACK_WINDOW) {
			datas.clear();
			ItemData data = null;
			if (Constances.isOversea) {
				for (int i = 0; i < Constances.m9ThreePackageOversea.length; i++) {
					data = new ItemData(Constances.m9ThreePackageOversea[i],
							Constances.img9ThreeNameStringID[i],
							Constances.img9ThreeImage[i]
							);
					datas.add(data);
				}
			} else {
				for (int i = 0; i < Constances.m9ThreePackage.length; i++) {
					data = new ItemData(Constances.m9ThreePackage[i],
							Constances.img9ThreeNameStringID[i],
							Constances.img9ThreeImage[i]
							);
					datas.add(data);
				}
			}
			mWindowsStatus = status;
			notifyDataSetChanged();
		}

	}

}
