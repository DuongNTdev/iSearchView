package com.quadnode.activities;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class SearchBarExampleActivity extends Activity implements
		OnClickListener, TextWatcher {

	EditText searchEditText;
	ImageView clearSearchBox;
	Button cancel;
	int realWidth = -1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.isearch_bar);

		cancel = (Button) findViewById(R.id.cancel);

		searchEditText = (EditText) findViewById(R.id.search_box);
		searchEditText.addTextChangedListener(this);
		clearSearchBox = (ImageView) findViewById(R.id.clear_search_box);
		clearSearchBox.setOnClickListener(this);
		cancel.setOnClickListener(this);

		cancel.getViewTreeObserver().addOnGlobalLayoutListener(
				new ViewTreeObserver.OnGlobalLayoutListener() {
					@Override
					public void onGlobalLayout() {
						cancel.setPivotX(cancel.getMeasuredWidth());
						if (realWidth == -1){
							realWidth = cancel.getMeasuredWidth();
							setWidthToCancelButton(0);
						}
					}
				});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.clear_search_box:
			clearSearchBox.setVisibility(View.GONE);
			searchEditText.setText("");
			break;
		case R.id.cancel:
			showHideCancelControl(false);
			searchEditText.setText("");
			break;
		default:
			break;
		}
	}

	private void showHideCancelControl(boolean show) {
		if (!show) {
			ValueAnimator anim = ValueAnimator.ofInt(cancel.getMeasuredWidth(),
					0);
			ObjectAnimator alpha = ObjectAnimator.ofFloat(cancel, View.ALPHA, 1f,
					0f);
			AnimatorSet animator = new AnimatorSet();
			animator.playTogether(anim, alpha);
			animator.start();
			anim.addUpdateListener(new AnimatorUpdateListener() {

				@Override
				public void onAnimationUpdate(ValueAnimator valueAnimator) {
					int val = (Integer) valueAnimator.getAnimatedValue();
					setWidthToCancelButton(val);
				}
			});

			animator.start();

		} else {
			if(cancel.getMeasuredWidth() > 0 ) return;
			ValueAnimator anim = ValueAnimator.ofInt(0, realWidth);
			ObjectAnimator alpha = ObjectAnimator.ofFloat(cancel, View.ALPHA, 0f,
					1f);
			AnimatorSet animator = new AnimatorSet();
			animator.playTogether(anim, alpha);
			animator.start();
			anim.addUpdateListener(new AnimatorUpdateListener() {

				@Override
				public void onAnimationUpdate(ValueAnimator valueAnimator) {
					int val = (Integer) valueAnimator.getAnimatedValue();
					setWidthToCancelButton(val);
				}
			});

			animator.start();
		}
	}
	private void setWidthToCancelButton(int width){
		ViewGroup.LayoutParams layoutParams = cancel
				.getLayoutParams();
		layoutParams.width = width;
		cancel.setLayoutParams(layoutParams);
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		if (searchEditText.getText().toString().equals("")) {
			clearSearchBox.setVisibility(View.GONE);
		} else {
			clearSearchBox.setVisibility(View.VISIBLE);
			showHideCancelControl(true);
		}
	}

	@Override
	public void afterTextChanged(Editable arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub

	}

}