import java.util.Scanner;

class Path {
	
	public int row,col;
	public int cost;
	public Path next;
	
	Path() {}
	
	Path(int row, int col, int cost) {
		this.row=row;
		this.col=col;
		this.cost=cost;
	}
}

class List {
	Path head;
	
	List()
	{
		head=null;
	}
	public void insert(int row, int col, int cost){
		Path n = new Path(row, col, cost);
		Path temp=null;
		if (head==null) {
			head=n;
		}
		else {
			temp=head;
			while(temp.next!=null){
				temp=temp.next;
			}
			temp.next=n;
		}
	}
}

public class EditDistance {
	
	public static Path match(String s1, String s2) {
		
		if (s1.length()==0 || s2.length()==0 || s1==null || s2==null) {
			return null;
		}
		int [][] solution = new int[s1.length()+1][s2.length()+1];
		solution[s1.length()][s2.length()]=0;
		
		
		for (int i=s1.length()-1;i>=0;i--) {
			solution[i][s2.length()] = solution[i+1][s2.length()]+2;
		}
		for (int j=s2.length()-1;j>=0;j--) {
			solution[s1.length()][j] = solution[s1.length()][j+1]+2;
		}
		for (int i=s1.length()-1;i>=0;i--) {
			for (int j=s2.length()-1;j>=0;j--) {
				if (s1.charAt(i)==s2.charAt(j)) {
					solution[i][j] = Math.min((solution[i+1][j+1]+0), Math.min(solution[i+1][j]+2, solution[i][j+1]+2));
				}
				else if (s1.charAt(i) != s2.charAt(j)) {
					solution[i][j] = Math.min((solution[i+1][j+1]+1), Math.min(solution[i+1][j]+2, solution[i][j+1]+2));
				}			 
			} 
		}
		// recovering the optimal alignment
		List alignment =new List();
		//row column indexes of the matrix
		int i=0,j=0; 
		int cost=0;
		//traverses the matrix from [0][0] to [M][N] to recover the alignment
		while (i != s1.length() && j != s2.length()) {
			//moves diagonally if characters same and cost same 
			if (s1.charAt(i)==s2.charAt(j) && solution[i][j]== solution[i+1][j+1]) {
				cost=0;
				alignment.insert(i, j, cost);
				i++;
				j++;
			}
			//moves diagonally if characters different and cost equals to cost+1
			else if (s1.charAt(i)!=s2.charAt(j) && solution[i][j]== solution[i+1][j+1]+1){
				cost=1;
				alignment.insert(i, j, cost);
				i++;
				j++;
			}
			//moves down
			else if (solution[i][j] == solution[i+1][j] + 2){
				cost=2;
				alignment.insert(i, j, cost);
				i++;
			}
			//moves right
			else if (solution[i][j] == solution[i][j+1]+2){
				cost=2;
				alignment.insert(i, j, cost);
				j++;
			}
		}
		return alignment.head; 
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner input=new Scanner(System.in);
		System.out.println("Enter first string!");
		String str1 = input.nextLine();
		System.out.println("Enter second string!");
		String str2 = input.nextLine();
		
		Path alignment = match(str1,str2);
		int total=0;
		/* 
		  Calculating minimum edit distance
		*/
		Path temp = alignment;
		if ( temp==null) {
			System.out.println("Both or one of the two strings is NULL or has length 0!");
			System.exit(0);
		}
		while (temp != null) {
			total+=temp.cost;
			temp=temp.next;
		}
		//Printing minimum edit distance
		System.out.println("Edit distance = " + total);
		
		/*
		 	printing the character from the first string, followed by the paired character
			from the second string, followed by the associated penalty
		*/
		temp=alignment;
		while (temp != null) 
		{
			if (temp.cost !=2) {
				System.out.println(str1.charAt(temp.row) + " " + str2.charAt(temp.col) + " " + temp.cost);
			}
			else {
				System.out.println(str1.charAt(temp.row) + " - " + temp.cost);
			}
			temp=temp.next;
		}
		input.close();
	}
}