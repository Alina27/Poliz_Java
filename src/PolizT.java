import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;


public class PolizT {
	
	private File file;
	private Stack<String> stack1 = new Stack<String>();
	private Stack<String> stack2 = new Stack<String>();
	private Stack<Integer> pos = new Stack<Integer>();
	
	public PolizT(File file){
		this.file = file;
	}

	private boolean moreOrEqual(String a, String b){
		if(a.equals("+")){
			if(b.equals("+")){return true;}
			else if(b.equals("-")){return true;}
			else if(b.equals("*")){return true;}
			else if(b.equals("/")){return true;}
			else if(b.equals("^")){return true;}
			
		}
		else if(a.equals("-")){
			if(b.equals("-")){return true;}
			else if(b.equals("+")){return true;}
			else if(b.equals("*")){return true;}
			else if(b.equals("/")){return true;}
			else if(b.equals("^")){return true;}
		}
		else if(a.equals("*")){
			if(b.equals("*")){return true;}
			else if(b.equals("/")){return true;}
			else if(b.equals("+")){return false;}
			else if(b.equals("-")){return false;}
			else if(b.equals("^")){return true;}
		}
		else if(a.equals("/")){
			if(b.equals("/")){return true;}
			else if(b.equals("*")){return true;}
			else if(b.equals("+")){return false;}
			else if(b.equals("-")){return false;}
			else if(b.equals("^")){return true;}
		}
		else if(a.equals("^")){
			if(b.equals("^")){return true;}
			else if(b.equals("*")){return false;}
			else if(b.equals("+")){return false;}
			else if(b.equals("-")){return false;}
			else if(b.equals("/")){return false;}
		}
		return false;
	}

	// help methods
	
   private ArrayList<String> transformToString(Stack<String> stack){
		ArrayList<String> res = new ArrayList<String>();
		while(!stack.isEmpty()){
		     String v = stack.pop();
		     if(!(v.equals("("))){
		    	 res.add(v + " ");
		     }
		}
		return res;
	}

   private static String reverse(ArrayList<String> list) {
	   if (list.size() == 1) {
	       return " " +list.get(0);
	   }
	   else {
	       return " "+ list.remove(list.size() - 1) + reverse(list);
	   } 
	 }
   
   public Stack<String> trans(File file) throws FileNotFoundException{
	   
	   Scanner sc = new Scanner(file);
	   
	   int counter = 2;
	   
	   while(sc.hasNext()){
		   
		   String c = sc.next();
		   
		    if (c.equals(":=")){
				stack2.push(c);
			} 
		    
			else if(c.equals("+")){
				   while(moreOrEqual(c, stack2.peek())){
					   stack1.push(stack2.pop());
					   continue;
				   }
					   stack2.push(c);
			}
			
			else if(c.equals("-")){ 
				 while(moreOrEqual(c, stack2.peek())){
					   stack1.push(stack2.pop());
					   continue;
				   }
					   stack2.push(c);
				}
			
			else if(c.equals("*")){ 
				 while(moreOrEqual(c, stack2.peek())){
					   stack1.push(stack2.pop());
					   continue;
				   }
					   stack2.push(c);
				}
			
			else if(c.equals("/")){ 
				 while(moreOrEqual(c, stack2.peek())){
					   stack1.push(stack2.pop());
					   continue;
				   }
					   stack2.push(c);
				}
			
			else if(c.equals("^")){
				   while(moreOrEqual(c, stack2.peek())){
					   stack1.push(stack2.pop());
				   }
				   	  stack2.push(c);
				   
				}
		    
			else if(c.equals("(")){
				stack2.push(c);
			    pos.push(stack2.size());	
			}
			
			else if(c.equals(")")){
				int count = pos.pop();
				for(int i= 0; i < count-1; i++){
				    if(!stack2.peek().equals("(")){
					  stack1.push(stack2.peek());
					  stack2.pop();
				    }
				}
			}
		    
			else if(c.equals("[")){
				stack2.push(c);
			}
		    
			else if(c.equals(",")){
				String str = "P";
				if(counter == 2){
					stack2.push(str);
					counter+=1;
				}
				else if(counter > 2){
					while(!(stack2.peek().equals("P"))){
						stack1.push(stack2.pop());
						continue;
					}
					counter+=1;
					stack2.push(str);
				}
			}  
		    
			else if(c.equals("]")){
				while(!stack2.peek().equals("[")){
					if(!stack2.peek().equals("P")){
						stack1.push(stack2.pop());
					}
					else {
						stack2.pop();
					}
				}
			   String s = "" + counter;	
			   stack1.push(s);
			   stack1.push(c);
			}
		   
			else {
				stack1.push(c);
			}
		   
	   }
	   
	   if(!sc.hasNext()){
		   while(!stack2.isEmpty()){
			   if(!stack2.peek().equals("[")){
				   stack1.push(stack2.pop()); 
			   }
			   else {
					stack2.pop();
				}
		   }
		}
	   
	   
	   return stack1;
   }
   
	public static void main(String[] args) throws FileNotFoundException {
		
		File file = new File("C:/Users/Alina/Desktop/hello3.txt");
		PolizT tp = new PolizT(file);
		ArrayList<String> res = new ArrayList<String>();
		res = tp.transformToString(tp.trans(file));
		System.out.println("Poliz: " + reverse(res));

	}

}
