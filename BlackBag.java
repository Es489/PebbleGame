
import java.util.ArrayList;



public class BlackBag {
    private ArrayList<Integer> pebbles;
    private BlackBag.WhiteBag pair;
    private String bb_name; 
    
    public BlackBag (ArrayList<Integer> i, String n) {
    		BlackBag.this.pebbles = i;
    		BlackBag.this.bb_name = n;
    		
    }
   
    public String getName() {
    	      return BlackBag.this.bb_name;
    }
	public  ArrayList <Integer> getPebbles(){
		return BlackBag.this.pebbles;
	}
	
    public BlackBag.WhiteBag getPair(){
    	      return BlackBag.this.pair;
     }
    /***
     * This method assigns White bag reference to Black bag, to establish pair connection.
     * @param o - White bag reference
     */
	 public void setPair(BlackBag.WhiteBag o) {
		 BlackBag.this.pair = o;
	 }

	
	  class WhiteBag  {
	    private ArrayList<Integer> wb_pebbles = new ArrayList<Integer>();
	    private String wb_name;
	    
	    
	    public WhiteBag(String n) {
	    	     this.wb_name = n;
	    }
	    /***
	     * This method adds all white bag pebbles to its paired black bag, and 
	     * empty white bag array of pebbles;
	     */
	    public  void fillBlackBag() {
		   BlackBag.this.pebbles.addAll(this.wb_pebbles);
		   this.wb_pebbles.removeAll(wb_pebbles);
		   
	   }

		public String getName() {
			return this.wb_name;
		}
		
		public ArrayList<Integer> getPebbles() {
			return this.wb_pebbles;
		}
       
	    
	}



}
