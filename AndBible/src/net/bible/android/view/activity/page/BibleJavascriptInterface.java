package net.bible.android.view.activity.page;

import net.bible.android.control.ControlFactory;
import net.bible.android.control.PassageChangeMediator;
import net.bible.android.control.page.splitscreen.SplitScreenControl;
import android.util.Log;
import android.webkit.JavascriptInterface;

public class BibleJavascriptInterface {

	private boolean notificationsEnabled = false;
	
	private VerseCalculator verseCalculator;
	
	private SplitScreenControl splitScreenControl = ControlFactory.getInstance().getSplitScreenControl();
	
	private static final String TAG = "BibleJavascriptInterface";
	
	public BibleJavascriptInterface(VerseCalculator verseCalculator) {
		this.verseCalculator = verseCalculator;
	}
	
	@JavascriptInterface 
	public void onLoad() {
		Log.d(TAG, "onLoad from js");
	}

	@JavascriptInterface
	public void onScroll(int newYPos) {
		// do not try to change verse while the page is changing - can cause all sorts of errors e.g. selected verse may not be valid in new chapter and cause chapter jumps
		if (notificationsEnabled && !PassageChangeMediator.getInstance().isPageChanging() && !splitScreenControl.isSeparatorMoving()) {
			verseCalculator.newPosition(newYPos);
		}
	}
	
	@JavascriptInterface
	public void clearVersePositionCache() {
		Log.d(TAG, "clear verse positions");
		verseCalculator.init();
	}

	@JavascriptInterface
	public void registerVersePosition(String verseId, int offset) {
		verseCalculator.registerVersePosition(Integer.valueOf(verseId), offset);
	}
	
	@JavascriptInterface
	public void log(String msg) {
		Log.d(TAG, msg);
	}

	public void setNotificationsEnabled(boolean notificationsEnabled) {
		this.notificationsEnabled = notificationsEnabled;
	}
}
