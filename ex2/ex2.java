// Compilation avec la commande "javac ex2.java"
// Execution avec la commande "java ex2" + arguments




public class ex2 {
	private static  String msg = null;
	private static  String key = null;
 	private static  String op = null;
	private static  String iv = null;
	private static  String mode = null;
	private static  int r = 0;
	private static  String result = "";
	static final int taille_bloc= 8;



    

    
    
  
    
//---------------------------------
	private static void ecb() {
		// À compléter
		
               
		
		System.out.println("ici ecb, le résultat est : " + result );
	}

//--------------------------------
	private static void cbc() {
			// À compléter
	       
	        
		System.out.println("ici cbc,  le résultat est : " + result );
	}

//--------------------------------
	private static void cfb() {
			// À compléter
	    
	        
		System.out.println("ici cfb,  le résultat est : " + result );
	}

//--------------------------------
	private static void ofb() {
		// À compléter
	     
	         
		 
	        
		System.out.println("ici ofb,  le résultat est : " + result );
	}

//--------------------------------
	private static void ctr() {
	// À compléter
		// À compléter

	        
		System.out.println("ici ctr,  le résultat est : " + result );
	}







// Main function -------------------------------------------------------
	public static void main(String[] args) throws Exception {
		
		// récupération des arguments
		for (int i = 0; i < args.length; i=i+2) {
			switch (args[i]){
				case "-msg" : msg=args[i+1];
				         break;
				case "-key" : key=args[i+1];
				 	break;
				case "-op"  : op=args[i+1];
				        break;
				case "-mode"  : mode=args[i+1];
					break;
				case "-iv"  : iv=args[i+1];
				        break;
				case "-r"  :  r=Integer.parseInt(args[i+1]);
				        break;
				default:
					System.out.println("arguments non valides");
					return;

			}
			
		 }
		 // Executer le mode demandé

			switch (mode){
				case "ECB" : ecb();
				         break;
				case "CBC" : cbc();
				        break;
				case "CTR"  : ctr();
				        break;
				case "OFB"  : ofb();
					     break;
				case "CFB"  : cfb();
				        break;
				        
				 default:
					System.out.println("mode non valide");
					return;


			}

             }
}
