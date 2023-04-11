package mod.tsd.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.drawable.Drawable;

public class SketchwareNotification {
	public Context context;
	public String NotificationChannelName;	
	public String NotificationChannelDescription;
	public NotificationManager NotifyManager;
	public androidx.core.app.NotificationCompat.Builder NotificationBuilder;
	
	public SketchwareNotification(Context c,String ncn,String ncd) {
		context = c;
		NotificationChannelName = ncn;
		NotificationChannelDescription = ncd;
		NotificationBuilder = new androidx.core.app.NotificationCompat.Builder(context, NotificationChannelName);
	}
	
	public void initNotificationManager(){
		NotifyManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		createNotificationChannelIfNotExists();
	}
	
	public void createNotificationChannelIfNotExists(){
    	if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
	    	NotificationChannel ntc = new NotificationChannel(NotificationChannelName, NotificationChannelDescription, NotificationManager.IMPORTANCE_HIGH);
	        NotifyManager.createNotificationChannel(ntc);
	    }
	}
	
	public void setIcon(int icon) {
		NotificationBuilder.setSmallIcon(icon);
	}
	
	public void setTitle(String title) {
		NotificationBuilder.setContentTitle(title);
	}
	
	public void setDescription(String description) {
		NotificationBuilder.setContentText(description);
	}
	
	public void setCancelable(boolean cancelable) {
		if (cancelable) {
			NotificationBuilder.setOngoing(false);
		} else {
			NotificationBuilder.setOngoing(true);
		}
	}
	
	public void setProgressDisabled() {
		NotificationBuilder.setProgress(0, 0, false);
	}
	
	public void setProgress(int min,int max,boolean continueLoading) {
		NotificationBuilder.setProgress(min,max,continueLoading);
	}
	
	public void showNotification(int notificationID) {
		NotifyManager.notify(notificationID,NotificationBuilder.build());
	}
	
	public void setSilent(boolean silent) {
		NotificationBuilder.setSilent(silent);
	}
	
	public void dismissNotification(int id) {
		if (NotifyManager != null) {
			NotifyManager.cancel(id);
		}
	}
}