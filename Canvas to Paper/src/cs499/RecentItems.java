package cs499;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class RecentItems {
	
	private static final int MAX_NUMBER = 5;
	
	private Preferences preferences;
	
	private LinkedList<HashMap<String,String>> recentQuizzes = new LinkedList<HashMap<String,String>>();
	
	public RecentItems() {
		setPreferences();
		load();
	}
	
	private void setPreferences() {
		preferences = Preferences.userRoot().node(this.getClass().getName());
	}
	
	
	public LinkedList<HashMap<String,String>> getRecentQuizzes(){
		return recentQuizzes;
	}
	
	public void addToRecent(Quiz quiz) {
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("quiz_id", Integer.toString(quiz.getId()));
		map.put("quiz_name", quiz.getName());
		
		if(!recentQuizzes.contains(map)) {
			if(recentQuizzes.size() >= MAX_NUMBER) {
				removeOldest();
			}
			recentQuizzes.add(map);
		}		
		
		store();
	}
	
	private void store () {
		
		for(HashMap<String,String> map: recentQuizzes) {
			preferences.putInt(map.get("quiz_name"), Integer.parseInt(map.get("quiz_id")));
		}		
	}
	
	private void removeOldest() {
		HashMap<String,String> oldest = recentQuizzes.pop();
		try {
			if(preferences.nodeExists(oldest.get("quiz_name"))) {
				preferences.node(oldest.get("quiz_name")).removeNode();
			}
		} catch (BackingStoreException e) {
			e.printStackTrace();
		}
	}
	
	// private void removeAll() {
	// 	try {
	// 		preferences.clear();
	// 	} catch (BackingStoreException e) {
	// 		e.printStackTrace();
	// 	}
	// }
	
	private void load() {
		
		try {
			for(String key:preferences.keys()) {
				HashMap<String,String> map = new HashMap<String,String>();
				map.put("quiz_name", key);
				map.put("quiz_id", Integer.toString(preferences.getInt(key, 0)));
				recentQuizzes.add(map);
			}
		} catch (BackingStoreException e) {
			e.printStackTrace();
		}
		while(recentQuizzes.size() > MAX_NUMBER) {
			removeOldest();
		}
	}
}
