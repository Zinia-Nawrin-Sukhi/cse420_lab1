import java.util.Scanner;
public class Lab{
  public static void main(String []args){
     Scanner sc = new Scanner (System.in);
     System.out.println(" enter regex number");
     int p = sc.nextInt();
     String []regex = new String[p];
     for(int i=1; i< p+1 ; i++){
       //System.out.println("enter regular expression");
       //System.out.println(i);
       regex[i]= sc.nextLine(); 
       //Pattern r = Pattern.compile(regex);
       System.out.println(i);
     }
  }
}