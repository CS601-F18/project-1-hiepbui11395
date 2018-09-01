package cs601.project1;

import java.util.ArrayList;
import java.util.Comparator;

public class ListLocation {
	private ArrayList<Location> listLocation;

	public ListLocation(ArrayList<Location> listLocation) {
		super();
		this.listLocation = listLocation;
	}

	/**
	 * Add Location to the list
	 * Check if the list already had input location or not
	 * If had, increase the count by 1. Else add the new ones
	 * 
	 * @param location
	 */
	public void addToList(Location location) {
		int index = 0;
		if((index = listLocation.indexOf(location)) == -1) {
			listLocation.add(location);
		} else {
			Location l = listLocation.get(index);
			l.setCount(l.getCount() + 1);
		}
	}

	/**
	 * 
	 * Sort the list of location by using Count in Location
	 * 
	 */
	public void sortByCount() {

		listLocation.sort(new Comparator<Location>() {

			@Override
			public int compare(Location o1, Location o2) {
				// TODO Auto-generated method stub
				return Integer.compare(o1.getCount(),o2.getCount());
			}
		});
	}
}
