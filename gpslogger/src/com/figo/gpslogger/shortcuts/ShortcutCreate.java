/*
*    This file is part of GPSLogger for Android.
*
*    GPSLogger for Android is free software: you can redistribute it and/or modify
*    it under the terms of the GNU General Public License as published by
*    the Free Software Foundation, either version 2 of the License, or
*    (at your option) any later version.
*
*    GPSLogger for Android is distributed in the hope that it will be useful,
*    but WITHOUT ANY WARRANTY; without even the implied warranty of
*    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*    GNU General Public License for more details.
*
*    You should have received a copy of the GNU General Public License
*    along with GPSLogger for Android.  If not, see <http://www.gnu.org/licenses/>.
*/

package com.figo.gpslogger.shortcuts;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import com.figo.gpslogger.R;

public class ShortcutCreate extends Activity
{
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);


        AlertDialog alert;
        final CharSequence[] items = {getString(R.string.shortcut_start), getString(R.string.shortcut_stop)};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.shortcut_pickaction);
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int item)
            {
                Intent shortcutIntent;
                String shortcutLabel;

                if (item == 0)
                {
                    shortcutIntent = new Intent(getApplicationContext(), ShortcutStart.class);
                    shortcutLabel = getString(R.string.shortcut_start);
                }
                else
                {
                    shortcutIntent = new Intent(getApplicationContext(), ShortcutStop.class);
                    shortcutLabel = getString(R.string.shortcut_stop);
                }

                Intent.ShortcutIconResource iconResource = Intent.ShortcutIconResource.fromContext
                        (getApplicationContext(), R.drawable.gpsloggericon2);
                Intent intent = new Intent();
                intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
                intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, shortcutLabel);
                intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconResource);
                setResult(RESULT_OK, intent);

                dialog.dismiss();
                finish();
            }
        });

        alert = builder.create();
        alert.show();

    }
}