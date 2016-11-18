package br.com.pontualmobile.utils.android.view;

import android.app.Activity;
import android.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Wellington on 22/10/2014.
 */
public class ViewMagics {
	private View parent;

	private ViewMagics(View parent) {
		this.parent = parent;
	}

	public static ViewMagics at(View parentView) {
		return new ViewMagics(parentView);
	}

	public static ViewMagics at(Activity activity) {
		return new ViewMagics(
				activity.getWindow()
						.getDecorView()
						.findViewById(android.R.id.content)
		);
	}

	public static ViewMagics at(Fragment fragment) {
		return new ViewMagics(
				fragment.getActivity()
						.getWindow()
						.getDecorView()
						.findViewById(android.R.id.content)
		);
	}

	public ViewMagics find(int viewId) {
		return at(parent.findViewById(viewId));
	}

	public ViewMagics setTextFor(int viewId, Object text) {
		((TextView) parent.findViewById(viewId)).setText(text != null ? text.toString() : "");
		return this;
	}

	public ViewMagics setTextSizeFor(int viewId, float size) {
		((TextView) parent.findViewById(viewId)).setTextSize(size);
		return this;
	}

	public ViewMagics setBackgroundFor(int viewId, int backgroundResource) {
		parent.findViewById(viewId).setBackgroundResource(backgroundResource);
		return this;
	}

	/**
	 * @param viewId
	 * @param visibility One of {View.VISIBLE}, {View.INVISIBLE} or {View.GONE}.
	 * @return the instance of {@link ViewMagics}
	 */
	public ViewMagics setVisibilityFor(int viewId, int visibility) {
		parent.findViewById(viewId).setVisibility(visibility);
		return this;
	}

	public ViewMagics setImageFor(int viewId, int imageResourceId) {
		((ImageView) parent.findViewById(viewId)).setImageResource(imageResourceId);
		return this;
	}

	public ViewMagics setClickFor(int viewId, View.OnClickListener onClickListener) {
		parent.findViewById(viewId).setOnClickListener(onClickListener);
		return this;
	}

	public ViewMagics setTextColorFor(int viewId, int color) {
		((TextView) parent.findViewById(viewId)).setTextColor(color);
		return this;
	}

	public View getView() {
		return parent;
	}

	public ViewGroup getViewGroup() {
		return (ViewGroup) parent;
	}
}
