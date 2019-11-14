package listADT;

import java.util.Comparator;
import entities.Invoice;

public class TotalComparator implements Comparator<Invoice>{

	@Override
	public int compare(Invoice o1, Invoice o2) {
		//o1 total less than o2 --> -1
		if(o1.computeTotal() < o2.computeTotal() || o2 == null) return -1;
		//o1 total greater than o2 --> 1
		else if(o1.computeTotal() > o2.computeTotal()) return 1;
		//o1 total equals o2 --> 0
		else return 0;
	}
}