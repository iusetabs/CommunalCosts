import java.util.*;
public class hashTest{
    public static void main(String [] args){
        HashMap<String, String> members = new HashMap<>();
        members.put("Ordinary", "1@dcu.ie");
        members.put("Ordinary", "2@dcu.ie");
        members.put("Contributor", "3@dcu.ie");
        members.put("Contributor", "4@dcu.ie");
        members.put("Administrator", "5@dcu.ie");
        members.put("Administrator", "6@dcu.ie");   
        
      Set set = members.entrySet();
      
      // Get an iterator
      Iterator i = set.iterator();
      
      // Display elements
      while(i.hasNext()) {
         Map.Entry me = (Map.Entry)i.next();
         System.out.print(me.getKey() + ": ");
         System.out.println(me.getValue());
      }
      System.out.println();
    }   
}