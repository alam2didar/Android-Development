/**AlarmReceiver.java  
 * extends BroadcastReceiver
 * Author:      Didar Alam, m150084@usna.edu
 * Date:        12 APR 2014
 * Description:  Receives when alarm is triggered and launches the Intent Service.
 *
 */

package edu.usna.cs.alam_project;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class AlarmReceiver extends BroadcastReceiver{
	
	 @Override
	    public void onReceive(Context context, Intent intent) {
	        Log.i("ALAM", "Alarm triggered, starting service!!");
				
		Intent serviceIntent = new Intent(context, MyIntentService.class);
		    context.startService(serviceIntent);
		    
		    
	    }
	
}
