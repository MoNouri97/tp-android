package com.example.profile.ui.gallery;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.profile.JSONParser;
import com.example.profile.R;
import com.example.profile.ui.home.HomeFragment;

import org.json.JSONException;
import org.json.JSONObject;

public class AddFragment extends Fragment {

	private Button btnAdd, btnInit;
	private EditText etFName,etLName,etPseudo;


	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.fragment_add, container, false);

		/*
		 *todo:
		 *  add form
		 * 2 btn , add , init
		 * add : asynctask server
		 *
		*/
		// getting refs
		btnAdd = root.findViewById(R.id.btn_add);
		btnInit = root.findViewById(R.id.btn_init);
		etFName = root.findViewById(R.id.et_fname);
		etLName = root.findViewById(R.id.et_lname);
		etPseudo = root.findViewById(R.id.et_pseudo);

		// listeners
		btnInit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// todo :
			}
		});

		btnAdd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				AddFragment.AddToDb addToDb = new AddToDb(AddFragment.this.getActivity(),
				                                          etFName.getText().toString(),
				                                          etLName.getText().toString(),
				                                          etPseudo.getText().toString()
				                                          );
				addToDb.execute();

				etFName.setText("");
				etLName.setText("");
				etPseudo.setText("");

			}
		});
		return root;
	}


	class AddToDb extends AsyncTask {
		String fname,lname,pseudo,message;
		Context context;

		public AddToDb( Context context,String fname, String lname, String pseudo) {
			this.fname = fname;
			this.lname = lname;
			this.pseudo = pseudo;
			this.context = context;
		}

		@Override
		protected Object doInBackground(Object[] objects) {

			// server ip
//			String ip = "127.0.0.1";
			String ip = "192.168.1.108";

			// avd : 10.0.2.2
			String url = String.format("http://%s/servicephp/add_user.php?nom=%s&prenom=%s&pseudo=%s",
			                           ip,
			                           fname,
			                           lname,
			                           pseudo);
			JSONObject response = JSONParser.makeRequest(url);

			try {
				int s = response.getInt("success");
				if (s == 0) {
					message = response.getString("message");
				} else {
					message= "Successfully Added ...";

				}

			} catch (JSONException e) {
				e.printStackTrace();
			}


			return null;
		}

		@Override
		protected void onPostExecute(Object o) {
			Toast.makeText(context , message, Toast.LENGTH_SHORT).show();
		}
	}
}