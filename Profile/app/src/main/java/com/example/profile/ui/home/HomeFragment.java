package com.example.profile.ui.home;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.profile.JSONParser;
import com.example.profile.Profile;
import com.example.profile.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

	Button btnDownload;
	ListView lv;
	ArrayList<Profile> data = new ArrayList<>();

	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.fragment_home, container, false);

		lv = root.findViewById(R.id.lv_home);
		btnDownload = root.findViewById(R.id.btn_download_home);

		btnDownload.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// download
				MyDownload download = new MyDownload(HomeFragment.this.getActivity());
				download.execute();

			}
		});


		return root;
	}


	class MyDownload extends AsyncTask {
		Context context;
		AlertDialog alert;

		public MyDownload(Context context) {
			this.context = context;
		}

		@Override
		protected Object doInBackground(Object[] objects) {

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
//      cant update ui here -> exception , use onProgressUpdate instead
//			btnDownload.setText("50% done ...");
			publishProgress(1);

			// server ip
			String ip = "192.168.1.108";
//			String ip = "127.0.0.1";
			// avd : 10.0.2.2
			String url = "http://" + ip + "/servicephp/get_all_user.php";
			JSONObject response = JSONParser.makeRequest(url);
			Log.e("TAG", "doInBackground: "+response );

			try {
				int s = response.getInt("success");
				if (s == 0) {
					String message = response.getString("message");
				} else {
					JSONArray jsonArray = response.getJSONArray("profil");

					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject object = jsonArray.getJSONObject(i);
						String n = object.getString("nom");
						String p = object.getString("prenom");
						String ps = object.getString("pseudo");

						data.add(new Profile(n, p, ps));
					}
				}

			} catch (JSONException e) {
				e.printStackTrace();
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			publishProgress(2);

			return null;
		}

		@Override
		protected void onProgressUpdate(Object[] values) {

			if (values[0] == (Object) 1) btnDownload.setText("50% done ...");
			else btnDownload.setText("100% done ...");

		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// on the ui thread
			AlertDialog.Builder dialog = new AlertDialog.Builder(context);
			dialog.setTitle("Download").setMessage("Please Wait ...");
			alert = dialog.create();
			alert.show();

		}

		@Override
		protected void onPostExecute(Object o) {
			super.onPostExecute(o);
			// on the ui thread
			alert.dismiss();

			ArrayAdapter adapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, data);
			lv.setAdapter(adapter);

		}
	}
}