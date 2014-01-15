package magnetix.uitestplugin;

import org.json.JSONArray;
import org.json.JSONException;


import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;

import android.app.Activity;
import android.graphics.Color;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.RelativeLayout;

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
		RelativeLayout lContainerLayout = new RelativeLayout(ctx);
		lContainerLayout.setBackgroundColor(Color.BLUE);
		
		lContainerLayout.setLayoutParams(new RelativeLayout.LayoutParams( LayoutParams.MATCH_PARENT , LayoutParams.MATCH_PARENT ));

		// Custom view
		Button mCustomView = new Button(ctx);
		mCustomView.setText("Test2");
		LayoutParams lButtonParams = new RelativeLayout.LayoutParams( LayoutParams.WRAP_CONTENT , LayoutParams.WRAP_CONTENT);
		((android.widget.RelativeLayout.LayoutParams) lButtonParams).addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		mCustomView.setLayoutParams(lButtonParams);
		lContainerLayout.addView(mCustomView);

		// Adding full screen container
		ctx.addContentView(lContainerLayout, new LayoutParams( LayoutParams.MATCH_PARENT , LayoutParams.MATCH_PARENT ) );
		
		callbackContext.success(message); // Thread-safe.
	}
}
