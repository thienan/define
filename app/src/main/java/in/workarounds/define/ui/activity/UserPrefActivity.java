package in.workarounds.define.ui.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.StringRes;
import android.view.View;
import android.widget.RadioButton;

import in.workarounds.define.R;
import in.workarounds.define.util.PrefUtils;

/**
 * Created by madki on 13/10/15.
 */
public class UserPrefActivity extends BaseActivity implements View.OnClickListener {
    public static final int OPTION_SILENT   = 1;
    public static final int OPTION_PRIORITY = 2;
    public static final int OPTION_DIRECT   = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_prefs);

        findViewById(R.id.rb_option_direct).setOnClickListener(this);
        findViewById(R.id.rb_option_priority).setOnClickListener(this);
        findViewById(R.id.rb_option_silent).setOnClickListener(this);
    }

    @Override
    protected String getToolbarTitle() {
        return "Settings";
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.rb_option_silent ||
                id == R.id.rb_option_direct ||
                id == R.id.rb_option_priority ) {
            boolean checked = ((RadioButton) v).isChecked();
            if(checked) {
                switch (id) {
                    case R.id.rb_option_direct:
                        PrefUtils.setNotifyMode(OPTION_DIRECT, this);
                        copyTutorialText(R.string.notify_tutorial_direct);
                        break;
                    case R.id.rb_option_priority:
                        PrefUtils.setNotifyMode(OPTION_PRIORITY, this);
                        copyTutorialText(R.string.notify_tutorial_priority);
                        break;
                    case R.id.rb_option_silent:
                        PrefUtils.setNotifyMode(OPTION_SILENT, this);
                        copyTutorialText(R.string.notify_tutorial_silent);
                        break;
                }
            }
        }
    }

    private void copyTutorialText(@StringRes int stringRes){
        String notifyTutorial = getString(stringRes);

        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Define", notifyTutorial);
        clipboard.setPrimaryClip(clip);
    }

    @IntDef({OPTION_DIRECT, OPTION_PRIORITY, OPTION_SILENT})
    public @interface NotifyMode {
    }
}
