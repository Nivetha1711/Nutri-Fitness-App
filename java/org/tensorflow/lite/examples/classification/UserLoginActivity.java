package org.tensorflow.lite.examples.classification;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserLoginActivity extends Activity  {

	Connection conn;
	EditText username,password,hostIP;
	Button signin,signup,adm;
	String user,pass,user1,pass1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		signin=(Button)findViewById(R.id.loginbtn);
		signup=(Button)findViewById(R.id.regbtn);

		adm=(Button)findViewById(R.id.admbtn);
		
		username=(EditText)findViewById(R.id.edtloginusername);
		password=(EditText)findViewById(R.id.edtloginpassword);
//		conn=CONN();
		
		
		
        
		signin.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				user=username.getText().toString();
				pass=password.getText().toString();
				
				SharedPreferences.Editor editor =getSharedPreferences("username",Context.MODE_PRIVATE).edit();
		        editor.putString("username",user);
		        editor.commit();
		        editor.apply();
				
				//SharedPreferences.Editor editor =getSharedPreferences("username",Context.MODE_PRIVATE).edit();
                //editor.putString("username",user);
                //editor.commit();
               // editor.apply();

					
				new QuerySQL().execute(user,pass);
				
			}
			
		});
		signup.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				startActivity(new Intent(UserLoginActivity.this,RegisterActivity.class));		
				
			}
			
		});
		adm.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				startActivity(new Intent(UserLoginActivity.this,AdminLoginActivity.class));

			}

		});
		
	}

	
	
	public class QuerySQL extends AsyncTask<String, Void, Boolean> {

		ProgressDialog pDialog ;
		Exception error;
		ResultSet rs;
	    @Override
	    protected void onPreExecute() {
	        super.onPreExecute();
	        
	        pDialog = new ProgressDialog(UserLoginActivity.this);
	        pDialog.setTitle("Authentication");
	        pDialog.setMessage("Verifying your credentials...");
	        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	        pDialog.setIndeterminate(false);
	        pDialog.setCancelable(false);
	        pDialog.show();
	    }

	    @Override
	    protected Boolean doInBackground(String... args) {
	    	
	    	user1 = new String(args[0]);
	    	pass1 = new String(args[1]);
	    	
	    	
			
			try {
				
				
				Class.forName("com.mysql.jdbc.Driver");
				 conn = DriverManager.getConnection("jdbc:mysql://mysql-75344-0.cloudclusters.net:18880/foodwheelchairuser","admin","cU5zYktH");		
			} catch (SQLException se) {
				Log.e("ERRO1",se.getMessage());
			} catch (ClassNotFoundException e) {
				Log.e("ERRO2",e.getMessage());
			} catch (Exception e) {
			    Log.e("ERRO3",e.getMessage());
			}
			

			try {
				
			
				String COMANDOSQL="select * from userdetails where username='"+user1+"' && password='"+pass1+"'";
				Statement statement = conn.createStatement();
				rs = statement.executeQuery(COMANDOSQL);
			if(rs.next()){
				
				
				
				return true;
			}

			return false;
				
				// Toast.makeText(getBaseContext(),
				// "Successfully Inserted.", Toast.LENGTH_LONG).show();
			} catch (Exception e) {
				error = e;
				return false;
//				Toast.makeText(getBaseContext(),"Successfully Registered...", Toast.LENGTH_LONG).show();
			}


	    }

	    @SuppressLint("NewApi")
		@Override
	    protected void onPostExecute(Boolean result1) {
	    	pDialog.dismiss ( ) ;
	    	if(result1)
	    	{
                
			
					
//					System.out.println("ELSE(JSON) LOOP EXE");
					try {// try3 open
						
						Intent intent=new Intent(UserLoginActivity.this, UserActivity.class);
						//intent.putExtra("latitude", lati);
						//intent.putExtra("longitude", longi);
						//intent.putExtra("loginuser", user1);
					
						startActivity(intent);			
						
					} catch (Exception e1) {
						Toast.makeText(getBaseContext(), e1.toString(),
								Toast.LENGTH_LONG).show();

					}					
				
            
	    	}else
	    	{
	    		if(error!=null)
	    		{
	    			Toast.makeText(getBaseContext(),error.getMessage().toString() ,Toast.LENGTH_LONG).show();
	    		}
	    		else
	    		{
	    			Toast.makeText(getBaseContext(),"Check your credentials!!!" ,Toast.LENGTH_LONG).show();
	    		}
	    	}
	    	super.onPostExecute(result1);
	    }
	}
	
	
	


}
