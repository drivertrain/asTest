package com.theboyz.ffs;

/*********************************************************
* ---LOW PRIORITY---
*
* Might Develop this into a dialog that appears after save changes so
* the user can visually see who is being added or dropped
*************************************************************/
import android.app.Activity;
import android.app.Dialog;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.view.View;

import org.apache.http.message.BasicNameValuePair;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PlayerDialog extends Dialog implements View.OnClickListener
{
    private Activity parent;
    ArrayList<BasicNameValuePair> todo;

    public PlayerDialog(Activity parent, ArrayList<BasicNameValuePair> todo)
    {
        super(parent);
        this.parent = parent;
        this.todo = todo;
    }

    @Override
    public void onProvideKeyboardShortcuts(List<KeyboardShortcutGroup> data, @Nullable Menu menu, int deviceId)
    {
        return;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture)
    {
        return;
    }

    @Override
    public void onClick(View v)
    {

    }
}
