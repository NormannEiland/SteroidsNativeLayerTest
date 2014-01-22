package magnetix.uitestplugin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;


import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.http.conn.routing.RouteInfo.LayerType;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;

public class UITestPlugin extends CordovaPlugin {

    CallbackContext callbackContext;
    
	@Override
	/**
	 * Called by each javascript plugin function
	 */
	public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
		this.callbackContext = callbackContext;
		
		try {
			// Action selector
			if ("open".equals(action)) {
				final String text = args.getString(0);	
	            cordova.getActivity().runOnUiThread(new Runnable() {
	                public void run() {
	                	open(text, callbackContext);
	                }
	            });
	            return true;
			}
		} catch (IllegalStateException e){
			callbackContext.error(e.getMessage());
		}
		
		// Method not found
		return false;
	}
	
	private void open(String message, CallbackContext callbackContext) {
		Activity ctx = cordova.getActivity();
		
		List<String> classNames = new ArrayList<String>();
		List<View> views = new ArrayList<View>();
		
		View v = ctx.getCurrentFocus();
		views.add(v);
		Class<? extends View> vc = v.getClass();
		String vcn = vc.getName();
		classNames.add(vcn);
		
		View currentView = v;
		try	{
			//while (!classNames.get(classNames.size() - 1).startsWith("com.android.internal")) {
			while (true) {
				currentView = (View) currentView.getParent();
				views.add(currentView);
				Class<? extends View> viewClass = currentView.getClass();
				classNames.add(viewClass.getName());
			}
		} catch (Exception err) {
			// Went beyond view stack
		}
		
//vp = (LinearLayout)v.getParent();
//FrameLayout vpp = (FrameLayout)vp.getParent();
//vp.animate().x((float)500).y((float)500);
		
		
		//ScaleAnimation animation = new ScaleAnimation(Animation.RELATIVE_TO_SELF, (float)0.2, Animation.RELATIVE_TO_SELF, (float)0.2);
		//vp.startAnimation(animation);
		
		//final int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
	     //final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
	     //vp.measure(widthSpec, heightSpec);
	     
	     //ValueAnimator mAnimator = slideAnimator(vp.getMeasuredHeight(), 300, true);
	     //ValueAnimator mAnimator2 = slideAnimator(vp.getMeasuredWidth(), 300, false);
	     //mAnimator.start();
	     //mAnimator2.start();
		
		//LayoutParams lp = vp.getLayoutParams();
		//lp.height = 300;
		//lp.width = 300;
		//v.setBackgroundColor(Color.RED);
		//vp.setLayoutParams(new LinearLayout.LayoutParams(300, 300));
		
		RelativeLayout lContainerLayout = new RelativeLayout(ctx);
		lContainerLayout.setBackgroundColor(Color.YELLOW);
		
		lContainerLayout.setLayoutParams(new RelativeLayout.LayoutParams( LayoutParams.MATCH_PARENT , LayoutParams.MATCH_PARENT ));

		// Custom view
		Button mCustomView = new Button(ctx);
		mCustomView.setText("Test4");
		LayoutParams lButtonParams = new RelativeLayout.LayoutParams( LayoutParams.WRAP_CONTENT , LayoutParams.WRAP_CONTENT);
		((android.widget.RelativeLayout.LayoutParams) lButtonParams).addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		mCustomView.setLayoutParams(lButtonParams);
		lContainerLayout.addView(mCustomView);

		
		View outerAppgyver = null;
		View outerRelative = null;
		Boolean appGyverFound = false;
		Boolean relativeFound = false;
		for(Iterator<View> i = views.iterator(); i.hasNext(); ) {
		    View vi = i.next();
		    if (appGyverFound) {
		    	if (vi.getClass().getName().contains("RelativeLayout")) {
		    	//if (vi.getClass().getName().contains("Frame")) {
		    		relativeFound = true;
		    		outerRelative = vi;
		    	}
		    }	
		    if (vi.getClass().getName().contains("appgyver")) {
		    //if (vi.getClass().getName().contains("Keyboard")) {	
		    	appGyverFound = true;
		    	outerAppgyver = vi;
		    }	
		}
		
		if (relativeFound && outerRelative != null) {
			((RelativeLayout)outerRelative).addView(lContainerLayout, 0);
			//((FrameLayout)outerRelative).addView(lContainerLayout, 0);
		}
		if (appGyverFound && outerAppgyver != null) {
			outerAppgyver.animate().x((float)500).y((float)500);
		}
		
		// Adding full screen container
		//ctx.addContentView(lContainerLayout, new LayoutParams( LayoutParams.MATCH_PARENT , LayoutParams.MATCH_PARENT ));
		
		List<String> newClassNames = new ArrayList<String>();
		Class<? extends View> newVc = lContainerLayout.getClass();
		String newVcn = newVc.getName();
		newClassNames.add(newVcn);
		
		View newCurrentView = lContainerLayout;
		try	{
			//while (!classNames.get(classNames.size() - 1).startsWith("com.android.internal")) {
			while (true) {
				newCurrentView = (View) newCurrentView.getParent();
				Class<? extends View> newViewClass = newCurrentView.getClass();
				newClassNames.add(newViewClass.getName());
			}
		} catch (Exception err) {
			// Went beyond view stack
		}
		
		
		//vp.bringToFront();
		
		
//ViewParent v_p = lContainerLayout.getParent();
		
		
		//lContainerLayout.bringToFront();
		
		String layouts = "";
		for(Iterator<String> i = classNames.iterator(); i.hasNext(); ) {
		    String className = i.next();
		    layouts += className + "\r\n";
		}
		
		layouts += "\r\n\r\n";
		for(Iterator<String> i = newClassNames.iterator(); i.hasNext(); ) {
		    String newClassName = i.next();
		    layouts += newClassName + "\r\n";
		}
		
		callbackContext.success(layouts); // Thread-safe.
	}
	
	LinearLayout vp;
	
	private ValueAnimator slideAnimator(int start, int end, final Boolean height) {
		ValueAnimator animator = ValueAnimator.ofInt(start, end);
	    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
	         @Override
	         public void onAnimationUpdate(ValueAnimator valueAnimator) {
	            //Update Height
	            int value = (Integer) valueAnimator.getAnimatedValue();
	            ViewGroup.LayoutParams layoutParams = vp.getLayoutParams();
	            if (height) {
	            	layoutParams.height = value;
	            } else {
	            	layoutParams.width = value;
	            }
	            
	            vp.setLayoutParams(layoutParams);
	         }
	    });
	    return animator;
	}
}
