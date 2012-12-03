package eu.lighthouselabs.obd.reader.activity;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import eu.lighthouselabs.obd.commands.ObdCommand;
import eu.lighthouselabs.obd.commands.ObdMultiCommand;
import eu.lighthouselabs.obd.reader.R;
import eu.lighthouselabs.obd.reader.config.ObdConfig;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class CommandActivity extends Activity implements
		OnItemSelectedListener {

	final private ArrayList<ObdCommand> commands = ObdConfig.getCommands();
	private Map<String, ObdCommand> cmdMap = null;
	protected static final UUID MY_UUID = UUID
			.fromString("00001101-0000-1000-8000-00805F9B34FB");
	private Handler handler = null;
	private SharedPreferences prefs = null;
	private final static String NO_SELECTION_TXT = "Choose Command to Run...";

	public void onCreate(Bundle savedInstance) {
		super.onCreate(savedInstance);
		prefs = PreferenceManager
				.getDefaultSharedPreferences(CommandActivity.this);
		setContentView(R.layout.command);
		Spinner cmdSpin = (Spinner) findViewById(R.id.command_spinner);
		cmdSpin.setOnItemSelectedListener(this);
		cmdMap = new HashMap<String, ObdCommand>();
		ArrayList<String> cmdStrs = new ArrayList<String>();
		cmdStrs.add(NO_SELECTION_TXT);
		String[] configCmdStrs = ConfigActivity
				.getReaderConfigCommands(prefs);
//		ObdMultiCommand configCmd = new ObdMultiCommand(configCmdStrs,
//				"Configure Reader", "string", "string");
//		cmdMap.put(configCmd.getDesc(), configCmd);
//		cmdStrs.add(configCmd.getDesc());
		ObdMultiCommand configCmd = new ObdMultiCommand();
		for (ObdCommand cmd : commands) {
			cmdMap.put(cmd.getName(), cmd);
			cmdStrs.add(cmd.getName());
		}
		ArrayAdapter<String> adapt = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, cmdStrs);
		cmdSpin.setAdapter(adapt);
		handler = new Handler();
	}

	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		Spinner cmdSpin = (Spinner) findViewById(R.id.command_spinner);
		String cmdDesc = cmdSpin.getSelectedItem().toString();
		if (NO_SELECTION_TXT.equals(cmdDesc)) {
			return;
		}
		ObdCommand cmd = cmdMap.get(cmdDesc);
		ObdReaderCommandActivityWorkerThread worker = null;
		try {
//			worker = new ObdReaderCommandActivityWorkerThread(
//					ObdConnectThread.getCopy(cmd));
			worker = new ObdReaderCommandActivityWorkerThread(
					getCopy(cmd));
		} catch (Exception e) {
			Toast.makeText(this, "Error copying command: " + e.getMessage(),
					Toast.LENGTH_LONG).show();
			return;
		}
		worker.start();
		cmdSpin.setSelection(0);
	}
	
	public static ObdCommand getCopy(ObdCommand cmd)
			throws IllegalArgumentException, SecurityException,
			InstantiationException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		return cmd.getClass().getConstructor(cmd.getClass()).newInstance(cmd);
	}

	public void onNothingSelected(AdapterView<?> arg0) {
	}

	public void showMessage(final String message) {
		final Activity act = this;
		handler.post(new Runnable() {
			public void run() {
				Toast.makeText(act, message, Toast.LENGTH_LONG).show();
			}
		});
	}

	private void setText(final String value, final boolean clear) {
		handler.post(new Runnable() {
			public void run() {
				TextView txtView = (TextView) findViewById(R.id.command_result_text);
				String curr = (String) txtView.getText();
				if (curr == null || clear || "".equals(curr)) {
					curr = value;
				} else {
					curr = curr + "\n" + value;
				}
				txtView.setText(curr);
			}
		});
	}

	public void logMsg(final String msg) {
		setText(msg, false);
	}

	private class ObdReaderCommandActivityWorkerThread extends Thread {
		ObdCommand cmd = null;

		private ObdReaderCommandActivityWorkerThread(ObdCommand cmd) {
			this.cmd = cmd;
		}

		public void run() {
			/*String devString = prefs.getString(
					ConfigActivity.BLUETOOTH_LIST_KEY, null);
			final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter
					.getDefaultAdapter();
			if (mBluetoothAdapter == null) {
				showMessage("This device does not support bluetooth");
				return;
			}
			if (devString == null || "".equals(devString)) {
				showMessage("No bluetooth device selected");
				return;
			}
			BluetoothDevice dev = mBluetoothAdapter.getRemoteDevice(devString);
			double ve = ConfigActivity.getVolumetricEfficieny(prefs);
			double ed = ConfigActivity.getEngineDisplacement(prefs);
			boolean imperialUnits = prefs.getBoolean(
					ConfigActivity.IMPERIAL_UNITS_KEY, false);
			final ObdCommandConnectThread thread = new ObdCommandConnectThread(
					dev, CommandActivity.this, cmd, ed, ve,
					imperialUnits);
			thread.setVolumetricEfficiency(ve);
			thread.setEngineDisplacement(ed);
			setText(String.format("Running %s...", cmd.getDesc()), true);
			try {
				thread.start();
				thread.join();
			} catch (Exception e1) {
				showMessage("Error getting connection: " + e1.getMessage());
			}
			String res = null;
			if (!thread.getResults().containsKey(cmd.getDesc())) {
				res = "Didn't get a result.";
			} else {
				res = thread.getResults().get(cmd.getDesc());
			}
			logMsg("Formated result: " + res);*/
		}
	}
}
